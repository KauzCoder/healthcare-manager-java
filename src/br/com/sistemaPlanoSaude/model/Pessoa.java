package br.com.sistemaPlanoSaude.model;
import model.enums.Sexo;
import model.enums.NivelAcesso;

public abstract class Pessoa {
    protected  String nome;
    protected  String cpf;
    protected Sexo sexo;
    protected String dataDeNascimento;
    protected  int idade;
    protected  String endereco;
    protected  String telefone;
    protected  String email;
    protected  NivelAcesso nivelAcesso;

    // ===============================
    //     Construtores
    // ===============================
    public Pessoa(String nome, String cpf, int idade, String endereco, 
                  String telefone, String email, Sexo sexo, 
                  String dataDeNascimento, NivelAcesso nivelAcesso) {
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.sexo = sexo;
        this.dataDeNascimento = dataDeNascimento;
        this.nivelAcesso = NivelAcesso.INTERESSADO;
    }

     // ===============================
    //        Getters e Setters
    // ===============================
    public Sexo getSexo() { return sexo; }
    public void setSexo(Sexo sexo) { this.sexo = sexo; }

    public String getDataDeNascimento() { return dataDeNascimento; }
    public void setDataDeNascimento(String dataDeNascimento) { this.dataDeNascimento = dataDeNascimento; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public NivelAcesso getNivelAcesso() { return nivelAcesso; }
    public void setNivelAcesso(NivelAcesso nivelAcesso) { this.nivelAcesso = nivelAcesso; }

    
    // Método abstrato — as subclasses são obrigadas a implementar,
    public abstract void exibirInfo();
}

