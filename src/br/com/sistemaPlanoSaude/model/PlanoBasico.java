package br.com.sistemaPlanoSaude.model;

import model.enums.Cobertura;
import model.enums.Abrangencia;
import model.enums.TipoAcomodacao;
import java.time.LocalDate;
public class PlanoBasico extends PlanoSaude {

   public PlanoBasico(String codigo) {
        super(
            "Plano Basico",
            codigo,
            1500.0,
            Cobertura.AMBULATORIAL,
            10,
            true,
            TipoAcomodacao.APARTAMENTO,
            Abrangencia.ESTADUAL,
            LocalDate.now()
        );
    }

    @Override
    public double calcularMensalidade() {
        // Exemplo de cálculo com adicional
        return getValorBase() * 1.3; 
    }
    
    public void darAcesso() {
            System.out.println("Acesso concedido ao plano BÁSICO. Bem-vindo ao sistema de pacientes!");
        }
}