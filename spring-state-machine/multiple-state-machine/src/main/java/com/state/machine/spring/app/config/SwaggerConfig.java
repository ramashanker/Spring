package com.state.machine.spring.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {


    @Bean
    public Docket docketApi() {
        return new Docket(SWAGGER_2).apiInfo(apiInfo())
                                    .select()
                                    .apis(RequestHandlerSelectors.basePackage("com.state.machine"))
                                    .paths(PathSelectors.regex("(?!/error).+"))
                                    .paths(PathSelectors.regex("(?!/actuator).+"))
                                    .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Spring State Machine")
                                   .description("Interactive API for State Machine")
                                   .build();
    }
}
