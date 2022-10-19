package br.senai.sc.editoralivros.model.entities;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_livros")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Livro {
    @Id
    @Column(length = 13, nullable = false, unique = true)
    private Long isbn;

    @Column(length = 50, nullable = false)
    private String titulo;

    @ManyToOne //Muitos livros para um Autor
    @JoinColumn(name = "cpf_autor", nullable = false)
    private Autor autor;

    @Column(nullable = false)
    private Integer qtdPag;

    @Column
    private Integer pagRevisadas;

    @ManyToOne //Muitos livros para um Revisor
    @JoinColumn(name = "cpf_revisor")
    private Revisor revisor;

    @Enumerated
    @Column(nullable = false)
    private Status status;

    @ManyToOne //Muitos livros para uma Editora
    @JoinColumn(name = "cnpj_editora")
    private Editora editora;

    //    1 - Aprovado
    //    2 - Em revisão
    //    3 - Aguardando revisão
    //    4 - Aguardando edição
    //    5 - Reprovado
    //    6 - Publicado
}
