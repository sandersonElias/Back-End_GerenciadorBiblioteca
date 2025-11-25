package com.Gerenciador.Biblioteca_BackEnd.controller;

import com.Gerenciador.Biblioteca_BackEnd.dto.LivroDto;
import com.Gerenciador.Biblioteca_BackEnd.dto.LivroMinDto;
import com.Gerenciador.Biblioteca_BackEnd.service.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://biblioteca-frontend-five.vercel.app/")
@RequestMapping("/livro")
@RequiredArgsConstructor
public class LivroController {

    private final LivroService livroService;

    // Busca por filtro (titulo, genero, autor, catalogacao)
    @GetMapping("/buscar/{filtro}/{termo}")
    public ResponseEntity<List<LivroDto>> buscarPorFiltro(
            @PathVariable String filtro,
            @PathVariable String termo)
    {
        List<LivroDto> livroDto;

        switch (filtro.toLowerCase()) {
            case "titulo":
                livroDto = livroService.buscarPorTitulo(termo);
                break;
            case "genero":
                livroDto = livroService.buscarPorGenero(termo);
                break;
            case "autor":
                livroDto = livroService.buscarPorAutor(termo);
                break;
            case "catalogacao":
                livroDto = livroService.buscarPorCatalogacao(termo);
                break;
            default:
                return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(livroDto);
    }

    // Lista todos os livros (detalhado)
    @GetMapping("/todos")
    public ResponseEntity<List<LivroDto>> todosLivros(){
        List<LivroDto> livroDto = livroService.listarTodos();
        return ResponseEntity.ok(livroDto);
    }

    // Busca por Id (detalhado)
    @GetMapping("/{id}")
    public ResponseEntity<LivroDto> buscarLivro(@PathVariable Long id){
        LivroDto livroDto = livroService.buscarPorId(id);
        return ResponseEntity.ok(livroDto);
    }

    // Lista livros disponíveis (detalhado)
    @GetMapping("/disponiveis")
    public ResponseEntity<List<LivroDto>> listarDisponiveis(){
        List<LivroDto> livros = livroService.listarDisponiveis();
        return ResponseEntity.ok(livros);
    }

    // Lista livros mais populares, parâmetro opcional ?limite=5
    @GetMapping("/populares")
    public ResponseEntity<List<LivroDto>> listarPopulares(@RequestParam(required = false, defaultValue = "5") int limite){
        List<LivroDto> livros = livroService.listarMaisPopulares(limite);
        return ResponseEntity.ok(livros);
    }

    // Lista mínima (id + titulo) útil para dropdowns
    @GetMapping("/minimos")
    public ResponseEntity<List<LivroMinDto>> listarMinimos(){
        List<LivroMinDto> livros = livroService.listarMinimos();
        return ResponseEntity.ok(livros);
    }

    // Criar novo livro (envia/retorna LivroDto detalhado)
    @PostMapping
    public ResponseEntity<LivroDto> criarLivro(@RequestBody LivroDto livroDto){
        LivroDto criado = livroService.insertLivro(livroDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }
}
