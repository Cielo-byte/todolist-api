package com.maricielo.todolist.controller;

import com.maricielo.todolist.dto.TareaRequest;
import com.maricielo.todolist.entity.Tarea;
import com.maricielo.todolist.entity.Usuario;
import com.maricielo.todolist.repository.TareaRepository;
import com.maricielo.todolist.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
@RequiredArgsConstructor
public class TareaController {
    private final TareaRepository tareaRepository;
    private final UsuarioRepository usuarioRepository;

    private Usuario getUsuario(String email){
        return usuarioRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("Usuario no encontrado"));
    }

    @GetMapping
    public List<Tarea> listar(@AuthenticationPrincipal String email){
        return tareaRepository.findByUsuarioId(getUsuario(email).getId());
    }

    @PostMapping
    public Tarea crear(@RequestBody TareaRequest tareaRequest,
                       @AuthenticationPrincipal String email){
        Tarea tarea = new Tarea();
        tarea.setTitulo(tareaRequest.getTitulo());
        tarea.setDescripcion(tareaRequest.getDescripcion());
        tarea.setUsuario(getUsuario(email));

        return tareaRepository.save(tarea);
    }

    @PutMapping("/{id}/completar")
    public ResponseEntity<?> completar(@PathVariable Long id,
                                       @AuthenticationPrincipal String email){
        Tarea tarea= tareaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Tarea no encontrada"));
        if(!tarea.getUsuario().getEmail().equals(email)){
            return ResponseEntity.status(403).body("No autorizado");
        }
        tarea.setCompletada(true);
        return ResponseEntity.ok(tareaRepository.save(tarea));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id,
                                      @AuthenticationPrincipal String email){
        Tarea tarea=tareaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Tarea no encontrada"));
        if(!tarea.getUsuario().getEmail().equals(email)){
            return ResponseEntity.status(403).body("No autorizado");
        }
        tareaRepository.delete(tarea);
        return ResponseEntity.ok("Tarea eliminada");
    }
}
