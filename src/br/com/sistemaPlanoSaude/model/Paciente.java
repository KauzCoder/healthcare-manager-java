package br.com.sistemaPlanoSaude.model;

import java.time.LocalDate;
import model.enums.NivelAcesso;
import model.enums.Sexo;
public class Paciente extends Pessoa {
    private String numeroCarteirinha;
    private String planoSaude;
    private LocalDate dataCadastro;


    //Construtor parametrizado e com uso do super

      public Paciente(String nome, String cpf, int idade, String endereco, String telefone, String email,
                    Sexo sexo, String dataDeNascimento, String numeroCarteirinha, String planoSaude) {
        super(nome, cpf, idade, endereco, telefone, email, sexo, dataDeNascimento, NivelAcesso.PACIENTE);

    if (numeroCarteirinha == null || numeroCarteirinha.trim().isEmpty()) {
            throw new IllegalArgumentException("Número da carteirinha não pode ser vazio.");
        }
        if (planoSaude == null || planoSaude.trim().isEmpty()) {
            throw new IllegalArgumentException("Plano de saúde não pode ser vazio.");
        }

        this.numeroCarteirinha = numeroCarteirinha;
        this.planoSaude = planoSaude;
        this.dataCadastro = LocalDate.now();
    }

    // Getters e Setters
    public String getNumeroCarteirinha() { return numeroCarteirinha; }
    public void setNumeroCarteirinha(String numeroCarteirinha) {
        if (numeroCarteirinha == null || numeroCarteirinha.trim().isEmpty()) {
            throw new IllegalArgumentException("Número da carteirinha não pode ser vazio.");
        }
        this.numeroCarteirinha = numeroCarteirinha;
    }

    public String getPlanoSaude() { return planoSaude; }
    public void setPlanoSaude(String planoSaude) {
        if (planoSaude == null || planoSaude.trim().isEmpty()) {
            throw new IllegalArgumentException("Plano de saúde não pode ser vazio.");
        }
        this.planoSaude = planoSaude;
    }

    public LocalDate getDataCadastro() { return dataCadastro; }
}