package com.wyait.manage.dao.business;

import com.wyait.manage.entity.business.qiDTO;
import com.wyait.manage.entity.business.qiSearchDTO;
import com.wyait.manage.entity.business.qiSetDTO;
import com.wyait.manage.pojo.business.QualityInstruction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface QualityInstructionMapper {
    List<qiDTO> getQIs(@Param("qiSearchDTO") qiSearchDTO qiSearchDTO);
    QualityInstruction findQualityInstructionByInvCodeAndProcedureCode(@Param("invCode") String invCode, @Param("procedureCode") String procedureCode);
    int insert(qiSetDTO qiSetDTO);
    int insertTotallyNew(qiSetDTO qiSetDTO);
    QualityInstruction getQualityInstruction(Integer id);
    QualityInstruction findQualityInstructionByIPV(@Param("invCode") String invCode, @Param("procedureCode") String procedureCode, @Param("version") Integer version);
    int update(qiSetDTO qiSetDTO);
    int setDelQualityInstruction(@Param("id") Integer id, @Param("isDel") Integer isDel);

}
