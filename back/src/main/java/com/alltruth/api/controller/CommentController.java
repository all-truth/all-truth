package com.alltruth.api.controller;

import com.alltruth.api.dto.CommentDTO;
import com.alltruth.api.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/review/{id}/comment")
    public ResponseEntity<List<CommentDTO.CommentRes>> getComments(@PathVariable("id") Long id){
        List<CommentDTO.CommentRes> res = commentService.getCommentsById(id);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/review/{id}/comment")
    public ResponseEntity<CommentDTO.CommentRes> postComment(@PathVariable("id") Long id, @RequestBody CommentDTO.CommentReq req){
        CommentDTO.CommentRes res = commentService.writeComment(id, req);

        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/review/comment/{id}")
    public ResponseEntity deleteComment(@PathVariable("id") Long id){
        commentService.deleteComment(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/review/comment/{id}")
    public ResponseEntity<CommentDTO.CommentRes> putComment(@PathVariable("id") Long id, @RequestBody CommentDTO.CommentReq req){
        CommentDTO.CommentRes res = commentService.updateComment(id, req);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/review/comments/user/my")
    public ResponseEntity<List<CommentDTO.CommentRes>> getMyComments(){
        List<CommentDTO.CommentRes> res = commentService.getMyComments();

        return ResponseEntity.ok(res);
    }

    @GetMapping("/review/comments/user/my-paging")
    public ResponseEntity<CommentDTO.PageCommentRes> getMyPagingComments(@RequestParam(name="page", required = false, defaultValue = "1") Integer page,
                                                                           @RequestParam(name="size", required = false, defaultValue = "40") Integer size){
        CommentDTO.PageCommentRes res = commentService.getMyPagingComments(page, size);

        return ResponseEntity.ok(res);
    }
}
