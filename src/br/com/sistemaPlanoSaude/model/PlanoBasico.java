package br.com.sistemaPlanoSaude.model;

import br.com.sistemaPlanoSaude.model.enums.Cobertura;
import br.com.sistemaPlanoSaude.model.enums.PlanosDeSaude;
import br.com.sistemaPlanoSaude.model.enums.Abrangencia;
import br.com.sistemaPlanoSaude.model.enums.TipoAcomodacao;
import java.time.LocalDate;

public class PlanoBasico extends PlanoSaude {


    // ===============================
    //     Construtores
    // ===============================
   public PlanoBasico() {
        super(
            PlanosDeSaude.PLANO_BASICO,
            "BASICO123",
            1500.0,
            Cobertura.AMBULATORIAL,
            10,
            true,
            TipoAcomodacao.APARTAMENTO,
            Abrangencia.ESTADUAL,
            LocalDate.now()
        );
    }
    
        public void darAcesso() {
            System.out.println("Acesso concedido ao " + getNomePlano() + "! Benefícios ativados.");
        }


    
@Override
    public double calcularMensalidade() {
        // Preço fixo ou lógica de cálculo adicional
        return getValorBase();
    }

    @Override
    public void descricaoCompleta() {
        System.out.println("\n===== DESCRIÇÃO DO PLANO BASICO =====");

        System.out.println("Plano: " + getNomePlano());
        System.out.println("Código do plano: " + getCodigo());
        System.out.println("Status: " + (isAtivo() ? "Ativo" : "Inativo"));
        System.out.println("Data de criação: " + getDataCriacao());
        System.out.println("Última atualização: " + getUltimaAtualizacao());

        System.out.println("\n------- Cobertura e Estrutura -------");
        System.out.println("Cobertura: " + getCobertura());
        System.out.println("Acomodação: " + getTipoAcomodacao());
        System.out.println("Abrangência: " + getAbrangencia());
        System.out.println("Limite mensal de consultas: " + getLimiteConsultas());
        
        System.out.printf("Valor base: R$ %.2f%n", getValorBase());
        System.out.printf("Mensalidade final: R$ %.2f%n", calcularMensalidade());

        System.out.println("=========================================\n");
    }
}