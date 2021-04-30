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
public class CidadeControle {

	@Autowired
	private CidadeRepositorio cidadeRepositorio;

	@Autowired
	private EstadoRepositorio estadoRepositorio;
	
	@GetMapping("/administrativo/cidades/cadastro")
	public ModelAndView cadastro(Cidade cidade) {
		ModelAndView mav = new ModelAndView("administrativo/cidades/cadastro");
		mav.addObject("cidade", cidade);
		mav.addObject("listaEstados", this.estadoRepositorio.findAll());
		return mav;
	}

	@GetMapping("/administrativo/cidades/lista")
	public ModelAndView listar() {
		ModelAndView mav = new ModelAndView("administrativo/cidades/lista");
		mav.addObject("listaCidades", this.cidadeRepositorio.findAll());
		return mav;
	}
	
	@GetMapping("/administrativo/cidades/editar/{id}")
	public ModelAndView editar(@PathVariable("id") long id) {
	  Optional<Cidade> cidade = this.cidadeRepositorio.findById(id);
	  return cadastro(cidade.get());
	}
	
	@GetMapping("/administrativo/cidades/remover/{id}")
	public ModelAndView remover(@PathVariable("id") long id) {
	  Optional<Cidade> cidade = this.cidadeRepositorio.findById(id);
	  this.cidadeRepositorio.delete(cidade.get());;
	  return listar();
	}
	
	@PostMapping("/administrativo/cidades/salvar")
	public ModelAndView salvar(@Valid Cidade cidade, BindingResult result) {
		if(result.hasErrors()) {
			return cadastro(cidade);
		}
		this.cidadeRepositorio.saveAndFlush(cidade);
		return cadastro(new Cidade());
	}
}
