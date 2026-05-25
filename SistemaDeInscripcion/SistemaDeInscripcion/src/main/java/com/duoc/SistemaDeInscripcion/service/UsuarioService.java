package com.duoc.SistemaDeInscripcion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.SistemaDeInscripcion.dto.UsuarioResponseDTO;
import com.duoc.SistemaDeInscripcion.exception.ResourceNotFoundException;
import com.duoc.SistemaDeInscripcion.model.Usuario;
import com.duoc.SistemaDeInscripcion.repository.UsuarioRepository;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repo;

    // Obtener todos los usuarios (sin password)
    public List<UsuarioResponseDTO> getAllUsuarios() {
        return repo.findAll().stream()
            .map(this::toDTO)
            .toList();
    }

    // Obtener usuario por ID
    public UsuarioResponseDTO getUsuarioById(Long id) {
        Usuario usuario = repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Usuario no encontrado con ID: " + id));
        return toDTO(usuario);
    }

    // Crear usuario
    public UsuarioResponseDTO createUsuario(Usuario usuario) {
        if (repo.existsByCorreo(usuario.getCorreo())) {
            throw new IllegalArgumentException(
                "Ya existe un usuario con el correo: " + usuario.getCorreo());
        }
        Usuario guardado = repo.save(usuario);
        return toDTO(guardado);
    }

    // Actualizar usuario
    public UsuarioResponseDTO updateUsuario(Long id, Usuario usuario) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException(
                "Usuario no encontrado con ID: " + id);
        }
        usuario.setId(id);
        return toDTO(repo.save(usuario));
    }

    // Eliminar usuario
    public boolean deleteUsuario(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    // Convertir entidad a DTO 
    private UsuarioResponseDTO toDTO(Usuario u) {
        return UsuarioResponseDTO.builder()
            .id(u.getId())
            .nombre(u.getNombre())
            .correo(u.getCorreo())
            .rol(u.getRol())
            .build();
    }
}

