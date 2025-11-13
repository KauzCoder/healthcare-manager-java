package br.com.sistemaPlanoSaude.model;
public class PlanoPremium extends PlanoSaude {

    public PlanoPremium(String codigo) {
        super("Plano Premium", codigo, 250.0, "Completa", 10);
    }

    @Override
    public double calcularMensalidade() {
        // Exemplo de cálculo com adicional
        return getValorBase() * 1.3; 
    }
}