package br.com.sistemaPlanoSaude.model.funcionarios;
import br.com.sistemaPlanoSaude.model.consulta.Consulta;
import br.com.sistemaPlanoSaude.model.enums.NivelAcesso;
import br.com.sistemaPlanoSaude.model.enums.Sexo;
import br.com.sistemaPlanoSaude.model.enums.StatusPaciente;
import br.com.sistemaPlanoSaude.model.pessoas.Paciente;
import br.com.sistemaPlanoSaude.model.pessoas.Pessoa;
import br.com.sistemaPlanoSaude.model.planos.PlanoSaude;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Administrador extends Pessoa {
    private String senhaHash;
    private final LocalDate dataCadastro;
    private LocalDate ultimoLogin; 
    
    
    private final List<Medico> medicos = new ArrayList<>();
    private final List<Paciente> pacientes = new ArrayList<>();
    private final List<Consulta> consultas = new ArrayList<>();




    // ===============================
    //     Construtores
    // ===============================
    public Administrador(String nome, String cpf, int idade, String endereco, String telefone, String email, Sexo sexo, LocalDate dataDeNascimento,
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
                this.dataCadastro = dataCadastro != null ? dataCadastro : LocalDate.now();
                if (ultimoLogin != null) {
                    this.ultimoLogin = ultimoLogin;
                } else {
                    atualizarUltimoLogin();
                }
    }
    
    public String getSenhaHash(){ return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash;}
    
    public LocalDate getDataCadastro() { return dataCadastro; }

    public LocalDate getUltimoLogin(){ return ultimoLogin; }
    
    private void atualizarUltimoLogin() {
        this.ultimoLogin = LocalDate.now();
    }
    
    

    // ===============================
    //       Métodos automaticos 
    // ===============================

    public void registrarLogin() {
        atualizarUltimoLogin();
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
    //   Métodos de consulta 
    // ===============================

    public List<Consulta> consultarConsultaPorCPF(String cpf) { 
    List<Consulta> consultasDoPaciente = new ArrayList<>();

    for (Consulta c : consultas) {
        if (c.getPaciente().getCpf().equals(cpf)) {
            consultasDoPaciente.add(c);
        }
    }

    if (consultasDoPaciente.isEmpty()) {
        System.out.println("Nenhuma consulta encontrada para o CPF: " + cpf);
    }

    return consultasDoPaciente; 
}





    // ===============================
    //   Métodos de Controle de Paciente
    // ===============================


    public void criarPaciente(Paciente p) {
        pacientes.add(p);
        System.out.println("Paciente criado: " + p.getNome());
    }

    public void bloquearPaciente(String numeroCarteirinha) {
        Paciente p = buscarPacientePorCarteirinha(numeroCarteirinha);

        if (p != null) {
            p.setStatus(StatusPaciente.BLOQUEADO);
            System.out.println("Paciente bloqueado: " + numeroCarteirinha);
        } else {
            System.out.println("Paciente não encontrado!");
        }
    }

    public void desbloquearPaciente(String numeroCarteirinha) {
        Paciente p = buscarPacientePorCarteirinha(numeroCarteirinha);

        if (p != null) {
            p.setStatus(StatusPaciente.ATIVO);
            System.out.println("Paciente desbloqueado: " + numeroCarteirinha);
        } else {
            System.out.println("Paciente não encontrado!");
        }
    }

    public void alterarPermissoes(String numeroCarteirinha, NivelAcesso nivel) {
        Paciente p = buscarPacientePorCarteirinha(numeroCarteirinha);

        if (p != null) {
            p.setNivelAcesso(nivel);
            System.out.println("Permissão alterada para: " + nivel);
        } else {
            System.out.println("Paciente não encontrado!");
        }
    }


    public void resetarSenhaPaciente(String numeroCarteirinha) {
        Paciente p = buscarPacientePorCarteirinha(numeroCarteirinha);

        if (p != null) {
            // Método setSenhaHash não existe em Paciente — apenas sinalizamos o reset aqui.
            System.out.println("Senha resetada (simulada) para o paciente da carteirinha: " + numeroCarteirinha);
        } else {
            System.out.println("Paciente não encontrado!");
        }
    }



    private Paciente buscarPacientePorCarteirinha(String codigoCarteirinha) {
    for (Paciente p : pacientes) {
        if (p.getNumeroCarteirinha().equals(codigoCarteirinha)) {
            return p;
        }
    }
    return null;
}

    // ===============================
    //   Métodos de Verificação de dados
    // ===============================
    public boolean verificarSenha(String senhaDigitada) {
        if (this.senhaHash == null) {
            return false;
        }
        return this.senhaHash.equals(senhaDigitada);
    }


    // ===============================
    //   Métodos de Gestão de Preços (Planos)
    // ===============================

    
    /**
     * Atualiza o valor base de um plano para um novo valor absoluto.
     */
    public void atualizarPrecoPlano(PlanoSaude plano, double novoValor) {
        if (plano == null) {
            System.out.println("Plano inválido.");
            return;
        }
        double antigo = plano.getValorBase();
        plano.setValorBase(novoValor);
        plano.setUltimaAtualizacao(java.time.LocalDate.now());
        System.out.printf("Preço do plano %s (%s) alterado de R$ %.2f para R$ %.2f%n",
                plano.getNomePlano(), plano.getCodigo(), antigo, novoValor);
    }

    /**
     * Aplica um reajuste percentual (por exemplo 10.0 = +10%) sobre o valor base do plano.
     * Percentual pode ser negativo para redução.
     */
    public void aplicarReajustePercentual(PlanoSaude plano, double percentual) {
        if (plano == null) {
            System.out.println("Plano inválido.");
            return;
        }
        double antigo = plano.getValorBase();
        double novo = antigo * (1.0 + percentual / 100.0);
        plano.setValorBase(novo);
        plano.setUltimaAtualizacao(java.time.LocalDate.now());
        System.out.printf("Reajuste de %.2f%% aplicado ao plano %s: R$ %.2f -> R$ %.2f%n",
                percentual, plano.getNomePlano(), antigo, novo);
    }

    /**
     * Aplica reajuste percentual a uma lista de planos.
     */
    public void aplicarReajusteEmLista(List<PlanoSaude> planos, double percentual) {
        if (planos == null || planos.isEmpty()) {
            System.out.println("Nenhum plano informado para reajuste.");
            return;
        }
        for (PlanoSaude p : planos) {
            aplicarReajustePercentual(p, percentual);
        }
    }



    @Override
    public void exibirInfo() {
        System.out.println("\n===== INFORMAÇÕES DO Médico =====");

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