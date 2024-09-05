package com.sunniesfish.todo_app.todo.repository;

import com.sunniesfish.todo_app.todo.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByMemberIdOrderByOrderIndexAsc(Long memberId);
}
