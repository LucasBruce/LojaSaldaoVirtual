package br.com.bruce.lojaVirtual.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bruce.lojaVirtual.modelos.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

}
