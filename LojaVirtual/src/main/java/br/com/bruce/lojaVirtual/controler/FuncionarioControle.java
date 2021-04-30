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

import br.com.bruce.lojaVirtual.modelos.Funcionario;
import br.com.bruce.lojaVirtual.repositorio.CidadeRepositorio;
import br.com.bruce.lojaVirtual.repositorio.FuncionarioRepositorio;

@Controller
public class FuncionarioControle {

	@Autowired
	private FuncionarioRepositorio funcionarioRepositorio;
    
	@Autowired
	private CidadeRepositorio cidadeRepositorio;
	
	@GetMapping("/administrativo/funcionarios/cadastro")
	public ModelAndView cadastro(Funcionario funcionario) {
		ModelAndView mav = new ModelAndView("administrativo/funcionarios/cadastro");
		mav.addObject("funcionario", funcionario);
		mav.addObject("listaCidades", this.cidadeRepositorio.findAll());
		return mav;
	}

	@GetMapping("/administrativo/funcionarios/lista")
	public ModelAndView listar() {
		ModelAndView mav = new ModelAndView("administrativo/funcionarios/lista");
		mav.addObject("listaFuncionarios", this.funcionarioRepositorio.findAll());
		return mav;
	}
	
	@GetMapping("/administrativo/funcionarios/editar/{id}")
	public ModelAndView editar(@PathVariable("id") long id) {
	  Optional<Funcionario> funcionario = this.funcionarioRepositorio.findById(id);
	  return cadastro(funcionario.get());
	}
	
	@GetMapping("/administrativo/funcionarios/remover/{id}")
	public ModelAndView remover(@PathVariable("id") long id) {
	  Optional<Funcionario> funcionario = this.funcionarioRepositorio.findById(id);
	  this.funcionarioRepositorio.delete(funcionario.get());;
	  return listar();
	}
	
	@PostMapping("/administrativo/funcionarios/salvar")
	public ModelAndView salvar(@Valid Funcionario funcionario, BindingResult result) {
		if(result.hasErrors()) {
			return cadastro(funcionario);
		}
		this.funcionarioRepositorio.saveAndFlush(funcionario);
		return cadastro(new Funcionario());
	}
}
