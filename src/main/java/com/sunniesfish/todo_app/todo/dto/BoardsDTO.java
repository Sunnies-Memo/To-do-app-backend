package com.sunniesfish.todo_app.todo.dto;

import com.sunniesfish.todo_app.todo.entity.ToDo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardsDTO {
    private long boardId;
    private String username;
    private long orderIndex;
    private String title;
    private List<ToDo> toDoList;
}
