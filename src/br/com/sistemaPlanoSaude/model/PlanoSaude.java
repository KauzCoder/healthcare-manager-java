package br.com.sistemaPlanoSaude.model;
import br.com.sistemaPlanoSaude.model.enums.Cobertura;
import br.com.sistemaPlanoSaude.model.enums.Abrangencia;
import br.com.sistemaPlanoSaude.model.enums.TipoAcomodacao;

import java.time.LocalDate;
import java.util.Random;

public abstract class PlanoSaude {

    // Atributos principais
    protected  String nomePlano;          // Nome comercial do plano
    protected final String codigo;             // Identificador imutavel único do plano
    protected  double valorBase;          // Valor base mensal
    protected  Cobertura cobertura; // Tipo de cobertura (ex: Ambulatorial, Hospitalar, Completa)
    protected  int limiteConsultas;       // Consultas mensais incluídas
    protected  boolean ativo;             // Status do plano

    protected  TipoAcomodacao tipoAcomodacao;     // Ex: "Enfermaria", "Apartamento"
    protected  Abrangencia abrangencia;        // Ex: "Regional", "Nacional", "Internacional"
    protected  LocalDate dataCriacao;     // Data em que o plano foi criado
    protected  LocalDate ultimaAtualizacao; // Última modificação de dados\
    
    // ===============================
    //     Construtores
    // ===============================
    public PlanoSaude(String nomePlano, String codigoPrefixo, double valorBase, 
                    Cobertura cobertura, int limiteConsultas, boolean ativo,
                    TipoAcomodacao tipoAcomodacao, Abrangencia abrangencia, LocalDate dataCriacao) {

        this.nomePlano = nomePlano;
        this.codigo = gerarCodigoCarteirinha(codigoPrefixo);
        this.valorBase = valorBase;
        this.cobertura = cobertura;
        this.limiteConsultas = limiteConsultas;
        this.ativo = ativo;
        this.tipoAcomodacao = tipoAcomodacao;
        this.abrangencia = abrangencia;
        this.dataCriacao = dataCriacao != null ? dataCriacao : LocalDate.now();
        this.ultimaAtualizacao = LocalDate.now();
    }

     // ===============================
    //        Getters e Setters
    // ===============================
    public String getNomePlano() { return nomePlano; }
    public void setNomePlano(String nomePlano) { this.nomePlano = nomePlano; }
    
    public String getCodigo() { return codigo; }

    public TipoAcomodacao getTipoAcomodacao() { return tipoAcomodacao; }
    public void setTipoAcomodacao(TipoAcomodacao tipoAcomodacao) { this.tipoAcomodacao = tipoAcomodacao; }

    public double getValorBase() { return valorBase; }
    public void setValorBase(double valorBase) { this.valorBase = valorBase; }

    public Cobertura getCobertura() { return cobertura; }
    public void setCobertura(Cobertura cobertura) { this.cobertura = cobertura; }

    public int getLimiteConsultas() { return limiteConsultas; }
    public void setLimiteConsultas(int limiteConsultas) { this.limiteConsultas = limiteConsultas; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    public Abrangencia getAbrangencia() { return abrangencia; }
    public void setAbrangencia(Abrangencia abrangencia) { this.abrangencia = abrangencia; }

    public LocalDate getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDate dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDate getUltimaAtualizacao() { return ultimaAtualizacao; }
    public void setUltimaAtualizacao(LocalDate ultimaAtualizacao) { this.ultimaAtualizacao = ultimaAtualizacao; }

    // método para gerar código de carteirinha baseado no código do plano
    private String gerarCodigoCarteirinha(String codigoBase) {
        Random random = new Random();
        int sufixo = random.nextInt(900000000) + 100000000;
        return codigoBase + sufixo;
    }


   // ===============================
    //        Status Helpers 
    // ===============================
    public boolean estaAtivo() { return this.ativo; }

    public void ativar() {
        if (!this.ativo) {
            this.ativo = true;
            this.ultimaAtualizacao = LocalDate.now();
        }
    }

    public void desativar() {
        if (this.ativo) {
            this.ativo = false;
            this.ultimaAtualizacao = LocalDate.now();
        }
    }
    


    // ===============================
    //        Método abstrato 
    // ===============================
    public abstract double calcularMensalidade();
    public abstract void descricaoCompleta();
}