package br.com.sistemaPlanoSaude.model;

public class PlanoPremium extends PlanoSaude {

    private boolean atendimentoDomiciliar;
    private boolean coberturaInternacional;

    // ===============================
    //     Construtores
    // ===============================
    public PlanoPremium(boolean atendimentoDomiciliar, boolean coberturaInternacional, String codigo) {
        super(
            "Plano Premium",          // nome do plano
            "PRM123",                   // código do plano
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

    
     // ===============================
    //        Getters e Setters
    // ===============================
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
    
    
        public void darAcesso() {
            System.out.println("Acesso concedido ao plano PREMIUM. Você possui benefícios completos!");
        }

    @Override
    public double calcularMensalidade() {
        // Preço fixo ou lógica de cálculo adicional
        return getValorBase() * 1.8;
    }
    
    @Override
    public void descricaoCompleta() {
        System.out.println("\n===== DESCRIÇÃO DO PLANO PREMIUM =====");

        System.out.println("Plano: " + getNomePlano());
        System.out.println("Código do plano: " + getCodigo());
        System.out.println("Status: " + (isAtivo() ? "Ativo" : "Inativo"));
        System.out.println("Data de criação: " + getDataCriacao());
        System.out.println("Última atualização: " + getUltimaAtualizacao());

        System.out.println("\n------- Cobertura e Estrutura do Plano -------");
        System.out.println("Cobertura: " + getCobertura());
        System.out.println("Tipo de acomodação: " + getTipoAcomodacao());
        System.out.println("Abrangência: " + getAbrangencia());
        System.out.println("Limite mensal de consultas: " + getLimiteConsultas());
        System.out.printf("Valor base: R$ %.2f%n", getValorBase());
        System.out.printf("Mensalidade final: R$ %.2f%n", calcularMensalidade());

        System.out.println("\n------- Benefícios Exclusivos Premium -------");
        System.out.println("Atendimento domiciliar: " + (atendimentoDomiciliar ? "Sim" : "Não"));
        System.out.println("Cobertura internacional: " + (coberturaInternacional ? "Sim" : "Não"));


        System.out.println("======================================\n");
    }
}
 