package io.app.agileintent.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;

import io.app.agileintent.domain.EmailConfirmation;
import io.app.agileintent.security.AuthenticationResponse;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class Swagger2Config {

	public static final Contact CONTACT = new Contact("Kandaguru Baskaran",
			"http://ec2-3-106-55-146.ap-southeast-2.compute.amazonaws.com/", "kandaguru1707@gmail.com");
	public static final String DESCRIPTION = "Agile Intent REST API";
	public static final String TITLE = "Agile Intent";
	public static final String VERSION = "2.0.0";

	@Bean
	public Docket swaggerDocumentation(TypeResolver typeResolver) {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("io.app.agileintent")).paths(PathSelectors.regex("/.*"))
				.build().apiInfo(apiInfo()).additionalModels(typeResolver.resolve(AuthenticationResponse.class),
						typeResolver.resolve(EmailConfirmation.class));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(TITLE).description(DESCRIPTION).contact(CONTACT).version(VERSION).build();
	}
}
