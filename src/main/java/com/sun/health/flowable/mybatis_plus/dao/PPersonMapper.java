package com.sun.health.flowable.mybatis_plus.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.sun.health.flowable.mybatis_plus.model.PPerson;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author SJ
 * @since 2018-05-09
 */
public interface PPersonMapper extends BaseMapper<PPerson> {

    List<PPerson> selectPPersonList(Pagination page);

    Integer deleteAll();

}
