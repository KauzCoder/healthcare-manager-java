package br.com.sistemaPlanoSaude.view.formularios;

import br.com.sistemaPlanoSaude.model.funcionarios.Medico;
import br.com.sistemaPlanoSaude.model.consulta.Horario;
import br.com.sistemaPlanoSaude.service.HorarioService;
import br.com.sistemaPlanoSaude.service.MedicoService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class FormularioAgendaMedico {

    private final Scanner scanner = new Scanner(System.in);
    private final MedicoService medicoService = new MedicoService();
    private final HorarioService horarioService = new HorarioService();

    private final SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

    // ===========================================================
    //                       MENU PRINCIPAL
    // ===========================================================
    public void abrirMenu() {

        while (true) {

            System.out.println("\n╔════════════════════════════════════╗");
            System.out.println("║          AGENDA DO MÉDICO          ║");
            System.out.println("╠════════════════════════════════════╣");
            System.out.println("║  1️⃣ Adicionar horário               ║");
            System.out.println("║  2️⃣ Remover horário                ║");
            System.out.println("║  3️⃣ Listar horários                ║");
            System.out.println("║  4️⃣ Voltar                         ║");
            System.out.println("╚════════════════════════════════════╝");
            System.out.print("\n👉 Escolha uma opção: ");

            String entrada = scanner.nextLine();
            int opcao;

            try {
                opcao = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("❌ Digite um número válido!");
                continue;
            }

            switch (opcao) {
                case 1 -> adicionarHorario();
                case 2 -> removerHorario();
                case 3 -> listarHorarios();
                case 4 -> { return; }
                default -> System.out.println("❌ Opção inválida!");
            }
        }
    }

    // ===========================================================
    //                       ESCOLHER MÉDICO
    // ===========================================================
    private Medico selecionarMedico() {

        List<Medico> medicos = medicoService.listarTodos();

        if (medicos == null || medicos.isEmpty()) {
            System.out.println("⚠ Nenhum médico cadastrado!");
            return null;
        }

        System.out.println("\n--- Médicos Cadastrados ---");
        for (int i = 0; i < medicos.size(); i++) {
            Medico m = medicos.get(i);
            System.out.println((i + 1) + ". " + m.getNome() + " (CRM: " + m.getCrm() + ")");
        }

        System.out.print("Escolha o número do médico: ");
        String entrada = scanner.nextLine();

        int indice;
        try {
            indice = Integer.parseInt(entrada);
        } catch (Exception e) {
            System.out.println("❌ Entrada inválida!");
            return null;
        }

        if (indice < 1 || indice > medicos.size()) {
            System.out.println("❌ Número fora da lista!");
            return null;
        }

        return medicos.get(indice - 1);
    }

    // ===========================================================
    //                     ADICIONAR HORÁRIO
    // ===========================================================
    private void adicionarHorario() {

        Medico medico = selecionarMedico();
        if (medico == null) return;

        System.out.print("Informe o horário (HH:mm): ");
        String horarioStr = scanner.nextLine().trim();

        Date horaFormatada;
        try {
            horaFormatada = formatoHora.parse(horarioStr);
        } catch (ParseException e) {
            System.out.println("❌ Formato de horário inválido!");
            return;
        }

        boolean ok = horarioService.criarHorario(horaFormatada, true, medico.getCrm());

        if (ok)
            System.out.println("✔ Horário adicionado!");
        else
            System.out.println("❌ Horário inválido ou já existente!");
    }

    // ===========================================================
    //                     REMOVER HORÁRIO
    // ===========================================================
    private void removerHorario() {

        Medico medico = selecionarMedico();
        if (medico == null) return;

        List<Horario> horarios = horarioService.listarHorariosPorMedico(medico.getCrm());

        if (horarios.isEmpty()) {
            System.out.println("⚠ Este médico não possui horários cadastrados!");
            return;
        }

        System.out.println("\n--- Horários do Médico ---");
        for (Horario h : horarios) {
            System.out.println("- " + formatoHora.format(h.getData()) + " (ID: " + h.getIdHorario() + ")");
        }

        System.out.print("Digite o ID do horário que deseja remover: ");
        String entrada = scanner.nextLine();

        int idHorario;
        try {
            idHorario = Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            System.out.println("❌ ID inválido!");
            return;
        }

        boolean ok = horarioService.removerHorario(medico.getCrm(), idHorario);

        if (ok)
            System.out.println("✔ Horário removido!");
        else
            System.out.println("❌ Horário não encontrado!");
    }

    // ===========================================================
    //                     LISTAR HORÁRIOS
    // ===========================================================
    private void listarHorarios() {

        Medico medico = selecionarMedico();
        if (medico == null) return;

        List<Horario> horarios = horarioService.listarHorariosPorMedico(medico.getCrm());

        System.out.println("\nHorários do médico " + medico.getNome() + ":");

        if (horarios.isEmpty()) {
            System.out.println("⚠ Nenhum horário cadastrado.");
            return;
        }

        for (Horario h : horarios) {
            System.out.println("- " + formatoHora.format(h.getData()) +
                               " | Disponível: " + h.isDisponibilidade());
        }
    }
}
