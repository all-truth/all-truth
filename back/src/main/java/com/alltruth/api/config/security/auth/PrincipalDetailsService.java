package com.alltruth.api.config.security.auth;

import com.alltruth.api.entity.User;
import com.alltruth.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException{
        User user = userRepository.findByLoginId(loginId).orElseThrow(()->new UsernameNotFoundException("유저 정보가 없음"));
        return new PrincipalDetails(user);
    }
}
