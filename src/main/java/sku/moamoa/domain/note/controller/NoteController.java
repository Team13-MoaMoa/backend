package sku.moamoa.domain.note.controller;

import io.swagger.annotations.Api;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sku.moamoa.domain.note.dto.NoteDto;
import sku.moamoa.domain.note.service.NoteService;
import sku.moamoa.domain.user.entity.User;
import sku.moamoa.global.annotation.LoginUser;
import sku.moamoa.global.result.ResultCode;
import sku.moamoa.global.result.ResultResponse;

@Api(tags = "쪽지 API")
@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@RequestMapping("/api/v1/notes")
public class NoteController {
    private final NoteService noteService;

//    @GetMapping
//    public ResponseEntity<ResultResponse> getNoteList(@LoginUser User user) {
//
//    }

    @PostMapping
    public ResponseEntity<ResultResponse> createNote(@LoginUser User user, NoteDto.CreateRequest body) {
        NoteDto.InfoResponse createNoteResponse = noteService.registerNote(user, body);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.NOTE_CREATE_SUCCESS, createNoteResponse));

    }
}
