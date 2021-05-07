package br.com.bruce.lojaVirtual.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bruce.lojaVirtual.modelos.Papel;

public interface PapelRepositorio extends JpaRepository<Papel, Long> {

}
