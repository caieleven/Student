package com.run.student;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {
    public static void main(String[] args){
        /*构建代码自动生成器对象*/
        AutoGenerator autoGenerator = new AutoGenerator();

        /*1. 全局配置*/
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setAuthor("baorun chen");
        String projectPath = System.getProperty("user.dir");
        globalConfig.setOutputDir(projectPath + "/src/main/java");
        globalConfig.setServiceName("%sService");
        globalConfig.setOpen(false);
        globalConfig.setFileOverride(true);
        autoGenerator.setGlobalConfig(globalConfig);

        /*2. 数据源*/
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://139.224.62.56:3306/students?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=GMT%2B8&zeroDateTimeBehavior=convertToNull");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("QWEasd123");
        dsc.setDbType(DbType.MYSQL);
        autoGenerator.setDataSource(dsc);

        /*3. 包配置*/
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.run.student");
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setController("controller");
        autoGenerator.setPackageInfo(pc);

        /*4. 策略配置*/
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setInclude("notice");
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setEntityLombokModel(true);
        autoGenerator.setStrategy(strategyConfig);

        /*5. 其它配置*/
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {

            }
        };
        String templatePath = "/templates/mapper.xml.vm";
        List<FileOutConfig> fileOutConfigs = new ArrayList<>();
        fileOutConfigs.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(fileOutConfigs);
        autoGenerator.setCfg(cfg);



        autoGenerator.execute();

    }
}
