package sku.moamoa.global.config;

import io.swagger.models.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods(HttpMethod.GET.name(),HttpMethod.POST.name(), HttpMethod.PUT.name(),
                        HttpMethod.PATCH.name(),HttpMethod.OPTIONS.name(),HttpMethod.DELETE.name())
                .allowCredentials(false).maxAge(3600);
    }

}
