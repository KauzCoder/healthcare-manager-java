package br.com.sistemaPlanoSaude.view.admin;

import br.com.sistemaPlanoSaude.database.PacienteDataBase;
import br.com.sistemaPlanoSaude.model.enums.NivelAcesso;
import br.com.sistemaPlanoSaude.model.funcionarios.Administrador;
import br.com.sistemaPlanoSaude.model.pessoas.Paciente;
import br.com.sistemaPlanoSaude.view.formularios.FormularioPaciente;
import java.util.Scanner;

public class AdminPacienteView {

    private final Scanner scanner = new Scanner(System.in);
    private final PacienteDataBase pacienteDB = new PacienteDataBase();

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
            System.out.println(" [ 6 ] ➜ Buscar Paciente por Carteirinha");
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
            boolean added = pacienteDB.adicionarPaciente(novo);
            if (added) {
                // Mantém o administrador atualizado com o paciente criado
                admin.criarPaciente(novo);
                System.out.println("\n✔ Paciente cadastrado com sucesso (DB em memória atualizado)!");
            } else {
                System.out.println("\n❌ Não foi possível cadastrar: carteirinha já existe no banco de dados.");
            }
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

        java.util.List<Paciente> lista = pacienteDB.listarTodos();

        // Sincroniza o administrador com os pacientes existentes no DB (não remove, apenas adiciona ausentes)
        for (Paciente p : lista) {
            boolean presente = admin.getPacientes().stream()
                    .anyMatch(ap -> ap.getNumeroCarteirinha().equals(p.getNumeroCarteirinha()));
            if (!presente) {
                admin.criarPaciente(p);
            }
        }

        if (lista.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
            return;
        }

        System.out.println("\n--- Lista de Pacientes (do banco em memória) ---");
        for (Paciente p : lista) {
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

        // Tenta bloquear no DB primeiro e também no administrador (se existir)
        boolean dbBlock = pacienteDB.buscarCarteirinha(codigo) != null;
        if (dbBlock) {
            // marca no objeto do DB
            pacienteDB.buscarCarteirinha(codigo).setStatus(br.com.sistemaPlanoSaude.model.enums.StatusPaciente.BLOQUEADO);
        }
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

        boolean dbFound = pacienteDB.buscarCarteirinha(codigo) != null;
        if (dbFound) {
            pacienteDB.buscarCarteirinha(codigo).setStatus(br.com.sistemaPlanoSaude.model.enums.StatusPaciente.ATIVO);
        }
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

        // altera tanto no administrador quanto no DB (se existir)
        Paciente p = pacienteDB.buscarCarteirinha(codigo);
        if (p != null) {
            p.setNivelAcesso(nivel);
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

        // Reset simulado no admin e no DB (se aplicável)
        Paciente p = pacienteDB.buscarCarteirinha(codigo);
        if (p != null) {
            // Não há setSenhaHash em Paciente, apenas informar que foi resetado no DB simuladamente
            System.out.println("Senha resetada (simulada) no DB para a carteirinha: " + codigo);
        }
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

        Paciente encontrado = pacienteDB.buscarCarteirinha(codigo);

        if (encontrado == null) {
            System.out.println("❌ Paciente não encontrado no banco de dados.");
            return;
        }

        // garante que o administrador possua referência ao paciente em memória
        boolean presente = admin.getPacientes().stream()
                .anyMatch(ap -> ap.getNumeroCarteirinha().equals(encontrado.getNumeroCarteirinha()));
        if (!presente) {
            admin.criarPaciente(encontrado);
        }

        System.out.println("\n📄 **Dados do Paciente:**\n");
        encontrado.exibirInfo();
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
