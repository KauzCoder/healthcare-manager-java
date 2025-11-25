package br.com.sistemaPlanoSaude.view.consulta;

import br.com.sistemaPlanoSaude.model.consulta.Consulta;
import br.com.sistemaPlanoSaude.model.consulta.Horario;
import br.com.sistemaPlanoSaude.model.enums.ConsultaStatus;
import br.com.sistemaPlanoSaude.model.funcionarios.Medico;
import br.com.sistemaPlanoSaude.model.pessoas.Paciente;
import br.com.sistemaPlanoSaude.service.ConsultaService;
import br.com.sistemaPlanoSaude.service.HorarioService;
import br.com.sistemaPlanoSaude.service.PacienteService;
import br.com.sistemaPlanoSaude.util.ConsoleColors;
import br.com.sistemaPlanoSaude.util.MetodosAuxiliares;

import java.util.List;
import java.util.Scanner;

public class GeracaoConsultaMedico {

    private final Medico medico;
    private final ConsultaService consultaService;
    private final HorarioService horarioService;
    private final PacienteService pacienteService;
    private final Scanner scanner = new Scanner(System.in);

    public GeracaoConsultaMedico(Medico medico, ConsultaService consultaService, HorarioService horarioService) {
        this.medico = medico;
        this.consultaService = consultaService;
        this.horarioService = horarioService;
        this.pacienteService = new PacienteService();
    }

    public void iniciar() {
        while (true) {
            MetodosAuxiliares.limparTela();
            exibirCabecalho();
            exibirMenu();

            int opcao = lerInteiro();
            if (opcao == 0) break;
            processarOpcao(opcao);
        }
        System.out.println(ConsoleColors.GREEN + "\nSaindo do sistema. Ate mais!" + ConsoleColors.RESET);
    }
    //Toda a interface de interacao do medico com o sistema de consultas se encontra aqui



