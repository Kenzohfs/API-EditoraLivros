package br.senai.sc.editoralivros.model.entities;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;

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

    @ManyToMany //Muitos livros para muitos Autores
    @JoinTable(
            name = "tb_livro_autor",
            joinColumns = @JoinColumn(name = "isbn_livro", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "cpf_autor", nullable = false)
    )
    private List<Autor> autores;

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

    @OneToOne(cascade = CascadeType.ALL)
    private Arquivo arquivo;

    public void setArquivo(MultipartFile file) {
        try {
            this.arquivo = new Arquivo(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //    1 - Aprovado
    //    2 - Em revisão
    //    3 - Aguardando revisão
    //    4 - Aguardando edição
    //    5 - Reprovado
    //    6 - Publicado
}
