package com.sun.health.flowable.mybatis_plus;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 *
 * 参数说明
 *
 * 主键策略选择
 * MP支持4种主键策略
 * 1.IdType.AUTO        数据库自增
 * 2.IdType.INPUT       用户输入ID
 * 3.IdType.ID_WORKER   全局唯一ID，内容为空自动填充
 * 4.IdType.UUID        全局唯一ID，内容为空自动填充
 * IdType.ID_WORKER 使用开源项目Sequence，是一个分布式高效有序ID生产工具，思路开源与Twitter-Snowflake算法
 *
 * 表及字段命名策略选择
 * 建议数据库表名和表字段名采用驼峰命名方式，采用下划线命名方法请开启全局下划线开关，如果表字段名命名方式不一致请注解指定。
 *
 * 注意模板引擎根据具体情况选择，自定义模板引擎需要继承
 * AbstractTemplateEngine
 *
 * <p>
 *     代码生成器演示
 * </p>
 */
public class MpGenerator {

    /**
     *
     * <p>
     *     MYSQL生成演示
     * </p>
     *
     */
    public static void main(String[] args) {

        AutoGenerator mpg = new AutoGenerator();
        // 选择freemarker引擎，默认是velocity
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("E://Work/");
        gc.setFileOverride(true);
        gc.setActiveRecord(true); // 不需要ActiveRecord特性请改为false
        gc.setEnableCache(false); // XML二级缓存
        gc.setBaseResultMap(true); // XML ResultMap
        gc.setBaseColumnList(false); // XML columnList
//        gc.setKotlin(true); // 是否生成Kotlin代码
        gc.setAuthor("SJ");

        // 自定义文件命名, 注意%s会自动填充表实体属性
//        gc.setMapperName("%sDao");
//        gc.setXmlName("%sDao");
//        gc.setServiceName("MP%sService");
//        gc.setServiceImplName("%sServiceDiy");
//        gc.setControllerName("%sAction");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                System.out.println("转换类型:" + fieldType);
                // processTypeConvert存在默认类型转换，如果不想要则自定义返回。
                return super.processTypeConvert(fieldType);
            }
        });
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUrl("jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false");
        dsc.setUsername("root");
        dsc.setPassword("");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
//        strategyConfig.setCapitalMode(true); // 全局大写命名 ORACLE注意
        strategyConfig.setTablePrefix(new String[] {"tlog_", "tsys_"}); // 表名前缀
        strategyConfig.setNaming(NamingStrategy.underline_to_camel); // 表名生成策略
        // 需要生成的表
//        strategyConfig.setInclude(new String[] {"user"});
        // 排除生成的表
//        strategyConfig.setExclude(new String[] {"test"});
        // 自定义实体父类
//        strategyConfig.setSuperEntityClass("com.sun.health.flowable.mybatis_plus.BaseEntity");
        // 自定义实体，公共字段
//        strategyConfig.setSuperEntityColumns(new String[] {"test_id", "age"});
        // 自定义mapper父类
//        strategyConfig.setSuperMapperClass("com.sun.health.flowable.mybatis_plus.BaseMapper");
        // 自定义service父类
//        strategyConfig.setSuperServiceClass("com.sun.health.flowable.mybatis_plus.BaseService");
        // 自定义service实现类父类
//        strategyConfig.setSuperServiceImplClass("com.sun.health.flowable.mybatis_plus.BaseServiceImpl");
        // 自定义controller父类
//        strategyConfig.setSuperControllerClass("com.sun.health.flowable.myabtis_plus.BaseController");
        // 实体是否生成字段常量 默认false
//        strategyConfig.setEntityColumnConstant(true);
        // 实体是否为构建者模型 默认false
//        strategyConfig.setEntityBuilderModel(true);
        mpg.setStrategy(strategyConfig);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.sun.health.flowable.mybatis_plus");
        pc.setModuleName("test");
        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在VM中使用cfg.abc 【可无】
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor());
                this.setMap(map);
            }
        };

        // 自定义xxList.jsp生成
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        focList.add(new FileOutConfig() {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return "D://my_" + tableInfo.getEntityName() + ".jsp";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return "develop/code/xml/" + tableInfo.getEntityName() + ".xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 关闭默认xml生成，调整生成至根目录
        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);
        mpg.setTemplate(tc);

        // 自定义模板配置 可以copy源码mybatis-plus/src/main/resources/templates下面内容修改
        // 放置到src/main/resources/templates目录下，默认名称一下可以不配，也可以自定义模板名称
//        TemplateConfig tc = new TemplateConfig();
//        tc.setController("");
//        tc.setEntity("");
//        tc.setMapper("");
//        tc.setXml("");
//        tc.setService("");
//        tc.setServiceImpl("");
//        mpg.setTemplate(tc);

        mpg.execute();
        System.out.println(mpg.getCfg().getMap().get("abc"));

    }

}
