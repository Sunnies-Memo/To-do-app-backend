package com.sunniesfish.todo_app.todo.service;

import com.sunniesfish.todo_app.todo.entity.ToDo;
import com.sunniesfish.todo_app.todo.repository.ToDoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ToDoCRUDServiceImpl implements ToDoCRUDService {

    private ToDoRepository toDoRepository;

    @Transactional
    @Override
    public ToDo create(ToDo entity) {
        return toDoRepository.save(entity);
    }

    @Transactional
    @Override
    public Optional<ToDo> findById(Long aLong) {
        return toDoRepository.findById(aLong);
    }

    @Transactional
    @Override
    public List<ToDo> findAll() {
        return toDoRepository.findAll();
    }

    @Transactional
    @Override
    public ToDo update(Long aLong, ToDo entity) {
        return toDoRepository.save(entity);
    }

    @Transactional
    @Override
    public void delete(Long aLong) {
        toDoRepository.deleteById(aLong);
    }

    @Transactional
    @Override
    public List<ToDo> getAllToDosByBoardId(Long boardId) {
        return toDoRepository.findByBoard_BoardIdOrderByOrderIndexAsc(boardId);
    }

    @Transactional
    @Override
    public void deleteAllToDoByBoardId(Long boardId) {
        toDoRepository.deleteAllByBoard_BoardId(boardId);
    }
}
