package br.com.sistemaPlanoSaude.model;

import java.time.LocalDate;

public abstract class PlanoSaude {

    // Atributos principais
    private String nomePlano;          // Nome comercial do plano
    private String codigo;             // Identificador único do plano
    private double valorBase;          // Valor base mensal
    private String cobertura;          // Tipo de cobertura (ex: Ambulatorial, Hospitalar, Completa)
    private int limiteConsultas;       // Consultas mensais incluídas
    private boolean ativo;             // Status do plano

    // Atributos adicionais
    private String tipoAcomodacao;     // Ex: "Enfermaria", "Apartamento"
    private boolean incluiOdontologia; // Cobertura odontológica
    private boolean incluiExames;      // Cobertura de exames laboratoriais
    private boolean coparticipacao;    // Se há cobrança adicional por uso
    private double taxaCoparticipacao; // Percentual cobrado por consulta (se houver)
    private String abrangencia;        // Ex: "Regional", "Nacional", "Internacional"
    private LocalDate dataCriacao;     // Data em que o plano foi criado
    private LocalDate ultimaAtualizacao; // Última modificação de dados

    // Construtor
    public PlanoSaude(String nomePlano, String codigo, double valorBase, String cobertura, 
                      int limiteConsultas, boolean ativo, String tipoAcomodacao, boolean incluiOdontologia, 
                      boolean incluiExames, boolean coparticipacao, double taxaCoparticipacao, 
                      String abrangencia, LocalDate dataCriacao) {

        this.nomePlano = nomePlano;
        this.codigo = codigo;
        this.valorBase = valorBase;
        this.cobertura = cobertura;
        this.limiteConsultas = limiteConsultas;
        this.ativo = ativo;
        this.tipoAcomodacao = tipoAcomodacao;
        this.incluiOdontologia = incluiOdontologia;
        this.incluiExames = incluiExames;
        this.coparticipacao = coparticipacao;
        this.taxaCoparticipacao = taxaCoparticipacao;
        this.abrangencia = abrangencia;
        this.dataCriacao = dataCriacao;
        this.ultimaAtualizacao = LocalDate.now();
    }

    // Getters e Setters (Encapsulamento)
    public String getNomePlano() { return nomePlano; }
    public void setNomePlano(String nomePlano) { this.nomePlano = nomePlano; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public double getValorBase() { return valorBase; }
    public void setValorBase(double valorBase) { this.valorBase = valorBase; }

    public String getCobertura() { return cobertura; }
    public void setCobertura(String cobertura) { this.cobertura = cobertura; }

    public int getLimiteConsultas() { return limiteConsultas; }
    public void setLimiteConsultas(int limiteConsultas) { this.limiteConsultas = limiteConsultas; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    public String getTipoAcomodacao() { return tipoAcomodacao; }
    public void setTipoAcomodacao(String tipoAcomodacao) { this.tipoAcomodacao = tipoAcomodacao; }

    public boolean isIncluiOdontologia() { return incluiOdontologia; }
    public void setIncluiOdontologia(boolean incluiOdontologia) { this.incluiOdontologia = incluiOdontologia; }

    public boolean isIncluiExames() { return incluiExames; }
    public void setIncluiExames(boolean incluiExames) { this.incluiExames = incluiExames; }

    public boolean isCoparticipacao() { return coparticipacao; }
    public void setCoparticipacao(boolean coparticipacao) { this.coparticipacao = coparticipacao; }

    public double getTaxaCoparticipacao() { return taxaCoparticipacao; }
    public void setTaxaCoparticipacao(double taxaCoparticipacao) { this.taxaCoparticipacao = taxaCoparticipacao; }

    public String getAbrangencia() { return abrangencia; }
    public void setAbrangencia(String abrangencia) { this.abrangencia = abrangencia; }

    public LocalDate getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDate dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDate getUltimaAtualizacao() { return ultimaAtualizacao; }
    public void setUltimaAtualizacao(LocalDate ultimaAtualizacao) { this.ultimaAtualizacao = ultimaAtualizacao; }

    // Método abstrato (será sobrescrito nas subclasses)
    public abstract double calcularMensalidade();

    // Método abstrato — as subclasses são obrigadas a implementar.
    public void exibirInformacoes() {
        System.out.println("Plano: " + nomePlano);
        System.out.println("Código: " + codigo);
        System.out.println("Cobertura: " + cobertura);
        System.out.println("Abrangência: " + abrangencia);
        System.out.println("Acomodação: " + tipoAcomodacao);
        System.out.println("Valor base: R$ " + valorBase);
        System.out.println("Limite de consultas: " + limiteConsultas);
        System.out.println("Inclui odontologia: " + (incluiOdontologia ? "Sim" : "Não"));
        System.out.println("Inclui exames: " + (incluiExames ? "Sim" : "Não"));
        System.out.println("Coparticipação: " + (coparticipacao ? "Sim (" + taxaCoparticipacao + "%)" : "Não"));
        System.out.println("Ativo: " + (ativo ? "Sim" : "Não"));
        System.out.println("Criado em: " + dataCriacao);
        System.out.println("Última atualização: " + ultimaAtualizacao);
    }
}