package sku.moamoa.domain.note.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sku.moamoa.domain.user.entity.User;
import sku.moamoa.global.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Note extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
    @Column(name = "r_user_id")
    private String rUser;
    @Column(name = "noteRoom")
    private String noteRoom;
    @Column(name = "content")
    private String content;

    @Builder
    public Note(User user, String rUser, String noteRoom, String content) {
        this.user = user;
        this.rUser = rUser;
        this.noteRoom = noteRoom;
        this.content = content;
    }
}
