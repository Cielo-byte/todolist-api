package com.maricielo.todolist.service;

import com.maricielo.todolist.config.JwtUtil;
import com.maricielo.todolist.entity.Usuario;
import com.maricielo.todolist.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    //metodo implementado obligatoriamente
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario= usuarioRepository.findByEmail(email).orElseThrow(()
                -> new UsernameNotFoundException("Usuario no encontrado"));
        return new org.springframework.security.core.userdetails.User(
                usuario.getEmail(), usuario.getPassword(), List.of()
        );
    }

    public String registrar(String nombre, String email, String password){
        if (usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException("El email ya esta registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        //usuario.setPassword(password);
        usuario.setPassword(passwordEncoder.encode(password));
        usuarioRepository.save(usuario);

        return jwtUtil.generarToken(email);
    }

    public String login(String email, String password){
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("Credenciales incorrectas"));
        if(!passwordEncoder.matches(password, usuario.getPassword())){ //se espera que la contraseña guardada este cifrada con BCrypt
            throw new RuntimeException("Credenciales incorrectas");
        }

        return jwtUtil.generarToken(email);
    }

}
