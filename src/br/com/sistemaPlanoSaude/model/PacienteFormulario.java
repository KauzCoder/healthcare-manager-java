package br.com.sistemaPlanoSaude.model;

public class PacienteFormulario{
    private String nome;
    private int idade;
    private String cpf;
    private String telefone;
    private String endereco;
    private String planoSaude;

    public PacienteFormulario(String nome, int idade, String cpf, String telefone, String endereco, String planoSaude){
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
        this.planoSaude = planoSaude;
    }

    @Override
    public String toString(){
        return "---- Formulário do Paciente ----" +
        "nome: " + nome + "\n" +
        "idade: " + idade + "\n" +
        "cpf: " + cpf + "\n" +
        "telefone: " + telefone + "\n" +
        "endereco: " + endereco + "\n" +
        "planoSaude: " + planoSaude + "\n";
    }
}