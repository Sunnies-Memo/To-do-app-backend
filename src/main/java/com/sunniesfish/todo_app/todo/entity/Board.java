package com.sunniesfish.todo_app.todo.entity;

import com.sunniesfish.todo_app.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "board")
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardId;

    @Column(nullable = false)
    private long orderIndex;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private long memberId;
}
