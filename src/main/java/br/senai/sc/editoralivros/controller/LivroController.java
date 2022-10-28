package br.senai.sc.editoralivros.controller;

import br.senai.sc.editoralivros.dto.LivroDTO;
import br.senai.sc.editoralivros.model.entities.Arquivo;
import br.senai.sc.editoralivros.model.entities.Autor;
import br.senai.sc.editoralivros.model.entities.Livro;
import br.senai.sc.editoralivros.model.entities.Status;
import br.senai.sc.editoralivros.model.service.LivroService;
import br.senai.sc.editoralivros.util.LivroUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/editoralivros/livro")
public class LivroController {
    private LivroService livroService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestParam("livro") String livroJson,
                                       @RequestParam("arquivo") MultipartFile file) {
        LivroUtil util = new LivroUtil();
        Livro livro = util.convertJsonToModel(livroJson);

        if (livroService.existsById(livro.getIsbn())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Há um livro com o ISBN " + livro.getIsbn() + "cadastrado.");
        }

        livro.setArquivo(file);
        livro.setStatus(Status.AGUARDANDO_REVISAO);

        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.save(livro));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Object> findById(@PathVariable(value = "isbn") Long isbn) {
        Optional<Livro> livroOptional = livroService.findById(isbn);
        if (livroOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "O livro de ISBN " + isbn + " não foi encontrado.");
        }

        return ResponseEntity.status(HttpStatus.FOUND).body(livroOptional.get());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Livro>> findByStatus(@PathVariable(value = "status") Status status) {
        return ResponseEntity.status(HttpStatus.FOUND).body(livroService.findByStatus(status));
    }

    @GetMapping("/autor/{autor}")
    public ResponseEntity<List<Livro>> findByAutor(@PathVariable(value = "autor") Autor autor) {
        return ResponseEntity.status(HttpStatus.FOUND).body(livroService.findByAutor(autor));
    }

    @GetMapping
    public ResponseEntity<List<Livro>> findAll() {
        return ResponseEntity.status(HttpStatus.FOUND).body(livroService.findAll());
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Object> deleteById(@PathVariable(value = "isbn") Long isbn) {
        if (!livroService.existsById(isbn)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi encontrada nenhum livro com o ISBN" + isbn + ".");
        }

        livroService.deleteById(isbn);
        return ResponseEntity.status(HttpStatus.OK).body("Livro de ISBN " + isbn + " deletado.");
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<Object> update(@PathVariable(value = "isbn") Long isbn, @RequestBody @Valid LivroDTO livroDTO) {
        if (!livroService.existsById(livroDTO.getIsbn())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Não há um livro com o ISBN " + livroDTO.getIsbn() + "cadastrado.");
        }

        Livro livro = livroService.findById(livroDTO.getIsbn()).get();
        BeanUtils.copyProperties(livroDTO, livro, "isbn");
        livro.setIsbn(isbn);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.save(livro));
    }
}
