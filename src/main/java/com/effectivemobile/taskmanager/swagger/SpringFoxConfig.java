package com.effectivemobile.taskmanager.swagger;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
@Import({SpringDataRestConfiguration.class,
        BeanValidatorPluginsConfiguration.class})
public class SpringFoxConfig {

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.effectivemobile.taskmanager.user.controller")
                        .or(RequestHandlerSelectors.basePackage("com.effectivemobile.taskmanager.task.controller"))
                        .or(RequestHandlerSelectors.basePackage("com.effectivemobile.taskmanager.security.controller")))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "'Task Manager' REST API",
                "Документация 'Task Manager' API v1.0",
                null,
                null,
                new Contact("Тимур Давлетгареев", "https://github.com/TimurDavletgareev",
                        "timur@mailbox66.ru"),
                null, null, Collections.emptyList());
    }
}
