package com.wyait.manage.service.business;


import com.wyait.manage.entity.business.qiSearchDTO;
import com.wyait.manage.entity.business.qiSetDTO;
import com.wyait.manage.pojo.business.QualityInstruction;
import com.wyait.manage.utils.PageDataResult;

public interface QualityInstructionService {
    public PageDataResult getQualityInstructions(Integer page, Integer limit, qiSearchDTO qiSearchDTO);
    public String setQualityInstruction(qiSetDTO qiSetDTO);
    public QualityInstruction getQualityInstruction(Integer id);
    public String setDelQualityInstruction(Integer id, Integer isDel);
}
