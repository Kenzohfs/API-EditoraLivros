package br.senai.sc.editoralivros.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
    AGUARDANDO_REVISAO("Aguardando revisão"),
    EM_REVISAO("Em revisão"),
    APROVADO("Aprovado"),
    AGUARDANDO_EDICAO("Aguardando edição"),
    REPROVADO("Reprovado"),
    PUBLICADO("Publicado");

    private String nome;
}
