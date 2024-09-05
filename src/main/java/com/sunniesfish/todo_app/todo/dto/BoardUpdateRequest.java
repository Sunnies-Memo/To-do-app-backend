package com.sunniesfish.todo_app.todo.dto;

import com.sunniesfish.todo_app.todo.entity.Board;
import lombok.Data;

@Data
public class BoardUpdateRequest {
    private Board board;
    private int gap;
}
