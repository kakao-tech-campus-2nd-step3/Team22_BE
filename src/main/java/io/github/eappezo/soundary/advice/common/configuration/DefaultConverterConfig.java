package io.github.eappezo.soundary.advice.common.configuration;

import io.github.eappezo.soundary.advice.identification.IdentifierConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class DefaultConverterConfig implements WebMvcConfigurer {
    private final IdentifierConverter identifierConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(identifierConverter);
    }
}
