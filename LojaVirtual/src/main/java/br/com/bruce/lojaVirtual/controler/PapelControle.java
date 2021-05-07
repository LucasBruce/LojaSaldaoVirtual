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

import br.com.bruce.lojaVirtual.modelos.Papel;
import br.com.bruce.lojaVirtual.repositorio.PapelRepositorio;


@Controller
public class PapelControle {

	@Autowired
	private PapelRepositorio papelRepositorio;

	
	@GetMapping("/administrativo/papeis/cadastro")
	public ModelAndView cadastro(Papel papel) {
		ModelAndView mav = new ModelAndView("administrativo/papeis/cadastro");
		mav.addObject("papel", papel);
		return mav;
	}

	@GetMapping("/administrativo/papeis/lista")
	public ModelAndView listar() {
		ModelAndView mav = new ModelAndView("administrativo/papeis/lista");
		mav.addObject("listaPapeis", this.papelRepositorio.findAll());
		return mav;
	}
	
	@GetMapping("/administrativo/papeis/editar/{id}")
	public ModelAndView editar(@PathVariable("id") long id) {
	  Optional<Papel> papel = this.papelRepositorio.findById(id);
	  return cadastro(papel.get());
	}
	
	@GetMapping("/administrativo/papeis/remover/{id}")
	public ModelAndView remover(@PathVariable("id") long id) {
	  Optional<Papel> papel = this.papelRepositorio.findById(id);
	  this.papelRepositorio.delete(papel.get());;
	  return listar();
	}
	
	@PostMapping("/administrativo/papeis/salvar")
	public ModelAndView salvar(@Valid Papel papel, BindingResult result) {
		if(result.hasErrors()) {
			return cadastro(papel);
		}
		this.papelRepositorio.saveAndFlush(papel);
		return cadastro(new Papel());
	}
}
