package br.com.sistemaPlanoSaude.model;

public class PlanoPremium extends PlanoSaude {

    public PlanoPremium(String codigo) {
        super("Plano Premium", codigo, 300.0, "Completa", 12, true, 
              "Apartamento", true, true, false, 0.0, "Nacional", java.time.LocalDate.now());
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

 