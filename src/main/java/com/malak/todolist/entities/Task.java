package com.malak.todolist.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.malak.todolist.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;
    
    @Enumerated(EnumType.STRING)
    private Status status;
    
    private LocalDateTime dueDate;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    private TodoList list;

    @Builder
    public Task(String title, String description, Status status, LocalDateTime dueDate, TodoList list) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.list = list;
    }

    @PrePersist 
    void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != getClass()) return false;
        Task task = (Task) obj;

        return id != null && id.equals(task.getId());
    }

    @Override
    public int hashCode() {
        return id != null? id.hashCode():0;
    }
}
