package br.senai.sc.editoralivros.model.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@NoArgsConstructor
@Entity
@DiscriminatorValue("A")
public class Autor extends Pessoa {

    public Autor(Long cpf, String nome, String sobrenome, String email, String senha, Genero genero, Integer tipoPessoa) {
        super();
    }
}
