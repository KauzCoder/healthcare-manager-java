package br.com.sistemaPlanoSaude.model;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import br.com.sistemaPlanoSaude.model.enums.StatusPaciente;
import br.com.sistemaPlanoSaude.model.enums.TipoSanguineo;
import br.com.sistemaPlanoSaude.model.enums.NivelAcesso;
import br.com.sistemaPlanoSaude.model.enums.Sexo;
public class Paciente extends Pessoa {
    // Referência ao plano de saúde do paciente
    private PlanoSaude plano;

    private String numeroCarteirinha;
    private String planoSaude;
    private LocalDate dataCadastro;

    private TipoSanguineo tipoSanguineo;
    private StatusPaciente status;

    private double peso;
    private double altura;

    private final List<String> alergias = new ArrayList<>();
    private final List<String> doencasCronicas = new ArrayList<>();
    private final List<String> historicoCirurgias = new ArrayList<>();
    private final List<String> medicamentosEmUso = new ArrayList<>();


    // ===============================
    //     Construtores
    // ===============================

      public Paciente(String nome, String cpf, int idade, String endereco, String telefone, String email,Sexo sexo, LocalDate dataDeNascimento, String numeroCarteirinha, String planoSaude) {
        super(
            nome, 
            cpf, 
            idade, 
            endereco, 
            telefone, 
            email, 
            sexo, 
            dataDeNascimento, 
            NivelAcesso.PACIENTE
        );

    if (numeroCarteirinha == null || numeroCarteirinha.trim().isEmpty()) {
            throw new IllegalArgumentException("Número da carteirinha não pode ser vazio.");
        }
        if (planoSaude == null || planoSaude.trim().isEmpty()) {
            throw new IllegalArgumentException("Plano de saúde não pode ser vazio.");
        }

        this.numeroCarteirinha = numeroCarteirinha;
        this.planoSaude = planoSaude;
        this.dataCadastro = LocalDate.now();
        this.status = StatusPaciente.ATIVO;
    }

    // Getters e Setters
    public String getNumeroCarteirinha() { return numeroCarteirinha; }
    public void setNumeroCarteirinha(String numeroCarteirinha) {
        if (numeroCarteirinha == null || numeroCarteirinha.trim().isEmpty()) {
            throw new IllegalArgumentException("Número da carteirinha não pode ser vazio.");
        }
        this.numeroCarteirinha = numeroCarteirinha;
    }

    public String getPlanoSaude() { return planoSaude; }
    public void setPlanoSaude(String planoSaude) {
        if (planoSaude == null || planoSaude.trim().isEmpty()) {
            throw new IllegalArgumentException("Plano de saúde não pode ser vazio.");
        }
        this.planoSaude = planoSaude;
    }

    public LocalDate getDataCadastro() { return dataCadastro; }

    public double getPeso() {return peso;}
    public void setPeso(double peso) {this.peso = peso;}

    public double getAltura() {return altura;}
    public void setAltura(double altura) {this.altura = altura;}

    public StatusPaciente getStatus() {return status;}
    public void setStatus(StatusPaciente status) {this.status = status;}

    public TipoSanguineo getTipoSanguineo() {return tipoSanguineo; }
    public void setTipoSanguineo(TipoSanguineo tipoSanguineo) {this.tipoSanguineo = tipoSanguineo;}

    public List<String> getAlergias() {return alergias;}

    public List<String> getDoencasCronicas() {return doencasCronicas;}

    public List<String> getHistoricoCirurgias() {return historicoCirurgias;}

    public List<String> getMedicamentosEmUso() { return medicamentosEmUso; }




    // ===============================
    //      Métodos de Plano de Saúde
    // ===============================


    /**
     * Vincula um plano de saúde ao paciente.
     */
    public void vincularPlano(PlanoSaude plano) {
        this.plano = plano;
    }

    /**
     * Remove o vínculo do plano de saúde do paciente.
     */
    public void desvincularPlano() {
        this.plano = null;
    }

    /**
     * Retorna o plano de saúde vinculado ao paciente.
     */
    public PlanoSaude getPlano() {
        return plano;
    }

      // ===============================
    //      Métodos de cálculo
    // ===============================


    public double calcularIMC() {
        if (altura <= 0) return 0;
        return peso / (altura * altura);
    }


      // ===============================
    //       Métodos auxiliares
    // ===============================

    public void atualizarEndereco(String novoEndereco) {
        super.setEndereco(novoEndereco);
    }

    public void adicionarAlergia(String alergia) {
        if (alergia != null && !alergias.contains(alergia)) {
            alergias.add(alergia);
        }
    }

    public void adicionarMedicamento(String medicamento) {
        if (medicamento != null && !medicamentosEmUso.contains(medicamento)) {
            medicamentosEmUso.add(medicamento);
        }
    }

    public void adicionarDoencaCronica(String doencaCronica) {
        if (doencaCronica != null && !doencasCronicas.contains(doencaCronica)) {
            doencasCronicas.add(doencaCronica);
        }
    }

    public void adicionarHistoricoCirurgia(String cirurgia) {
        if (cirurgia != null && !historicoCirurgias.contains(cirurgia)) {
            historicoCirurgias.add(cirurgia);
        }
    }

    //O método contains() é utilizado para verificar se um elemento específico já existe em uma coleção (como uma lista, conjunto, etc.). No contexto do código, o contains() está sendo utilizado para garantir que um item (como uma alergia, doença crônica ou medicamento) não seja adicionado novamente à lista, evitando duplicações.


    @Override
    public void exibirInfo(){
    System.out.println("\n===== INFORMAÇÕES DO PACIENTE =====");

    System.out.println("Nome: " + getNome());
    System.out.println("CPF: " + getCpf());
    System.out.println("Data de nascimento: " + getDataDeNascimento());
    System.out.println("Sexo: " + getSexo());
    System.out.println("Endereço: " + getEndereco());
    System.out.println("Telefone: " + getTelefone());
    System.out.println("E-mail: " + getEmail());
    System.out.println("Nível de acesso: " + getNivelAcesso());

    System.out.println("\n--- Dados do Paciente ---");
    System.out.println("Número da carteirinha: " + getNumeroCarteirinha());
    System.out.println("Plano de saúde: " + getPlanoSaude());
    System.out.println("Data de cadastro: " + getDataCadastro());
    System.out.println("Status: " + getStatus());
    System.out.println("Tipo sanguíneo: " + getTipoSanguineo());

    System.out.println("\n--- Dados de Saúde ---");
        System.out.println("Peso: " + (getPeso() > 0 ? String.format("%.2f kg", getPeso()) : "N/A"));
        System.out.println("Altura: " + (getAltura() > 0 ? String.format("%.2f m", getAltura()) : "N/A"));
        System.out.println("IMC: " + (getAltura() > 0 && getPeso() > 0 ? String.format("%.2f", calcularIMC()) : "N/A"));

    System.out.println("\n--- Condições e Histórico ---");
    System.out.println("Alergias: " + (alergias.isEmpty() ? "Nenhuma" : alergias));
    System.out.println("Doenças Crônicas: " + (doencasCronicas.isEmpty() ? "Nenhuma" : doencasCronicas));
    System.out.println("Histórico de Cirurgias: " + (historicoCirurgias.isEmpty() ? "Nenhum" : historicoCirurgias));
    System.out.println("Medicamentos em Uso: " + (medicamentosEmUso.isEmpty() ? "Nenhum" : medicamentosEmUso));

    System.out.println("====================================\n");
    }


}