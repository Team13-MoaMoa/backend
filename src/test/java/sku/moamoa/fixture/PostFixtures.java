package sku.moamoa.fixture;

import sku.moamoa.domain.post.dto.PostDto;
import java.time.LocalDateTime;

import java.util.Arrays;


import static sku.moamoa.fixture.TechStackFixtures.*;
import static sku.moamoa.fixture.UserFixtures.mainUserInfoResponse;


public class PostFixtures {
    public static final PostDto.GetPostsResponse posts1 =
            PostDto.GetPostsResponse.builder()
                    .id(1L)
                    .title("같이 할 사람 모집합니다.")
                    .projectName("같이해요")
                    .content("실력 상관 없이 같이 프로젝트 하고 싶으신 분은 댓글 달아주세요!")
                    .deadline(LocalDateTime.now())
                    .headcount(3)
                    .jobTag(new String[] {"벡엔드","프론트엔드"})
                    .user(mainUserInfoResponse)
                    .techStackList(Arrays.asList(techStackArray))
                    .commentCount(0)
                    .createdAt(LocalDateTime.now())
                    .build();


}
