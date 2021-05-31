package com.atguigu.yygh.hosp.service.impl;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/5/2710:57
 */

import com.alibaba.fastjson.JSONObject;
import com.atguigu.hospital.util.ResultCodeEnum;
import com.atguigu.hospital.util.YyghException;
import com.atguigu.yygh.hosp.repository.ScheduleRepository;
import com.atguigu.yygh.hosp.service.DepartmentService;
import com.atguigu.yygh.hosp.service.HospitalService;
import com.atguigu.yygh.hosp.service.ScheduleService;
import com.atguigu.yygh.model.hosp.BookingRule;
import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.model.hosp.Schedule;
import com.atguigu.yygh.vo.hosp.BookingScheduleRuleVo;
import com.atguigu.yygh.vo.hosp.ScheduleOrderVo;
import com.atguigu.yygh.vo.hosp.ScheduleQueryVo;
import org.joda.time.format.DateTimeFormat;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.SneakyThrows;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/5/27 10:57
 * @updateDate 2021/5/27 10:57
 **/
@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private MongoTemplate mongoTemplate = null;
    @Autowired
    private DepartmentService departmentService = null;
    @Autowired
    private ScheduleRepository scheduleRepository = null;
    @Autowired
    private HospitalService hospitalService = null;

    //上传排班接口
    @Override
    public void save(Map<String, Object> paramMap) {

        String paramMapString = JSONObject.toJSONString(paramMap);
        Schedule schedule = JSONObject.parseObject(paramMapString, Schedule.class);

        Criteria criteria = Criteria.where("hoscode").is(schedule.getHoscode()).
                and("hosScheduleId").is(schedule.getHosScheduleId());
        Query query = new Query(criteria);
        Schedule schedule1 = mongoTemplate.findOne(query, Schedule.class);

        if (schedule1 != null) {
            schedule1.setUpdateTime(new Date());
            schedule1.setIsDeleted(0);
            schedule1.setStatus(1);
            mongoTemplate.save(schedule1);
        } else {
            schedule1.setCreateTime(new Date());
            schedule1.setUpdateTime(new Date());
            schedule1.setIsDeleted(0);
            schedule1.setStatus(1);
            mongoTemplate.save(schedule1);
        }
    }

    //查询排班接口
    @Override
    public Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleQueryVo, schedule);
        schedule.setIsDeleted(0);
        schedule.setStatus(1);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        Example<Schedule> example = Example.of(schedule, matcher);
        Page<Schedule> all = scheduleRepository.findAll(example, pageable);
        return all;
    }

    @Override
    public void remove(String hoscode, String hosScheduleId) {
        try {
            Criteria criteria = Criteria.where("hoscode").is(hoscode)
                    .and("hosScheduleId").is(hosScheduleId);
            Query query = new Query(criteria);
            Schedule schedule = mongoTemplate.findOne(query, Schedule.class);
            System.out.println(schedule);
            if (schedule != null) {
                mongoTemplate.remove(query, Schedule.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //根据医院编号 和 科室编号 ，查询排班规则数据

    @Override
    public Map<String, Object> getRuleSchedule(long page, long limit, String hoscode, String depcode) throws ParseException {
        Criteria criteria = Criteria.where("hoscode").is(hoscode).and("depcode").is(depcode);
        //2 根据工作日workDate期进行分组
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria),//匹配条件
                Aggregation.group("workDate")//分组字段
                        .first("workDate").as("workDate")
                        //3 统计号源数量
                        .count().as("docCount")
                        .sum("reservedNumber").as("reservedNumber")
                        .sum("availableNumber").as("availableNumber"),
                //排序
                Aggregation.sort(Sort.Direction.DESC, "workDate"),
                //4 实现分页
                Aggregation.skip((page - 1) * limit),
                Aggregation.limit(limit)
        );

        //调用方法，最终执行
        AggregationResults<BookingScheduleRuleVo> aggResults =
                mongoTemplate.aggregate(agg, Schedule.class, BookingScheduleRuleVo.class);
        List<BookingScheduleRuleVo> bookingScheduleRuleVoList = aggResults.getMappedResults();

        //分组查询的总记录数
        Aggregation totalAgg = Aggregation.newAggregation(Aggregation.match(criteria),
                Aggregation.group("workDate"));
        AggregationResults<BookingScheduleRuleVo> totalAggResults = mongoTemplate.aggregate(totalAgg, Schedule.class, BookingScheduleRuleVo.class);
        int total = totalAggResults.getMappedResults().size();

        //获取日期对应星期
        for (BookingScheduleRuleVo bookingScheduleRuleVo : bookingScheduleRuleVoList) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date workDate = simpleDateFormat.parse(bookingScheduleRuleVo.getWorkDate());
            System.out.println(workDate);
            String dayOfWeek = this.getDayOfWeek(new DateTime(workDate));
            bookingScheduleRuleVo.setDayOfWeek(dayOfWeek);
        }
        //设置最终数据，进行返回
        Map<String, Object> result = new HashMap<>();
        result.put("bookingScheduleRuleList", bookingScheduleRuleVoList);
        result.put("total", bookingScheduleRuleVoList.size());

        //获取医院名称
        String hospName = hospitalService.getHospName(hoscode);
        //其他基础数据
        Map<String, String> baseMap = new HashMap<>();
        baseMap.put("hosname", hospName);
        result.put("baseMap", baseMap);
        return result;
    }

    @Override
    public List<Schedule> getDetailSchedule(String hoscode, String depcode, String workDate) {
        //根据参数查询mongodb

        Criteria criteria = Criteria.where("hoscode").is(hoscode)
                .and("depcode").is(depcode)
                .and("workDate").is(new DateTime(workDate).toDate());
        Query query = new Query(criteria);
        List<Schedule> scheduleList = mongoTemplate.find(query, Schedule.class);
        scheduleList.stream().forEach(item -> {
            this.packageSchedule(item);
        });
        return scheduleList;
    }

    private Schedule packageSchedule(Schedule schedule) {
        //设置医院名称
        schedule.getParam().put("hosname", hospitalService.getHospName(schedule.getHoscode()));

        //设置科室名称
        schedule.getParam().put("depname", departmentService.getDepartment(schedule.getHoscode(), schedule.getDepcode()));
        //设置日期对应星期
        schedule.getParam().put("dayOfWeek", this.getDayOfWeek(new DateTime(schedule.getWorkDate())));
        return schedule;
    }

    /**
     * 根据日期获取周几数据
     *
     * @param dateTime
     * @return
     */
    private String getDayOfWeek(DateTime dateTime) {
        String dayOfWeek = "";
        switch (dateTime.getDayOfWeek()) {
            case DateTimeConstants.SUNDAY:
                dayOfWeek = "周日";
                break;
            case DateTimeConstants.MONDAY:
                dayOfWeek = "周一";
                break;
            case DateTimeConstants.TUESDAY:
                dayOfWeek = "周二";
                break;
            case DateTimeConstants.WEDNESDAY:
                dayOfWeek = "周三";
                break;
            case DateTimeConstants.THURSDAY:
                dayOfWeek = "周四";
                break;
            case DateTimeConstants.FRIDAY:
                dayOfWeek = "周五";
                break;
            case DateTimeConstants.SATURDAY:
                dayOfWeek = "周六";
            default:
                break;
        }
        return dayOfWeek;
    }


    @Override
    public Map<String, Object> getBookingScheduleRule(int page, int limit, String hoscode, String depcode) throws ParseException {
        try {
            Map<String, Object> result = new HashMap<>();
            //获取预约规则
            //根据医院编号获取预约规则
            Hospital hospital = hospitalService.getByHoscode(hoscode);
            if (hospital == null) {
                throw new YyghException(ResultCodeEnum.DATA_ERROR);
            }
            BookingRule bookingRule = hospital.getBookingRule();
            //获取可预约日期的数据（分页）
            IPage ipage = this.getListDate(page, limit, bookingRule);
            //当前可预约日期
            List<Date> dateList = ipage.getRecords();
            //获取可预约日期里面科室的剩余预约数
            Criteria criteria = Criteria.where("hoscode").in(hoscode).and("depcode").is(depcode)
                    .and("workDate").in(dateList);


            Aggregation agg = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group("workDate").first("workDate").as("workDate")
                            .count().as("docCount")
                            .sum("availableNumber").as("availableNumber")
                            .sum("reservedNumber").as("reservedNumber")
            );
            AggregationResults<BookingScheduleRuleVo> aggregateResult = mongoTemplate.aggregate(agg, Schedule.class, BookingScheduleRuleVo.class);

            List<BookingScheduleRuleVo> scheduleVoList = aggregateResult.getMappedResults();
            //合并数据  map集合 key日期  value预约规则和剩余数量等
            Map<String, BookingScheduleRuleVo> scheduleVoMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(scheduleVoList)) {
                scheduleVoMap = scheduleVoList.stream().
                        collect(
                                Collectors.toMap(BookingScheduleRuleVo::getWorkDate,
                                        BookingScheduleRuleVo -> BookingScheduleRuleVo));
            }
            //获取可预约排班规则
            List<BookingScheduleRuleVo> bookingScheduleRuleVoList = new ArrayList<>();
            for (int i = 0; i < dateList.size(); i++) {
                String date = String.valueOf(dateList.get(i));
                //从map集合根据key日期获取value值
                BookingScheduleRuleVo bookingScheduleRuleVo = scheduleVoMap.get(date);
                //如果当天没有排班医生
                if (bookingScheduleRuleVo == null) {
                    bookingScheduleRuleVo = new BookingScheduleRuleVo();
                    //就诊医生人数
                    bookingScheduleRuleVo.setDocCount(0);
                    //科室剩余预约数  -1表示无号
                    bookingScheduleRuleVo.setAvailableNumber(-1);
                }
                bookingScheduleRuleVo.setWorkDate(date);
                bookingScheduleRuleVo.setWorkDateMd(date);
                //计算当前预约日期对应星期
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
//            Date dates = simpleDateFormat.parse(date);
//            String dayOfWeek = this.getDayOfWeek(new DateTime(date));
//            bookingScheduleRuleVo.setDayOfWeek(dayOfWeek);

                //最后一页最后一条记录为即将预约   状态 0：正常 1：即将放号 -1：当天已停止挂号
                if (i == dateList.size() - 1 && page == ipage.getPages()) {
                    bookingScheduleRuleVo.setStatus(1);
                } else {
                    bookingScheduleRuleVo.setStatus(0);
                }
                //当天预约如果过了停号时间， 不能预约
//            if(i == 0 && page == 1) {
//                DateTime stopTime = this.getDateTime(new Date(), bookingRule.getStopTime());
//                if(stopTime.isBeforeNow()) {
//                    //停止预约
//                    bookingScheduleRuleVo.setStatus(-1);
//                }
//            }
                bookingScheduleRuleVoList.add(bookingScheduleRuleVo);
            }
            //可预约日期规则数据
            result.put("bookingScheduleList", bookingScheduleRuleVoList);
            result.put("total", ipage.getTotal());

            //其他基础数据
            Map<String, String> baseMap = new HashMap<>();
            //医院名称
            baseMap.put("hosname", hospitalService.getHospName(hoscode));
            //科室
            Department department = departmentService.getDepartment(hoscode, depcode);
            //大科室名称
            baseMap.put("bigName", department.getBigname());
            //科室名称
            baseMap.put("depname", department.getDepname());
            //月
            baseMap.put("workDateString", new DateTime().toString("yyyy年MM月"));
            //放号时间
            baseMap.put("releaseTime", bookingRule.getReleaseTime());
            //停号时间
            baseMap.put("stopTime", bookingRule.getStopTime());
            result.put("baseMap", baseMap);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取可预约日志分页数据
    private IPage getListDate(int page, int limit, BookingRule bookingRule) {
        //获取当天放号时间  年 月 日 小时 分钟
        DateTime releaseTime = this.getDateTime(new Date(), bookingRule.getReleaseTime());
        //获取预约周期
        Integer cycle = bookingRule.getCycle();
        //如果当天放号时间已经过去了，预约周期从后一天开始计算，周期+1
        if (releaseTime.isBeforeNow()) {
            cycle = cycle + 1;
        }
        //获取可预约所有日期，最后一天显示即将放号
        List<Date> dateList = new ArrayList<>();
        for (int i = 0; i < cycle; i++) {
            DateTime curDateTime = new DateTime().plusDays(i);
            String dateString = curDateTime.toString("yyyy-MM-dd");
            System.out.println(dateString);
            dateList.add(new DateTime(dateString).toDate());
        }
        //因为预约周期不同的，每页显示日期最多7天数据，超过7天分页
        List<Date> pageDateList = new ArrayList<>();
        int start = (page - 1) * limit;
        int end = (page - 1) * limit + limit;
        //如果可以显示数据小于7，直接显示
        if (end > dateList.size()) {
            end = dateList.size();
        }
        for (int i = start; i <= end; i++) {
            pageDateList.add(dateList.get(i));
        }
        //如果可以显示数据大于7，进行分页
        IPage<Date> ipage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(page, 7, dateList.size());
        ipage.setRecords(pageDateList);
        return ipage;
    }

    /**
     * 将Date日期（yyyy-MM-dd HH:mm）转换为DateTime
     */
    private DateTime getDateTime(Date date, String timeString) {
        String dateTimeString = new DateTime(date).toString("yyyy-MM-dd") + " " + timeString;
        DateTime dateTime = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm").parseDateTime(dateTimeString);
        return dateTime;
    }

    @Override
    public Schedule getScheduleId(String scheduleId) {

        Criteria criteria = Criteria.where("hosScheduleId").is(scheduleId);
        Query query = new Query(criteria);
        Schedule schedule = mongoTemplate.findOne(query, Schedule.class);
        return this.packageSchedule(schedule);
    }

    //根据排班id获取预约下单数据
    @Override
    public ScheduleOrderVo getScheduleOrderVo(String scheduleId) {
        return null;
    }

    @Override
    public void update(Schedule schedule) {

    }
}
