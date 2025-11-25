package br.com.sistemaPlanoSaude.view.interfaces;

import java.util.Scanner;

import br.com.sistemaPlanoSaude.model.enums.PlanosDeSaude;
import br.com.sistemaPlanoSaude.model.pessoas.Paciente;
import br.com.sistemaPlanoSaude.model.planos.PlanoBasico;
import br.com.sistemaPlanoSaude.model.planos.PlanoPremium;
import br.com.sistemaPlanoSaude.model.planos.PlanoSaude;
import br.com.sistemaPlanoSaude.util.MetodosAuxiliares;
import br.com.sistemaPlanoSaude.view.formularios.FormularioPaciente;
import br.com.sistemaPlanoSaude.service.PacienteService;

public class  InterfaceInterresado {
    private final PacienteService pacienteService = new PacienteService();
    private final Scanner scanner = new Scanner(System.in);

    public void exibirMenu() {

        MetodosAuxiliares.limparTela();

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                              ║");
        System.out.println("║                🌿  BEM-VINDO À HEALTH CARE  🌿               ║");
        System.out.println("║                                                              ║");
        System.out.println("║      Cuidar de você é a nossa prioridade. Escolha abaixo     ║");
        System.out.println("║        como deseja continuar e conheça nossos serviços.       ║");
        System.out.println("║                                                              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();

        System.out.println("📌 Opções Disponíveis:");
        System.out.println();
        System.out.println(" [ 1 ] ➜ Conhecer nossos Planos de Saúde");
        System.out.println(" [ 2 ] ➜ Saber mais sobre a empresa Health Care");
        System.out.println(" [ 3 ] ➜ Falar com um atendente virtual");
        System.out.println(" [ 4 ] ➜ Preencher formulário para aquisição de plano");
        System.out.println(" [ 0 ] ➜ Sair");
        System.out.println();

        System.out.print("👉 Digite sua opção: ");
        int opcao = lerInteiro();

        processarOpcao(opcao);
    }

    private void processarOpcao(int opcao) {

        MetodosAuxiliares.limparTela();

        switch (opcao) {

            case 1 -> exibirPlanos();

            case 2 -> exibirSobreEmpresa();

            case 3 -> exibirAtendimentoVirtual();

            case 4 -> iniciarContratacaoPlano();

            case 0 -> {
                System.out.println("Obrigado por visitar a Health Care! 💚");
                System.out.println("Desejamos muita saúde e bem-estar para você.\n");
            }

            default -> System.out.println("❌ Opção inválida! Tente novamente.");
        }
    }

    private void exibirPlanos() {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║   🏥 TIPOS DE PLANOS DISPONÍVEIS   ║");
        System.out.println("╚════════════════════════════════════╝\n");

        System.out.println("Oferecemos planos feitos sob medida para sua saúde:");
        System.out.println("• Plano Básico - Coberturas essenciais por um preço acessível.");
        System.out.println("• Plano Premium - Consultas mais rápidas e ampla rede credenciada.");
        System.out.println();
        System.out.println("👉 Para adquirir um plano, volte ao menu e escolha o formulário.");
        MetodosAuxiliares.pausarTela();
    }

    private void exibirSobreEmpresa() {
        System.out.println("╔═══════════════════════════════╗");
        System.out.println("║     🏢 SOBRE A HEALTH CARE    ║");
        System.out.println("╚═══════════════════════════════╝\n");

        System.out.println("""
                A Health Care nasceu com o objetivo de proporcionar acesso à saúde
                com rapidez, qualidade e transparência. Contamos com mais de 500 médicos
                credenciados, hospitais parceiros e atendimento humanizado 24 horas.
                """);
            MetodosAuxiliares.pausarTela();
    }

    private void exibirAtendimentoVirtual() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║     💬 ATENDIMENTO VIRTUAL       ║");
        System.out.println("╚══════════════════════════════════╝\n");

        System.out.println("""
            Olá! Sou o atendente virtual da Health Care.
                Como posso ajudar hoje?
                • Informações sobre planos
                • Valores e mensalidades
                • Como contratar um plano
                • Como funciona nossa rede credenciada
            """);
            MetodosAuxiliares.pausarTela();
    }

    private void iniciarContratacaoPlano() {
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║     📝 FORMULÁRIO DE AQUISIÇÃO DE PLANO    ║");
        System.out.println("╚═══════════════════════════════════════════╝\n");

        PlanoSaude planoEscolhido = escolherPlanoParaContratacao();
        if (planoEscolhido == null) {
            System.out.println("Operação cancelada. Retornando ao menu...");
            return;
        }

        Paciente novoPaciente = FormularioPaciente.cadastrarPaciente(scanner);

        if (novoPaciente == null) {
            System.out.println("Cadastro cancelado. Você pode retornar ao menu principal.");
            return;
        }

        novoPaciente.setPlanoSaude(planoEscolhido);
        novoPaciente.setNumeroCarteirinha(planoEscolhido.getCodigo());

        boolean cadastrado = pacienteService.cadastrarPaciente(novoPaciente);
        if (!cadastrado) {
            System.out.println("❌ Não foi possível registrar o paciente. Carteirinha já existente.");
            MetodosAuxiliares.pausarTela();
            return;
        }

        System.out.println("\n✨ Obrigado, " + novoPaciente.getNome() + "!");
        System.out.println("Seu pedido de contratação do **" + formatarNomePlano(planoEscolhido) + "** foi recebido.");
        System.out.println("Nosso time entrará em contato via WhatsApp em até 24 horas.");
        System.out.println("\n📄 CPF informado: " + novoPaciente.getCpf());
        System.out.println("Carteirinha: " + novoPaciente.getNumeroCarteirinha());
        System.out.println("🌱 Bem-vindo à Health Care!");
        MetodosAuxiliares.pausarTela();
    }

    private PlanoSaude escolherPlanoParaContratacao() {
        System.out.println("Escolha o plano desejado:");
        System.out.println("  [1] Plano Básico");
        System.out.println("  [2] Plano Premium");
        System.out.println("  [0] Cancelar solicitação");
        System.out.print("👉 Sua escolha: ");

        while (true) {
            String resposta = scanner.nextLine().trim();
            switch (resposta) {
                case "1" -> {
                    return new PlanoBasico();
                }
                case "2" -> {
                    return new PlanoPremium();
                }
                case "0" -> {
                    return null;
                }
                default -> System.out.print("Opção inválida. Tente 1, 2 ou 0: ");
            }
        }
    }

    private String formatarNomePlano(PlanoSaude plano) {
        if (plano == null || plano.getNomePlano() == null) return "Plano de Saúde";

        PlanosDeSaude tipo = plano.getNomePlano();
        return switch (tipo) {
            case PLANO_BASICO -> "Plano Básico";
            case PLANO_PREMIUM -> "Plano Premium";
        };
    }

    private int lerInteiro() {
        while (true) {
            try {
                String entrada = scanner.nextLine().trim();
                return Integer.parseInt(entrada);
            } catch (Exception e) {
                System.out.print("Digite um número válido: ");
            }
        }
    }
}
