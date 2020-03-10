package it.poste.bank.service.presentation.rest.config;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.RequestEntity;
import io.opentracing.contrib.java.spring.jaeger.starter.TracerBuilderCustomizer;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(Properties.class)
@EnableSwagger2
public class Configuration {

    @Autowired
    Properties cfg;

    /**
     * Generates swagger api document
     *
     * @return
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .ignoredParameterTypes(RequestEntity.class)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/**/*"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Bank service REST API", // title 
                "Bank service REST API.", // description
                "v. 1.0", // version
                "", // "Terms of service", 
                (Contact) null, // new Contact("John Doe", "www.example.com", "myeaddress@company.com"), 
                "", //"License of API", 
                "", //"API license URL", 
                Collections.emptyList());
    }

    /**
     * Adds custom tags to jaeger traces.
     *
     * @return
     */
    @Bean
    public TracerBuilderCustomizer getJaegerCustomizer() {
        return builder -> builder.withTags(cfg.getJaegerTags());
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
