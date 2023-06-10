package sku.moamoa.domain.note.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sku.moamoa.domain.note.dto.NoteDto;
import sku.moamoa.domain.note.entity.Note;
import sku.moamoa.domain.note.mapper.NoteMapper;
import sku.moamoa.domain.note.repository.NoteRepository;
import sku.moamoa.domain.user.dto.UserDto;
import sku.moamoa.domain.user.entity.User;

@Service
@Transactional
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

//    public List<UserDto.InfoResponse> findUsersByUserId() {
//    }

    public NoteDto.InfoResponse registerNote(User user, NoteDto.CreateRequest body) {
        Note note = noteMapper.toEntity(user, body);
        return noteMapper.toCreateResponse(noteRepository.save(note));
    }


}
