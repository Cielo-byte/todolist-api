package com.maricielo.todolist.controller;

import com.maricielo.todolist.dto.AuthRequest;
import com.maricielo.todolist.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<?> registro(@RequestBody AuthRequest request){
        String token= usuarioService.registrar(request.getNombre(), request.getEmail(), request.getPassword());

        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request){
        String token= usuarioService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(Map.of("token", token));
    }
}
