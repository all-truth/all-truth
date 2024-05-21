package com.alltruth.api.controller;

import com.alltruth.api.dto.UserDTO;
import com.alltruth.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserDTO.UserJoinReq req){
        UserDTO.UserJoinRes res = userService.join(req);

        return ResponseEntity.ok().body(res);
    }
}
