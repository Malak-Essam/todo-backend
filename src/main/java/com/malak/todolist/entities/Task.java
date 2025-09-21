package com.malak.todolist.entities;

import java.util.UUID;

import com.malak.todolist.enums.Status;

public class Task {
    private String id;
    private String title;
    private String description;
    private Status status;
    private UUID todoListId;
}
