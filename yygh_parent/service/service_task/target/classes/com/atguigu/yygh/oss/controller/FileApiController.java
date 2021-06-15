package com.atguigu.yygh.oss.controller;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/1510:12
 */

import com.atguigu.yygh.oss.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.atguigu.yygh.common.result.Result;

import java.io.IOException;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/15 10:12
 * @updateDate 2021/6/15 10:12
 **/
@RestController
@RequestMapping("/api/oss/file")
public class FileApiController {
    @Autowired
    private FileService fileService = null;

    //上传文件到阿里云oss
    @PostMapping("fileUpload")
    public Result fileUpload(MultipartFile file) throws IOException {
        String url = fileService.upload(file);
        return Result.ok(url);
    }
}
