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
        return null;
    }

    @Override
    public Optional<ToDo> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<ToDo> findAll() {
        return List.of();
    }

    @Override
    public ToDo update(Long aLong, ToDo entity) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
