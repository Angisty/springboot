package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.LevelEnum;
import com.example.common.enums.StatusEnum;
import com.example.entity.Farmer;
import com.example.entity.Poorapply;
import com.example.mapper.FarmerMapper;
import com.example.mapper.PoorapplyMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service
public class PoorapplyService {

    @Resource
    private PoorapplyMapper poorapplyMapper;
    @Resource
    private FarmerMapper farmerMapper;

    /**
     * 新增
     */
    public void add(Poorapply poorapply) {
        poorapply.setTime(DateUtil.format(new Date(), "yyyy-MM-dd"));
        poorapplyMapper.insert(poorapply);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        poorapplyMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            poorapplyMapper.deleteById(id);
        }
    }


    /**
     * 根据ID查询
     */
    public Poorapply selectById(Integer id) {
        return poorapplyMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Poorapply> selectAll(Poorapply poorapply) {
        return poorapplyMapper.selectAll(poorapply);
    }

    /**
     * 分页查询
     */
    public PageInfo<Poorapply> selectPage(Poorapply poorapply, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Poorapply> list = poorapplyMapper.selectAll(poorapply);
        return PageInfo.of(list);
    }


    public void updateById(Poorapply poorapply) {
        poorapplyMapper.updateById(poorapply);

        // 如果状态是审核通过，需要把用户的身份变成贫困户

        if (StatusEnum.CHECK_OK.status.equals(poorapply.getStatus())) {
            Farmer farmer = farmerMapper.selectById(poorapply.getFarmerId());
            if (ObjectUtil.isNotEmpty(farmer)) {
                farmer.setLevel(LevelEnum.POOR.level);
                farmerMapper.updateById(farmer);
            }
        }
    }

}