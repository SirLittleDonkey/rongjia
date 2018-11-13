package com.wyait.manage.service.basic;

import com.wyait.manage.entity.basic.ProcedureSearchDTO;
import com.wyait.manage.entity.basic.ProcedureVO;
import com.wyait.manage.pojo.basic.Procedure;
import com.wyait.manage.utils.PageDataResult;

public interface ProcedureService {
    public PageDataResult getProcedures(Integer page, Integer limit, ProcedureSearchDTO searchDTO);

    public String setProcedure(Procedure procedure);

    public ProcedureVO getProcedure(Integer id);

    public String setDelProcedure(Integer id, Integer isDel);
}
