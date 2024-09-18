package com.sunniesfish.todo_app.auth.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor
public class Member {


    @Id
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String profileImg;

    @Column(nullable = true)
    private String bgImg;
}
