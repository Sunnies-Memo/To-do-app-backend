package com.sunniesfish.todo_app.auth.service;

import com.sunniesfish.todo_app.auth.entity.Member;
import com.sunniesfish.todo_app.global.service.CRUDService;

import java.util.Optional;


public interface MemberCRUDService extends CRUDService<Member, String> {
    Optional<Member> findById(String username);
    Optional<Member> findByUsername(String username);
}
