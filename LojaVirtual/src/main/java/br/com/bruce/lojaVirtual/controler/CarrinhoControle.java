package br.com.bruce.lojaVirtual.controler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import br.com.bruce.lojaVirtual.modelos.ItensCompra;
import br.com.bruce.lojaVirtual.repositorio.ProdutoRepositorio;
import br.com.bruce.lojaVirtual.modelos.Produto;

@Controller
public class CarrinhoControle {

	private List<ItensCompra> itensCompra = new ArrayList<ItensCompra>();

	@Autowired
	private ProdutoRepositorio produtoRepositorio;

	@GetMapping("/carrinho")
	public ModelAndView chamarCarrinho() {
		ModelAndView mav = new ModelAndView("cliente/carrinho");
		mav.addObject("listaItens", itensCompra);
		return mav;
	}

	@GetMapping("/adicionarCarrinho/{id}")
	public ModelAndView adicioneCarrinho(@PathVariable Long id) {
		ModelAndView mav = new ModelAndView("cliente/carrinho");
		Optional<Produto> prod = produtoRepositorio.findById(id);
		Produto produto = prod.get();
		int controle = 0;
		for (ItensCompra it : itensCompra) {
			if (it.getProduto().getId().equals(produto.getId())) {
				it.setQuantidade(it.getQuantidade() + 1);
				controle = 1;
				break;
			}
		}
		if (controle == 0) {
			ItensCompra item = new ItensCompra();
			item.setProduto(produto);
			item.setValorUnitario(produto.getValorVenda());
			item.setQuantidade(item.getQuantidade() + 1);
			item.setValorTotal(item.getQuantidade() * item.getValorUnitario());
			itensCompra.add(item);
		}
		mav.addObject("listaItens", itensCompra);
		return mav;
	}
}
