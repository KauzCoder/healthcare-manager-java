package br.com.sistemaPlanoSaude.view.interfaces;

import java.util.Scanner;

import br.com.sistemaPlanoSaude.model.enums.PlanosDeSaude;
import br.com.sistemaPlanoSaude.model.pessoas.Paciente;
import br.com.sistemaPlanoSaude.model.planos.PlanoBasico;
import br.com.sistemaPlanoSaude.model.planos.PlanoPremium;
import br.com.sistemaPlanoSaude.model.planos.PlanoSaude;
import br.com.sistemaPlanoSaude.view.formularios.FormularioPaciente;

public class interfaceInterresado {

    private final Scanner scanner = new Scanner(System.in);

    public void exibirMenu() {

        limparTela();

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                              ║");
        System.out.println("║                🌿  BEM-VINDO À HEALTH CARE  🌿               ║");
        System.out.println("║                                                              ║");
        System.out.println("║      Cuidar de você é a nossa prioridade. Escolha abaixo     ║");
        System.out.println("║        como deseja continuar e conheça nossos serviços.       ║");
        System.out.println("║                                                              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();

        System.out.println("📌 **Opções Disponíveis:**");
        System.out.println();
        System.out.println(" [ 1 ] ➜ Conhecer nossos Planos de Saúde");
        System.out.println(" [ 2 ] ➜ Saber mais sobre a empresa Health Care");
        System.out.println(" [ 3 ] ➜ Falar com um atendente virtual");
        System.out.println(" [ 0 ] ➜ Sair");
        System.out.println();

        System.out.print("👉 Digite sua opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        processarOpcao(opcao);
    }

    private void processarOpcao(int opcao) {

        limparTela();

        switch (opcao) {

            case 1:
                System.out.println("╔════════════════════════════════════╗");
                System.out.println("║   🏥 TIPOS DE PLANOS DISPONÍVEIS   ║");
                System.out.println("╚════════════════════════════════════╝\n");

                System.out.println("Oferecemos planos feitos sob medida\n" +
                                   "para sua saúde, segurança e bem-estar.\n");

                System.out.println("• Plano Básico - Coberturas essenciais por um preço acessível.");
                System.out.println("• Plano Premium - Consultas mais rápidas e ampla rede.");
                System.out.println("• Plano Elite - Atendimento VIP, enfermaria e ampla cobertura.");
                System.out.println("• Plano Familiar - Proteção completa para toda a família.");
                System.out.println();
                System.out.println("👉 Para adquirir um plano, volte ao menu e escolha o formulário.");
                break;

            case 2:
                System.out.println("╔═══════════════════════════════╗");
                System.out.println("║     🏢 SOBRE A HEALTH CARE     ║");
                System.out.println("╚═══════════════════════════════╝\n");

                System.out.println("A Health Care nasceu com o objetivo de proporcionar\n" +
                                   "acesso à saúde com rapidez, qualidade e transparência.\n" +
                                   "Contamos com mais de 500 médicos credenciados, hospitais\n" +
                                   "parceiros e atendimento humanizado 24 horas.\n");
                System.out.println("Nosso compromisso é com você e sua família.\n");
                break;

            case 3:
                System.out.println("╔══════════════════════════════════╗");
                System.out.println("║     💬 ATENDIMENTO VIRTUAL       ║");
                System.out.println("╚══════════════════════════════════╝\n");

                System.out.println("Olá! Sou o atendente virtual da Health Care.\n" +
                                   "Como posso ajudar hoje?\n" +
                                   "• Informações sobre planos\n" +
                                   "• Valores e mensalidades\n" +
                                   "• Como contratar um plano\n" +
                                   "• Como funciona nossa rede credenciada\n");
                break;

            case 4: 
                System.out.println("╔═══════════════════════════════════════════╗");
                System.out.println("║     📝 FORMULÁRIO DE AQUISIÇÃO DE PLANO    ║");
                System.out.println("╚═══════════════════════════════════════════╝\n");


                PlanoSaude planoEscolhido = escolherPlanoParaContratacao();
                if (planoEscolhido == null) {
                    System.out.println("\nOperação cancelada. Você pode retornar ao menu principal a qualquer momento.");
                    break;
                }

                Paciente novoPaciente = FormularioPaciente.cadastrarPaciente(scanner);
                if (novoPaciente == null) {
                    System.out.println("\nCadastro cancelado. Você pode retornar ao menu principal a qualquer momento.");
                    break;
                }

                System.out.println("\n✨ Obrigado, " + novoPaciente.getNome() + "!");
                System.out.println("Seu pedido de contratação do **" + formatarNomePlano(planoEscolhido) + "** foi recebido.");
                System.out.println("Nosso time entrará em contato via WhatsApp em até 24 horas.");
                System.out.println("\n📄 CPF informado: " + novoPaciente.getCpf());
                System.out.println("🌱 Bem-vindo à Health Care! Sua saúde está em boas mãos.");
                break;

            case 0:
                System.out.println("Obrigado por visitar a Health Care! 💚");
                System.out.println("Desejamos muita saúde e bem-estar para você.\n");
                break;

            default:
                System.out.println("❌ Opção inválida! Tente novamente.");
                break;
        }
    }

    private void limparTela() {
        // Funciona na maioria dos consoles
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private PlanoSaude escolherPlanoParaContratacao() {
        System.out.println("\nEscolha o plano desejado:");
        System.out.println("  [1] Plano Básico");
        System.out.println("  [2] Plano Premium");
        System.out.println("  [0] Cancelar solicitação");
        System.out.print("\n👉 Sua escolha: ");

        while (true) {
            String resposta = scanner.nextLine().trim();
            switch (resposta) {
                case "1":
                    return new PlanoBasico();
                case "2":
                    return new PlanoPremium();
                case "0":
                    return null;
                default:
                    System.out.print("Opção inválida. Informe 1, 2 ou 0 para cancelar: ");
            }
        }
    }

    private String formatarNomePlano(PlanoSaude plano) {
        if (plano == null || plano.getNomePlano() == null) {
            return "Plano de Saúde";
        }

        PlanosDeSaude tipo = plano.getNomePlano();
        return switch (tipo) {
            case PLANO_BASICO -> "Plano Básico";
            case PLANO_PREMIUM -> "Plano Premium";
        };
    }
}