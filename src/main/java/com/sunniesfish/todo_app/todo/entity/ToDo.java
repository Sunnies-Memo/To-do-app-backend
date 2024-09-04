package com.sunniesfish.todo_app.todo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@Table(name = "todo")
@NoArgsConstructor
@AllArgsConstructor
public class ToDo {

    @Id
    @Column(name = "todo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long todoId;

    @Column(nullable = false)
    private long index;

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}
