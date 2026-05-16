package com.maricielo.todolist.repository;

import com.maricielo.todolist.entity.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
    List<Tarea> findByUsuarioId(Long usuarioId);
}
