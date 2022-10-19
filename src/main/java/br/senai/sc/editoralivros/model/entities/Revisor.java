package br.senai.sc.editoralivros.model.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@NoArgsConstructor
@Entity
@DiscriminatorValue("R")
public class Revisor extends Pessoa {
    public Revisor(Long cpf, String nome, String sobrenome, String email, String senha, Genero genero) {
        super();
    }
}
