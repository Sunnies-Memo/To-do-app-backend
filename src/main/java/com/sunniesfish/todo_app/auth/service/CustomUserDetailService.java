package com.sunniesfish.todo_app.auth.service;

import com.sunniesfish.todo_app.auth.entity.Member;
import com.sunniesfish.todo_app.auth.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                member.getUsername(),
                member.getPassword(),
                new ArrayList<>()
        );
    }
}
