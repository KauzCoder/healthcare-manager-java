package br.com.sistemaPlanoSaude.model;

public class PlanoBasico extends PlanoSaude {

    public PlanoBasico(String codigo) {
        super("Plano Premium", codigo, 300.0, "Completa", 12, true, 
              "Apartamento", true, true, false, 0.0, "Nacional", java.time.LocalDate.now());
    }
    

    @Override
    public double calcularMensalidade() {
        return getValorBase(); // preço fixo para plano básico
    }
}

 