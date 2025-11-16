package br.com.sistemaPlanoSaude.model;

import model.enums.Cobertura;
import model.enums.Abrangencia;
import model.enums.TipoAcomodacao;
import java.time.LocalDate;

public class PlanoPremium extends PlanoSaude {

    private boolean atendimentoDomiciliar;
    private boolean coberturaInternacional;

    // Construtor correto
    public PlanoPremium(boolean atendimentoDomiciliar, boolean coberturaInternacional, String codigo) {
        super(
            "Plano Premium",          // nome do plano
            codigo,                   // código do plano
            1500.0,                   // valor base
            Cobertura.COMPLETA,       // cobertura (enum)
            20,                       // limite de consultas
            true,                     // ativo
            TipoAcomodacao.APARTAMENTO,   // tipo de acomodação
            Abrangencia.NACIONAL,     // abrangência (enum)
            LocalDate.now()           // data de criação
        );

        this.atendimentoDomiciliar = atendimentoDomiciliar;
        this.coberturaInternacional = coberturaInternacional;
    }

    @Override
    public double calcularMensalidade() {
        // Preço fixo ou lógica de cálculo adicional
        return getValorBase();
    }

    public void darAcesso() {
        System.out.println("Acesso concedido ao plano PREMIUM. Você possui benefícios completos!");
    }

    // Getters e Setters dos atributos específicos do Premium
    public boolean isAtendimentoDomiciliar() {
        return atendimentoDomiciliar;
    }

    public void setAtendimentoDomiciliar(boolean atendimentoDomiciliar) {
        this.atendimentoDomiciliar = atendimentoDomiciliar;
    }

    public boolean isCoberturaInternacional() {
        return coberturaInternacional;
    }

    public void setCoberturaInternacional(boolean coberturaInternacional) {
        this.coberturaInternacional = coberturaInternacional;
    }
}

 