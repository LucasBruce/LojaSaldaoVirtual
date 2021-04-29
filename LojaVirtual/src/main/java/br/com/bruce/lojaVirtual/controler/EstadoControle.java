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

import br.com.bruce.lojaVirtual.modelos.Estado;
import br.com.bruce.lojaVirtual.repositorio.EstadoRepositorio;


@Controller
public class EstadoControle {

	@Autowired
	private EstadoRepositorio estadoRepositorio;

	
	@GetMapping("/administrativo/estados/cadastro")
	public ModelAndView cadastro(Estado estado) {
		ModelAndView mav = new ModelAndView("administrativo/estados/cadastro");
		mav.addObject("estado", estado);
		return mav;
	}

	@GetMapping("/administrativo/estados/lista")
	public ModelAndView listar() {
		ModelAndView mav = new ModelAndView("administrativo/estados/lista");
		mav.addObject("listaEstados", this.estadoRepositorio.findAll());
		return mav;
	}
	
	@GetMapping("/administrativo/estados/editar/{id}")
	public ModelAndView editar(@PathVariable("id") long id) {
	  Optional<Estado> estado = this.estadoRepositorio.findById(id);
	  return cadastro(estado.get());
	}
	
	@GetMapping("/administrativo/estados/remover/{id}")
	public ModelAndView remover(@PathVariable("id") long id) {
	  Optional<Estado> estado = this.estadoRepositorio.findById(id);
	  this.estadoRepositorio.delete(estado.get());;
	  return listar();
	}
	
	@PostMapping("/administrativo/estados/salvar")
	public ModelAndView salvar(@Valid Estado estado, BindingResult result) {
		if(result.hasErrors()) {
			return cadastro(estado);
		}
		this.estadoRepositorio.saveAndFlush(estado);
		return cadastro(new Estado());
	}
}
