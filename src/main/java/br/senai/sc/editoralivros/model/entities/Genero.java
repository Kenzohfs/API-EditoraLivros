package br.senai.sc.editoralivros.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Genero {
    MASCULINO("Masculino"),
    FEMININO("Feminino"),
    OUTRO("Outro");
    String nome;

    public static Genero getGeneroCorreto(Integer intGenero) {
        for (Genero genero : Genero.values()) {
            if (genero.ordinal() == (intGenero)) {
                return genero;
            }
        }
        throw  new RuntimeException("Genero não encontrado");
    }
}
