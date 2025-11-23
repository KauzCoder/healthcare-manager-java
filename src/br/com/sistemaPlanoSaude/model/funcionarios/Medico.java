package br.com.sistemaPlanoSaude.model.funcionarios;

import br.com.sistemaPlanoSaude.model.consulta.Consulta;
import br.com.sistemaPlanoSaude.model.consulta.Horario;
import br.com.sistemaPlanoSaude.model.enums.Especialidades;
import br.com.sistemaPlanoSaude.model.enums.Sexo;
import br.com.sistemaPlanoSaude.model.pessoas.Paciente;
import br.com.sistemaPlanoSaude.model.pessoas.Pessoa;
import br.com.sistemaPlanoSaude.model.enums.NivelAcesso;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Medico extends Pessoa {

    private Especialidades especialidade;
    private String crm;
    private LocalDate dataContratacao;
    private int salario;
    private final LocalDate dataCadastro;

    // Coleções para gerenciar consultas, pacientes e horários
    private List<Consulta> consultas;
    private List<Paciente> pacientes;
    private List<Horario> horarioAtendimento;
    private final List<String> horariosDisponiveis = new ArrayList<>();

    // ===============================
    //     Construtores
    // ===============================
    public Medico(String nome, String cpf, int idade, String endereco, String telefone, String email,Sexo sexo, LocalDate dataDeNascimento,Especialidades especialidade, String crm, LocalDate dataContratacao, int salario, NivelAcesso nivelAcesso) {
        super(
            nome, 
            cpf, 
            idade, 
            endereco, 
            telefone, 
            email, 
            sexo, 
            dataDeNascimento, 
            NivelAcesso.MEDICO
        );

        this.especialidade = especialidade;
        this.crm  = crm;
        this.salario = salario;
        this.dataContratacao = dataContratacao;
        this.dataCadastro = LocalDate.now();

        this.consultas = new ArrayList<>();
        this.pacientes = new ArrayList<>();
        this.horarioAtendimento = new ArrayList<>();
    }

    // ---------------- MÉTODOS CONSULTAS ----------------

    public Consulta agendarConsulta(Paciente p, Date data) {
        if (p == null) throw new IllegalArgumentException("Paciente não pode ser nulo");
        if (data == null) throw new IllegalArgumentException("Data não pode ser nula");

    
        Horario encontrado = null;
        for (Horario h : horarioAtendimento) {
            if (h.getData().equals(data) && h.isDisponibilidade()) {
                encontrado = h;
                break;
            }
        }
        if (encontrado == null) return null; // sem horário disponível

        
        Consulta nova = new Consulta(p, this, encontrado, "", "", null);
        consultas.add(nova);
        if (!pacientes.contains(p)) pacientes.add(p);
        return nova;
    }

    public void cancelarConsulta(int idConsulta) {
        for (Iterator<Consulta> it = consultas.iterator(); it.hasNext();) {
            Consulta c = it.next();
            if (c.getIdConsulta() == idConsulta) {
                c.cancelarConsultaStatus(); // libera horário internamente
                it.remove();
                return;
            }
        }
    }

    public List<Consulta> listarConsultas() {
        return new ArrayList<>(consultas); // cópia defensiva
    }

    public List<Consulta> consultasDoDia(Date data) {
        List<Consulta> lista = new ArrayList<>();
        for (Consulta c : consultas) {
            if (c.getHorario() != null && c.getHorario().getData().equals(data)) lista.add(c);
        }
        return lista;
    }

    // ---------------- MÉTODOS VERIFICAR DISPONIBILIDADE ----------------
    public boolean estaDisponivel(Date data) {
        if (data == null) return false;
        for (Horario h : horarioAtendimento) {
            if (h.getData().equals(data) && h.isDisponibilidade()) return true;
        }
        return false;
    }

    // ---------------- GETTERS E SETTERS ----------------

    public List<Consulta> getConsultas() { return new ArrayList<>(consultas); }
    public void setConsultas(List<Consulta> consultas) { this.consultas = consultas != null ? new ArrayList<>(consultas) : new ArrayList<>(); }

    public List<Paciente> getPacientes() { return new ArrayList<>(pacientes); }
    public void setPacientes(List<Paciente> pacientes) { this.pacientes = pacientes != null ? new ArrayList<>(pacientes) : new ArrayList<>(); }

    public List<Horario> getHorarioAtendimento() { return new ArrayList<>(horarioAtendimento); }
    public void setHorarioAtendimento(List<Horario> horarioAtendimento) { this.horarioAtendimento = horarioAtendimento != null ? new ArrayList<>(horarioAtendimento) : new ArrayList<>(); }

    public List<String> getHorariosDisponiveis() { return horariosDisponiveis; }

    public Especialidades getEspecialidade() { return especialidade; }
    public void setEspecialidade(Especialidades especialidade) { this.especialidade = especialidade; }

    public String getCrm() { return crm; }
    public void setCrm(String crm) { this.crm = crm;}

    public LocalDate getDataContratacao() { return dataContratacao; }
    public void setDataContratacao(LocalDate dataContratacao) { this.dataContratacao = dataContratacao; }

    public int getSalario() { return salario; }
    public void setSalario(int salario) { this.salario = salario; }

    public LocalDate getDataCadastro() { return dataCadastro; }

    @Override
    public void exibirInfo() {
        System.out.println("\n===== INFORMAÇÕES DO MÉDICO =====");
        System.out.println("Nome: " + getNome());
        System.out.println("CPF: " + getCpf());
        System.out.println("Idade: " + getIdade());
        System.out.println("Sexo: " + getSexo());
        System.out.println("Data de Nascimento: " + getDataDeNascimento());
        System.out.println("Endereço: " + getEndereco());
        System.out.println("Telefone: " + getTelefone());
        System.out.println("E-mail: " + getEmail());
        System.out.println("Nível de acesso: " + getNivelAcesso());
        System.out.println("Especialidade: " + getEspecialidade());
        System.out.println("CRM: " + getCrm());
        System.out.println("Data de contratação: " + getDataContratacao());
        System.out.println("Salário: R$ " + getSalario());
        System.out.println("Data de cadastro no sistema: " + getDataCadastro());
        System.out.println("=================================\n");
    }

}
