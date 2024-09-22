package com.sunniesfish.todo_app.todo.service;

import com.sunniesfish.todo_app.todo.entity.Board;
import com.sunniesfish.todo_app.todo.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BoardCRUDServiceImpl implements BoardCRUDService {

    private BoardRepository boardRepository;

    @Transactional
    @Override
    public Board create(Board entity) {
        return boardRepository.save(entity);
    }

    @Transactional
    @Override
    public Optional<Board> findById(Long aLong) {
        return boardRepository.findById(aLong);
    }

    @Transactional
    @Override
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Transactional
    @Override
    public Board update(Long aLong, Board entity) {
        return boardRepository.save(entity);
    }

    @Transactional
    @Override
    public void delete(Long aLong) {
        boardRepository.deleteById(aLong);
    }

    @Transactional
    @Override
    public List<Board> getBoardsByUsername(String username) {
        return boardRepository.findAllByUsernameOrderByOrderIndexAsc(username);
    }

    @Transactional
    @Override
    public Optional<Board> getLastBoardByUsername(String username) {
        return boardRepository.findByUsernameOrderByOrderIndexDesc(username);
    }

    @Transactional
    @Override
    public void deleteAllBoardsByUsername(String username) {
        boardRepository.deleteAllByUsername(username);
    }
}
