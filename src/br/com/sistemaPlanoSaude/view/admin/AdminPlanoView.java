package br.com.sistemaPlanoSaude.view.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.sistemaPlanoSaude.model.funcionarios.Administrador;
import br.com.sistemaPlanoSaude.model.planos.PlanoSaude;
import br.com.sistemaPlanoSaude.model.planos.PlanoBasico;
import br.com.sistemaPlanoSaude.model.planos.PlanoPremium;
import br.com.sistemaPlanoSaude.model.enums.PlanosDeSaude;

public class AdminPlanoView {

    private final Scanner scanner = new Scanner(System.in);
    private final Administrador admin;

    public AdminPlanoView(Administrador admin) {
        this.admin = admin;
    }

    // =====================================================================
    //                              MENU PRINCIPAL
    // =====================================================================

    public void exibirMenu() {

        limparTela();

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                              ║");
        System.out.println("║            🏥  PAINEL ADMINISTRATIVO - PLANOS  🏥            ║");
        System.out.println("║                                                              ║");
        System.out.println("║     Gerencie preços, reajustes e informações dos planos      ║");
        System.out.println("║                 da plataforma Health Care.                   ║");
        System.out.println("║                                                              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();

        System.out.println("📌 **Opções de Administração:**\n");
        System.out.println(" [ 1 ] ➜ Atualizar valor de um plano");
        System.out.println(" [ 2 ] ➜ Aplicar reajuste percentual em um plano");
        System.out.println(" [ 3 ] ➜ Aplicar reajuste em todos os planos");
        System.out.println(" [ 4 ] ➜ Listar planos e preços atuais");
        System.out.println(" [ 0 ] ➜ Voltar / Encerrar");
        System.out.println();

        System.out.print("👉 Digite sua opção: ");
        int opcao = -1;
        try {
            opcao = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            opcao = -1;
        }

        processarOpcao(opcao);
    }

    private void processarOpcao(int opcao) {

        limparTela();

        switch (opcao) {
            case 1:
                atualizarValorPlano();
                break;
            case 2:
                reajustePercentualPlano();
                break;
            case 3:
                reajusteEmTodos();
                break;
            case 4:
                listarPlanos();
                break;
            case 0:
                System.out.println("Voltando ao menu principal... 👋");
                return;
            default:
                System.out.println("❌ Opção inválida!");
                break;
        }

        System.out.println("\nPressione ENTER para continuar...");
        scanner.nextLine();
        exibirMenu();
    }

    // =====================================================================
    //                   MÉTODOS DE ADMINISTRAÇÃO DE PLANOS
    // =====================================================================

    private void atualizarValorPlano() {

        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║     📝 ALTERAÇÃO DE VALOR        ║");
        System.out.println("╚══════════════════════════════════╝\n");

        PlanoSaude plano = escolherPlano();
        if (plano == null) return;

        System.out.print("\nInforme o novo valor: R$ ");
        double novoValor = scanner.nextDouble();
        scanner.nextLine();

        admin.atualizarPrecoPlano(plano, novoValor);
    }

    private void reajustePercentualPlano() {

        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║      📈 REAJUSTE PERCENTUAL      ║");
        System.out.println("╚══════════════════════════════════╝\n");

        PlanoSaude plano = escolherPlano();
        if (plano == null) return;

        System.out.print("\nDigite o percentual (ex: 10 para +10% ou -5 para -5%): ");
        double percentual = scanner.nextDouble();
        scanner.nextLine();

        admin.aplicarReajustePercentual(plano, percentual);
    }

    private void reajusteEmTodos() {

        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║    📊 REAJUSTE EM TODOS PLANOS   ║");
        System.out.println("╚══════════════════════════════════╝\n");

        List<PlanoSaude> lista = new ArrayList<>();
        lista.add(new PlanoBasico());
        lista.add(new PlanoPremium());

        System.out.print("Percentual de reajuste: ");
        double percentual = scanner.nextDouble();
        scanner.nextLine();

        admin.aplicarReajusteEmLista(lista, percentual);
    }

    private void listarPlanos() {

        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║      📄 LISTA DE PLANOS          ║");
        System.out.println("╚══════════════════════════════════╝\n");

        List<PlanoSaude> planos = new ArrayList<>();
        planos.add(new PlanoBasico());
        planos.add(new PlanoPremium());

        for (PlanoSaude p : planos) {
            System.out.println("- " + formatarNomePlano(p));
            System.out.printf("  Valor atual: R$ %.2f%n", p.getValorBase());
            System.out.println("  Última atualização: " + p.getUltimaAtualizacao());
            System.out.println();
        }
    }

    // =====================================================================
    //                        MÉTODOS AUXILIARES
    // =====================================================================

    private PlanoSaude escolherPlano() {

        System.out.println("Selecione o plano:");
        System.out.println(" [1] Plano Básico");
        System.out.println(" [2] Plano Premium");
        System.out.println(" [0] Cancelar\n");

        System.out.print("👉 Sua escolha: ");
        String escolha = scanner.nextLine();

        switch (escolha) {
            case "1":
                return new PlanoBasico();
            case "2":
                return new PlanoPremium();
            case "0":
                return null;
            default:
                System.out.println("❌ Opção inválida.");
                return null;
        }
    }

    private String formatarNomePlano(PlanoSaude plano) {

        if (plano == null || plano.getNomePlano() == null)
            return "Plano de Saúde";

        PlanosDeSaude tipo = plano.getNomePlano();

        switch (tipo) {
            case PLANO_BASICO:
                return "Plano Básico";
            case PLANO_PREMIUM:
                return "Plano Premium";
            default:
                return "Plano de Saúde";
        }
    }


    private void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
