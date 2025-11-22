package br.com.sistemaPlanoSaude.model;
import br.com.sistemaPlanoSaude.model.enums.Cobertura;
import br.com.sistemaPlanoSaude.model.enums.Abrangencia;
import br.com.sistemaPlanoSaude.model.enums.TipoAcomodacao;
import br.com.sistemaPlanoSaude.model.enums.PlanosDeSaude;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class PlanoSaude {
    private static final List<String> CODIGOS_BASICO = new ArrayList<>();
    private static final List<String> CODIGOS_PREMIUM = new ArrayList<>();


    // Atributos principais
    protected  PlanosDeSaude nomePlano;          // Nome comercial do plano
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
    public PlanoSaude(PlanosDeSaude nomePlano, String codigoPrefixo, double valorBase, 
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
    public PlanosDeSaude getNomePlano() { return nomePlano; }
    public void setNomePlano(PlanosDeSaude nomePlano) { this.nomePlano = nomePlano; }
    
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

    private List<String> obterListaDoPlanoAtual() {
        return switch (this.nomePlano) {
            case PLANO_BASICO -> CODIGOS_BASICO;
            case PLANO_PREMIUM -> CODIGOS_PREMIUM;
            default -> throw new IllegalStateException("Plano não suportado: " + this.nomePlano);
        };
    }

    public boolean registrarCarteirinhaPaciente(String carteirinha) {
        if (carteirinha == null || carteirinha.trim().isEmpty()) {
            return false;
        }
        List<String> carteirinhas = obterListaDoPlanoAtual();
        String codigoNormalizado = carteirinha.trim().toUpperCase();
        if (carteirinhas.contains(codigoNormalizado)) {
            return false;
        }
        carteirinhas.add(codigoNormalizado);
        return true;
    }

    public boolean carteirinhaJaRegistrada(String carteirinha) {
        if (carteirinha == null) {
            return false;
        }
        return obterListaDoPlanoAtual().contains(carteirinha.trim().toUpperCase());
    }

    public List<String> listarCarteirinhasRegistradas() {
        return Collections.unmodifiableList(new ArrayList<>(obterListaDoPlanoAtual()));
    }

    public static List<String> listarCarteirinhasBasico() {
        return Collections.unmodifiableList(new ArrayList<>(CODIGOS_BASICO));
    }

    public static List<String> listarCarteirinhasPremium() {
        return Collections.unmodifiableList(new ArrayList<>(CODIGOS_PREMIUM));
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