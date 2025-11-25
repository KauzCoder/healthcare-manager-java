package br.com.sistemaPlanoSaude.view.interfaces;


import br.com.sistemaPlanoSaude.model.funcionarios.Medico;
import br.com.sistemaPlanoSaude.model.enums.Especialidades;
import br.com.sistemaPlanoSaude.service.ConsultaService;
import br.com.sistemaPlanoSaude.service.HorarioService;
import br.com.sistemaPlanoSaude.service.MedicoService;
import br.com.sistemaPlanoSaude.util.ConsoleColors;
import br.com.sistemaPlanoSaude.util.MetodosAuxiliares;
import br.com.sistemaPlanoSaude.view.formularios.FormularioAgendaMedico;
import br.com.sistemaPlanoSaude.view.consulta.GeracaoConsultaMedico;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Scanner;

public class InterfaceMedico {

    private final Scanner scanner = new Scanner(System.in);
    private final MedicoService medicoService;
    private final HorarioService horarioService;
    private final ConsultaService consultaService;

    private Medico medicoLogado;
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private GeracaoConsultaMedico consultaView;

    public InterfaceMedico(MedicoService medicoService, HorarioService horarioService,
                                   ConsultaService consultaService) {
        this.medicoService = medicoService != null ? medicoService : new MedicoService();
        this.horarioService = horarioService != null ? horarioService : new HorarioService();
        this.consultaService = consultaService != null ? consultaService : new ConsultaService(this.medicoService, this.horarioService);
    }


// Entry: pede login e abre a area do paciente
    public void exibirAreaMedico() {
        MetodosAuxiliares.limparTela();
        System.out.println(ConsoleColors.CYAN + "+============================================+" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN + "|          AREA DE LOGIN DO MEDICO           |" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN + "+============================================+" + ConsoleColors.RESET);

        System.out.print(ConsoleColors.YELLOW + "CRM do Medico: " + ConsoleColors.RESET);
        String crmMedico = scanner.nextLine().trim();
        if (crmMedico.isEmpty()) {
            System.out.println(ConsoleColors.RED + "CRM nao pode ser vazio. Acesso negado." + ConsoleColors.RESET);
            MetodosAuxiliares.pausarTela();
            return;
        }

        Medico medico = medicoService.buscarPorCrm(crmMedico);

        if (medico == null) {
            System.out.println(ConsoleColors.RED + "CRM invalido. Acesso negado." + ConsoleColors.RESET);
            MetodosAuxiliares.pausarTela();
            return;
        }

        this.medicoLogado = medico;
        this.consultaView = new GeracaoConsultaMedico(medicoLogado, consultaService, horarioService);
        exibirMenu(medicoLogado);
    }

    // =========================
    // Menu principal do médico
    // =========================
    public void exibirMenu(Medico medico) {
        while (true) {
            MetodosAuxiliares.limparTela();
            System.out.println("+========================================+");
            System.out.println("|           AREA DO MEDICO               |");
            System.out.println("+========================================+\n");

            System.out.println("Opcoes disponiveis:");
            System.out.println("[1] Gerenciar agenda de horarios");
            System.out.println("[2] Gerenciar consultas");
            System.out.println("[3] Editar meus dados");
            System.out.println("[0] Sair");

            System.out.print("Digite sua opcao: ");
            String opcao = scanner.nextLine().trim();

            switch (opcao) {
                case "1" -> gerenciarAgenda();
                case "2" -> gerenciarConsultas();
                case "3" -> editarDadosMedico();
                case "0" -> {
                    System.out.println("Saindo do sistema...");
                    return;
                }
                default -> {
                    System.out.println("Opcao invalida!");
                    MetodosAuxiliares.pausarTela();
                }
            }
        }
    }

    // =========================
    // Gerenciar agenda
    // =========================
    private void gerenciarAgenda() {
        if (medicoLogado == null) {
            System.out.println("Nenhum medico logado!");
            MetodosAuxiliares.pausarTela();
            return;
        }
        FormularioAgendaMedico formulario = new FormularioAgendaMedico(medicoLogado);
        formulario.abrirMenu();
    }

    // =========================
    // Gerenciar consultas
    // =========================
    private void gerenciarConsultas() {
        if (medicoLogado == null) {
            System.out.println("Nenhum medico logado!");
            MetodosAuxiliares.pausarTela();
            return;
        }
        GeracaoConsultaMedico geracaoConsulta = new GeracaoConsultaMedico(medicoLogado, consultaService, horarioService);
        geracaoConsulta.iniciar();
    }

    // =========================
    // Editar dados do médico
    // =========================
    private void editarDadosMedico() {
        if (medicoLogado == null) {
            System.out.println("Nenhum medico logado!");
            MetodosAuxiliares.pausarTela();
            return;
        }

        medicoLogado.exibirInfo();

        System.out.print("Deseja alterar algum dado? (S/N): ");
        String resposta = scanner.nextLine().trim().toUpperCase();
        if (!resposta.equals("S")) return;

        boolean editar = true;
        while (editar) {
            System.out.println("\nCampos para editar:");
            System.out.println("[1] Nome");
            System.out.println("[2] Endereco");
            System.out.println("[3] Telefone");
            System.out.println("[4] Email");
            System.out.println("[5] Especialidade");
            System.out.println("[6] CRM");
            System.out.println("[7] Data de contratacao");
            System.out.println("[8] Salario");
            System.out.println("[0] Voltar");

            System.out.print("Escolha um campo: ");
            String campo = scanner.nextLine().trim();

            switch (campo) {
                case "1" -> {
                    System.out.print("Novo nome: ");
                    medicoService.atualizarNome(medicoLogado.getCrm(), scanner.nextLine());
                }
                case "2" -> {
                    System.out.print("Novo endereco: ");
                    medicoService.atualizarEndereco(medicoLogado.getCrm(), scanner.nextLine());
                }
                case "3" -> {
                    System.out.print("Novo telefone: ");
                    medicoService.atualizarTelefone(medicoLogado.getCrm(), scanner.nextLine());
                }
                case "4" -> {
                    System.out.print("Novo email: ");
                    medicoService.atualizarEmail(medicoLogado.getCrm(), scanner.nextLine());
                }
                case "5" -> {
                    System.out.println("Escolha especialidade:");
                    for (Especialidades e : Especialidades.values()) System.out.println("- " + e);
                    System.out.print("Especialidade: ");
                    medicoService.atualizarEspecialidade(
                            medicoLogado.getCrm(),
                            Especialidades.valueOf(scanner.nextLine().trim().toUpperCase())
                    );
                }
                case "6" -> System.out.println("CRM nao pode ser alterado!");
                case "7" -> {
                    System.out.print("Nova data de contratacao (yyyy-MM-dd): ");
                    try {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        Date data = df.parse(scanner.nextLine().trim());
                        LocalDate localDate = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        medicoService.atualizarDataContratacao(medicoLogado.getCrm(), localDate);
                    } catch (ParseException e) {
                        System.out.println("Data invalida!");
                    }
                }
                case "8" -> System.out.println("Salario so pode ser alterado por administrador!");
                case "0" -> {
                    editar = false;
                    System.out.println("Alteracoes salvas!");
                }
                default -> System.out.println("Opcao invalida!");
            }
        }
        MetodosAuxiliares.pausarTela();
    }

    // =========================
    // Definir médico logado
    // =========================
    public void setMedicoLogado(Medico medico) {
        this.medicoLogado = medico;
    }
}
