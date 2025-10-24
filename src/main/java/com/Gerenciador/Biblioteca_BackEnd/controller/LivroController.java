package com.Gerenciador.Biblioteca_BackEnd.controller;

import com.Gerenciador.Biblioteca_BackEnd.dto.LivroDto;
import com.Gerenciador.Biblioteca_BackEnd.service.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://biblioteca-monsa.onrender.com/")
@RequestMapping("/livro")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    //Busca Personalisada:
    @GetMapping("/{filtro}/{termo}")
    public ResponseEntity<List<LivroDto>> livros(
            @PathVariable String filtro,
            @PathVariable String termo)
    {
        List<LivroDto> livroDto;

        switch (filtro.toLowerCase()) {
            case "titulo":
                livroDto = livroService.buscarTitulo(termo);
                break;
            case "genero":
                livroDto = livroService.buscarGenero(termo);
                break;
            case "autor":
                livroDto = livroService.buscarAutor(termo);
                break;
            case "catalogacao":
                livroDto = livroService.buscarCatalogacao(termo);
                break;
            default:
                return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<List<LivroDto>>(livroDto, HttpStatus.OK);
    }

    //Busca de todos os livros:
    @GetMapping("/todos")
    public ResponseEntity<List<LivroDto>> todosLivro(){
        List<LivroDto> livroDto = livroService.todosLivro();
        return new ResponseEntity<List<LivroDto>>(livroDto, HttpStatus.OK);
    }

    //Busca por Id:
    @GetMapping("/{id}")
    public ResponseEntity<LivroDto> buscarLivro(@PathVariable Long id){
        LivroDto livroDto = livroService.buscarLivro(id);
        return new ResponseEntity<>(livroDto, HttpStatus.OK);
    }
}