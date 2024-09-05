package com.sunniesfish.todo_app.todo.dto;

import com.sunniesfish.todo_app.todo.entity.ToDo;
import lombok.Data;

@Data
public class ToDoUpdateRequest {
    private ToDo todo;
    private int gap;
}
