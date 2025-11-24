package br.com.sistemaPlanoSaude.view.admin;

import br.com.sistemaPlanoSaude.model.enums.PlanosDeSaude;
import br.com.sistemaPlanoSaude.model.funcionarios.Administrador;
import br.com.sistemaPlanoSaude.model.planos.PlanoBasico;
import br.com.sistemaPlanoSaude.model.planos.PlanoPremium;
import br.com.sistemaPlanoSaude.model.planos.PlanoSaude;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    // ===============================
    // MÉTODOS DE INTEGRAÇÃO COM BANCO EM MEMÓRIA
    // ===============================

    // Simulação de banco de dados em memória para planos
    private final List<PlanoSaude> planosDB = new ArrayList<>();

    // Adicionar novo plano
    public void adicionarPlano() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║     ➕ ADICIONAR NOVO PLANO      ║");
        System.out.println("╚══════════════════════════════════╝\n");

        PlanoSaude novoPlano = escolherPlano();
        if (novoPlano == null) return;

        System.out.print("Informe o valor base do plano: R$ ");
        double valor = scanner.nextDouble();
        scanner.nextLine();
        novoPlano.setValorBase(valor);

        // Adiciona ao banco em memória
        boolean exists = planosDB.stream().anyMatch(p -> p.getNomePlano() == novoPlano.getNomePlano());
        if (exists) {
            System.out.println("❌ Plano já existe no banco de dados!");
            return;
        }
        planosDB.add(novoPlano);
        System.out.println("✔ Plano adicionado ao banco de dados em memória!");
    }

    // Listar todos os planos do banco
    public void listarPlanosBanco() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║   📄 LISTA DE PLANOS (BANCO)     ║");
        System.out.println("╚══════════════════════════════════╝\n");

        if (planosDB.isEmpty()) {
            System.out.println("Nenhum plano cadastrado no banco.");
            return;
        }
        for (PlanoSaude p : planosDB) {
            System.out.println("- " + formatarNomePlano(p));
            System.out.printf("  Valor base: R$ %.2f\n", p.getValorBase());
            System.out.println("  Última atualização: " + p.getUltimaAtualizacao());
            System.out.println();
        }
    }

    // Buscar plano por tipo
    public void buscarPlano() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║   🔎 BUSCAR PLANO PELO TIPO      ║");
        System.out.println("╚══════════════════════════════════╝\n");

        System.out.print("Digite o tipo (1-Básico, 2-Premium): ");
        String tipo = scanner.nextLine();
        PlanoSaude plano = null;
        if ("1".equals(tipo)) {
            plano = planosDB.stream().filter(p -> p instanceof PlanoBasico).findFirst().orElse(null);
        } else if ("2".equals(tipo)) {
            plano = planosDB.stream().filter(p -> p instanceof PlanoPremium).findFirst().orElse(null);
        }
        if (plano != null) {
            System.out.println("Plano encontrado:");
            System.out.println("- " + formatarNomePlano(plano));
            System.out.printf("  Valor base: R$ %.2f\n", plano.getValorBase());
            System.out.println("  Última atualização: " + plano.getUltimaAtualizacao());
        } else {
            System.out.println("❌ Plano não encontrado no banco de dados.");
        }
    }

    // Remover plano pelo tipo
    public void removerPlano() {
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║     ❌ REMOVER PLANO             ║");
        System.out.println("╚══════════════════════════════════╝\n");

        System.out.print("Digite o tipo do plano para remover (1-Básico, 2-Premium): ");
        String tipo = scanner.nextLine();
        boolean removed = false;
        if ("1".equals(tipo)) {
            removed = planosDB.removeIf(p -> p instanceof PlanoBasico);
        } else if ("2".equals(tipo)) {
            removed = planosDB.removeIf(p -> p instanceof PlanoPremium);
        }
        if (removed) {
            System.out.println("✔ Plano removido do banco de dados em memória!");
        } else {
            System.out.println("❌ Plano não encontrado para remoção.");
        }
    }

}
