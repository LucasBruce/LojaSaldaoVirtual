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

import br.com.bruce.lojaVirtual.modelos.Produto;
import br.com.bruce.lojaVirtual.repositorio.ProdutoRepositorio;

@Controller
public class ProdutoControle {

	@Autowired
	private ProdutoRepositorio produtoRepositorio;

	@GetMapping("/administrativo/produtos/cadastro")
	public ModelAndView cadastro(Produto produto) {
		ModelAndView mav = new ModelAndView("administrativo/produtos/cadastro");
		mav.addObject("produto", produto);
		return mav;
	}

	@GetMapping("/administrativo/produtos/lista")
	public ModelAndView listar() {
		ModelAndView mav = new ModelAndView("administrativo/produtos/lista");
		mav.addObject("listaProdutos", this.produtoRepositorio.findAll());
		return mav;
	}

	@GetMapping("/administrativo/produtos/editar/{id}")
	public ModelAndView editar(@PathVariable("id") long id) {
		Optional<Produto> produto = this.produtoRepositorio.findById(id);
		return cadastro(produto.get());
	}

	@GetMapping("/administrativo/produtos/remover/{id}")
	public ModelAndView remover(@PathVariable("id") long id) {
		Optional<Produto> produto = this.produtoRepositorio.findById(id);
		this.produtoRepositorio.delete(produto.get());
		;
		return listar();
	}

	@PostMapping("/administrativo/produtos/salvar")
	public ModelAndView salvar(@Valid Produto produto, BindingResult result) {
		if (result.hasErrors()) {
			return cadastro(produto);
		}
		this.produtoRepositorio.saveAndFlush(produto);
		return cadastro(new Produto());
	}
}
