package br.senai.sc.editoralivros.model.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@NoArgsConstructor
@Entity
@DiscriminatorValue("D")
public class Diretor extends Pessoa {
    public Diretor(Long cpf, String nome, String sobrenome, String email, String senha, Genero genero) {
        super();
    }
}
