package com.lamp.devops.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;

import java.time.LocalDate;

/**
 * @author god-lamp
 * @since 2023-12-28
 */
public class CodegenUtil {
    public static void main(String[] args) {
        //配置数据源
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://192.168.156.22:3306/devops?characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("Tv75aYT8@");

        //创建配置内容，两种风格都可以。
        GlobalConfig globalConfig = createGlobalConfigUseStyle1();

        //通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);

        //生成代码
        generator.generate();
    }

    public static GlobalConfig createGlobalConfigUseStyle1() {
        // 创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();

        // 设置根包
        globalConfig.setBasePackage("com.lamp.devops");

        // 设置生成 entity 并启用 Lombok
        globalConfig.setEntityGenerateEnable(true);
        // 开启 Entity 的生成
        // 设置生成 Entity 并启用 Lombok、设置父类
        globalConfig.setAuthor("god-lamp");
        globalConfig.setEntityWithLombok(true);
        globalConfig.setTableDefGenerateEnable(true);
        globalConfig.enableEntity().setWithLombok(true);
        globalConfig.setSince(LocalDate.now().toString());

        // 设置生成 mapper
        globalConfig.setMapperGenerateEnable(true);
        globalConfig.setMapperXmlGenerateEnable(true);
        // 设置添加注解
        globalConfig.getMapperConfig().setMapperAnnotation(true);

        globalConfig.setServiceGenerateEnable(true);
        globalConfig.getServiceConfig().setClassPrefix("I");

        globalConfig.setServiceImplGenerateEnable(true);

        globalConfig.setControllerGenerateEnable(true);

        return globalConfig;
    }
}
