package br.com.bruce.lojaVirtual.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PricipalControle {

	@GetMapping("/administrativo")
	public String home() {
		return "administrativo/home";
	}

}
