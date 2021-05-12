package br.com.bruce.lojaVirtual.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bruce.lojaVirtual.modelos.Compra;

public interface CompraRepositorio extends JpaRepository<Compra, Long> {

}
