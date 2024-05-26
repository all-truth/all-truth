package com.alltruth.api.service;

import com.alltruth.api.config.common.exceptions.ErrorCode;
import com.alltruth.api.config.common.exceptions.GlobalException;
import com.alltruth.api.config.security.SecurityConfig;
import com.alltruth.api.dto.UserDTO;
import com.alltruth.api.entity.User;
import com.alltruth.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
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

    public UserDTO.UserInfoRes getUserInfo(){
        User user = userRepository.findById(SecurityConfig.getUserId()).orElseThrow(()->new GlobalException(ErrorCode.USER_NOT_FOUND));
        return UserDTO.UserInfoRes.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .build();
    }

}
