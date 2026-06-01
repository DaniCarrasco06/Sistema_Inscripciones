package com.duoc.sistemadeinscripcion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duoc.sistemadeinscripcion.model.Usuario;

import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
}

