package br.com.sistemaPlanoSaude.view.admin;
import br.com.sistemaPlanoSaude.database.MedicoDataBase;
import br.com.sistemaPlanoSaude.model.funcionarios.Administrador;
import br.com.sistemaPlanoSaude.model.funcionarios.Medico;
import br.com.sistemaPlanoSaude.view.formularios.FormularioMedico;
import java.util.Scanner;

public class AdminMedicoView {

    private final Scanner scanner = new Scanner(System.in);
    private final MedicoDataBase medicoDB = new MedicoDataBase();

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

        if (novo == null) {
            System.out.println("\n❌ Cadastro cancelado.");
            return;
        }

        boolean added = medicoDB.adicionarMedico(novo);
        if (added) {
            // mantém lista do administrador sincronizada com o DB em memória
            admin.getMedicos().add(novo);
            System.out.println("\n✔ Médico cadastrado com sucesso (DB em memória atualizado)!");
        } else {
            System.out.println("\n❌ Não foi possível cadastrar: CRM já existe no banco de dados.");
        }
    }

    // ===============================================================
    // LISTAR MÉDICOS
    // ===============================================================
    private void listarMedicos(Administrador admin) {
        System.out.println("╔═════════════════════════╗");
        System.out.println("║       📋 LISTA DE MÉDICOS");
        System.out.println("╚═════════════════════════╝\n");

        java.util.List<Medico> lista = medicoDB.listarTodos();

        // Sincroniza a lista do administrador com o DB em memória (substitui conteúdo)
        admin.getMedicos().clear();
        admin.getMedicos().addAll(lista);

        if (lista.isEmpty()) {
            System.out.println("Nenhum médico cadastrado.");
            return;
        }

        System.out.println("\n--- Lista de Médicos (do banco em memória) ---");
        for (Medico m : lista) {
            System.out.println(m);
        }
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

        boolean removed = medicoDB.removerPorCrm(crm);
        if (removed) {
            // manter sincronizado com admin
            admin.removerMedico(crm);
            System.out.println("\n✔ Médico removido (do DB em memória e do administrador).");
        } else {
            System.out.println("\n❌ Médico não encontrado no banco de dados.");
        }
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

        Medico m = medicoDB.buscarPorCrm(crm);
        if (m != null) {
            // garante sincronização superficial
            if (!admin.getMedicos().contains(m)) {
                admin.getMedicos().add(m);
            }
            m.exibirInfo();
            return;
        }

        System.out.println("❌ Médico não encontrado no banco de dados!");
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
