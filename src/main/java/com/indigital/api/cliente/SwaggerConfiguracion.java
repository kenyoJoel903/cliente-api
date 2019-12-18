package com.indigital.api.cliente;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguracion {

	@Bean
	public Docket api() {                
	    return new Docket(DocumentationType.SWAGGER_2)
	    		.globalOperationParameters(
	    				Arrays.asList(new ParameterBuilder()
	    						.name("Accept-Language")
	    						.description("Idioma, ejemplo es-PE es-US")
	    						.modelRef(new ModelRef("string"))
	    						.parameterType("header")
	    						.defaultValue("es-PE")
	    						.required(true)
	    						.build()))
	      .select()
	      .apis(RequestHandlerSelectors.basePackage("com.indigital.api.cliente.controller"))
	      .paths(PathSelectors.any())         
	      .build()
	      .apiInfo(apiInfo());
	}
	 
	private ApiInfo apiInfo() {
	     return new ApiInfo(
	       "Kenyo Joel Pecho Ñaupari", 
	       "Some custom description of API.", 
	       "API CLIENTE", 
	       "ss", 
	       new Contact("kenyoJoel903", "https://github.com/kenyoJoel903", "kenyojoel903@gmail.com"), 
	       "License of API", "API license URL", Collections.emptyList());
	}
}
