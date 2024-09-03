package com.sunniesfish.todo_app.todo.repository;

import com.sunniesfish.todo_app.todo.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
