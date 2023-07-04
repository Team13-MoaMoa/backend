package sku.moamoa.domain.user.controller;

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
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import sku.moamoa.domain.likeboard.service.LikeBoardService;
import sku.moamoa.domain.post.dto.PostDto;
import sku.moamoa.domain.post.entity.Post;
import sku.moamoa.domain.post.service.PostService;
import sku.moamoa.domain.user.dto.SignUpRequest;
import sku.moamoa.domain.user.dto.UserDto;
import sku.moamoa.domain.user.entity.AuthProvider;
import sku.moamoa.domain.user.repository.UserRepository;
import sku.moamoa.domain.user.service.UserService;
import sku.moamoa.global.entity.BaseTestEntity;
import sku.moamoa.global.resolver.LoginUserArgumentResolver;
import sku.moamoa.global.security.SecurityUtil;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sku.moamoa.fixture.PostFixtures.posts1;
import static sku.moamoa.fixture.UserFixtures.*;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest extends BaseTestEntity {
    @MockBean private UserService userService;
    @MockBean private PostService postService;
    @MockBean private LikeBoardService likeBoardService;
    @MockBean private SecurityUtil securityUtil;
    @MockBean private UserRepository userRepository;
    @MockBean private RedisTemplate<String, Object> restTemplate;
    @MockBean private LoginUserArgumentResolver loginUserArgumentResolver;

    @Test
    void getUser() throws Exception {
        // then
        // given
        UserDto.DetailInfoResponse userDetail = UserDto.DetailInfoResponse.builder()
                .id(1L)
                .nickname("송회원")
                .imageUrl("www.songUserImageUrl.com")
                .portfolioUrl("www.songUserPortFolioUrl.com")
                .githubUrl("www.songUserGithubUrl.com")
                .build();
        // when
        given(loginUserArgumentResolver.resolveArgument(any(),any(),any(),any())).willReturn(mainUser);
        when(userService.findUserById(any())).thenReturn(userDetail);

        ResultActions resultActions = mvc.perform(RestDocumentationRequestBuilders.get("/api/v1/users/{uid}",1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer {ACCESS_TOKEN}")
        );
        resultActions
                .andExpect(status().isOk())
                .andDo(print())
                // RestDocs 설정
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer AccessToken")
                        ),
                        pathParameters(
                                parameterWithName("uid").description("로그인한 유저의 id")
                        )
                ))
                // OAS 설정
                .andDo(MockMvcRestDocumentationWrapper.document("{class-name}/{method-name}",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer AccessToken")
                        ),
                        pathParameters(
                                parameterWithName("uid").description("로그인한 유저의 id")
                        )
                ))
        ;
    }

    @Test
    void getMyPosts() throws Exception {
        // given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", "1");

        ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[] {
                parameterWithName("page").optional().description("페이지 번호"),
        };

        // 결과 데이터 생성
        List<PostDto.GetPostsResponse> postList = new LinkedList<>();
        postList.add(posts1);
        Pageable pageable = PageRequest.of(0,6);
        Page<PostDto.GetPostsResponse> result = new PageImpl<>(postList, pageable, 1L);

        // when
        given(loginUserArgumentResolver.resolveArgument(any(),any(),any(),any())).willReturn(mainUser);
        when(postService.findPostByUser(anyInt(), any())).thenReturn(result);

        ResultActions resultActions = mvc.perform(RestDocumentationRequestBuilders.get("/api/v1/users/projects")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .queryParams(params)
                .header("Authorization", "Bearer {ACCESS_TOKEN}")
        );
        resultActions
                .andExpect(status().isOk())
                .andDo(print())
                // RestDocs 설정
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer AccessToken")
                        ),
                        requestParameters( parameterDescriptors )
                ))
                // OAS 설정
                .andDo(MockMvcRestDocumentationWrapper.document("{class-name}/{method-name}",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer AccessToken")
                        ),
                        requestParameters( parameterDescriptors)
                        ))
        ;
    }

    @Test
    void getMyPostsByLike() throws Exception {
        // given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("page", "1");

        ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[] {
                parameterWithName("page").optional().description("페이지 번호"),
        };

        // 결과 데이터 생성
        List<PostDto.GetPostsResponse> postList = new LinkedList<>();
        postList.add(posts1);
        Pageable pageable = PageRequest.of(0,6);
        Page<PostDto.GetPostsResponse> result = new PageImpl<>(postList, pageable, 1L);

        // when
        given(loginUserArgumentResolver.resolveArgument(any(),any(),any(),any())).willReturn(mainUser);
        when(likeBoardService.findAllByUser(anyInt(), any())).thenReturn(result);

        ResultActions resultActions = mvc.perform(RestDocumentationRequestBuilders.get("/api/v1/users/likes")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .queryParams(params)
                .header("Authorization", "Bearer {ACCESS_TOKEN}")
        );
        resultActions
                .andExpect(status().isOk())
                .andDo(print())
                // RestDocs 설정
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer AccessToken")
                        ),
                        requestParameters( parameterDescriptors )
                ))
                // OAS 설정
                .andDo(MockMvcRestDocumentationWrapper.document("{class-name}/{method-name}",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer AccessToken")
                        ),
                        requestParameters( parameterDescriptors)
                ))
        ;
    }

    @Test
    void like() throws Exception {
        // given
        Post post = Post.builder()
                .title("같이 할 사람 모집합니다.")
                .projectName("같이해요")
                .content("실력 상관 없이 같이 프로젝트 하고 싶으신 분은 댓글 달아주세요!")
                .deadline(LocalDateTime.now())
                .headcount(3)
                .jobTag(new String[] {"벡엔드","프론트엔드"})
                .user(mainUser)
                .build();
        // when
        given(loginUserArgumentResolver.resolveArgument(any(),any(),any(),any())).willReturn(mainUser);
        when(postService.findById(any())).thenReturn(post);

        ResultActions resultActions = mvc.perform(RestDocumentationRequestBuilders.post("/api/v1/users/likes/{pid}",1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer {ACCESS_TOKEN}")
        );
        resultActions
                .andExpect(status().isOk())
                .andDo(print())
                // RestDocs 설정
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer AccessToken")
                        ),
                        pathParameters( parameterWithName("pid").description("게시물의 id"))
                ))
                // OAS 설정
                .andDo(MockMvcRestDocumentationWrapper.document("{class-name}/{method-name}",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer AccessToken")
                        ),
                        pathParameters( parameterWithName("pid").description("게시물의 id"))
                ))
        ;

    }

    @Test
    void createUser() throws Exception {
        // given
        FieldDescriptor[] fieldDescriptors = new FieldDescriptor[]{
                fieldWithPath("id").type(JsonFieldType.STRING).description("유저 아이디"),
                fieldWithPath("nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
                fieldWithPath("email").type(JsonFieldType.STRING).description("유저 이메일"),
                fieldWithPath("auth_provider").type(JsonFieldType.STRING).description("사용한 소셜 로그인 서비스 종류"),
                fieldWithPath("image_url").type(JsonFieldType.STRING).description("유저 이미지 url"),
                fieldWithPath("port_folio_url").type(JsonFieldType.STRING).description("유저 포트폴리오 url"),
                fieldWithPath("github_url").type(JsonFieldType.STRING).description("유저 깃허브 url")
        };

        SignUpRequest signUpRequest = SignUpRequest.builder()
                .id("1")
                .nickname("송회원")
                .email("songuser@naver.com")
                .authProvider(AuthProvider.KAKAO)
                .imageUrl("www.songUserImageUrl.com")
                .portFolioUrl("www.songUserPortFolioUrl.com")
                .githubUrl("www.songUserGithubUrl.com")
                .build();


        // when
        when(userService.createUser(any())).thenReturn(1L);

        ResultActions resultActions = mvc.perform(RestDocumentationRequestBuilders.post("/api/v1/users/signup")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpRequest))
        );
        resultActions
                .andExpect(status().isOk())
                .andDo(print())
                // RestDocs 설정
                .andDo(restDocs.document(
                        requestFields(fieldDescriptors)
                ))
                // OAS 설정
                .andDo(MockMvcRestDocumentationWrapper.document("{class-name}/{method-name}",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestFields(fieldDescriptors)
                ))
        ;

    }
}