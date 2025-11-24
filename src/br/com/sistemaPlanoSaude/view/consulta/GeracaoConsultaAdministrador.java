package br.com.sistemaPlanoSaude.view.consulta;

import br.com.sistemaPlanoSaude.model.consulta.Consulta;
import br.com.sistemaPlanoSaude.model.funcionarios.Administrador;
import br.com.sistemaPlanoSaude.service.ConsultaService;
import br.com.sistemaPlanoSaude.util.MetodosAuxiliares;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class GeracaoConsultaAdministrador {

    private final Administrador administrador;
    private final ConsultaService consultaService;
    private final Scanner scanner = new Scanner(System.in);

    private final DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

    public GeracaoConsultaAdministrador(Administrador administrador, ConsultaService consultaService) {
        this.administrador = administrador;
        this.consultaService = consultaService;
    }

    public void iniciar() {

        int opcao;

        while (true) {

            MetodosAuxiliares.limparTela();

            System.out.println("╔════════════════════════════════════════════════════════════╗");
            System.out.println("║                      🩺 CONSULTAS — ADMIN                  ║");
            System.out.println("╠════════════════════════════════════════════════════════════╣");
            System.out.println("║   Gerencie, visualize e consulte todas as consultas do     ║");
            System.out.println("║                 sistema HealthCare Plus.                    ║");
            System.out.println("╚════════════════════════════════════════════════════════════╝\n");

            System.out.println("╔══════════════════ MENU PRINCIPAL ══════════════════╗");
            System.out.println("║  [ 1 ] 🔎 Consultar consultas por CPF               ║");
            System.out.println("║  [ 2 ] 📋 Listar todas as consultas                 ║");
            System.out.println("║  [ 3 ] ❌ Cancelar consulta                         ║");
            System.out.println("║  [ 0 ] ↩ Voltar                                     ║");
            System.out.println("╚════════════════════════════════════════════════════╝");

            System.out.print("\n👉 Escolha uma opção: ");
            opcao = lerInteiro();

            processarOpcao(opcao);
        }
    }

    private void processarOpcao(int opcao) {

        MetodosAuxiliares.limparTela();

        switch (opcao) {

            case 1 -> {
                exibirCabecalho("🔎 CONSULTAR CONSULTAS POR CPF");
                consultarPorCPF();
                MetodosAuxiliares.pausarTela();
            }

            case 2 -> {
                exibirCabecalho("📋 TODAS AS CONSULTAS REGISTRADAS");
                listarTodasConsultas();
                MetodosAuxiliares.pausarTela();
            }

            case 3 -> {
                exibirCabecalho("❌ CANCELAR CONSULTA");
                cancelarConsulta();
                MetodosAuxiliares.pausarTela();
            }

            case 0 -> {
                System.out.println("\n👋 Saindo do módulo de consultas...");
                return;
            }

            default -> {
                System.out.println("❌ Opção inválida! Tente novamente.");
                MetodosAuxiliares.pausarTela();
            }
        }
    }

    // ============================================================
    //              CONSULTAR CONSULTAS POR CPF
    // ============================================================
    private void consultarPorCPF() {

        System.out.print("📄 Digite o CPF do paciente: ");
        String cpf = scanner.nextLine().trim();

        List<Consulta> consultas = consultaService.listarPorPaciente(cpf);

        if (consultas.isEmpty()) {
            System.out.println("\n❌ Nenhuma consulta encontrada para este CPF.");
            return;
        }

        exibirListaConsultas(consultas);
    }

    // ============================================================
    //              LISTAR TODAS CONSULTAS
    // ============================================================
    private void listarTodasConsultas() {

        List<Consulta> consultas = consultaService.listarTodas();

        if (consultas.isEmpty()) {
            System.out.println("❌ Nenhuma consulta cadastrada no sistema.");
            return;
        }

        exibirListaConsultas(consultas);
    }

    // ============================================================
    //              CANCELAR CONSULTA
    // ============================================================
    private void cancelarConsulta() {

        System.out.print("🆔 Digite o ID da consulta que deseja cancelar: ");
        int id = lerInteiro();

        boolean sucesso = consultaService.cancelarConsulta(id);

        if (sucesso) {
            System.out.println("\n✔ Consulta cancelada com sucesso!");
        } else {
            System.out.println("\n❌ Consulta não encontrada.");
        }
    }

    // ============================================================
    //              EXIBIR CONSULTAS
    // ============================================================
    private void exibirListaConsultas(List<Consulta> consultas) {

        for (Consulta consulta : consultas) {

            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.println("║                 📄 CONSULTA                ║");
            System.out.println("╠════════════════════════════════════════════╣");

            System.out.printf ("║  🆔 ID...............: %-20d ║\n", consulta.getIdConsulta());
            System.out.printf ("║  📅 Data.............: %-20s ║\n", consulta.getData().format(formatoData));
            System.out.printf ("║  ⏰ Horário..........: %-20s ║\n", consulta.getHorario().getData().toInstant()
                                                               .atZone(java.time.ZoneId.systemDefault())
                                                               .toLocalTime()
                                                               .format(formatoHora));
            System.out.printf ("║  📌 Status...........: %-20s ║\n", consulta.getStatus());
            System.out.println("╠════════════════════════ PACIENTE ════════════════════════╣");
            System.out.printf ("║  👤 Nome.............: %-20s ║\n", consulta.getPaciente().getNome());
            System.out.printf ("║  🪪 CPF..............: %-20s ║\n", consulta.getPaciente().getCpf());
            System.out.printf ("║  🪪 Plano............: %-20s ║\n", String.valueOf(consulta.getPaciente().getPlanoSaude()));
            System.out.println("╠════════════════════════ MÉDICO ═══════════════════════════╣");
            System.out.printf ("║  🩺 Nome.............: %-20s ║\n", consulta.getMedico().getNome());
            System.out.printf ("║  🏥 Especialidade....: %-20s ║\n", consulta.getMedico().getEspecialidade());
            System.out.println("╚════════════════════════════════════════════╝\n");
        }
    }

    // ============================================================
    //              FUNÇÕES AUXILIARES
    // ============================================================


    private void exibirCabecalho(String titulo) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   " + titulo);
        System.out.println("╚════════════════════════════════════════════╝\n");
    }

    private static int lerInteiro() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("❌ Digite um número válido: ");
            }
        }
    }

}
