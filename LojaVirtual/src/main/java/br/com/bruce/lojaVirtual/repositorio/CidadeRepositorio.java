package br.com.bruce.lojaVirtual.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bruce.lojaVirtual.modelos.Cidade;

public interface CidadeRepositorio extends JpaRepository<Cidade, Long> {

}
