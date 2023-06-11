package sku.moamoa.domain.note.mapper;

import org.springframework.stereotype.Component;
import sku.moamoa.domain.note.dto.NoteDto;
import sku.moamoa.domain.note.entity.Note;
import sku.moamoa.domain.user.entity.User;

@Component
public class NoteMapper {
    public Note toEntity(User user, NoteDto.CreateRequest body) {
        return Note.builder()
                .user(user)
                .rUser(body.getUserId().toString())
                .content(body.getContent())
                .noteRoom(makeNoteRoomName(user.getId(), body.getUserId()))
                .build();
    }

    public NoteDto.InfoResponse toCreateResponse(Note note) {
        return NoteDto.InfoResponse.builder()
                .id(note.getId())
                .build();
    }

    private String makeNoteRoomName(Long u1, Long u2) {
        // 작은게 앞으로, 결과 예시 : "(123),(789)"
        boolean flag = u1 < u2 ? true : false;
        if(flag) return "(" + Long.toString(u1) + "),(" + Long.toString(u2) + ")";
        else return "(" + Long.toString(u2) + "),(" + Long.toString(u1) + ")";
    }
}
