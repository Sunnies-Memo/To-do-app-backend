package com.sunniesfish.todo_app.auth.service;

import com.sunniesfish.todo_app.auth.entity.Member;
import com.sunniesfish.todo_app.auth.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberCRUDServiceImpl implements MemberCRUDService {

    private MemberRepository memberRepository;

    @Override
    public Member create(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Optional<Member> findById(String username) {
        return memberRepository.findById(username);
    }

    @Override
    public Optional<Member> findByUsername(String username) {
        System.out.println("여기까지 오긴옴??");
        return memberRepository.findByUsername(username);
    }

    @Override
    public List<Member> findAll() {
        return List.of();
    }

    @Override
    public Member update(String username, Member entity) {
        return memberRepository.save(entity);
    }

    @Override
    public void delete(String username) {
        memberRepository.deleteById(username);
    }
}
