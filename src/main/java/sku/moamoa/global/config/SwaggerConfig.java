package sku.moamoa.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import sku.moamoa.global.annotation.LoginUser;


// swagger 접속 url -> http://localhost:8080/swagger-ui/index.html#/

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        // Security 스키마 설정
        SecurityScheme bearerAuth = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("Authorization")
                .in(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION);

        SecurityRequirement addSecurityItem = new SecurityRequirement();
        addSecurityItem.addList("Authorization");

        return new OpenAPI()
                // Security 인증 컴포넌트 설정
                .components(new Components().addSecuritySchemes("Authorization", bearerAuth))
                // API 마다 Security 인증 컴포넌트 설정
                .addSecurityItem(addSecurityItem)
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("Moa Moa Swagger API")
                .description("이 문서는 테스트하지 않은 API과 함께 명세 되어있어 안정성을 보장하지 않습니다.\n" +
                        "테스트까지 완료된 안전한 API 명세서를 확인하고 싶으시다면 http://localhost:8080/swagger/swagger-ui.html 로,\n" +
                        "RestDocs를 이용한 API 명세서를 확인하고 싶으시다면 http://localhost:8080/docs/index.html 로 접속해주세요.")
                .version("1.0.0");
    }
    static {
        SpringDocUtils.getConfig().addAnnotationsToIgnore(LoginUser.class);
    }
}
