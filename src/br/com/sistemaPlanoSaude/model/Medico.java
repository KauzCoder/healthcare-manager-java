package br.com.sistemaPlanoSaude.model;
import model.enums.Especialidades;
import model.enums.NivelAcesso;

import java.time.LocalDate;
public class Medico extends Pessoa {

    private Especialidades especialidade;
    private String crm;
    private LocalDate dataContratacao;
    private int salario;
    private final LocalDate dataCadastro;

    public Medico(String nome, String cpf, int idade, String endereco, String telefone, String email,
                Especialidades especialidade, String crm, LocalDate dataContratacao, int salario, NivelAcesso nivelAcesso) {
            super(nome, cpf, idade, endereco, telefone, email, NivelAcesso.MEDICO);

        if (crm == null || crm.isBlank()) {
            throw new IllegalArgumentException("CRM não pode ser vazio.");
        }

    this.especialidade = especialidade;
    this.crm = crm;
    this.salario = salario;
    this.dataContratacao = dataContratacao;
    this.dataCadastro = LocalDate.now();
    }

    // Getters e Setters
    public Especialidades getEspecialidade() { return especialidade; }
    public void setEspecialidade(Especialidades especialidade) { this.especialidade = especialidade; }

    public String getCrm() { return crm; }
    public void setCrm(String crm) { this.crm = crm; }

    public LocalDate getDataContratacao() { return dataContratacao; }
    public void setDataContratacao(LocalDate dataContratacao) { this.dataContratacao = dataContratacao; }

    public int getSalario() { return salario; }
    public void setSalario(int salario) { this.salario = salario; }


    public LocalDate getDataCadastro() {return dataCadastro;}


    @Override
    public void exibirInfo() {
        System.out.println("=== Dados do Médico ===");
        System.out.println("Nome: " + nome);
        System.out.println("CPF: " + cpf);
        System.out.println("Idade: " + idade);
        System.out.println("Endereço: " + endereco);
        System.out.println("Telefone: " + telefone);
        System.out.println("Email: " + email);
        System.out.println("Especialidade: " + especialidade);
        System.out.println("CRM: " + crm);
        System.out.println("Data de Contratação: " + dataContratacao);
        System.out.println("Data de Cadastro no Sistema: " + dataCadastro);
        System.out.println("Salário: R$ " + salario);
    }
}
