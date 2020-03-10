package it.poste.bank.service.presentation.rest.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "")
public class Properties {

    /**
     * Custom tags for jaeger tracing
     */
    private Map<String, String> jaegerTags;
}
