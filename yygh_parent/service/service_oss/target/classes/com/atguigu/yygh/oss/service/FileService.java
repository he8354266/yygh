package com.atguigu.yygh.oss.service;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/1110:20
 */

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/11 10:20
 * @updateDate 2021/6/11 10:20
 **/
public interface FileService {
    //上传文件到阿里云oss
    String upload(MultipartFile file) throws IOException;
}
