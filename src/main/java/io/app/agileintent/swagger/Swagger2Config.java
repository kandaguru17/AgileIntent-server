package io.app.agileintent.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration

public class Swagger2Config {

	@Bean
	public Docket swaggerDocumentation() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors
						.basePackage("io.app.agileintent"))
				.paths(PathSelectors.regex("/.*"))
				.build()
				.apiInfo(apiInfo());
	}

	
	 private ApiInfo apiInfo() {
	        return new ApiInfoBuilder().title("Agile Intent")
	            .description("Agile Intent REST API")
	            .contact(new Contact("Kandaguru Baskaran", "http://ec2-3-106-55-146.ap-southeast-2.compute.amazonaws.com/", "kandaguru1707@gmail.com"))
	            .version("1.0.0")
	            .build();
	    }
}
