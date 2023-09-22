package sku.moamoa.domain.post.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import sku.moamoa.domain.post.dto.PostDto;
import sku.moamoa.domain.post.entity.Post;
import sku.moamoa.domain.post.service.PostService;
import sku.moamoa.domain.user.repository.UserRepository;
import sku.moamoa.global.entity.BaseTestEntity;
import sku.moamoa.global.security.SecurityConfig;
import sku.moamoa.global.security.SecurityUtil;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sku.moamoa.fixture.PostFixtures.post1;

@WebMvcTest(controllers = PostController.class)
class PostControllerTest extends BaseTestEntity {
    @MockBean private PostService postService;
    @MockBean private SecurityUtil securityUtil;
    @MockBean private UserRepository userRepository;
    @MockBean private RedisTemplate<String, Object> restTemplate;

    @Test
    void getPosts() throws Exception{
        // given
        var parameterDescriptors = new ParameterDescriptor[]{
                parameterWithName("page").description("페이지 번호"),
                parameterWithName("position").description("개발 포지션"),
                parameterWithName("language").description("기술 스택"),
                parameterWithName("search").description("검색 키워드")
        };

        // 결과 데이터 생성
        List<PostDto.GetPostsResponse> postList = new LinkedList<>();
        postList.add(post1);
        PageRequest pageRequest = PageRequest.of(0,6);
        Page<PostDto.GetPostsResponse> result = new PageImpl<>(postList, pageRequest, 1L);

        // when
        when(postService.findAllPostByTechStackNames(1,"","","")).thenReturn(result);

        // then
        mvc.perform(RestDocumentationRequestBuilders.get("/api/v1/posts/all")
                                .param("page", "1")
                                .param("position", "")
                                .param("language", "")
                                .param("search", "")
                        )
                .andExpect(status().isOk())
                .andDo(print())
                // rest docs 설정
                .andDo(restDocs.document(
                        requestParameters(
                                parameterDescriptors
                        )
                ))
//                // OAS 설정
                .andDo(MockMvcRestDocumentationWrapper.document("{class-name}/{method-name}",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestParameters(
                                parameterDescriptors
                        )
                ))

        ;
    }

//    @Test
//    void getPost()  throws Exception{
//    }
//
//    @Test
//    void createPost()  throws Exception{
//    }
//
//    @Test
//    void updatePost()  throws Exception{
//    }
//
//    @Test
//    void deletePost()  throws Exception{
//    }
}