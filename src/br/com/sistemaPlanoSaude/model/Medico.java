package br.com.sistemaPlanoSaude.model;
import model.enums.Especialidades;
import model.enums.Sexo;
import model.enums.NivelAcesso;

import java.time.LocalDate;
public class Medico extends Pessoa {

    private Especialidades especialidade;
    private String crm;
    private LocalDate dataContratacao;
    private int salario;
    private final LocalDate dataCadastro;


    // ===============================
    //     Construtores
    // ===============================
    public Medico(String nome, String cpf, int idade, String endereco, String telefone, String email, Sexo sexo, String dataDeNascimento,
                Especialidades especialidade, String crm, LocalDate dataContratacao, int salario, NivelAcesso nivelAcesso) {
            super( nome, 
                    cpf, 
                    idade, 
                    endereco, 
                    telefone, 
                    email, 
                    sexo, 
                    dataDeNascimento,
                    NivelAcesso.MEDICO);

        if (crm == null || crm.isBlank()) {
            throw new IllegalArgumentException("CRM não pode ser vazio.");
        }

    this.especialidade = especialidade;
    this.crm = crm;
    this.salario = salario;
    this.dataContratacao = dataContratacao;
    this.dataCadastro = LocalDate.now();
    }

    // ===============================
    //        Getters e Setters
    // ===============================
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
        System.out.println("\n===== INFORMAÇÕES DO MÉDICO =====");

        // Dados herdados da classe Pessoa
        System.out.println("Nome: " + getNome());
        System.out.println("CPF: " + getCpf());
        System.out.println("Idade: " + getIdade());
        System.out.println("Sexo: " + getSexo());
        System.out.println("Data de Nascimento: " + getDataDeNascimento());
        System.out.println("Endereço: " + getEndereco());
        System.out.println("Telefone: " + getTelefone());
        System.out.println("E-mail: " + getEmail());
        System.out.println("Nível de acesso: " + getNivelAcesso());

        // Dados exclusivos do médico
        System.out.println("Especialidade: " + getEspecialidade());
        System.out.println("CRM: " + getCrm());
        System.out.println("Data de contratação: " + getDataContratacao());
        System.out.println("Salário: R$ " + getSalario());
        System.out.println("Data de cadastro no sistema: " + getDataCadastro());

        System.out.println("=================================\n");
}

}
