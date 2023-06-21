package sku.moamoa.global.entity;
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import sku.moamoa.global.config.RestDocsConfig;

@Import(RestDocsConfig.class)
@ExtendWith(RestDocumentationExtension.class)
public class BaseTestEntity {
    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected RestDocumentationResultHandler restDocs;
    @Autowired
    protected ObjectMapper objectMapper;

    @BeforeEach
    void setUp(
            final WebApplicationContext context,
            final RestDocumentationContextProvider provider
    )
    {
        this.mvc = MockMvcBuilders.webAppContextSetup(context) // mvc 설정
                .apply(MockMvcRestDocumentation.documentationConfiguration(provider)) // restDocs 설정
                .alwaysDo(restDocs)
                .build();
    }
}
