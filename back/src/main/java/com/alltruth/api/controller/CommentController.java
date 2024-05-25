package com.alltruth.api.controller;

import com.alltruth.api.dto.CommentDTO;
import com.alltruth.api.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/review/{id}/comment")
    public ResponseEntity<List<CommentDTO.CommentRes>> getComments(@PathVariable Long id){
        List<CommentDTO.CommentRes> res = commentService.getCommentsById(id);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/review/{id}/comment")
    public ResponseEntity<CommentDTO.CommentRes> postComment(@PathVariable Long id, @RequestBody CommentDTO.CommentReq req){
        CommentDTO.CommentRes res = commentService.writeComment(id, req);

        return ResponseEntity.ok(res);
    }
}
