package sku.moamoa.domain.likeboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sku.moamoa.domain.likeboard.entity.LikeBoard;
import sku.moamoa.domain.likeboard.mapper.LikeBoardMapper;
import sku.moamoa.domain.likeboard.repository.LikeBoardRepository;
import sku.moamoa.domain.post.entity.Post;
import sku.moamoa.domain.user.entity.User;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeBoardService {
    private final LikeBoardRepository likeBoardRepository;
    private final LikeBoardMapper likeBoardMapper;

    public void registerLikeBoard(User user, Post post) {
        LikeBoard likeBoard = likeBoardMapper.toEntity(user,post);
        likeBoardRepository.save(likeBoard);
    }
}
