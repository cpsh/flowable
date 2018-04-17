package com.sun.health.flowable;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.impl.ProcessEngineImpl;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.impl.db.DbSchemaCreate;

public class ProcessEngineConfigurationDemo {

    /**
     * Flowable流程引擎使用flowable.cfg.xml的XML文件进行配置。
     * 获取ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
     * 也可以使用编程方式构造ProcessEngineConfiguration对象。
     * ProcessEngineConfiguration.createXXX()方法都返回ProcessEngineConfiguration对象，并可以继续按需调整。
     *  ProcessEngineConfiguration.buildEngine()返回ProcessEngine对象。
     *
     * StandaloneProcessEngineConfiguration：流程引擎独立运行。Flowable自行处理事务。在默认情况下，数据库检查只在引擎启动时进行。
     * StandaloneInMemProcessEngineConfiguration：便于使用单元测试的类。Flowable自行处理事务。默认使用H2内存数据库。数据库只在引擎启动的使用创建，关闭时删除。使用这个类是，通常无需很多配置。
     * SpringProcessEngineConfiguration：在流程引擎处于Spring环境时使用。
     * JtaProcessEngineConfiguration：用于引擎独立运行，并使用JTA事务的情况。
     *
     * Flowable数据库配置
     * jdbcUrl:数据库的JDBC　URL
     * jdbcDriver:特定数据库类型的驱动实现
     * jdbcUsername:用于链接数据库的用户名
     * jdbcPassword:用于连接数据库的密码
     * 通过提供的JDBC参数构造的数据源，使用默认的MyBatis连接池设置。可用下列属性调整这个连接池
     * jdbcMaxActiveConnections:连接池能够容纳的最大活动连接数量。默认10
     * jdbcMaxIdleConnections：连接池最大空闲连接数量
     * jdbcMaxCheckoutTime：连接从池中取出到强制返回的最大时间间隔
     * jdbcMaxWaitTime:底层设置，在连接池取连接的时间异常长时，打印日志并尝试重新获取连接 默认20000（单位微秒）
     *
     * 推荐使用DataSource而不是MyBatis的默认实现
     * databaseType：通常无需设置，从数据库连接信息中自动得出。
     * databaseSchemaUpdate：用于设置流程引擎启动关闭时使用的数据库表结构控制策略
     * false（默认）：当引擎启动时，检查数据库表结构的版本是否匹配库文件版本。版本不匹配时抛出异常。
     * true：构建引擎时，检查并在需要时更新表结构。表结构不存在则会创建。
     * create-drop：引擎创建时创建表结构，并在引擎关闭时删除表结构。
     *
     * Flowable  JNDI
     * 默认情况下，Flowable的数据库配置保存在每个Web应用WEB-INF/classes目录下的db.properties文件中。
     * 可以通过JNDI(Java Naming and Directory Interface.Java命名和目录接口）获取数据库连接时，完全由Servlet容器管理，并可以在war部署之外管理部署。同时提供了更多的控制连接的参数。
     * Tomcat的JNDI资源配置在$CATALINA_BASE/conf/[enginename]/[hostname]/[warname].xml
     * JNDI参数
     * datasource.jndi.name:数据源的JNDI名
     * datasource.jndi.resourceRef:设置是否在J2EE容器中查找。JNDI名中没有包含java:comp/env前缀，是否需要添加。默认为true
     *
     *
     * 创建数据库
     * 最简单的方法是：
     * 配置Configuration（xml配置文件或ProcessEngineConfiguration）。
     * 然后执行DBSchemaCreate.main(String[] args)方法
     * 然而通常只有数据库管理员可以在数据库中执行DDL语句。DDL的SQL脚本在Flowable下载页面或发布目录中找到，位于database子目录.org/flowable/db/create包中也有一份，drop目录下则是删除。
     * 格式为
     * flowable.{db}.{create|drop}.{type}.sql
     * type为：
     * engine引擎执行必需的表
     * history历史和审计信息的表。历史级别设置为none时不需要。请注意不适用这些表会导致部分功能失效。
     *(提示 建议使用5.6.4版本及以上的MySQL数据库)
     *
     * 数据库表名说明
     * Flowable的所有数据库都以ACT开头。第二部分时说明表用途的两字符标示符。服务API的命名也大略符合这个规则
     * ACT_RE_* 表示repository 包含静态信息，例如流程定义与流程资源（图片、规则等）
     * ACT_RU_* 表示runtime。这些表存储运行时信息，例如流程实例（process instance）、用户任务（use task）、变量（variable）、作业（job）等。Flowable只在流程实例运行中保存运行时数据，并在流程实例结束时删除记录。保证运行时表数据量小、速度快。
     * ACT_HI_* 表示history 这些表存储历史数据，例如已完成的流程实例、变量、任务等
     * ACT_GE_* 通用数据
     *
     *
     * Flowable作业执行器、启用作业执行器
     * Flowable V6中唯一可用的作业执行器，是V5中的一步执行器（async executor），提供性能更好，对数据库也更友好的执行异步作业的方式
     * 如果在Java EE 7 下运行，还可以使用ManagedJobExecutor来管理线程。需要在配置中加入线程工厂
     * <bean id="customJobExecutor" class="...ManagedAsyncExecutor">
     *     <property name="threadFactory" ref="threadFactory" />
     * </bean>
     *
     * AsyncExecutor是管理线程池的组件，用于触发定时器与其他异步任务。也可以使用其他实现（例如消息队列）
     * 默认情况下，AsyncExecutor未激活，也不会启动。需要加入配置
     * <property name=asyncExecutorActivate" value="true' />
     *
     * Flowable部署缓存
     * 鉴于流程定义信息不会改变，为了避免每次使用流程定义时都读取数据库，所有流程定义都会（在解析后）被缓存。默认情况下，这个缓存没有限制。要限制定义缓存，需要添加参数
     * <property name="processDefinitionCacheLimit" value="10" />
     * 会将默认的hashmap替换为LRU（lastly recently used)缓存，以进行限制。当然，参数的最佳取值，取决与总的流程定义数量，一级实际使用的流程定义数量。
     * 可以注入自己的缓存实现，必须是实现了org.flowable.engine.impl.persistence.deploy.DeploymentCache接口的bean
     * 配置规则缓存(rules cache)可以使用类似的名为knowledgeBaseCacheLimit与knowledgeBaseCache参数，只有在流程中使用规则任务时才需要设置。
     *
     * @param args
     */
    public static void main(String[] args) {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
//        ProcessEngineConfiguration cfg = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();
//        ProcessEngineConfiguration cfg = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("flowable.cfg.xml");
//        ProcessEngineConfiguration.cfg = ProcessEngineConfiguration.createProcessEngineConfigurationFromInputStream()
        ProcessEngineConfiguration cfg = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();

//        ProcessEngine processEngine = new StandaloneProcessEngineConfiguration()
//                .setJdbcUrl()
//                .setJdbcUsername()
//                .setJdbcPassword()
//                .setJdbcDriver()
//                .buildProcessEngine();


        DbSchemaCreate.main(args);
    }

}
