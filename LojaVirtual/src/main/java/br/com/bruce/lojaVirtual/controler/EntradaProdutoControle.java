package br.com.bruce.lojaVirtual.controler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.bruce.lojaVirtual.modelos.EntradaItens;
import br.com.bruce.lojaVirtual.modelos.EntradaProduto;
import br.com.bruce.lojaVirtual.modelos.Estado;
import br.com.bruce.lojaVirtual.modelos.Produto;
import br.com.bruce.lojaVirtual.repositorio.CidadeRepositorio;
import br.com.bruce.lojaVirtual.repositorio.EntradaItensRepositorio;
import br.com.bruce.lojaVirtual.repositorio.EntradaProdutoRepositorio;
import br.com.bruce.lojaVirtual.repositorio.EstadoRepositorio;
import br.com.bruce.lojaVirtual.repositorio.FuncionarioRepositorio;
import br.com.bruce.lojaVirtual.repositorio.ProdutoRepositorio;

@Controller
public class EntradaProdutoControle {

	private List<EntradaItens> listaEntrada = new ArrayList<EntradaItens>();
	
	@Autowired
	private EntradaProdutoRepositorio entradaProdutoRepositorio;

	@Autowired
	private EntradaItensRepositorio entradaItensRepositorio;

	@Autowired
	private FuncionarioRepositorio funcionarioRepositorio;
	
	@Autowired
	private ProdutoRepositorio produtoRepositorio;
	
	@Autowired
	private CidadeRepositorio cidadeRepositorio;
	
	@GetMapping("/administrativo/entrada/cadastro")
	public ModelAndView cadastro(EntradaProduto entrada,
			EntradaItens entradaItens) {
		ModelAndView mav = new ModelAndView("administrativo/entrada/cadastro");
		mav.addObject("entrada", entrada);
		mav.addObject("listaEntradaItens", this.listaEntrada);
		mav.addObject("entradaItens", entradaItens);
		mav.addObject("listarFuncionarios", this.funcionarioRepositorio.findAll());
		mav.addObject("listaProdutos", this.produtoRepositorio.findAll());
		return mav;
	}

	@GetMapping("/administrativo/entrada/listar")
	public ModelAndView listar() {
		ModelAndView mav = new ModelAndView("administrativo/estados/lista");
		mav.addObject("listaCidades", this.cidadeRepositorio.findAll());
		return mav;
	}

//	@GetMapping("/administrativo/estados/editar/{id}")
//	public ModelAndView editar(@PathVariable("id") long id) {
//	  Optional<Estado> estado = this.estadoRepositorio.findById(id);
//	  return cadastro(estado.get());
//	}

//	@GetMapping("/administrativo/estados/remover/{id}")
//	public ModelAndView remover(@PathVariable("id") long id) {
//	  Optional<Estado> estado = this.estadoRepositorio.findById(id);
//	  this.estadoRepositorio.delete(estado.get());;
//	  return listar();
//	}

	@PostMapping("/administrativo/entrada/salvar")
	public ModelAndView salvar(String acao ,EntradaProduto entrada, EntradaItens entradaItens) {

		if(acao.equals("itens")) {
			this.listaEntrada.add(entradaItens);
		}else if(acao.equals("salvar")){
			this.entradaProdutoRepositorio.saveAndFlush(entrada);
			for(EntradaItens it:listaEntrada) {
				it.setEntrada(entrada);
				entradaItensRepositorio.saveAndFlush(it);
				Optional<Produto> prod = this.produtoRepositorio.findById(it.getProduto().getId());
				Produto produto = prod.get();
				produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + it.getQuantidade());
				produto.setValorVenda(it.getValorVenda());
				this.produtoRepositorio.saveAndFlush(produto);
				this.listaEntrada = new ArrayList<>();
				
			}
			return cadastro(new EntradaProduto(), new EntradaItens());
		}
		System.out.println(this.listaEntrada.size());
		
		return cadastro(entrada,  new EntradaItens());
	}
}
