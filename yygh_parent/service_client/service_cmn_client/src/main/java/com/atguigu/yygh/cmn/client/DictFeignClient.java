package com.atguigu.yygh.cmn.client;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/810:40
 */

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/8 10:40
 * @updateDate 2021/6/8 10:40
 **/
@FeignClient(value = "service-cmn")
@Repository
public interface DictFeignClient {
    //根据dictcode和value查询
    @GetMapping("/admin/cmn/dict/getName/{dictCode}/{value}")
    public String getName(@PathVariable("dictCode") String dictCode, @PathVariable("value") String value);

    //根据value查询
    @GetMapping("/admin/cmn/dict/getName/{value}")
    public String getName(@PathVariable("value") String value);
}
