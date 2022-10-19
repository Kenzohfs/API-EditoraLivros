package br.senai.sc.editoralivros.dto;

import br.senai.sc.editoralivros.model.entities.Autor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroDTO {
    private Long isbn;
    private String titulo;
    private Autor autor;
    private Integer qtdPag;
}
