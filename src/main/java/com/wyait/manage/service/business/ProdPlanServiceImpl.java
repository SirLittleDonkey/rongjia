package com.wyait.manage.service.business;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.dao.business.ProdPlanMapper;
import com.wyait.manage.entity.business.ProdPlanDTO;
import com.wyait.manage.entity.business.ProdPlanSearchDTO;
import com.wyait.manage.entity.business.ProdPlanVO;
import com.wyait.manage.entity.business.ppSetDTO;
import com.wyait.manage.pojo.User;
import com.wyait.manage.utils.PageDataResult;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdPlanServiceImpl implements ProdPlanService{

    @Autowired
    private ProdPlanMapper prodPlanMapper;

    @Override
    public PageDataResult getProdPlans(Integer page, Integer limit, ProdPlanSearchDTO prodPlanSearchDTO) {
        PageDataResult pdr = new PageDataResult();
        PageHelper.startPage(page, limit);
        List<ProdPlanDTO> prodPlans = prodPlanMapper.getProdPlans(prodPlanSearchDTO);
        //获取分页查询后的数据
        PageInfo<ProdPlanDTO> pageInfo = new PageInfo<>(prodPlans);
        //设置获取到的总记录数total：
        pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
        pdr.setList(prodPlans);

        return pdr;
    }

    @Override
    public String setProdPlan(ppSetDTO ppSetDTO) {
        int prodPlanId;
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(ppSetDTO.getId() != null){
            //修改
            ppSetDTO.setInsertUid(user.getId());
            this.prodPlanMapper.update(ppSetDTO);
        }else{
            //新增
            ppSetDTO.setInsertUid(user.getId());
            ppSetDTO.setIsDel(false);
            this.prodPlanMapper.insert(ppSetDTO);
        }
        return "ok";
    }

    @Override
    public ProdPlanVO getProdPlan(Integer id) {
        return this.prodPlanMapper.getProdPlan(id);
    }
}
