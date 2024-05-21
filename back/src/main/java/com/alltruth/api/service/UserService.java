package com.alltruth.api.service;

import com.alltruth.api.dto.UserDTO;
import com.alltruth.api.entity.User;
import com.alltruth.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserDTO.UserJoinRes join(UserDTO.UserJoinReq userJoinReq){
        User checkUser = userRepository.findByLoginId(userJoinReq.getLoginId()).orElse(null);

        if(checkUser != null){
            //throw new Error("아이디가 중복 됐습니다!");
            return new UserDTO.UserJoinRes("아이디가 중복 됐습니다!");
        }

        User user = User.builder()
                .loginId(userJoinReq.getLoginId())
                .password(bCryptPasswordEncoder.encode(userJoinReq.getPassword()))
                .roles("ROLE_USER")
                .build();


        userRepository.save(user);

        return new UserDTO.UserJoinRes("회원가입 완료!");
    }
}
