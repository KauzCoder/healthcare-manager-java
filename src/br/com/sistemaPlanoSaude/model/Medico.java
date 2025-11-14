package br.com.sistemaPlanoSaude.model;

import java.time.LocalDate;
public class Medico extends Pessoa {

    private String especialidade;
    private String crm;
    private LocalDate dataContratacao;
    private int Salario;
    private final LocalDate dataCadastro;

    public Medico(String nome, String cpf, int idade, String endereco, String telefone, String email,
                  String especialidade, String crm, LocalDate dataContratacao, int Salario) {
        super(nome, cpf, idade, endereco, telefone, email);
        this.especialidade = especialidade;
        this.crm = crm;
        this.dataCadastro = LocalDate.now();
        this.Salario = Salario;
        this.dataContratacao = dataContratacao;
    }

    // Getters e Setters
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
    public String getCrm() { return crm; }
    public void setCrm(String crm) { this.crm = crm; }
    public LocalDate getDataContratacao() { return dataContratacao; }
    public void setDataContratacao(LocalDate dataContratacao) { this.dataContratacao = dataContratacao; }
    public int getSalario() { return Salario; }
    public void setSalario(int Salario) { this.Salario = Salario; }

    @Override
    public void exibirInfo() {
        System.out.println("Médico ID: " + id);
        System.out.println("Nome: " + nome);
        System.out.println("CPF: " + cpf);
        System.out.println("Idade: " + idade);
        System.out.println("Endereço: " + endereco);
        System.out.println("Telefone: " + telefone);
        System.out.println("Email: " + email);
        System.out.println("Especialidade: " + especialidade);
        System.out.println("CRM: " + crm);
        System.out.println("Data de Contratação: " + dataContratacao);
        System.out.println("Salário: " + Salario);
    }



}

