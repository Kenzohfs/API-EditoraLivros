package br.senai.sc.editoralivros.dto;

import br.senai.sc.editoralivros.model.entities.Genero;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class PessoaDTO {
    @NotBlank //Não pode ser sem valor (String nome = "") nem nulo
    private Long CPF;
    @NotBlank
    private String nome;
    @NotBlank
    private String sobrenome;
    @NotBlank
    private String email;
    @NotBlank
    private String senha;
    @NotBlank
    private Genero genero;
}
