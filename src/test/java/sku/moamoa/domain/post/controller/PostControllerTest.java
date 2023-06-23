package sku.moamoa.domain.post.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import sku.moamoa.domain.comment.dto.CommentDto;
import sku.moamoa.domain.post.dto.PostDto;
import sku.moamoa.domain.post.service.PostService;
import sku.moamoa.domain.user.repository.UserRepository;
import sku.moamoa.global.entity.BaseTestEntity;
import sku.moamoa.global.security.SecurityUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sku.moamoa.fixture.CommentFixtures.*;
import static sku.moamoa.fixture.PostFixtures.posts1;
import static sku.moamoa.fixture.TechStackFixtures.techStackArray;
import static sku.moamoa.fixture.UserFixtures.mainUserInfoResponse;

@WebMvcTest(controllers = PostController.class)
class PostControllerTest extends BaseTestEntity {
    @MockBean private PostService postService;
    @MockBean private SecurityUtil securityUtil;
    @MockBean private UserRepository userRepository;
    @MockBean private RedisTemplate<String, Object> restTemplate;

    @Test
    void getPosts() throws Exception{
        // given
        // 파라미터 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", "1");
        params.add("position", "");
        params.add("language", "");
        params.add("search", "");

        ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[] {
                parameterWithName("page").optional().description("페이지 번호"),
                parameterWithName("position").optional().description("개발 포지션"),
                parameterWithName("language").optional().description("기술 스택"),
                parameterWithName("search").optional().description("검색 키워드")
        };

        // 결과 데이터 생성
        List<PostDto.GetPostsResponse> postList = new LinkedList<>();
        postList.add(posts1);
        Pageable pageable = PageRequest.of(0,6);
        Page<PostDto.GetPostsResponse> result = new PageImpl<>(postList, pageable, 1L);

        // when
        when(postService.findAllPostByTechStackNames(1,"","","")).thenReturn(result);

        ResultActions resultActions = mvc.perform(RestDocumentationRequestBuilders.get("/api/v1/posts/all")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParams(params)
                );
        // then
        resultActions
                .andExpect(status().isOk())
                .andDo(print())
                // RestDocs 설정
                .andDo(restDocs.document(
                        requestParameters(
                                parameterDescriptors
                        )
                ))
                // OAS 설정
                .andDo(MockMvcRestDocumentationWrapper.document("{class-name}/{method-name}",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestParameters(
                                parameterDescriptors
                        )
                ))
        ;
    }

    @Test
    void getPost()  throws Exception{
        // given
        // 결과 데이터 생성
        List<CommentDto.InfoResponse> commentList = new ArrayList<>();
        commentList.add(comment1);
        commentList.add(comment2);
        commentList.add(comment3);

        PostDto.GetPostResponse result = PostDto.GetPostResponse.builder()
                .id(1L)
                .title("같이 할 사람 모집합니다.")
                .projectName("같이해요")
                .content("실력 상관 없이 같이 프로젝트 하고 싶으신 분은 댓글 달아주세요!")
                .deadline(LocalDateTime.now())
                .headcount(3)
                .jobTag(new String[] {"벡엔드","프론트엔드"})
                .user(mainUserInfoResponse)
                .techStackList(Arrays.asList(techStackArray))
                .commentList(commentList)
                .createdAt(LocalDateTime.now())
                .build();

        // when
        when(postService.findPostById(1L)).thenReturn(result);

        ResultActions resultActions = mvc.perform(RestDocumentationRequestBuilders.get("/api/v1/posts/{pid}","1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer {ACCESS_TOKEN}")
                );
                // then
        resultActions
                .andExpect(status().isOk())
                .andDo(print())
                // RestDocs 설정
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer AccessToken")
                        ),
                        pathParameters(
                                parameterWithName("pid").description("게시물의 id")
                        )
                ))
                // OAS 설정
                .andDo(MockMvcRestDocumentationWrapper.document("{class-name}/{method-name}",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
//                        requestHeaders(
//                                headerWithName("Authorization").description("Bearer AccessToken")
//                        ),
                        pathParameters(
                                parameterWithName("pid").description("게시물의 id")
                        )
                ))
        ;
    }
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