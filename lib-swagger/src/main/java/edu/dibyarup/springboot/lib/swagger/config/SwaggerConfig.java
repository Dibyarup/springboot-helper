package edu.dibyarup.springboot.lib.swagger.config;

import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private final String apiTitle;

	private final String apiDescription;

	private final String apiVersion;

	public SwaggerConfig(final @Value("${swagger.api.title:API Title}") String apiTitle,
			final @Value("${swagger.api.description:Api Description}") String apiDescription,
			final @Value("${swagger.api.version:0.0.1}") String apiVersion,
			final @Value("${git.tag:git.tag is not available in application.yml}") String gitTag) {
		this.apiTitle = apiTitle;
		this.apiDescription = apiDescription;
		this.apiVersion = formatApiVersion(apiVersion, gitTag);
	}

	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();

	}

	private ApiInfo apiInfo() {
		return new ApiInfo(apiTitle, apiDescription, apiVersion, ApiInfo.DEFAULT.getTermsOfServiceUrl(),
				ApiInfo.DEFAULT_CONTACT, ApiInfo.DEFAULT.getLicense(), ApiInfo.DEFAULT.getLicenseUrl(),
				ApiInfo.DEFAULT.getVendorExtensions());
	}

	private String formatApiVersion(final String apiVersion, final String gitTag) {
		return String.format("%s [gitID: %s] [gitTag: %s]", apiVersion, getGitId(), gitTag);
	}

	private String getGitId() {
		try {
			return ResourceBundle.getBundle("git").getString("git.commit.id").substring(0, 7);
		} catch (Exception e) {
			return "no git.properties is present on classpath with git.commit.id hash";
		}
	}

}
