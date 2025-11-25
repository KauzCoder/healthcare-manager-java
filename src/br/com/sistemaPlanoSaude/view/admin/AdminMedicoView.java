package br.com.sistemaPlanoSaude.view.admin;

import br.com.sistemaPlanoSaude.database.MedicoDataBase;
import br.com.sistemaPlanoSaude.model.funcionarios.Administrador;
import br.com.sistemaPlanoSaude.model.funcionarios.Medico;
import br.com.sistemaPlanoSaude.util.ConsoleColors;
import br.com.sistemaPlanoSaude.util.MetodosAuxiliares;
import br.com.sistemaPlanoSaude.view.formularios.FormularioMedico;
import java.util.Scanner;

public class AdminMedicoView {

    private final Scanner scanner = new Scanner(System.in);
    private final MedicoDataBase medicoDB = new MedicoDataBase();

    public void exibirMenu(Administrador admin) {

        MetodosAuxiliares.limparTela();

        int opcao = -1;

        while (opcao != 0) {

            System.out.println(ConsoleColors.BLUE + ConsoleColors.BOLD +
                "╔══════════════════════════════════════════════════════════════╗\n" +
                "║                                                              ║\n" +
                "║           🩺  PAINEL DO ADMINISTRADOR — MÉDICOS  🩺          ║\n" +
                "║                                                              ║\n" +
                "║     Gerencie profissionais, cadastre novos médicos,           ║\n" +
                "║     visualize informações e mantenha tudo organizado.         ║\n" +
                "║                                                              ║\n" +
                "╚══════════════════════════════════════════════════════════════╝"
                + ConsoleColors.RESET);

            System.out.println();

            System.out.println(ConsoleColors.CYAN + "📌 **Opções para Gerenciamento de Médicos:**\n" + ConsoleColors.RESET);
            System.out.println(" [ 1 ] ➜ Cadastrar Médico");
            System.out.println(" [ 2 ] ➜ Listar Médicos");
            System.out.println(" [ 3 ] ➜ Remover Médico pelo CRM");
            System.out.println(" [ 4 ] ➜ Exibir Informações de um Médico");
            System.out.println(" [ 0 ] ➜ Voltar");
            System.out.println();

            System.out.print(ConsoleColors.YELLOW + "👉 Digite sua opção: " + ConsoleColors.RESET);
            opcao = lerInteiro();

            processarOpcao(opcao, admin);
        }
    }

    // ===============================================================
    // PROCESSAMENTO DO MENU
    // ===============================================================
    private void processarOpcao(int opcao, Administrador admin) {

        MetodosAuxiliares.limparTela();

        switch (opcao) {
            case 1 -> cadastrarMedico(admin);
            case 2 -> listarMedicos(admin);
            case 3 -> removerMedico(admin);
            case 4 -> exibirMedico(admin);

            case 0 -> {
                System.out.println(ConsoleColors.BLUE + "Retornando ao menu principal... 💼" + ConsoleColors.RESET);
                return;
            }

            default -> System.out.println(ConsoleColors.RED + "❌ Opção inválida! Tente novamente." + ConsoleColors.RESET);
        }

        System.out.println(ConsoleColors.PURPLE + "\nPressione ENTER para continuar..." + ConsoleColors.RESET);
        scanner.nextLine();
        MetodosAuxiliares.limparTela();
    }

    // ===============================================================
    // CADASTRAR MÉDICO
    // ===============================================================
    private void cadastrarMedico(Administrador admin) {
        System.out.println(ConsoleColors.BLUE + ConsoleColors.BOLD +
            "╔═════════════════════════════╗\n" +
            "║       ➕ CADASTRAR MÉDICO    ║\n" +
            "╚═════════════════════════════╝\n" +
            ConsoleColors.RESET);

        Medico novo = FormularioMedico.cadastrarMedico(scanner);

        if (novo == null) {
            System.out.println(ConsoleColors.RED + "\n❌ Cadastro cancelado." + ConsoleColors.RESET);
            return;
        }

        boolean added = medicoDB.adicionarMedico(novo);
        if (added) {
            admin.getMedicos().add(novo);
            System.out.println(ConsoleColors.GREEN + "\n✔ Médico cadastrado com sucesso (DB em memória atualizado)!" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + "\n❌ Não foi possível cadastrar: CRM já existe no banco de dados." + ConsoleColors.RESET);
        }
    }

    // ===============================================================
    // LISTAR MÉDICOS
    // ===============================================================
    private void listarMedicos(Administrador admin) {

        System.out.println(ConsoleColors.BLUE + ConsoleColors.BOLD +
            "╔═════════════════════════╗\n" +
            "║       📋 LISTA DE MÉDICOS\n" +
            "╚═════════════════════════╝\n" +
            ConsoleColors.RESET);

        java.util.List<Medico> lista = medicoDB.listarTodos();

        admin.getMedicos().clear();
        admin.getMedicos().addAll(lista);

        if (lista.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW + "Nenhum médico cadastrado." + ConsoleColors.RESET);
            return;
        }

        System.out.println(ConsoleColors.CYAN + "\n--- Lista de Médicos (do banco em memória) ---" + ConsoleColors.RESET);
        for (Medico m : lista) {
            System.out.println(m);
        }
    }

    // ===============================================================
    // REMOVER MÉDICO
    // ===============================================================
    private void removerMedico(Administrador admin) {

        System.out.println(ConsoleColors.BLUE + ConsoleColors.BOLD +
            "╔══════════════════════════════╗\n" +
            "║       ❌ REMOVER MÉDICO       ║\n" +
            "╚══════════════════════════════╝\n" +
            ConsoleColors.RESET);

        System.out.print(ConsoleColors.YELLOW + "Digite o CRM do médico para remover: " + ConsoleColors.RESET);
        String crm = scanner.nextLine();

        boolean removed = medicoDB.removerPorCrm(crm);
        if (removed) {
            admin.removerMedico(crm);
            System.out.println(ConsoleColors.GREEN + "\n✔ Médico removido (do DB em memória e do administrador)." + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + "\n❌ Médico não encontrado no banco de dados." + ConsoleColors.RESET);
        }
    }

    // ===============================================================
    // EXIBIR INFO DO MÉDICO
    // ===============================================================
    private void exibirMedico(Administrador admin) {

        System.out.println(ConsoleColors.BLUE + ConsoleColors.BOLD +
            "╔══════════════════════════════╗\n" +
            "║  🔎 CONSULTAR DADOS DO MÉDICO  ║\n" +
            "╚══════════════════════════════╝\n" +
            ConsoleColors.RESET);

        System.out.print(ConsoleColors.YELLOW + "Informe o CRM: " + ConsoleColors.RESET);
        String crm = scanner.nextLine();

        Medico m = medicoDB.buscarPorCrm(crm);
        if (m != null) {
            if (!admin.getMedicos().contains(m)) {
                admin.getMedicos().add(m);
            }
            m.exibirInfo();
            return;
        }

        System.out.println(ConsoleColors.RED + "❌ Médico não encontrado no banco de dados!" + ConsoleColors.RESET);
    }

    // ===============================================================
    // UTILITÁRIOS
    // ===============================================================

    private int lerInteiro() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.print(ConsoleColors.RED + "Digite um número válido: " + ConsoleColors.RESET);
            }
        }
    }
}
