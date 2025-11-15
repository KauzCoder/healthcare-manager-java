package br.com.sistemaPlanoSaude.model;
import model.enums.Cobertura;
import model.enums.Abrangencia;
import model.enums.TipoAcomodacao;

import java.time.LocalDate;
import java.util.Random;

public abstract class PlanoSaude {

    // Atributos principais
    private String nomePlano;          // Nome comercial do plano
    private String codigo;             // Identificador único do plano
    private double valorBase;          // Valor base mensal
    private String cobertura;          // Tipo de cobertura (ex: Ambulatorial, Hospitalar, Completa)
    private int limiteConsultas;       // Consultas mensais incluídas
    private boolean ativo;             // Status do plano
    private double valorMensal;
   
    // Atributos adicionais
    protected  TipoAcomodacao tipoAcomodacao;     // Ex: "Enfermaria", "Apartamento"
    protected  Abrangencia abrangencia;        // Ex: "Regional", "Nacional", "Internacional"
    protected  LocalDate dataCriacao;     // Data em que o plano foi criado
    protected  LocalDate ultimaAtualizacao; // Última modificação de dados\

    // Construtor
    public PlanoSaude(String nomePlano, String codigo, double valorBase, 
                    Cobertura cobertura, int limiteConsultas, boolean ativo,
                    TipoAcomodacao tipoAcomodacao, Abrangencia abrangencia, LocalDate dataCriacao) {

        this.nomePlano = nomePlano;
        this.codigo = gerarCodigoCarteirinha();
        this.valorBase = valorBase;
        this.cobertura = cobertura;
        this.limiteConsultas = limiteConsultas;
        this.ativo = ativo;
        this.tipoAcomodacao = tipoAcomodacao;
        this.abrangencia = abrangencia;
        this.dataCriacao = dataCriacao != null ? dataCriacao : LocalDate.now();
        this.ultimaAtualizacao = LocalDate.now();
    }

    // Getters e Setters (Encapsulamento)
    public String getNomePlano() { return nomePlano; }
    public void setNomePlano(String nomePlano) { this.nomePlano = nomePlano; }

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

    public double getValorMensal() { return valorMensal; }
    public void setValorMensal(double valorMensal) { this.valorMensal = valorMensal;}
    

    // Método abstrato (será sobrescrito nas subclasses)
    public abstract double calcularMensalidade();

    public abstract void descricaoCompleta();
}