    private void exibirCabecalho() {
        System.out.println(ConsoleColors.BG_BLUE + ConsoleColors.BOLD + "+========================================+" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BG_BLUE + ConsoleColors.BOLD + "|        SISTEMA DE CONSULTAS            |" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BG_BLUE + ConsoleColors.BOLD + "+========================================+" + ConsoleColors.RESET);
        System.out.println("\n" + ConsoleColors.CYAN + "Bem-vindo(a), Dr(a). " + medico.getNome() + ConsoleColors.RESET);
    }
    private void exibirMenu() {
        System.out.println(ConsoleColors.YELLOW + "Escolha uma opcao:" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BOLD + "1" + ConsoleColors.RESET + " - Agendar consulta");
        System.out.println(ConsoleColors.BOLD + "2" + ConsoleColors.RESET + " - Ver minhas consultas");
        System.out.println(ConsoleColors.BOLD + "3" + ConsoleColors.RESET + " - Registrar receita (apenas consultas realizadas)");
        System.out.println(ConsoleColors.BOLD + "4" + ConsoleColors.RESET + " - Registrar anotacoes (apenas consultas realizadas)");
        System.out.println(ConsoleColors.BOLD + "5" + ConsoleColors.RESET + " - Marcar consulta como realizada");
        System.out.println(ConsoleColors.BOLD + "6" + ConsoleColors.RESET + " - Cancelar consulta");
        System.out.println(ConsoleColors.BOLD + "0" + ConsoleColors.RESET + " - Sair");
        System.out.print("\nOpcao: ");
    }
    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> agendarConsulta();
            case 2 -> exibirConsultas();
            case 3 -> registrarReceita();
            case 4 -> registrarAnotacoes();
            case 5 -> marcarComoRealizada();
            case 6 -> cancelarConsulta();
            case 0 -> {
                // Sair do sistema
                System.out.println(ConsoleColors.GREEN + "\nSaindo do sistema. Ate mais!" + ConsoleColors.RESET);
            }
            default -> {
                System.out.println(ConsoleColors.RED + "Opcao invalida!" + ConsoleColors.RESET);
                MetodosAuxiliares.pausarTela();
            }
        }
    }

    // ==========================
    //       VISUALIZACAO
    // ==========================
    private void exibirConsultas() {
        MetodosAuxiliares.limparTela();
        System.out.println(ConsoleColors.BG_GREEN + ConsoleColors.BOLD + "+========================================+" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BG_GREEN + ConsoleColors.BOLD + "|            MINHAS CONSULTAS            |" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BG_GREEN + ConsoleColors.BOLD + "+========================================+" + ConsoleColors.RESET);

        List<Consulta> consultas = consultaService.listarPorMedico(medico.getCrm());

        if (consultas.isEmpty()) {
            System.out.println(ConsoleColors.RED + "\nNenhuma consulta agendada." + ConsoleColors.RESET);
        } else {
            for (Consulta consulta : consultas) {
                System.out.println(ConsoleColors.BLUE + "--------------------------------------------" + ConsoleColors.RESET);
                System.out.printf("%sID:%s %d | %sPaciente:%s %s | %sStatus:%s %s%n",
                        ConsoleColors.BOLD, ConsoleColors.RESET, consulta.getIdConsulta(),
                        ConsoleColors.BOLD, ConsoleColors.RESET, consulta.getPaciente().getNome(),
                        ConsoleColors.BOLD, ConsoleColors.RESET, consulta.getStatus());
            }
            System.out.println(ConsoleColors.BLUE + "--------------------------------------------" + ConsoleColors.RESET);
        }
        MetodosAuxiliares.pausarTela();
    }



    private void agendarConsulta() {
    MetodosAuxiliares.limparTela();
    System.out.println(ConsoleColors.BG_PURPLE + ConsoleColors.BOLD + "+========================================+" + ConsoleColors.RESET);
    System.out.println(ConsoleColors.BG_PURPLE + ConsoleColors.BOLD + "|             AGENDAR CONSULTA           |" + ConsoleColors.RESET);
    System.out.println(ConsoleColors.BG_PURPLE + ConsoleColors.BOLD + "+========================================+" + ConsoleColors.RESET);

    // ==========================
    // Selecionar paciente
    // ==========================
    System.out.print(ConsoleColors.YELLOW + "Digite o CPF do paciente: " + ConsoleColors.RESET);
    String cpfPaciente = scanner.nextLine().trim();
    if (cpfPaciente.isEmpty()) {
        System.out.println(ConsoleColors.RED + "CPF invalido!" + ConsoleColors.RESET);
        MetodosAuxiliares.pausarTela();
        return;
    }

    // Buscar paciente no banco de dados pelo CPF
    Paciente paciente = pacienteService.buscarPorCpf(cpfPaciente);
    if (paciente == null) {
        System.out.println(ConsoleColors.RED + "Paciente nao encontrado no sistema!" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW + "Verifique se o CPF esta correto." + ConsoleColors.RESET);
        MetodosAuxiliares.pausarTela();
        return;
    }

    System.out.println(ConsoleColors.GREEN + "Paciente encontrado: " + paciente.getNome() + ConsoleColors.RESET);
    System.out.println(ConsoleColors.CYAN + "CPF: " + paciente.getCpf() + ConsoleColors.RESET);

    // ==========================
    // Listar horarios disponiveis
    // ==========================
    List<Horario> horarios = horarioService.listarHorariosPorMedico(medico.getCrm());
    horarios.removeIf(h -> !h.isDisponibilidade()); // somente horarios livres

    if (horarios.isEmpty()) {
        System.out.println(ConsoleColors.RED + "Nenhum horario disponivel para agendamento." + ConsoleColors.RESET);
        MetodosAuxiliares.pausarTela();
        return;
    }

    System.out.println(ConsoleColors.CYAN + "\nHorarios disponiveis:" + ConsoleColors.RESET);
    for (Horario h : horarios) {
        System.out.printf("ID: %d | Data/Hora: %s%n", h.getIdHorario(), h.getData());
    }

    System.out.print(ConsoleColors.YELLOW + "\nDigite o ID do horario escolhido: " + ConsoleColors.RESET);
    String idInput = scanner.nextLine().trim();
    
    int idHorario;
    try {
        idHorario = Integer.parseInt(idInput);
    } catch (NumberFormatException e) {
        System.out.println(ConsoleColors.RED + "ID invalido!" + ConsoleColors.RESET);
        MetodosAuxiliares.pausarTela();
        return;
    }

    Horario horarioEscolhido = null;
    for (Horario h : horarios) {
        if (h.getIdHorario() == idHorario) {
            horarioEscolhido = h;
            break;
        }
    }

    if (horarioEscolhido == null) {
        System.out.println(ConsoleColors.RED + "Horario invalido!" + ConsoleColors.RESET);
        MetodosAuxiliares.pausarTela();
        return;
    }

    // ==========================
    // Digitar descricao
    // ==========================
    System.out.print(ConsoleColors.YELLOW + "Digite a descricao da consulta: " + ConsoleColors.RESET);
    String descricao = scanner.nextLine().trim();

    // ==========================
    // Agendar a consulta
    // ==========================
    boolean sucesso = consultaService.agendarConsulta(paciente, medico.getCrm(), idHorario, descricao);
    if (sucesso) {
        System.out.println(ConsoleColors.GREEN + "Consulta agendada com sucesso!" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN + "Paciente: " + paciente.getNome() + ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN + "Horario: " + horarioEscolhido.getData() + ConsoleColors.RESET);
    } else {
        System.out.println(ConsoleColors.RED + "Falha ao agendar a consulta." + ConsoleColors.RESET);
    }

    MetodosAuxiliares.pausarTela();
}


    // ==========================
    //       OPERACOES
    // ==========================
    private void registrarReceita() {
        Consulta consulta = selecionarConsultaRealizada();
        if (consulta == null) return;

        System.out.print(ConsoleColors.YELLOW + "Digite a receita: " + ConsoleColors.RESET);
        String receita = scanner.nextLine().trim();
        if (!receita.isEmpty()) {
            consultaService.atualizarReceita(consulta.getIdConsulta(), receita);
            System.out.println(ConsoleColors.GREEN + "Receita registrada com sucesso!" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + "Nenhuma alteracao feita." + ConsoleColors.RESET);
        }
        MetodosAuxiliares.pausarTela();
    }

    private void registrarAnotacoes() {
        Consulta consulta = selecionarConsultaRealizada();
        if (consulta == null) return;

        System.out.print(ConsoleColors.YELLOW + "Digite as anotacoes: " + ConsoleColors.RESET);
        String anotacoes = scanner.nextLine().trim();
        if (!anotacoes.isEmpty()) {
            consultaService.atualizarAnotacoes(consulta.getIdConsulta(), anotacoes);
            System.out.println(ConsoleColors.GREEN + "Anotacoes registradas com sucesso!" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + "Nenhuma alteracao feita." + ConsoleColors.RESET);
        }
        MetodosAuxiliares.pausarTela();
    }

    private void marcarComoRealizada() {
        Consulta consulta = selecionarConsulta();
        if (consulta == null) return;

        boolean sucesso = consultaService.realizarConsulta(consulta.getIdConsulta());
        System.out.println(sucesso ? ConsoleColors.GREEN + "Consulta marcada como realizada!" + ConsoleColors.RESET
                        : ConsoleColors.RED + "Nao foi possivel marcar a consulta." + ConsoleColors.RESET);
        MetodosAuxiliares.pausarTela();
    }

    private void cancelarConsulta() {
        Consulta consulta = selecionarConsulta();
        if (consulta == null) return;

        boolean sucesso = consultaService.cancelarConsulta(consulta.getIdConsulta());
        System.out.println(sucesso ? ConsoleColors.GREEN + "Consulta cancelada com sucesso!" + ConsoleColors.RESET
                        : ConsoleColors.RED + "Nao foi possivel cancelar a consulta." + ConsoleColors.RESET);
        MetodosAuxiliares.pausarTela();
    }

    // ==========================
    //       AUXILIARES
    // ==========================
    private Consulta selecionarConsulta() {
        List<Consulta> consultas = consultaService.listarPorMedico(medico.getCrm());

        if (consultas.isEmpty()) {
            System.out.println(ConsoleColors.RED + "Nenhuma consulta disponivel." + ConsoleColors.RESET);
            MetodosAuxiliares.pausarTela();
            return null;
        }

        System.out.println("\nEscolha o ID da consulta:");
        for (Consulta consulta : consultas) {
            System.out.printf("%sID:%s %d | %sPaciente:%s %s | %sStatus:%s %s%n",
                    ConsoleColors.BOLD, ConsoleColors.RESET, consulta.getIdConsulta(),
                    ConsoleColors.BOLD, ConsoleColors.RESET, consulta.getPaciente().getNome(),
                    ConsoleColors.BOLD, ConsoleColors.RESET, consulta.getStatus());
        }

        int id = lerInteiro();
        for (Consulta consulta : consultas) {
            if (consulta.getIdConsulta() == id) return consulta;
        }

        System.out.println(ConsoleColors.RED + "Consulta nao encontrada." + ConsoleColors.RESET);
        MetodosAuxiliares.pausarTela();
        return null;
    }

    private Consulta selecionarConsultaRealizada() {
        Consulta consulta = selecionarConsulta();
        if (consulta != null && consulta.getStatus() != ConsultaStatus.REALIZADA) {
            System.out.println(ConsoleColors.RED + "So e possivel alterar consultas ja realizadas." + ConsoleColors.RESET);
            MetodosAuxiliares.pausarTela();
            return null;
        }
        return consulta;
    }

    private int lerInteiro() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print(ConsoleColors.RED + "Valor invalido! Digite um numero: " + ConsoleColors.RESET);
            }
        }
    }
}
