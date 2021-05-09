package br.com.bruce.lojaVirtual.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginControle {

	
	@GetMapping("/login")
	public ModelAndView negado() {
		ModelAndView mav = new ModelAndView("/login");
		return mav;
	}

	
}
