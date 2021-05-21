package br.com.bruce.lojaVirtual.controler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.bruce.lojaVirtual.modelos.Cliente;
import br.com.bruce.lojaVirtual.modelos.Compra;
import br.com.bruce.lojaVirtual.modelos.ItensCompra;
import br.com.bruce.lojaVirtual.repositorio.ClienteRepositorio;
import br.com.bruce.lojaVirtual.repositorio.CompraRepositorio;
import br.com.bruce.lojaVirtual.repositorio.ItensCompraRepositorio;
import br.com.bruce.lojaVirtual.repositorio.ProdutoRepositorio;
import br.com.bruce.lojaVirtual.modelos.Produto;

@Controller
public class CarrinhoControle {

	private List<ItensCompra> itensCompra = new ArrayList<ItensCompra>();
	private Compra compra = new Compra();
	private Cliente cliente;

	@Autowired
	private ProdutoRepositorio produtoRepositorio;

	@Autowired
	private ClienteRepositorio clienteRepositorio;

	@Autowired
	private CompraRepositorio compraRepositorio;

	@Autowired
	private ItensCompraRepositorio itensCompraRepositorio;

	private void calcularTotal() {
		compra.setValorTotal(0.);
		for (ItensCompra it : itensCompra) {
			compra.setValorTotal(compra.getValorTotal() + it.getValorTotal());
		}
	}

	@GetMapping("/carrinho")
	public ModelAndView chamarCarrinho() {
		ModelAndView mav = new ModelAndView("cliente/carrinho");
		this.calcularTotal();
		// System.out.println(compra.getValorTotal());
		mav.addObject("compra", compra);
		mav.addObject("listaItens", itensCompra);
		return mav;
	}

	private void buscarUsuarioLogado() {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		if (!(autenticado instanceof AnonymousAuthenticationToken)) {
			String email = autenticado.getName();
			cliente = this.clienteRepositorio.buscarClienteEmail(email).get(0);
		}
	}

	@GetMapping("/finalizar")
	public ModelAndView finalizarCompra() {
		buscarUsuarioLogado();
		ModelAndView mav = new ModelAndView("cliente/finalizar");
		this.calcularTotal();
		// System.out.println(compra.getValorTotal());
		mav.addObject("compra", compra);
		mav.addObject("listaItens", itensCompra);
		mav.addObject("cliente", cliente);
		return mav;
	}

	@PostMapping("/finalizar/confirmar")
	public ModelAndView confirmarCompra(String formaPagamento) {
		ModelAndView mav = new ModelAndView("cliente/mensagemFinalizou");
		this.compra.setCliente(cliente);
		this.compra.setFormaPagamento(formaPagamento);
		this.compraRepositorio.saveAndFlush(compra);

		for (ItensCompra c : itensCompra) {
			c.setCompra(compra);
			this.itensCompraRepositorio.saveAndFlush(c);
		}
		itensCompra = new ArrayList<>();
		compra = new Compra();
		return mav;
	}

	@GetMapping("/alterarQuantidade/{id}/{acao}")
	public String alterarQuantidade(@PathVariable Long id, @PathVariable Integer acao) {

		for (ItensCompra it : itensCompra) {
			if (it.getProduto().getId().equals(id)) {
				// System.out.println(it.getValorTotal());
				if (acao.equals(1)) {
					it.setQuantidade(it.getQuantidade() + 1);
					it.setValorTotal(0.);
					it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
				} else if (acao == 0) {
					if (it.getQuantidade() != (0))
						it.setQuantidade(it.getQuantidade() - 1);
					it.setValorTotal(0.);
					it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
				}
				break;

			}
		}

		return "redirect:/carrinho";
	}

	@GetMapping("/removerProduto/{id}")
	public String removerProdutoCarrinho(@PathVariable Long id) {

		for (ItensCompra it : itensCompra) {
			if (it.getProduto().getId().equals(id)) {
				itensCompra.remove(it);
				break;

			}
		}

		return "redirect:/carrinho";
	}

	@GetMapping("/adicionarCarrinho/{id}")
	public String adicioneCarrinho(@PathVariable Long id) {
		Optional<Produto> prod = produtoRepositorio.findById(id);
		Produto produto = prod.get();
		int controle = 0;
		for (ItensCompra it : itensCompra) {
			if (it.getProduto().getId().equals(produto.getId())) {
				it.setQuantidade(it.getQuantidade() + 1);
				it.setValorTotal(0.);
				it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
				controle = 1;
				break;
			}
		}
		if (controle == 0) {
			ItensCompra item = new ItensCompra();
			item.setProduto(produto);
			item.setValorUnitario(produto.getValorVenda());
			item.setQuantidade(item.getQuantidade() + 1);
			item.setValorTotal(item.getValorTotal() + (item.getQuantidade() * item.getValorUnitario()));

			itensCompra.add(item);
		}
		return "redirect:/carrinho";
	}
}
