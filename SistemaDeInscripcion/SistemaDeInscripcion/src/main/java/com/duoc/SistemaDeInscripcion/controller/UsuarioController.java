package com.duoc.sistemadeinscripcion.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.duoc.sistemadeinscripcion.dto.UsuarioResponseDTO;
import com.duoc.sistemadeinscripcion.model.Usuario;
import com.duoc.sistemadeinscripcion.service.UsuarioService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    // GET todos los usuarios
    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioResponseDTO>> obtenerUsuarios() {
        return ResponseEntity.ok(service.getAllUsuarios());
    }

    // GET usuario por ID
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.getUsuarioById(id));
    }

    // POST crear usuario
    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@Valid @RequestBody Usuario usuario) {
        UsuarioResponseDTO nuevo = service.createUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // PUT actualizar usuario
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(
            @PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        return ResponseEntity.ok(service.updateUsuario(id, usuario));
    }

    // DELETE eliminar usuario
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        boolean eliminado = service.deleteUsuario(id);
        return eliminado
            ? ResponseEntity.ok().build()
            : ResponseEntity.notFound().build();
    }
}
