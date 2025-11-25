package br.com.sistemaPlanoSaude.view.consulta;

import br.com.sistemaPlanoSaude.model.consulta.Consulta;
import br.com.sistemaPlanoSaude.model.pessoas.Paciente;
import br.com.sistemaPlanoSaude.service.ConsultaService;
import br.com.sistemaPlanoSaude.util.MetodosAuxiliares;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class GeraçãoConsultaPaciente {

    private final Paciente paciente;
    private final ConsultaService consultaService;
    private final Scanner scanner;

    private final DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");

    public GeraçãoConsultaPaciente(Paciente paciente, ConsultaService consultaService) {
        this.paciente = paciente;
        this.consultaService = consultaService;
        this.scanner = new Scanner(System.in);
    }

    // ============================
    //           MENU
    // ============================
    public void iniciar() {

        while (true) {

            MetodosAuxiliares.limparTela();

            System.out.println("╔═══════════════════════════════════════════════════════╗");
            System.out.println("║                 🩺 CONSULTAS — PACIENTE               ║");
            System.out.println("║        Acompanhe, reagende e visualize suas consultas ║");
            System.out.println("╚═══════════════════════════════════════════════════════╝\n");

            System.out.println("╔══════════════════ MENU DO PACIENTE ══════════════════╗");
            System.out.println("║  [ 1 ] 📋 Minhas consultas                            ║");
            System.out.println("║  [ 2 ] 🔎 Detalhes de consulta                        ║");
            System.out.println("║  [ 3 ] ❌ Cancelar consulta                           ║");
            System.out.println("║  [ 4 ] 🔄 Solicitar reagendamento                     ║");
            System.out.println("║  [ 0 ] ↩ Voltar                                       ║");
            System.out.println("╚═══════════════════════════════════════════════════════╝");

            System.out.print("\n👉 Escolha uma opção: ");
            int opcao = lerInteiro();

            switch (opcao) {
                case 0 -> {
                    System.out.println("👋 Voltando...");
                    return;
                }
                case 1 -> listarConsultasMenu();
                case 2 -> visualizarDetalhes();
                case 3 -> cancelarConsulta();
                case 4 -> solicitarReagendamento();
                default -> {
                    System.out.println("❌ Opção inválida.");
                    MetodosAuxiliares.pausarTela();
                }
            }
        }
    }

    // ============================
    //        LISTAR CONSULTAS
    // ============================
    private void listarConsultasMenu() {
        exibirCabecalho("📋 MINHAS CONSULTAS");
        listarConsultas();
        MetodosAuxiliares.pausarTela();
    }

    private void listarConsultas() {

        List<Consulta> consultas = consultaService.listarPorPaciente(paciente.getCpf());

        if (consultas.isEmpty()) {
            System.out.println("❌ Você não possui consultas registradas.");
            return;
        }

        exibirListaConsultas(consultas);
    }

    // ============================
    //      DETALHES COMPLETOS
    // ============================
    private void visualizarDetalhes() {

        List<Consulta> consultas = consultaService.listarPorPaciente(paciente.getCpf());

        if (consultas.isEmpty()) {
            System.out.println("❌ Nenhuma consulta encontrada.");
            MetodosAuxiliares.pausarTela();
            return;
        }

        exibirListaConsultas(consultas);

        System.out.print("\n🆔 Digite o ID da consulta: ");
        int id = lerInteiro();

        Consulta consulta = consultaService.buscarPorId(id);

        if (consulta == null || !consulta.getPaciente().getCpf().equals(paciente.getCpf())) {
            System.out.println("❌ Consulta não encontrada.");
            MetodosAuxiliares.pausarTela();
            return;
        }

        exibirConsultaCompleta(consulta);
        MetodosAuxiliares.pausarTela();
    }

    // ============================
    //       CANCELAR CONSULTA
    // ============================
    private void cancelarConsulta() {

        listarConsultas();

        System.out.print("\n🆔 Digite o ID da consulta a cancelar: ");
        int id = lerInteiro();

        boolean sucesso = consultaService.cancelarConsulta(id);

        if (sucesso) {
            System.out.println("\n✔ Consulta cancelada com sucesso!");
        } else {
            System.out.println("\n❌ Consulta não encontrada.");
        }

        MetodosAuxiliares.pausarTela();
    }

    // ============================
    //    SOLICITAR REAGENDAMENTO
    // ============================
    private void solicitarReagendamento() {

        listarConsultas();

        System.out.print("\n🆔 Digite o ID da consulta que deseja reagendar: ");
        int id = lerInteiro();

        System.out.print("⏰ Digite o NOVO ID do horário desejado: ");
        int novoHorario = lerInteiro();

        boolean sucesso = consultaService.reagendarConsulta(id, novoHorario);

        if (sucesso) {
            System.out.println("\n✔ Solicitação de reagendamento realizada com sucesso!");
        } else {
            System.out.println("\n❌ Não foi possível reagendar. ID inválido ou horário indisponível.");
        }

        MetodosAuxiliares.pausarTela();
    }

    // ============================
    //   EXIBIR LISTA DE RESUMO
    // ============================
    private void exibirListaConsultas(List<Consulta> consultas) {

        for (Consulta consulta : consultas) {

            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.printf("║  🆔 ID...............: %-20d ║\n", consulta.getIdConsulta());
            System.out.printf("║  📅 Data.............: %-20s ║\n", consulta.getData().format(formatoData));
            System.out.printf("║  ⏰ Hora.............: %-20s ║\n", consulta.getHora().format(formatoHora));
            System.out.printf("║  📌 Status...........: %-20s ║\n", consulta.getStatus());
            System.out.println("╚════════════════════════════════════════════╝");
        }
    }

    // ============================
    //   EXIBIR DETALHES COMPLETOS
    // ============================
    private void exibirConsultaCompleta(Consulta consulta) {

        System.out.println("\n╔════════════════ CONSULTA COMPLETA ═════════════════╗");
        System.out.printf("║  🆔 ID...............: %-20d ║\n", consulta.getIdConsulta());
        System.out.printf("║  📅 Data.............: %-20s ║\n", consulta.getData().format(formatoData));
        System.out.printf("║  ⏰ Hora.............: %-20s ║\n", consulta.getHora().format(formatoHora));
        System.out.printf("║  📌 Status...........: %-20s ║\n", consulta.getStatus());
        System.out.println("╠══════════════════════ MÉDICO ══════════════════════╣");
        System.out.printf("║  🩺 Nome.............: %-20s ║\n", consulta.getMedico().getNome());
        System.out.printf("║  🏥 Especialidade....: %-20s ║\n", consulta.getMedico().getEspecialidade());
        System.out.println("╚════════════════════════════════════════════════════╝");
    }

    // ============================
    //         AUXILIARES
    // ============================
    private void exibirCabecalho(String titulo) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   " + titulo);
        System.out.println("╚════════════════════════════════════════════╝\n");
    }

    private int lerInteiro() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("❌ Digite um número válido: ");
            }
        }
    }
}
