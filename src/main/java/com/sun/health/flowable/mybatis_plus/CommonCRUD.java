package com.sun.health.flowable.mybatis_plus;

/**
 *
 * 通用CRUD
 *
 * 实体无注解话设置，表字段如下设置，主键叫id可无注解，大小写如下规则
 * 1.驼峰命名
 * 2.全局配置：下划线命名dbColumnUnderline设置true，大写isCapitalMode设置为true
 *
 * 注解
 * 表名注解 @TableName
 * value        表名 默认为空
 * resultMap    xml字段映射resultMapID
 *
 * 主键注解 @TableId
 * value        字段值 驼峰命名规则方式，此值可为空
 * type         主键ID策略类型
 *
 * 字段注解  @TableField
 * value        字段名
 * update       预处理set字段自定义注入 update = "%s + 1" -> update table_name set field_name = field_name + 1
 * condition    预处理where实体条件自定义运算规则     condition = SqlCondition.LIKE -> LIKE CONCAT('%', field_value, '%')
 * el
 * exist        是否为数据库表字段
 * strategy     字段验证 默认非空验证
 * fill         字段填充标记 DEFAULT 默认不处理    INSERT 插入填充字段   UPDATE  更新填充字段  INSERT_UPDATE 插入和更新填充字段
 *
 * 序列主键策略  @KeySequence
 * value        序列名
 * clazz        id的类型
 *
 * 乐观锁标记注释 @Version
 *
 *
 */
public class CommonCRUD {
}
