package sku.moamoa.domain.note.controller;

import io.swagger.annotations.Api;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sku.moamoa.domain.note.dto.NoteDto;
import sku.moamoa.domain.note.service.NoteService;
import sku.moamoa.domain.user.dto.UserDto;
import sku.moamoa.domain.user.entity.User;
import sku.moamoa.global.annotation.LoginUser;
import sku.moamoa.global.result.ResultCode;
import sku.moamoa.global.result.ResultResponse;

import java.util.List;

@Api(tags = "쪽지 API")
@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api/v1/notes")
public class NoteController {
    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<ResultResponse> getNoteList(@LoginUser User user) {
        List<UserDto.InfoResponse> userInfoList = noteService.findNotesByNoteRoom(user);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_NOTE_LIST_SUCCESS, userInfoList));
    }


    @GetMapping("/{uid}")
    public ResponseEntity<ResultResponse> getNoteDetail(@LoginUser User user, @PathVariable Long uid) {
        List<NoteDto.DetailInfoResponse> noteList = noteService.findAllByNoteRoom(user, uid);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_DETAIL_NOTE_LIST_SUCCESS, noteList));

    }


    @PostMapping
    public ResponseEntity<ResultResponse> createNote(@LoginUser User user, @RequestBody NoteDto.CreateRequest body) {
        NoteDto.InfoResponse createNoteResponse = noteService.registerNote(user, body);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.NOTE_CREATE_SUCCESS, createNoteResponse));
    }


}
