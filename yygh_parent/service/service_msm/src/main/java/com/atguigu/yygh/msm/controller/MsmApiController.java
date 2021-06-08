package com.atguigu.yygh.msm.controller;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/711:22
 */

import com.aliyuncs.utils.StringUtils;
import com.atguigu.yygh.msm.service.MsmService;
import com.atguigu.yygh.msm.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.atguigu.yygh.common.result.Result;

import java.util.concurrent.TimeUnit;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/7 11:22
 * @updateDate 2021/6/7 11:22
 **/
@RestController
@RequestMapping("/api/msm")
public class MsmApiController {
    @Autowired
    private MsmService msmService = null;
    @Autowired
    private RedisTemplate<String, String> redisTemplate = null;

    //发送手机验证码
    @GetMapping("send/{phone}")
    public Result sendCode(@PathVariable String phone) {
        //从redis获取验证码，如果获取获取到，返回ok
        // key 手机号  value 验证码
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return Result.ok();
        }
        //如果从redis获取不到，生成验证码
        code = RandomUtil.getSixBitRandom();
        //调用service方法，通过整合短信服务进行发送
        boolean isSend = msmService.send(phone, code);
        //生成验证码放到redis里面，设置有效时间
        if (isSend) {
            redisTemplate.opsForValue().set(phone, code, 2, TimeUnit.MINUTES);
            return Result.ok();
        } else {
            return Result.fail().message("发送短信失败");
        }
    }
}
