package br.com.bruce.lojaVirtual.controler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.bruce.lojaVirtual.modelos.Produto;
import br.com.bruce.lojaVirtual.repositorio.ProdutoRepositorio;

@Controller
public class ProdutoControle {

	private static String caminhoImagens = "C:\\Users\\lucas.matheus\\Pictures";

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
		return listar();
	}
	
	@GetMapping("/administrativo/produtos/mostrarImagem/{imagem}")
	@ResponseBody
	public byte[] retornaImagem(@PathVariable("imagem") String imagem) throws IOException {
		//System.out.println(imagem);
		File imagemArquivo = new File(caminhoImagens+imagem);
		
		if(imagem!=null || imagem.trim().length()>0) {
		 
		 return Files.readAllBytes(imagemArquivo.toPath());
	}
		return null;
	}

	@PostMapping("/administrativo/produtos/salvar")
	public ModelAndView salvar(@Valid Produto produto, BindingResult result,
			@RequestParam("file") MultipartFile arquivo) {
		
		if (result.hasErrors()) {
			return cadastro(produto);
		}
		
		this.produtoRepositorio.saveAndFlush(produto);
		
		try {
			if(!arquivo.isEmpty()) {
				byte[] bytes = arquivo.getBytes();
				Path caminho = Paths.get(caminhoImagens + String.valueOf(produto.getId())+arquivo.getOriginalFilename());
				Files.write(caminho, bytes);
				
				produto.setNomeImagem(String.valueOf(produto.getId())+arquivo.getOriginalFilename());
				this.produtoRepositorio.saveAndFlush(produto);
			}
		}catch(IOException e) {
			
			e.printStackTrace();
			
		}
		
		
		return cadastro(new Produto());
	}
}
