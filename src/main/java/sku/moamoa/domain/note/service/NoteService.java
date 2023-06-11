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
import sku.moamoa.domain.user.mapper.UserMapper;
import sku.moamoa.domain.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserService userService;
    private final NoteMapper noteMapper;
    private final UserMapper userMapper;

    public List<UserDto.InfoResponse> findUsersByNoteRoom(User user) {
        String noteRoom = "(" + Long.toString(user.getId()) + ")";
        List<Note> noteList = noteRepository.findUsersByNoteRoom(noteRoom);
        List<User> userList = toUserList(user, noteList);
        return userMapper.toUserInfoResDtoList(userList);
    }

    public NoteDto.InfoResponse registerNote(User user, NoteDto.CreateRequest body) {
        Note note = noteMapper.toEntity(user, body);
        return noteMapper.toCreateResponse(noteRepository.save(note));
    }

    private List<User> toUserList(User user, List<Note> noteList) { // userMapper에 userService를 DI 시 bean 사이클 발생, 사이클 회피를 위해 따로 뺌
        return noteList.stream().map(n -> toUser(user, n)).collect(Collectors.toList());
    }

    private User toUser(User user, Note note) {
        return user.equals(note.getUser()) ? userService.findById(Long.parseLong(note.getRUser())) : user;
    }
}
