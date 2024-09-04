package com.sunniesfish.todo_app.todo.service;

import com.sunniesfish.todo_app.todo.entity.ToDo;
import com.sunniesfish.todo_app.todo.repository.ToDoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ToDoCRUDServiceImpl implements ToDoCRUDService {

    private ToDoRepository toDoRepository;

    @Override
    public ToDo create(ToDo entity) {
        return toDoRepository.save(entity);
    }

    @Override
    public Optional<ToDo> findById(Long aLong) {
        return toDoRepository.findById(aLong);
    }

    @Override
    public List<ToDo> findAll() {
        return toDoRepository.findAll();
    }

    @Override
    public ToDo update(Long aLong, ToDo entity) {
        return toDoRepository.save(entity);
    }

    @Override
    public void delete(Long aLong) {
        toDoRepository.deleteById(aLong);
    }

    @Override
    public List<ToDo> getAllToDosByBoardId(Long boardId) {
        return toDoRepository.findByBoard_BoardIdOrderByIndexAsc(boardId);
    }

    @Override
    public void deleteAllToDoByBoardId(Long boardId) {
        toDoRepository.deleteAllByBoard_BoardId(boardId);
    }
}
