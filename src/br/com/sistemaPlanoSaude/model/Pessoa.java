package br.com.sistemaPlanoSaude.model;

public abstract class Pessoa {
    protected  int id;
    protected  String nome;
    protected  String cpf;
    protected  int idade;
    protected  String endereco;
    protected  String telefone;
    protected  String email;
    protected boolean planoDeSaude;

    public Pessoa(String nome, String cpf, int idade, String endereco, String telefone, String email) {
        this.id = (int) (Math.random() * 1000) + 1;
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
    }

    //Getters e Setters
    public int getId() { return id; }
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

    // Método abstrato — as subclasses são obrigadas a implementar,
    public abstract void exibirInfo();
}
