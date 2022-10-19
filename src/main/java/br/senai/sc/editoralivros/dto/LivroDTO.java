package br.senai.sc.editoralivros.dto;

import br.senai.sc.editoralivros.model.entities.Autor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LivroDTO {
    private Long isbn;
    private String titulo;
    private List<Autor> autores;
    private Integer qtdPag;
}
