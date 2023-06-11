package sku.moamoa.domain.note.mapper;

import org.springframework.stereotype.Component;
import sku.moamoa.domain.note.dto.NoteDto;
import sku.moamoa.domain.note.entity.Note;
import sku.moamoa.domain.user.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NoteMapper {
    public Note toEntity(User user, NoteDto.CreateRequest body, String noteRoom) {
        return Note.builder()
                .user(user)
                .rUser(body.getUserId().toString())
                .content(body.getContent())
                .noteRoom(noteRoom)
                .build();
    }

    public NoteDto.InfoResponse toCreateResponse(Note note) {
        return NoteDto.InfoResponse.builder()
                .id(note.getId())
                .build();
    }
    public List<NoteDto.DetailInfoResponse> toDetailInfoResponseList(User user, List<Note> noteList) {
        return noteList.stream().map(n -> toDetailInfoResponse(user, n)).collect(Collectors.toList());
    }

    public NoteDto.DetailInfoResponse toDetailInfoResponse(User user, Note note) {
        return NoteDto.DetailInfoResponse.builder()
                .userId(user.getId())
                .content(note.getContent())
                .isSender(user.getId().equals(note.getUser().getId()))
                .createdAt(note.getCreatedAt())
                .build();
    }
}
