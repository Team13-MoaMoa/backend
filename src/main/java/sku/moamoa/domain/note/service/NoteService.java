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

    public NoteDto.InfoResponse registerNote(User user, NoteDto.CreateRequest body) {
        String noteRoom = makeNoteRoomName(user.getId(), body.getUserId());
        Note note = noteMapper.toEntity(user, body, noteRoom);
        return noteMapper.toCreateResponse(noteRepository.save(note));
    }

    public List<UserDto.InfoResponse> findNotesByNoteRoom(User user) {
        String noteRoom = "(" + Long.toString(user.getId()) + ")";
        List<Note> noteList = noteRepository.findNotesByNoteRoom(noteRoom);
        List<User> userList = toUserList(user, noteList);
        return userMapper.toUserInfoResDtoList(userList);
    }

    public List<NoteDto.DetailInfoResponse> findAllByNoteRoom(User user, Long uid) {
        String noteRoom = makeNoteRoomName(user.getId(), uid);
        List<Note> noteList = noteRepository.findAllByNoteRoom(noteRoom);
        return noteMapper.toDetailInfoResponseList(user, noteList);

    }

    private String makeNoteRoomName(Long u1, Long u2) {
        // 작은게 앞으로, 결과 예시 : "(123),(789)"
        boolean flag = u1 < u2 ? true : false;
        if(flag) return "(" + Long.toString(u1) + "),(" + Long.toString(u2) + ")";
        else return "(" + Long.toString(u2) + "),(" + Long.toString(u1) + ")";
    }

    private List<User> toUserList(User user, List<Note> noteList) { // userMapper에 userService를 DI 시 bean 사이클 발생, 사이클 회피를 위해 따로 뺌
        return noteList.stream().map(n -> toUser(user, n)).collect(Collectors.toList());
    }

    private User toUser(User user, Note note) {
        String userId = note.getNoteRoom().replace("("+user.getId().toString()+")", "").replaceAll("[^0-9]","");
        return userService.findById(Long.parseLong(userId));
    }
}
