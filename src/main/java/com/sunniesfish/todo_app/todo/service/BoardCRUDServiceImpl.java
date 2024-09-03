package com.sunniesfish.todo_app.todo.service;

import com.sunniesfish.todo_app.todo.entity.Board;
import com.sunniesfish.todo_app.todo.repository.BoardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BoardCRUDServiceImpl implements BoardCRUDService {

    private BoardRepository boardRepository;

    @Override
    public Board create(Board entity) {
        return null;
    }

    @Override
    public Optional<Board> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Board> findAll() {
        return List.of();
    }

    @Override
    public Board update(Long aLong, Board entity) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
