package br.com.bruce.lojaVirtual.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bruce.lojaVirtual.modelos.Permissao;

public interface PermissaoRepositorio extends JpaRepository<Permissao, Long> {

}
