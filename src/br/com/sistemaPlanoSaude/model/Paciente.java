package br.com.sistemaPlanoSaude.model;

import java.time.LocalDate;
public class Paciente extends Pessoa {
    private String numeroCarteirinha;
    private final LocalDate dataCadastro;
    private boolean possuiDependentes;
    


    //Construtor parametrizado com uso do super

      public Paciente(String nome, String cpf, int idade, String endereco, String telefone, String email,
                    String numeroCarteirinha, String planoSaude, boolean possuiDependentes) {
        super(nome, cpf, idade, endereco, telefone, email);
        if (numeroCarteirinha == null || numeroCarteirinha.trim().isEmpty()) {
            throw new IllegalArgumentException("Número da carteirinha não pode ser vazio.");
        }
        if (planoSaude == null || planoSaude.trim().isEmpty()) {
            throw new IllegalArgumentException("Plano de saúde não pode ser vazio.");
        }
        this.numeroCarteirinha = numeroCarteirinha;
        this.planoSaude = planoSaude;
        this.dataCadastro = LocalDate.now();
        this.possuiDependentes = possuiDependentes;
    }



    public String getNumeroCarteirinha() {return numeroCarteirinha;}
    public void setNumeroCarteirinha(String numeroCarteirinha) {
        if (numeroCarteirinha == null || numeroCarteirinha.trim().isEmpty()) {
            throw new IllegalArgumentException("Número da carteirinha não pode ser vazio.");
        }
        this.numeroCarteirinha = numeroCarteirinha;
    }
    public String getPlanoSaude() {return planoSaude;}
    public void setPlanoSaude(String planoSaude) {
        if (planoSaude == null || planoSaude.trim().isEmpty()) {
            throw new IllegalArgumentException("Plano de saúde não pode ser vazio.");
        }
        this.planoSaude = planoSaude;
    }
    public LocalDate getDataCadastro() {return dataCadastro;}
    public boolean isPossuiDependentes() {return possuiDependentes;}
    public void setPossuiDependentes(boolean possuiDependentes) {this.possuiDependentes = possuiDependentes;}

    @Override
    public void exibirInfo() {
        System.out.println("=== Dados do Paciente ===");
        System.out.println("ID: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("CPF: " + cpf);
        System.out.println("Idade: " + idade);
        System.out.println("Endereço: " + endereco);
        System.out.println("Telefone: " + telefone);
        System.out.println("Email: " + email);
        System.out.println("Carteirinha: " + numeroCarteirinha);
        System.out.println("Plano de Saúde: " + planoSaude);
        System.out.println("Data de Cadastro: " + dataCadastro);
        System.out.println("Possui Dependentes: " + (possuiDependentes ? "Sim" : "Não"));
    }
}