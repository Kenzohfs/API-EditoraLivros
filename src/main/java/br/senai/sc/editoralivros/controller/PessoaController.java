package br.senai.sc.editoralivros.controller;

import br.senai.sc.editoralivros.dto.PessoaDTO;
import br.senai.sc.editoralivros.model.entities.Pessoa;
import br.senai.sc.editoralivros.model.factory.PessoaFactory;
import br.senai.sc.editoralivros.model.service.PessoaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/editoralivros/pessoa")
public class PessoaController {
    //Spring data -> JpaRepository
    //Validation -> DTO
    //Spring web -> Interage com o front-end
    private PessoaService pessoaService;

    @GetMapping("/email/{email}")
    public ResponseEntity<Object> findByEmail(@PathVariable(value = "email") String email) {
        if (pessoaService.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi encontrada nenhuma pessoa com este E-mail.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.findByEmail(email).get());
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Object> findById(@PathVariable(value = "cpf") Long cpf) {
        if (!pessoaService.existsById(cpf)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi encontrada nenhuma pessoa com este CPF.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.findById(cpf).get());
    }

    @GetMapping
    public ResponseEntity<List<Pessoa>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.findAll());
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody @Valid PessoaDTO pessoaDTO) {
        if (!pessoaService.existsById(pessoaDTO.getCpf())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Esse CPF não existe.");
        }

        Pessoa pessoa = new Pessoa();
        BeanUtils.copyProperties(pessoaDTO, pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.save(pessoa));
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid PessoaDTO pessoaDTO) {
        if (pessoaService.existsById(pessoaDTO.getCpf())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este CPF já está cadastrado.");
        }

        if (pessoaService.existsByEmail(pessoaDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Este E-mail já está cadastrado.");
        }

        Pessoa pessoa = new PessoaFactory().getPessoa(pessoaDTO);
        BeanUtils.copyProperties(pessoaDTO, pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.save(pessoa));
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<Object> deleteById(@PathVariable(value = "cpf") Long cpf) {
        if (!pessoaService.existsById(cpf)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não foi encontrada nenhuma pessoa com este CPF.");
        }

        pessoaService.deleteById(cpf);
        return ResponseEntity.status(HttpStatus.OK).body("Pessoa deletada.");
    }
}

