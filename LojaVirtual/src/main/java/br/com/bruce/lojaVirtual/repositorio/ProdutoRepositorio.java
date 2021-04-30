package br.com.bruce.lojaVirtual.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bruce.lojaVirtual.modelos.Produto;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {

}
