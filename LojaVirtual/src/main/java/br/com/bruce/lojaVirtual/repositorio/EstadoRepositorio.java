package br.com.bruce.lojaVirtual.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bruce.lojaVirtual.modelos.Estado;

public interface EstadoRepositorio extends JpaRepository<Estado, Long> {

}
