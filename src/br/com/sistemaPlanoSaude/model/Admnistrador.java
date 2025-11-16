package br.com.sistemaPlanoSaude.model;
import model.enums.Sexo;
import model.enums.NivelAcesso;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;

public class Admnistrador extends Pessoa {
    private String senhaHash;
    private final LocalDate dataCadastro;
    private final LocalDate ultimoLogin; 
    
    private List<Medico> medicos = new ArrayList<>();


    // ===============================
    //     Construtores
    // ===============================
    public Admnistrador(String nome, String cpf, int idade, String endereco, String telefone, String email, Sexo sexo, String dataDeNascimento,
                        NivelAcesso nivelAcesso, String senhaHash, LocalDate dataCadastro, LocalDate ultimoLogin){
            super(
                nome, 
                cpf, 
                idade, 
                endereco, 
                telefone, 
                email, 
                sexo, 
                dataDeNascimento, 
                NivelAcesso.ADMINISTRADOR
                );

                this.senhaHash = senhaHash;
                this.dataCadastro = LocalDate.now();
                this.ultimoLogin  = atualizarUltimoLogin();
    }


    public String getSenhaHash(){ return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash;}

    public LocalDate getDataCadastro() { return dataCadastro; }

    public LocalDate getUltimoLogin(){ return ultimoLogin; }

    private void atualizarUltimoLogin() {
        this.ultimoLogin = LocalDate.now();
    }



    // ===============================
    //       Métodos auxiliares
    // ===============================


    //Metodos para alterar medico
    public void cadastrarMedico(Medico m){
        medicos.add(m);
        System.out.println("Plano de saúde cadastrado: " + m.getNome());
    }
    public void listarMedicos(){
        System.out.println("\n--- Lista de Médicos ---");
        for (Medico m : medicos){
            System.out.println(m);
        }
    }
    public void removerMedico(String crm) {
        medicos.removeIf(m -> m.getCrm().equals(crm));
        System.out.println("Médico removido: " + crm);
    }

    // ===============================
    //   Métodos de Controle de Usuário
    // ===============================

    public void criarUsuario(Usuario u) {
        System.out.println("Usuário criado: " + u.getNome());
    }

    public void bloquearUsuario(String cpf) {
        System.out.println("Usuário bloqueado: " + cpf);
    }

    public void desbloquearUsuario(String cpf ) {
        System.out.println("Usuário desbloqueado: " + cpf);
    }

    public void alterarPermissoes(String cpf, NivelAcesso nivel) {
        System.out.println("Alterando permissão do usuário " + cpf + " para " + nivel);
    }

    public void resetarSenhaUsuario(String cpf) {
        System.out.println("Senha do usuário " + cpf + " foi resetada.");
    }

    public void registrarLogin() {
        atualizarUltimoLogin();
    }


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

        System.out.println("=================================\n");
}
}
