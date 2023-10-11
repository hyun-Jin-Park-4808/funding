package com.hyunjin.funding.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//http://localhost:8080/swagger-ui.html
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket api() {

    //Authentication header 처리를 위해 사용
    Parameter parameterBuilder
        = new ParameterBuilder().
        name("Authorization") //헤더 이름
        .description("Bearer +로그인 후 발급받은 토큰을 입력해 주세요.") //설명
        .modelRef(new ModelRef("string"))
        .parameterType("header")
        .required(false)
        .build();

    List<Parameter> parameters = new ArrayList<>();
    parameters.add(parameterBuilder);

    return new Docket(DocumentationType.SWAGGER_2)
        .globalOperationParameters(parameters)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.hyunjin.funding"))
        // 내가 만든 컨트롤러만 swagger에 보여주고 싶을 때
        .paths(PathSelectors.any())
        // any: 모든 api 보여주겠다. ant("/wish/**"): 특정 패턴의 api만 보여주겠다.
        .build()
        .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("펀딩 프로젝트 ")
        .description("스프링 부트를 활용한 서버 API 기반 펀딩 서비스입니다.\n"
            + "name 입력란에는 아무것도 입력하지 않으면 됩니다.")
        .version("2.0")
        .build();
  }
}
