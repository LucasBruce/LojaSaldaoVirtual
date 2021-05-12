package br.com.bruce.lojaVirtual.controler;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class CarrinhoControle {

	@GetMapping("/carrinho")
	public ModelAndView chamarCarrinho(){
	ModelAndView mav = new ModelAndView("cliente/carrinho");
	return mav;
	}

	@GetMapping("/adicionarCarrinho/{id}")
	public ModelAndView adicioneCarrinho(@PathVariable Long id) {
		System.out.println("ID do produto: "+id);
		ModelAndView mav = new ModelAndView("cliente/carrinho");
		return mav;
	}
}
