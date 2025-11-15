package br.com.sistemaPlanoSaude.model;
public class PlanoBasico extends PlanoSaude {

    public PlanoBasico(String codigo) {
        super("Plano Basico", codigo, 100.0, "Basica", 5);
    }

    @Override
    public double calcularMensalidade() {
        // Exemplo de cálculo com adicional
        return getValorBase() * 1.3; 
    }
}