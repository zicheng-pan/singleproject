package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {

    /*
        配置swagger2核心配置 docket
        http://localhost:8088/swagger-ui.html   原路径
        http://localhost:8088/doc.html  换皮肤之后的swagger api路径
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.example.controller"))    // 指定swagger扫描的包
                .paths(PathSelectors.any())     // 所有controller
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().title("电商平台API")    //文档标题
                .contact(new Contact("zicheng","http://zz.pan","zicheng.pan"))
                .description("为了学习加油")
                .version("1.0.1")       // 文档版本号
                .termsOfServiceUrl("http://zicheng.info")   //网站地址
                .build();
    }
}
