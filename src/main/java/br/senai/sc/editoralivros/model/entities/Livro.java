package br.senai.sc.editoralivros.model.entities;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@Entity
@Table(name = "tb_livros")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Livro {
    @Id
    @Column(length = 13, nullable = false, unique = true)
    private Long ISBN;

    @Column(length = 50, nullable = false)
    private String titulo;

    @Column(nullable = false)
    private Autor autor;

    @Column(nullable = false)
    private Integer qtdPag;

    @Column(nullable = false)
    private Integer pagRevisadas;

    @Column
    private Revisor revisor;

    @Enumerated
    @Column(nullable = false)
    private Status status;

    @Column
    private Editora editora;

    //    1 - Aprovado
    //    2 - Em revisão
    //    3 - Aguardando revisão
    //    4 - Aguardando edição
    //    5 - Reprovado
    //    6 - Publicado
}
