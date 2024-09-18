package com.sunniesfish.todo_app.auth.repository;

import com.sunniesfish.todo_app.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByUsername(String username);
}
