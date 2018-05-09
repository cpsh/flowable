package com.sun.health.flowable.mybatis_plus;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by 华硕 on 2018-05-09.
 */
public class GeneratorDemo {

    public static void main(String[] args) {

        AutoGenerator autoGenerator = new AutoGenerator();

        Properties properties = System.getProperties(); // 系统参数

        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig
                .setOutputDir(System.getProperty("user.dir") + "/src/main/java/")
                .setActiveRecord(true)
                .setAuthor("SJ")
                .setEnableCache(false)
                .setBaseResultMap(true)
//                .setIdType(IdType.ID_WORKER)
                .setBaseColumnList(false)
                .setFileOverride(true);
        autoGenerator.setGlobalConfig(globalConfig);

        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
//                .setCapitalMode(true)
//                .setEntityLombokModel(false)
//                .setDbColumnUnderline(true)
                .setNaming(NamingStrategy.underline_to_camel)
//                .entityTableFieldAnnotationEnable(true)
                .setInclude(new String[] {"p_person"});
        autoGenerator.setStrategy(strategyConfig);

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUrl("jdbc:mysql://localhost:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false")
                .setUsername("root")
                .setPassword("")
                .setDbType(DbType.MYSQL)
                .setTypeConvert(new MySqlTypeConvert() {
                    @Override
                    public DbColumnType processTypeConvert(String fieldType) {
                        System.out.println("转换类型:" + fieldType);
                        return super.processTypeConvert(fieldType);
                    }
                });
        autoGenerator.setDataSource(dataSourceConfig);

        PackageConfig packageConfig = new PackageConfig();
        packageConfig
                .setParent("com.sun.health.flowable.mybatis_plus")
//                .setModuleName("demo")
                .setEntity("model")
                .setMapper("dao")
                .setXml("dao.xml")
                .setService("service")
                .setServiceImpl("service.impl")
                .setController("controller");
        autoGenerator.setPackageInfo(packageConfig);

        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor());
                this.setMap(map);
            }
        };
        autoGenerator.setCfg(injectionConfig);

        autoGenerator.execute();

        System.out.println(autoGenerator.getCfg().getMap().get("abc"));


    }

}
