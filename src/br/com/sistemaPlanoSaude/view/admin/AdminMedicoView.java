package br.com.sistemaPlanoSaude.view.admin;
import br.com.sistemaPlanoSaude.model.funcionarios.Administrador;
import br.com.sistemaPlanoSaude.model.funcionarios.Medico;
import br.com.sistemaPlanoSaude.view.formularios.FormularioMedico;


import java.util.Scanner;

public class AdminMedicoView {

    private final Scanner scanner = new Scanner(System.in);

    public void exibirMenu(Administrador admin) {

        limparTela();

        int opcao = -1;

        while (opcao != 0) {

            System.out.println("╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                                                              ║");
            System.out.println("║           🩺  PAINEL DO ADMINISTRADOR — MÉDICOS  🩺          ║");
            System.out.println("║                                                              ║");
            System.out.println("║     Gerencie profissionais, cadastre novos médicos,           ║");
            System.out.println("║     visualize informações e mantenha tudo organizado.         ║");
            System.out.println("║                                                              ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println();

            System.out.println("📌 **Opções para Gerenciamento de Médicos:**\n");
            System.out.println(" [ 1 ] ➜ Cadastrar Médico");
            System.out.println(" [ 2 ] ➜ Listar Médicos");
            System.out.println(" [ 3 ] ➜ Remover Médico pelo CRM");
            System.out.println(" [ 4 ] ➜ Exibir Informações de um Médico");
            System.out.println(" [ 0 ] ➜ Voltar");
            System.out.println();

            System.out.print("👉 Digite sua opção: ");
            opcao = lerInteiro();

            processarOpcao(opcao, admin);
        }
    }

    // ===============================================================
    // PROCESSAMENTO DO MENU
    // ===============================================================
    private void processarOpcao(int opcao, Administrador admin) {

        limparTela();

        switch (opcao) {
            case 1:
                cadastrarMedico(admin);
                break;

            case 2:
                listarMedicos(admin);
                break;

            case 3:
                removerMedico(admin);
                break;

            case 4:
                exibirMedico(admin);
                break;

            case 0:
                System.out.println("Retornando ao menu principal... 💼");
                return;

            default:
                System.out.println("❌ Opção inválida! Tente novamente.");
        }

        System.out.println("\nPressione ENTER para continuar...");
        scanner.nextLine();
        limparTela();
    }

    // ===============================================================
    // CADASTRAR MÉDICO
    // ===============================================================
    private void cadastrarMedico(Administrador admin) {
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║       ➕ CADASTRAR MÉDICO    ║");
        System.out.println("╚═════════════════════════════╝\n");

        Medico novo = FormularioMedico.cadastrarMedico(scanner); // SEU FORMULÁRIO

        if (novo != null) {
            admin.cadastrarMedico(novo);
            System.out.println("\n✔ Médico cadastrado com sucesso!");
        } else {
            System.out.println("\n❌ Cadastro cancelado.");
        }
    }

    // ===============================================================
    // LISTAR MÉDICOS
    // ===============================================================
    private void listarMedicos(Administrador admin) {
        System.out.println("╔═════════════════════════╗");
        System.out.println("║       📋 LISTA DE MÉDICOS");
        System.out.println("╚═════════════════════════╝\n");

        admin.listarMedicos();
    }

    // ===============================================================
    // REMOVER MÉDICO
    // ===============================================================
    private void removerMedico(Administrador admin) {

        System.out.println("╔══════════════════════════════╗");
        System.out.println("║       ❌ REMOVER MÉDICO       ║");
        System.out.println("╚══════════════════════════════╝\n");

        System.out.print("Digite o CRM do médico para remover: ");
        String crm = scanner.nextLine();

        admin.removerMedico(crm);

        System.out.println("\n✔ Operação concluída.");
    }

    // ===============================================================
    // EXIBIR INFO COMPLETA DE UM MÉDICO
    // ===============================================================
    private void exibirMedico(Administrador admin) {

        System.out.println("╔══════════════════════════════╗");
        System.out.println("║  🔎 CONSULTAR DADOS DO MÉDICO  ║");
        System.out.println("╚══════════════════════════════╝\n");

        System.out.print("Informe o CRM: ");
        String crm = scanner.nextLine();

        for (Medico m : admin.getMedicos()) {
            if (m.getCrm().equalsIgnoreCase(crm)) {
                m.exibirInfo();
                return;
            }
        }

        System.out.println("❌ Médico não encontrado!");
    }

    // ===============================================================
    // UTILITÁRIOS
    // ===============================================================
    private void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private int lerInteiro() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.print("Digite um número válido: ");
            }
        }
    }
}
