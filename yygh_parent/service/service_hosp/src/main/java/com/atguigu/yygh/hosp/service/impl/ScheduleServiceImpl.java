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

    //??????????????????
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

    //??????????????????
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


    //?????????????????? ??? ???????????? ???????????????????????????

    @Override
    public Map<String, Object> getRuleSchedule(long page, long limit, String hoscode, String depcode) throws ParseException {
        Criteria criteria = Criteria.where("hoscode").is(hoscode).and("depcode").is(depcode);
        //2 ???????????????workDate???????????????
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria),//????????????
                Aggregation.group("workDate")//????????????
                        .first("workDate").as("workDate")
                        //3 ??????????????????
                        .count().as("docCount")
                        .sum("reservedNumber").as("reservedNumber")
                        .sum("availableNumber").as("availableNumber"),
                //??????
                Aggregation.sort(Sort.Direction.DESC, "workDate"),
                //4 ????????????
                Aggregation.skip((page - 1) * limit),
                Aggregation.limit(limit)
        );

        //???????????????????????????
        AggregationResults<BookingScheduleRuleVo> aggResults =
                mongoTemplate.aggregate(agg, Schedule.class, BookingScheduleRuleVo.class);
        List<BookingScheduleRuleVo> bookingScheduleRuleVoList = aggResults.getMappedResults();

        //???????????????????????????
        Aggregation totalAgg = Aggregation.newAggregation(Aggregation.match(criteria),
                Aggregation.group("workDate"));
        AggregationResults<BookingScheduleRuleVo> totalAggResults = mongoTemplate.aggregate(totalAgg, Schedule.class, BookingScheduleRuleVo.class);
        int total = totalAggResults.getMappedResults().size();

        //????????????????????????
        for (BookingScheduleRuleVo bookingScheduleRuleVo : bookingScheduleRuleVoList) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date workDate = simpleDateFormat.parse(bookingScheduleRuleVo.getWorkDate());
            System.out.println(workDate);
            String dayOfWeek = this.getDayOfWeek(new DateTime(workDate));
            bookingScheduleRuleVo.setDayOfWeek(dayOfWeek);
        }
        //?????????????????????????????????
        Map<String, Object> result = new HashMap<>();
        result.put("bookingScheduleRuleList", bookingScheduleRuleVoList);
        result.put("total", bookingScheduleRuleVoList.size());

        //??????????????????
        String hospName = hospitalService.getHospName(hoscode);
        //??????????????????
        Map<String, String> baseMap = new HashMap<>();
        baseMap.put("hosname", hospName);
        result.put("baseMap", baseMap);
        return result;
    }

    @Override
    public List<Schedule> getDetailSchedule(String hoscode, String depcode, String workDate) {
        //??????????????????mongodb

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
        //??????????????????
        schedule.getParam().put("hosname", hospitalService.getHospName(schedule.getHoscode()));

        //??????????????????
        schedule.getParam().put("depname", departmentService.getDepartment(schedule.getHoscode(), schedule.getDepcode()));
        //????????????????????????
        schedule.getParam().put("dayOfWeek", this.getDayOfWeek(new DateTime(schedule.getWorkDate())));
        return schedule;
    }

    /**
     * ??????????????????????????????
     *
     * @param dateTime
     * @return
     */
    private String getDayOfWeek(DateTime dateTime) {
        String dayOfWeek = "";
        switch (dateTime.getDayOfWeek()) {
            case DateTimeConstants.SUNDAY:
                dayOfWeek = "??????";
                break;
            case DateTimeConstants.MONDAY:
                dayOfWeek = "??????";
                break;
            case DateTimeConstants.TUESDAY:
                dayOfWeek = "??????";
                break;
            case DateTimeConstants.WEDNESDAY:
                dayOfWeek = "??????";
                break;
            case DateTimeConstants.THURSDAY:
                dayOfWeek = "??????";
                break;
            case DateTimeConstants.FRIDAY:
                dayOfWeek = "??????";
                break;
            case DateTimeConstants.SATURDAY:
                dayOfWeek = "??????";
            default:
                break;
        }
        return dayOfWeek;
    }


    @Override
    public Map<String, Object> getBookingScheduleRule(int page, int limit, String hoscode, String depcode) throws ParseException {
        try {
            Map<String, Object> result = new HashMap<>();
            //??????????????????
            //????????????????????????????????????
            Hospital hospital = hospitalService.getByHoscode(hoscode);
            if (hospital == null) {
                throw new YyghException(ResultCodeEnum.DATA_ERROR);
            }
            BookingRule bookingRule = hospital.getBookingRule();
            //??????????????????????????????????????????
            IPage ipage = this.getListDate(page, limit, bookingRule);
            //?????????????????????
            List<Date> dateList = ipage.getRecords();
            //???????????????????????????????????????????????????
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
            //????????????  map?????? key??????  value??????????????????????????????
            Map<String, BookingScheduleRuleVo> scheduleVoMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(scheduleVoList)) {
                scheduleVoMap = scheduleVoList.stream().
                        collect(
                                Collectors.toMap(BookingScheduleRuleVo::getWorkDate,
                                        BookingScheduleRuleVo -> BookingScheduleRuleVo));
            }
            //???????????????????????????
            List<BookingScheduleRuleVo> bookingScheduleRuleVoList = new ArrayList<>();
            for (int i = 0; i < dateList.size(); i++) {
                String date = String.valueOf(dateList.get(i));
                //???map????????????key????????????value???
                BookingScheduleRuleVo bookingScheduleRuleVo = scheduleVoMap.get(date);
                //??????????????????????????????
                if (bookingScheduleRuleVo == null) {
                    bookingScheduleRuleVo = new BookingScheduleRuleVo();
                    //??????????????????
                    bookingScheduleRuleVo.setDocCount(0);
                    //?????????????????????  -1????????????
                    bookingScheduleRuleVo.setAvailableNumber(-1);
                }
                bookingScheduleRuleVo.setWorkDate(date);
                bookingScheduleRuleVo.setWorkDateMd(date);
                //????????????????????????????????????
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//???????????????MM
//            Date dates = simpleDateFormat.parse(date);
//            String dayOfWeek = this.getDayOfWeek(new DateTime(date));
//            bookingScheduleRuleVo.setDayOfWeek(dayOfWeek);

                //?????????????????????????????????????????????   ?????? 0????????? 1??????????????? -1????????????????????????
                if (i == dateList.size() - 1 && page == ipage.getPages()) {
                    bookingScheduleRuleVo.setStatus(1);
                } else {
                    bookingScheduleRuleVo.setStatus(0);
                }
                //??????????????????????????????????????? ????????????
//            if(i == 0 && page == 1) {
//                DateTime stopTime = this.getDateTime(new Date(), bookingRule.getStopTime());
//                if(stopTime.isBeforeNow()) {
//                    //????????????
//                    bookingScheduleRuleVo.setStatus(-1);
//                }
//            }
                bookingScheduleRuleVoList.add(bookingScheduleRuleVo);
            }
            //???????????????????????????
            result.put("bookingScheduleList", bookingScheduleRuleVoList);
            result.put("total", ipage.getTotal());

            //??????????????????
            Map<String, String> baseMap = new HashMap<>();
            //????????????
            baseMap.put("hosname", hospitalService.getHospName(hoscode));
            //??????
            Department department = departmentService.getDepartment(hoscode, depcode);
            //???????????????
            baseMap.put("bigName", department.getBigname());
            //????????????
            baseMap.put("depname", department.getDepname());
            //???
            baseMap.put("workDateString", new DateTime().toString("yyyy???MM???"));
            //????????????
            baseMap.put("releaseTime", bookingRule.getReleaseTime());
            //????????????
            baseMap.put("stopTime", bookingRule.getStopTime());
            result.put("baseMap", baseMap);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //?????????????????????????????????
    private IPage getListDate(int page, int limit, BookingRule bookingRule) {
        //????????????????????????  ??? ??? ??? ?????? ??????
        DateTime releaseTime = this.getDateTime(new Date(), bookingRule.getReleaseTime());
        //??????????????????
        Integer cycle = bookingRule.getCycle();
        //???????????????????????????????????????????????????????????????????????????????????????+1
        if (releaseTime.isBeforeNow()) {
            cycle = cycle + 1;
        }
        //????????????????????????????????????????????????????????????
        List<Date> dateList = new ArrayList<>();
        for (int i = 0; i < cycle; i++) {
            DateTime curDateTime = new DateTime().plusDays(i);
            String dateString = curDateTime.toString("yyyy-MM-dd");
            System.out.println(dateString);
            dateList.add(new DateTime(dateString).toDate());
        }
        //??????????????????????????????????????????????????????7??????????????????7?????????
        List<Date> pageDateList = new ArrayList<>();
        int start = (page - 1) * limit;
        int end = (page - 1) * limit + limit;
        //??????????????????????????????7???????????????
        if (end > dateList.size()) {
            end = dateList.size();
        }
        for (int i = start; i <= end; i++) {
            pageDateList.add(dateList.get(i));
        }
        //??????????????????????????????7???????????????
        IPage<Date> ipage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(page, 7, dateList.size());
        ipage.setRecords(pageDateList);
        return ipage;
    }

    /**
     * ???Date?????????yyyy-MM-dd HH:mm????????????DateTime
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

    //????????????id????????????????????????
    @Override
    public ScheduleOrderVo getScheduleOrderVo(String scheduleId) {
        ScheduleOrderVo scheduleOrderVo = new ScheduleOrderVo();
        //??????????????????
        Schedule schedule = this.getScheduleId(scheduleId);
        if (schedule == null) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }
        //????????????????????????
        Hospital hospital = hospitalService.getByHoscode(schedule.getHoscode());
        if (hospital == null) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }
        BookingRule bookingRule = hospital.getBookingRule();
        if (bookingRule == null) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }

        //????????????????????????scheduleOrderVo
        scheduleOrderVo.setHoscode(schedule.getHoscode());
        scheduleOrderVo.setHosname(hospitalService.getHospName(schedule.getHoscode()));
        scheduleOrderVo.setDepcode(schedule.getDepcode());
        scheduleOrderVo.setDepname(departmentService.getDepName(schedule.getHoscode(), schedule.getDepcode()));
        scheduleOrderVo.setHosScheduleId(schedule.getHosScheduleId());
        scheduleOrderVo.setAvailableNumber(schedule.getAvailableNumber());
        scheduleOrderVo.setTitle(schedule.getTitle());
        scheduleOrderVo.setReserveDate(schedule.getWorkDate());
        scheduleOrderVo.setReserveTime(schedule.getWorkTime());
        scheduleOrderVo.setAmount(schedule.getAmount());

        //?????????????????????????????????????????????-1????????????0???
        Integer quitDay = bookingRule.getQuitDay();
        DateTime quitTime = this.getDateTime(new DateTime(schedule.getWorkDate()).plusDays(quitDay).toDate(), bookingRule.getQuitTime());
        scheduleOrderVo.setQuitTime(quitTime.toDate());


        //??????????????????
        DateTime startTime = this.getDateTime(new Date(), bookingRule.getReleaseTime());
        scheduleOrderVo.setStartTime(startTime.toDate());


        //??????????????????
        DateTime endTime = this.getDateTime(new DateTime().plusDays(bookingRule.getCycle()).toDate(), bookingRule.getStopTime());
        scheduleOrderVo.setEndTime(endTime.toDate());

        //????????????????????????
        DateTime stopTime = this.getDateTime(new Date(), bookingRule.getStopTime());
        scheduleOrderVo.setStopTime(stopTime.toDate());
        return scheduleOrderVo;
    }

    @Override
    public void update(Schedule schedule) {
        schedule.setUpdateTime(new Date());
        mongoTemplate.save(schedule);
    }
}
