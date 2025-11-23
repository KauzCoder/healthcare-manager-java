package br.com.sistemaPlanoSaude.view.interfaces;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import br.com.sistemaPlanoSaude.model.consulta.Consulta;
import br.com.sistemaPlanoSaude.model.consulta.Horario;
import br.com.sistemaPlanoSaude.model.enums.ConsultaStatus;
import br.com.sistemaPlanoSaude.model.funcionarios.Medico;
import br.com.sistemaPlanoSaude.model.pessoas.Paciente;

public class InterfaceConsulta {

    private final Scanner scanner = new Scanner(System.in);

    public Consulta exibirMenuConsulta(Paciente paciente, List<Medico> medicos) {

        limparTela();

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                              ║");
        System.out.println("║        🩺  AGENDAMENTO DE CONSULTA - HEALTH CARE  🩺        ║");
        System.out.println("║                                                              ║");
        System.out.println("║   Escolha um médico e marque seu horário de atendimento      ║");
        System.out.println("║                                                              ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();

        System.out.println("📌 **Escolha um médico:**");

        for (int i = 0; i < medicos.size(); i++) {
            System.out.println(" [ " + (i + 1) + " ] ➜ " + medicos.get(i).getNome());
        }

        System.out.println(" [ 0 ] ➜ Cancelar");
        System.out.print("\n👉 Digite sua opção: ");
        int opcao = scanner.nextInt();
        scanner.nextLine();

        if (opcao == 0) {
            System.out.println("\nAgendamento cancelado.");
            return null;
        }

        if (opcao < 1 || opcao > medicos.size()) {
            System.out.println("\n❌ Opção inválida!");
            return null;
        }

        Medico medico = medicos.get(opcao - 1);

        System.out.print("\n📅 Digite a data da consulta (AAAA-MM-DD): ");
        String dataStr = scanner.nextLine();

        System.out.print("⏰ Digite a hora da consulta (HH:MM): ");
        String horaStr = scanner.nextLine();

        System.out.print("📝 Descrição do atendimento: ");
        String descricao = scanner.nextLine();

        try {
            LocalDate dataLocal = LocalDate.parse(dataStr);
            LocalTime horaLocal = LocalTime.parse(horaStr);

            Date dataHora = Date.from(
                    dataLocal
                            .atTime(horaLocal)
                            .atZone(ZoneId.systemDefault())
                            .toInstant()
            );

            Horario horario = new Horario(dataHora, true);

            Consulta consulta = new Consulta(
                    paciente,
                    medico,
                    horario,
                    descricao,
                    null,
                    ConsultaStatus.AGENDADA
            );

            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.println("║      ✅ CONSULTA AGENDADA COM SUCESSO      ║");
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.println();
            System.out.println("👤 Paciente: " + paciente.getNome());
            System.out.println("🩺 Médico: " + medico.getNome());
            System.out.println("📅 Data: " + consulta.getData());
            System.out.println("⏰ Hora: " + consulta.getHora());
            System.out.println("📌 Status: " + consulta.getStatus());

            return consulta;

        } catch (Exception e) {
            System.out.println("\n❌ Erro ao agendar consulta: " + e.getMessage());
            return null;
        }
    }

    private void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
