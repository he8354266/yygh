package com.atguigu.hospital.config;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/5/2110:19
 */

import com.atguigu.hospital.util.YyghException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/5/21 10:19
 * @updateDate 2021/5/21 10:19
 **/
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    public String error(Exception e) {
        e.printStackTrace();
        return "error";
    }

    /**
     * 自定义异常处理方法
     *
     * @param e
     * @return
     */
    @ExceptionHandler(YyghException.class)
    public String error(Exception e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }
}
