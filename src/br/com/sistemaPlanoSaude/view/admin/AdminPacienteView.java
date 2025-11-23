package br.com.sistemaPlanoSaude.view.admin;

import br.com.sistemaPlanoSaude.model.enums.NivelAcesso;
import br.com.sistemaPlanoSaude.model.pessoas.Paciente;
import br.com.sistemaPlanoSaude.model.funcionarios.Administrador;
import br.com.sistemaPlanoSaude.view.formularios.FormularioPaciente;

import java.util.Scanner;

public class AdminPacienteView {

    private final Scanner scanner = new Scanner(System.in);

    public void exibirMenu(Administrador admin) {

        limparTela();

        int opcao = -1;

        while (opcao != 0) {

            System.out.println("╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                                                              ║");
            System.out.println("║        🧑‍⚕️  PAINEL DO ADMINISTRADOR — PACIENTES   🧑‍⚕️       ║");
            System.out.println("║                                                              ║");
            System.out.println("║       Gerencie pacientes, permissões e status da conta        ║");
            System.out.println("║        com facilidade e total controle administrativo.         ║");
            System.out.println("║                                                              ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println();

            System.out.println("📌 **Opções Disponíveis:**\n");
            System.out.println(" [ 1 ] ➜ Cadastrar Paciente");
            System.out.println(" [ 2 ] ➜ Listar Pacientes");
            System.out.println(" [ 3 ] ➜ Bloquear Paciente");
            System.out.println(" [ 4 ] ➜ Desbloquear Paciente");
            System.out.println(" [ 5 ] ➜ Alterar Permissões");
            System.out.println(" [ 6 ] ➜ Resetar Senha");
            System.out.println(" [ 7 ] ➜ Buscar Paciente por Carteirinha");
            System.out.println(" [ 0 ] ➜ Voltar");
            System.out.println();

            System.out.print("👉 Digite sua opção: ");
            opcao = lerInteiro();

            processarOpcao(admin, opcao);
        }
    }

    // ===============================================================
    // PROCESSAR ESCOLHA DO MENU
    // ===============================================================
    private void processarOpcao(Administrador admin, int opcao) {

        limparTela();

        switch (opcao) {

            case 1 -> cadastrarPaciente(admin);

            case 2 -> listarPacientes(admin);

            case 3 -> bloquearPaciente(admin);

            case 4 -> desbloquearPaciente(admin);

            case 5 -> alterarPermissoesPaciente(admin);

            case 6 -> resetarSenha(admin);

            case 7 -> buscarPaciente(admin);

            case 0 -> {
                System.out.println("Retornando ao menu principal... 💼");
                return;
            }

            default -> System.out.println("❌ Opção inválida! Tente novamente.");
        }

        System.out.println("\nPressione ENTER para continuar...");
        scanner.nextLine();
        limparTela();
    }

    // ===============================================================
    // 1 — CADASTRAR PACIENTE
    // ===============================================================
    private void cadastrarPaciente(Administrador admin) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║        📝 CADASTRAR PACIENTE         ║");
        System.out.println("╚══════════════════════════════════════╝\n");

        Paciente novo = FormularioPaciente.cadastrarPaciente(scanner);

        if (novo != null) {
            admin.criarPaciente(novo);
            System.out.println("\n✔ Paciente cadastrado com sucesso!");
        } else {
            System.out.println("\n❌ Operação cancelada.");
        }
    }

    // ===============================================================
    // 2 — LISTAR PACIENTES
    // ===============================================================
    private void listarPacientes(Administrador admin) {

        System.out.println("╔═══════════════════════════════╗");
        System.out.println("║       📋 LISTA DE PACIENTES    ║");
        System.out.println("╚═══════════════════════════════╝\n");

        if (admin.getPacientes().isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
            return;
        }

        for (Paciente p : admin.getPacientes()) {
            System.out.println(p);
        }
    }

    // ===============================================================
    // 3 — BLOQUEAR PACIENTE
    // ===============================================================
    private void bloquearPaciente(Administrador admin) {

        System.out.println("╔══════════════════════════════╗");
        System.out.println("║       🔒 BLOQUEAR PACIENTE    ║");
        System.out.println("╚══════════════════════════════╝\n");

        System.out.print("Informe o número da carteirinha: ");
        String codigo = scanner.nextLine();

        admin.bloquearPaciente(codigo);
    }

    // ===============================================================
    // 4 — DESBLOQUEAR PACIENTE
    // ===============================================================
    private void desbloquearPaciente(Administrador admin) {

        System.out.println("╔════════════════════════════════╗");
        System.out.println("║      🔓 DESBLOQUEAR PACIENTE    ║");
        System.out.println("╚════════════════════════════════╝\n");

        System.out.print("Informe o número da carteirinha: ");
        String codigo = scanner.nextLine();

        admin.desbloquearPaciente(codigo);
    }

    // ===============================================================
    // 5 — ALTERAR PERMISSÕES
    // ===============================================================
    private void alterarPermissoesPaciente(Administrador admin) {

        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║      🛂  ALTERAR PERMISSÕES       ║");
        System.out.println("╚══════════════════════════════════╝\n");

        System.out.print("Número da carteirinha: ");
        String codigo = scanner.nextLine();

        System.out.println("\nEscolha o novo nível de acesso:");
        System.out.println("  [1] PACIENTE");
        System.out.println("  [2] ADMINISTRADOR");
        System.out.print("👉 Sua escolha: ");

        NivelAcesso nivel = null;

        while (nivel == null) {
            String escolha = scanner.nextLine().trim();
            switch (escolha) {
                case "1" -> nivel = NivelAcesso.PACIENTE;
                case "2" -> nivel = NivelAcesso.ADMINISTRADOR;
                default -> System.out.print("Opção inválida. Digite 1 ou 2: ");
            }
        }

        admin.alterarPermissoes(codigo, nivel);
    }

    // ===============================================================
    // 6 — RESETAR SENHA
    // ===============================================================
    private void resetarSenha(Administrador admin) {

        System.out.println("╔════════════════════════════════╗");
        System.out.println("║        🔁 RESETAR SENHA         ║");
        System.out.println("╚════════════════════════════════╝\n");

        System.out.print("Número da carteirinha: ");
        String codigo = scanner.nextLine();

        admin.resetarSenhaPaciente(codigo);
    }

    // ===============================================================
    // 7 — BUSCAR PACIENTE
    // ===============================================================
    private void buscarPaciente(Administrador admin) {

        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║     🔎 CONSULTAR PACIENTE POR CARTEIRINHA ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        System.out.print("Informe o número da carteirinha: ");
        String codigo = scanner.nextLine();

        Paciente encontrado = admin.getPacientes().stream()
                .filter(p -> p.getNumeroCarteirinha().equals(codigo))
                .findFirst()
                .orElse(null);

        if (encontrado == null) {
            System.out.println("❌ Paciente não encontrado.");
        } else {
            System.out.println("\n📄 **Dados do Paciente:**\n");
            encontrado.exibirInfo();
        }
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
