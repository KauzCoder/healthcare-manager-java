package br.com.sistemaPlanoSaude.view.formularios;

import br.com.sistemaPlanoSaude.model.funcionarios.Medico;
import br.com.sistemaPlanoSaude.service.AgendaService;
import br.com.sistemaPlanoSaude.database.FuncionariosDataBase;

import java.util.List;
import java.util.Scanner;

public class FormularioAgendaMedico {

    private Scanner scanner = new Scanner(System.in);
    private AgendaService agendaService = new AgendaService();

    public void menuAgenda() {

        while (true) {
            System.out.println("\n===== GERENCIAR AGENDA DO MÉDICO =====");
            System.out.println("1. Adicionar horário");
            System.out.println("2. Remover horário");
            System.out.println("3. Listar horários do médico");
            System.out.println("4. Voltar");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            switch (opcao) {
                case 1 -> adicionarHorario();
                case 2 -> removerHorario();
                case 3 -> listarHorarios();
                case 4 -> {
                    return; // volta ao menu anterior
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private Medico escolherMedico() {
        List<Medico> medicos = FuncionariosDataBase.getMedicos();

        if (medicos.isEmpty()) {
            System.out.println("Nenhum médico cadastrado!");
            return null;
        }

        System.out.println("\n---- Médicos cadastrados ----");
        for (int i = 0; i < medicos.size(); i++) {
            System.out.println((i + 1) + ". " + medicos.get(i).getNome());
        }

        System.out.print("Escolha um médico: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha < 1 || escolha > medicos.size()) {
            System.out.println("Escolha inválida!");
            return null;
        }

        return medicos.get(escolha - 1);
    }

    private void adicionarHorario() {
        Medico medico = escolherMedico();
        if (medico == null) return;

        System.out.print("Digite o horário (HH:mm): ");
        String horario = scanner.nextLine();

        boolean sucesso = agendaService.adicionarHorario(medico, horario);

        if (sucesso)
            System.out.println("Horário adicionado com sucesso!");
        else
            System.out.println("Erro: horário inválido ou já cadastrado!");
    }

    private void removerHorario() {
        Medico medico = escolherMedico();
        if (medico == null) return;

        System.out.println("Horários atuais:");
        medico.getHorariosDisponiveis().forEach(h -> System.out.println("- " + h));

        System.out.print("Digite o horário para remover: ");
        String horario = scanner.nextLine();

        boolean sucesso = agendaService.removerHorario(medico, horario);

        if (sucesso)
            System.out.println("Horário removido com sucesso!");
        else
            System.out.println("Horário não encontrado!");
    }

    private void listarHorarios() {
        Medico medico = escolherMedico();
        if (medico == null) return;

        System.out.println("\nHorários disponíveis do médico " + medico.getNome() + ":");
        medico.getHorariosDisponiveis().forEach(h -> System.out.println("- " + h));
    }
}
