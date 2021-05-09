package br.com.bruce.lojaVirtual.controler;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.bruce.lojaVirtual.modelos.Cidade;

import br.com.bruce.lojaVirtual.repositorio.CidadeRepositorio;
import br.com.bruce.lojaVirtual.repositorio.EstadoRepositorio;



@Controller
public class NegadoControle {

	
	@GetMapping("/negadoCliente")
	public ModelAndView negado() {
		ModelAndView mav = new ModelAndView("/negadoCliente");
		return mav;
	}

	
}
