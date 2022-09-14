package com.generation.gamestation.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.gamestation.model.Categoria;
import com.generation.gamestation.model.Produto;
import com.generation.gamestation.repository.CategoriaRepository;
import com.generation.gamestation.repository.ProdutoRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	CategoriaRepository categoriaRepository;
	ProdutoRepository produtoRepository;
	
	@GetMapping
	public ResponseEntity<List<Categoria>> getAll(){
		
		return ResponseEntity.ok(categoriaRepository.findAll());
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> getById(Long id){
		
		return categoriaRepository.findById(id)
				.map(resposta->ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("tipo/{tipo}")
	public ResponseEntity<List<Categoria>> getByTipo(String tipo){
		
		return ResponseEntity.ok(categoriaRepository.findAllByTipoContainingIgnoreCase(tipo));
		
	}
	
//	@PostMapping
//	public ResponseEntity<Categoria> post(@Valid @RequestBody Categoria categoria){
//		
//		return ResponseEntity.status(HttpStatus.CREATED)
//				.body(categoriaRepository.save(categoria));
//		
//	}
	
	@PostMapping
    public ResponseEntity<Produto> salvarProdutos(@Valid @RequestBody Produto produto) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produtoModel));
        return categoriaRepository.findById(produto.getCategoria().getId())
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto)))
                .orElse(ResponseEntity.badRequest().build());
	}
	
	@PutMapping
	public ResponseEntity<Categoria> put (@Valid @RequestBody Categoria categoria){
		
		return categoriaRepository.findById(categoria.getId())
				.map(resposta->ResponseEntity.status(HttpStatus.CREATED)
				.body(categoriaRepository.save(categoria)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
		
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(Long id) {
		
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		
		if(categoria.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		categoriaRepository.deleteById(id);
		
	}

}
