package br.com.bruce.lojaVirtual.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bruce.lojaVirtual.modelos.Estado;
@Repository
public interface EstadoRepositorio extends JpaRepository<Estado, Long> {

}
