package br.com.bruce.lojaVirtual.controler;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.bruce.lojaVirtual.modelos.Cliente;
import br.com.bruce.lojaVirtual.repositorio.CidadeRepositorio;
import br.com.bruce.lojaVirtual.repositorio.ClienteRepositorio;
import br.com.bruce.lojaVirtual.repositorio.EstadoRepositorio;



@Controller
public class ClienteControle {

	@Autowired
	private ClienteRepositorio clienteRepositorio;

	@Autowired
	private CidadeRepositorio cidadeRepositorio;
	
	@GetMapping("/cliente/cadastrar")
	public ModelAndView cadastro(Cliente cliente) {
		ModelAndView mav = new ModelAndView("cliente/cadastrar");
		mav.addObject("cliente", cliente);
		mav.addObject("listaCidade", this.cidadeRepositorio.findAll());
		return mav;
	}

	@GetMapping("/cliente/cidades/lista")
	public ModelAndView listar() {
		ModelAndView mav = new ModelAndView("cliente/cidades/lista");
		mav.addObject("listaCidades", this.clienteRepositorio.findAll());
		return mav;
	}
	
	@GetMapping("/cliente/editar/{id}")
	public ModelAndView editar(@PathVariable("id") long id) {
	  Optional<Cliente> cliente = this.clienteRepositorio.findById(id);
	  return cadastro(cliente.get());
	}
	
	@PostMapping("/cliente/salvar")
	public ModelAndView salvar(@Valid Cliente cliente, BindingResult result) {
		if(result.hasErrors()) {
			return cadastro(cliente);
		}
		cliente.setSenha(new BCryptPasswordEncoder().encode(cliente.getSenha()));
		this.clienteRepositorio.saveAndFlush(cliente);
		return cadastro(new Cliente());
	}
}
