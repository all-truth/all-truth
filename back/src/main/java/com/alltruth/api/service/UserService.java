package com.alltruth.api.service;

import com.alltruth.api.config.common.exceptions.ErrorCode;
import com.alltruth.api.config.common.exceptions.GlobalException;
import com.alltruth.api.config.file.FileUploadUtil;
import com.alltruth.api.config.security.SecurityConfig;
import com.alltruth.api.dto.UserDTO;
import com.alltruth.api.entity.ReviewImage;
import com.alltruth.api.entity.User;
import com.alltruth.api.entity.UserImage;
import com.alltruth.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final FileUploadUtil fileUploadUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Transactional
    public UserDTO.UserJoinRes join(UserDTO.UserJoinReq userJoinReq){

        if(userRepository.findByLoginId(userJoinReq.getLoginId()).isPresent()) throw new GlobalException(ErrorCode.ID_ALREADY_EXIST);

        User user = User.builder()
                .loginId(userJoinReq.getLoginId())
                .password(bCryptPasswordEncoder.encode(userJoinReq.getPassword()))
                .roles("ROLE_USER")
                .nickname(userJoinReq.getNickname())
                .build();


        userRepository.save(user);

        return new UserDTO.UserJoinRes("회원가입 완료!");
    }

    @Transactional(readOnly = true)
    public UserDTO.UserInfoRes getUserInfo(){
        User user = userRepository.findById(SecurityConfig.getUserId()).orElseThrow(()->new GlobalException(ErrorCode.USER_NOT_FOUND));

        return new UserDTO.UserInfoRes().toUserInfoResByUser(user);
    }

    @Transactional
    public UserDTO.UserInfoRes updateUser(String nickname,
                           String password,
                           String passwordConfirm,
                           MultipartFile image){

        User user = userRepository.findById(SecurityConfig.getUserId())
                .orElseThrow(()->new GlobalException(ErrorCode.USER_NOT_FOUND));

        if(nickname != null){
            user.updateNickname(nickname);
        }
        if(password != null){
            if(!password.equals(passwordConfirm)) throw new GlobalException(ErrorCode.PASSWORD_CONFIRM_NOT_MATCH);

            user.updatePassword(bCryptPasswordEncoder.encode(password));
        }
        if(image != null){
            if(user.getImage() != null) { // 기존 이미지가 존재하면 삭제
                fileUploadUtil.delete(user.getImage().getName());
            }

            Path filePath = fileUploadUtil.store(image);
            String fileName = filePath.getFileName().toString();
            UserImage userImage = UserImage.builder()
                    .name(fileName)
                    .user(user)
                    .url("http://localhost:8080/user/img/" + fileName)
                    .build();
        }

        return new UserDTO.UserInfoRes().toUserInfoResByUser(user);
    }

    public Resource getImage(String fileName){
        Resource res = fileUploadUtil.loadAsResource(fileName);
        return res;
    }

}
