package br.com.bruce.lojaVirtual.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bruce.lojaVirtual.modelos.EntradaProduto;

public interface EntradaProdutoRepositorio extends JpaRepository<EntradaProduto, Long> {

}
