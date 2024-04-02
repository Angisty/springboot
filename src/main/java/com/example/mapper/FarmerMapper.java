package com.example.mapper;

import com.example.entity.Farmer;

import java.util.List;

/**
 * 操作farmer相关数据接口
*/
public interface FarmerMapper {

    /**
      * 新增
    */
    int insert(Farmer farmer);

    /**
      * 删除
    */
    int deleteById(Integer id);

    /**
      * 修改
    */
    int updateById(Farmer farmer);

    /**
      * 根据ID查询
    */
    Farmer selectById(Integer id);

    /**
      * 查询所有
    */
    List<Farmer> selectAll(Farmer farmer);


    Farmer selectByUsername(String username);
}