package br.com.bruce.lojaVirtual.controler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.bruce.lojaVirtual.repositorio.ProdutoRepositorio;


@Controller
public class IndexControle {

	@Autowired
	private ProdutoRepositorio produtoRepositorio;
	
	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("/index");
		mav.addObject("listaProdutos", this.produtoRepositorio.findAll());
		return mav;
	}

	
}
