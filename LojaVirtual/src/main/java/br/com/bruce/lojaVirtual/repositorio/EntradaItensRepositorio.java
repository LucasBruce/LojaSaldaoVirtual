package br.com.bruce.lojaVirtual.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bruce.lojaVirtual.modelos.EntradaItens;

public interface EntradaItensRepositorio extends JpaRepository<EntradaItens, Long> {

}
