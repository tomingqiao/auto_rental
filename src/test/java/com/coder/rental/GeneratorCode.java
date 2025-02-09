package com.coder.rental;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
public class GeneratorCode {
    private static final String AUTHOR="叶发通";
    private static final String JDBC_URL="jdbc:mysql://localhost:3306/auto_rental";
    private static final String JDBC_USERNAME="root";
    private static final String JDBC_PASSWORD="root";
    private static final String OUT_DIR=".\\src\\main\\java";
    private static final String PACKAGE_NAME="com.coder";
    private static final String MODULE_NAME="rental";
    private static final String[] TABLES={
            "auto_brand","auto_info","auto_maker",
            "busi_customer","busi_maintain","busi_order","busi_rental_type","busi_violation",
            "sys_dept","sys_permission","sys_role","sys_role_permission","sys_user","sys_user_role"
    };
    private static final String[] PREFIX={"busi_","sys_"};

    @Test
    void generatorCode(){
        FastAutoGenerator.create(JDBC_URL,JDBC_USERNAME,JDBC_PASSWORD)
                .globalConfig(builder -> {
                    builder.author(AUTHOR)
                            .enableSwagger()
                            .outputDir(OUT_DIR);
                })
                .packageConfig(builder -> {
                    builder.parent(PACKAGE_NAME)
                            .moduleName(MODULE_NAME)
                            .pathInfo(Collections.singletonMap(OutputFile.xml,".\\src\\main\\resources\\mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude(TABLES)
                            .addTablePrefix(PREFIX)
                            .entityBuilder()
                            .enableLombok()
                            .enableChainModel()
                            .controllerBuilder()
                            .enableRestStyle();
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
