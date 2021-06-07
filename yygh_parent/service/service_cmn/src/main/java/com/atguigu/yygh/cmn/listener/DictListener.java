package com.atguigu.yygh.cmn.listener;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/6/217:10
 */

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.yygh.cmn.mapper.DictMapper;
import com.atguigu.yygh.model.cmn.Dict;
import com.atguigu.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/6/2 17:10
 * @updateDate 2021/6/2 17:10
 **/
public class DictListener extends AnalysisEventListener<DictEeVo> {
    private DictMapper dictMapper = null;

    public DictListener(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }


    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictEeVo, dict);
        this.dictMapper.insert(dict);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
