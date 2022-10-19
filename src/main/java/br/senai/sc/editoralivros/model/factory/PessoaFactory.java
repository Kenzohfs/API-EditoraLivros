package br.senai.sc.editoralivros.model.factory;

import br.senai.sc.editoralivros.dto.PessoaDTO;
import br.senai.sc.editoralivros.model.entities.Autor;
import br.senai.sc.editoralivros.model.entities.Diretor;
import br.senai.sc.editoralivros.model.entities.Pessoa;
import br.senai.sc.editoralivros.model.entities.Revisor;

public class PessoaFactory {
    public Pessoa getPessoa(PessoaDTO pessoaDTO) {
        switch (pessoaDTO.getTipo()) {
            case 1 -> {
                return new Autor(
                        pessoaDTO.getCpf(),
                        pessoaDTO.getNome(),
                        pessoaDTO.getSobrenome(),
                        pessoaDTO.getEmail(),
                        pessoaDTO.getSenha(),
                        pessoaDTO.getGenero()
                );
            }
            case 2 -> {
                return new Revisor(
                        pessoaDTO.getCpf(),
                        pessoaDTO.getNome(),
                        pessoaDTO.getSobrenome(),
                        pessoaDTO.getEmail(),
                        pessoaDTO.getSenha(),
                        pessoaDTO.getGenero()
                );
            }
            default -> {
                return new Diretor(
                        pessoaDTO.getCpf(),
                        pessoaDTO.getNome(),
                        pessoaDTO.getSobrenome(),
                        pessoaDTO.getEmail(),
                        pessoaDTO.getSenha(),
                        pessoaDTO.getGenero()
                );
            }
        }
    }
}
