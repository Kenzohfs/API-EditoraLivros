package br.senai.sc.editoralivros.dto;

import br.senai.sc.editoralivros.model.entities.Genero;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
public class PessoaDTO {
//    @NotBlank
// Não pode ser sem valor (String nome = "") nem nulo
    private Long cpf;
//    @NotBlank
    private String nome;
//    @NotBlank
    private String sobrenome;
//    @NotBlank
    private String email;
//    @NotBlank
    private String senha;
//    @NotBlank
    private Genero genero;
    private Integer tipo;
}
