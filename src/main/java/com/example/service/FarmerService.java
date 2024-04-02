package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Constants;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Business;
import com.example.entity.Farmer;
import com.example.exception.CustomException;
import com.example.mapper.FarmerMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 管理员业务处理
 **/
@Service
public class FarmerService {

    @Resource
    private FarmerMapper farmerMapper;

    /**
     * 新增
     */
    public void add(Farmer farmer) {
        Farmer dbFarmer = farmerMapper.selectByUsername(farmer.getUsername());
        if (ObjectUtil.isNotNull(dbFarmer)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        if (ObjectUtil.isEmpty(farmer.getPassword())) {
            farmer.setPassword(Constants.USER_DEFAULT_PASSWORD);
        }
        if (ObjectUtil.isEmpty(farmer.getName())) {
            farmer.setName(farmer.getUsername());
        }
        farmer.setRole(RoleEnum.FARMER.name());
        farmerMapper.insert(farmer);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        farmerMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            farmerMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Farmer farmer) {
        farmerMapper.updateById(farmer);
    }

    /**
     * 根据ID查询
     */
    public Farmer selectById(Integer id) {
        return farmerMapper.selectById(id);
    }
    /**
     * 根据username查询
     */
    public Farmer selectByUsername(String username) {
        return farmerMapper.selectByUsername(username);
    }
    /**
     * 查询所有
     */
    public List<Farmer> selectAll(Farmer farmer) {
        return farmerMapper.selectAll(farmer);
    }

    /**
     * 分页查询
     */
    public PageInfo<Farmer> selectPage(Farmer farmer, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Farmer> list = farmerMapper.selectAll(farmer);
        return PageInfo.of(list);
    }

    /**
     * 登录
     */
    public Account login(Account account) {
        Account dbFarmer = farmerMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbFarmer)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbFarmer.getPassword())) {
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        // 生成token
        String tokenData = dbFarmer.getId() + "-" + RoleEnum.FARMER.name();
        String token = TokenUtils.createToken(tokenData, dbFarmer.getPassword());
        dbFarmer.setToken(token);
        return dbFarmer;
    }

    /**
     * 注册
     */
    public void register(Account account) {
        Farmer farmer = new Farmer();
        BeanUtils.copyProperties(account, farmer);
        add(farmer);
    }

    /**
     * 修改密码
     */
    public void updatePassword(Account account) {
        Farmer dbFarmer = farmerMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbFarmer)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbFarmer.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        dbFarmer.setPassword(account.getNewPassword());
        farmerMapper.updateById(dbFarmer);
    }

}