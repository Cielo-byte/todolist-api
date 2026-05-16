package com.maricielo.todolist.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tareas")
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    private String descripcion;

    private boolean completada=false;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}
