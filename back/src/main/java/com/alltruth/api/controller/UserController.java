package com.alltruth.api.controller;

import com.alltruth.api.dto.UserDTO;
import com.alltruth.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    @PostMapping("/join")
    public ResponseEntity join(@RequestBody UserDTO.UserJoinReq req){
        UserDTO.UserJoinRes res = userService.join(req);

        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/user")
    public ResponseEntity<UserDTO.UserInfoRes> getUserInfo(){
        UserDTO.UserInfoRes res = userService.getUserInfo();
        return ResponseEntity.ok(res);
    }

    @PatchMapping("/user")
    public ResponseEntity<UserDTO.UserInfoRes> updateUserInfo(@RequestPart(value = "nickname", required = false) String nickname,
                                         @RequestPart(value = "password", required = false) String password,
                                         @RequestPart(value = "passwordConfirm", required = false) String passwordConfirm,
                                         @RequestPart(value = "image", required = false) MultipartFile image){
        UserDTO.UserInfoRes res = userService.updateUser(nickname,password,passwordConfirm,image);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/user/img/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable("filename") String fileName) {
        Resource res = userService.getImage(fileName);

        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(res);
    }
}
