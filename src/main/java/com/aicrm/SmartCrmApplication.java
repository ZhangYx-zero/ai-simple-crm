package com.aicrm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * AI 智能销售 CRM 启动类
 */
@SpringBootApplication
@MapperScan("com.aicrm.mapper")   // 扫描 Mapper 接口，生成代理
public class SmartCrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartCrmApplication.class, args);
        System.out.println("=====================================================");
        System.out.println("  AI-CRM 启动成功！接口文档：http://localhost:8080/doc.html");
        System.out.println("  演示账号：admin/123456  manager/123456  seller/123456");
        System.out.println("=====================================================");
    }
}
