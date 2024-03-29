package br.senai.sc.editoralivros.repository;

import br.senai.sc.editoralivros.model.entities.Autor;
import br.senai.sc.editoralivros.model.entities.Livro;
import br.senai.sc.editoralivros.model.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByStatus(Status status);
    List<Livro> findByAutores(Autor autor);
    List<Livro> findByIsbnAndStatus(Long isbn, Status status);

    // Importante! Os nomes dos métodos devem coincidir com o nome dos atributos da classe livro
    // Parâmetros não fazem diferença
}
