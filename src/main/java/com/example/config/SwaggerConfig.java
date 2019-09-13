package com.example.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    // 參考來源網址 : https://blog.csdn.net/liu0bing/article/details/80826590 (最重要的是兩個方法都需要重寫)
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html") 
			.addResourceLocations("classpath:/META-INF/resources/"); 
		registry.addResourceHandler("/webjars/**") 
			.addResourceLocations("classpath:/META-INF/resources/webjars/"); 
	}

    @Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.globalOperationParameters(initHeaderParameters())
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.example"))
				.paths(PathSelectors.any())
				.build();
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("User-GateWay Rest API Doc")
				.contact(new Contact("User.com", null, null))
//				.license("Apache License Version 2.0")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
				.version("1.0.0")
				.build();
	}
    
	// ※如需要加入header的顯示或是提供測試時的輸入在每一道訊息,可加入Parameter
	private List<Parameter> initHeaderParameters() {
		List<Parameter> parameters = new ArrayList<>();
		parameters.add(buildParameter("X-User-API-Key", "API Key", "String", "header", false, ""));
		parameters.add(buildParameter("X-User-API-Version", "API Version", "String", "header", false, "0.9.99"));
		parameters.add(buildParameter("X-User-APP-ID", "APP ID", "String", "header", false, "com.example"));
		parameters.add(buildParameter("X-User-APP-Version", "App Version", "String", "header", false, "4.0.1"));
		parameters.add(buildParameter("X-User-RES-Version", "RES Version", "String", "header", false, "2522121169970"));
		parameters.add(buildParameter("Authorization", "Base64 encoded of Terminal Id + Passphrase", "String", "header", false, "Basic NDQzNDk2MzI6bFVuclJZbU1Ga3p2Zm1TNlBONDc="));
		return parameters;
	}
	
	private Parameter buildParameter(String key, String description, String modelRef,
			String parameterType, boolean required, String defaultValue) {
		
		ParameterBuilder headerParam = new ParameterBuilder();
		headerParam.name(key);
		headerParam.description(description);
		headerParam.modelRef(new ModelRef(modelRef));
		headerParam.parameterType(parameterType);
		headerParam.required(required);
		headerParam.defaultValue(defaultValue);
		
		return headerParam.build();
	}
}
