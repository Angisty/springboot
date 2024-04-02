package com.example.controller;

import com.example.common.Result;
import com.example.entity.Farmer;
import com.example.service.FarmerService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 农户前端操作接口
 **/
@RestController
@RequestMapping("/farmer")
public class FarmerController {

    @Resource
    private FarmerService farmerService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Farmer farmer) {
        farmerService.add(farmer);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        farmerService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        farmerService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody Farmer farmer) {
        farmerService.updateById(farmer);
        return Result.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Farmer farmer = farmerService.selectById(id);
        return Result.success(farmer);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Farmer farmer ) {
        List<Farmer> list = farmerService.selectAll(farmer);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Farmer farmer,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<Farmer> page = farmerService.selectPage(farmer, pageNum, pageSize);
        return Result.success(page);
    }

}