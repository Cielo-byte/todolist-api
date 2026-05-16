package com.maricielo.todolist.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String nombre;
    private String email;
    private String password;
}
