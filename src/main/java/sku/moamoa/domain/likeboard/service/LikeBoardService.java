package sku.moamoa.domain.likeboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sku.moamoa.domain.likeboard.entity.LikeBoard;
import sku.moamoa.domain.likeboard.mapper.LikeBoardMapper;
import sku.moamoa.domain.likeboard.repository.LikeBoardRepository;
import sku.moamoa.domain.post.dto.PostDto;
import sku.moamoa.domain.post.entity.Post;
import sku.moamoa.domain.post.mapper.PostMapper;
import sku.moamoa.domain.user.entity.User;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeBoardService {
    private final LikeBoardRepository likeBoardRepository;
    private final LikeBoardMapper likeBoardMapper;
    private final PostMapper postMapper;

    public void registerLikeBoard(User user, Post post) {
        LikeBoard likeBoard = likeBoardMapper.toEntity(user,post);
        likeBoardRepository.save(likeBoard);
    }

    public Page<PostDto.GetPostsResponse> findAllByUser(int page, User user) {
        PageRequest pageRequest = PageRequest.of(page-1,6, Sort.by(Sort.Direction.DESC, "id"));
        Page<LikeBoard> likeBoardList = likeBoardRepository.findAllByUser(pageRequest, user);
        return postMapper.toGetPostResponseDto(likeBoardList);
    }
}
