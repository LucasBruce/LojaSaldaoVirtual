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

import br.com.bruce.lojaVirtual.modelos.Permissao;

import br.com.bruce.lojaVirtual.repositorio.PermissaoRepositorio;
import br.com.bruce.lojaVirtual.repositorio.EstadoRepositorio;
import br.com.bruce.lojaVirtual.repositorio.FuncionarioRepositorio;
import br.com.bruce.lojaVirtual.repositorio.PapelRepositorio;



@Controller
public class PermissaoControle {

	@Autowired
	private PermissaoRepositorio permissaoRepositorio;

	@Autowired
	private FuncionarioRepositorio funcionarioRepositorio;
	
	@Autowired
	private PapelRepositorio papelRepositorio;
	
	@GetMapping("/administrativo/permissoes/cadastro")
	public ModelAndView cadastro(Permissao permissao) {
		ModelAndView mav = new ModelAndView("administrativo/permissoes/cadastro");
		mav.addObject("permissao", permissao);
		mav.addObject("listaFuncionarios", this.funcionarioRepositorio.findAll());
		mav.addObject("listaPapel", this.papelRepositorio.findAll());
		return mav;
	}

	@GetMapping("/administrativo/permissoes/lista")
	public ModelAndView listar() {
		ModelAndView mav = new ModelAndView("administrativo/permissoes/lista");
		mav.addObject("listaPermissoes", this.permissaoRepositorio.findAll());
		return mav;
	}
	
	@GetMapping("/administrativo/permissoes/editar/{id}")
	public ModelAndView editar(@PathVariable("id") long id) {
	  Optional<Permissao> permissao = this.permissaoRepositorio.findById(id);
	  return cadastro(permissao.get());
	}
	
	@GetMapping("/administrativo/permissoes/remover/{id}")
	public ModelAndView remover(@PathVariable("id") long id) {
	  Optional<Permissao> permissao = this.permissaoRepositorio.findById(id);
	  this.permissaoRepositorio.delete(permissao.get());
	  return listar();
	}
	
	@PostMapping("/administrativo/permissoes/salvar")
	public ModelAndView salvar(@Valid Permissao permissao, BindingResult result) {
		if(result.hasErrors()) {
			return cadastro(permissao);
		}
		this.permissaoRepositorio.saveAndFlush(permissao);
		return cadastro(new Permissao());
	}
}
