package com.sun.health.flowable.mybatis_plus.service.impl;

import com.sun.health.flowable.mybatis_plus.model.PPerson;
import com.sun.health.flowable.mybatis_plus.dao.PPersonMapper;
import com.sun.health.flowable.mybatis_plus.service.IPPersonService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author SJ
 * @since 2018-05-09
 */
@Service
public class PPersonServiceImpl extends ServiceImpl<PPersonMapper, PPerson> implements IPPersonService {

}
