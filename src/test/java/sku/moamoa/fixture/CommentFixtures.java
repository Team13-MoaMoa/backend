package sku.moamoa.fixture;

import sku.moamoa.domain.comment.dto.CommentDto;

import java.time.LocalDateTime;

import static sku.moamoa.fixture.UserFixtures.*;

public class CommentFixtures {
    public static final CommentDto.InfoResponse comment1 = CommentDto.InfoResponse.builder()
            .id(1L)
            .content("좋은 프로젝트 입니다!")
            .user(subUser2)
            .createdAt(LocalDateTime.now())
            .build();
    public static final CommentDto.InfoResponse comment2 = CommentDto.InfoResponse.builder()
            .id(2L)
            .content("저 참여하고 싶습니다! 쪽지 주세요!")
            .user(subUser1)
            .createdAt(LocalDateTime.now())
            .build();
    public static final CommentDto.InfoResponse comment3 = CommentDto.InfoResponse.builder()
            .id(3L)
            .content("아직 자리 남았나요?")
            .user(subUser3)
            .createdAt(LocalDateTime.now())
            .build();


}
