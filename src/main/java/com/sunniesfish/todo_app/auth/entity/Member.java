package com.sunniesfish.todo_app.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @Column(nullable = false, unique = true)
    private String memberId;

    @Column(nullable = false)
    private String password;
}
