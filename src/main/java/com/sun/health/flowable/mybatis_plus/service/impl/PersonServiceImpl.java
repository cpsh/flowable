package com.sun.health.flowable.mybatis_plus.service.impl;

import com.sun.health.flowable.mybatis_plus.model.Person;
import com.sun.health.flowable.mybatis_plus.dao.PersonMapper;
import com.sun.health.flowable.mybatis_plus.service.IPersonService;
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
public class PersonServiceImpl extends ServiceImpl<PersonMapper, Person> implements IPersonService {

}
