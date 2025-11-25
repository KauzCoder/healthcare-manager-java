package br.com.sistemaPlanoSaude.view.interfaces;


import br.com.sistemaPlanoSaude.model.funcionarios.Medico;
import br.com.sistemaPlanoSaude.model.enums.Especialidades;
import br.com.sistemaPlanoSaude.service.ConsultaService;
import br.com.sistemaPlanoSaude.service.HorarioService;
import br.com.sistemaPlanoSaude.service.MedicoService;
import br.com.sistemaPlanoSaude.util.MetodosAuxiliares;
import br.com.sistemaPlanoSaude.view.formularios.FormularioAgendaMedico;
import br.com.sistemaPlanoSaude.view.consulta.GeraçãoConsultaMedico;

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

    public InterfaceMedico(MedicoService medicoService, HorarioService horarioService,
                                   ConsultaService consultaService) {
        this.medicoService = medicoService;
        this.horarioService = horarioService;
        this.consultaService = consultaService;
    }

    // =========================
    // Menu principal do médico
    // =========================
    public void exibirMenu() {
        while (true) {
            MetodosAuxiliares.limparTela();
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("║           🩺 ÁREA DO MÉDICO 🩺         ║");
            System.out.println("╚════════════════════════════════════════╝\n");

            System.out.println("📌 Opções disponíveis:");
            System.out.println("[1] Gerenciar agenda de horários");
            System.out.println("[2] Gerenciar consultas");
            System.out.println("[3] Editar meus dados");
            System.out.println("[0] Sair");

            System.out.print("👉 Digite sua opção: ");
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
                    System.out.println("❌ Opção inválida!");
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
            System.out.println("❌ Nenhum médico logado!");
            MetodosAuxiliares.pausarTela();
            return;
        }
        FormularioAgendaMedico formulario = new FormularioAgendaMedico();
        formulario.abrirMenu();
    }

    // =========================
    // Gerenciar consultas
    // =========================
    private void gerenciarConsultas() {
        if (medicoLogado == null) {
            System.out.println("❌ Nenhum médico logado!");
            MetodosAuxiliares.pausarTela();
            return;
        }
        GeraçãoConsultaMedico geracaoConsulta = new GeraçãoConsultaMedico(medicoLogado, consultaService, horarioService);
        geracaoConsulta.iniciar();
    }

    // =========================
    // Editar dados do médico
    // =========================
    private void editarDadosMedico() {
        if (medicoLogado == null) {
            System.out.println("❌ Nenhum médico logado!");
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
            System.out.println("[2] Endereço");
            System.out.println("[3] Telefone");
            System.out.println("[4] Email");
            System.out.println("[5] Especialidade");
            System.out.println("[6] CRM");
            System.out.println("[7] Data de contratação");
            System.out.println("[8] Salário");
            System.out.println("[0] Voltar");

            System.out.print("Escolha um campo: ");
            String campo = scanner.nextLine().trim();

            switch (campo) {
                case "1" -> {
                    System.out.print("Novo nome: ");
                    medicoService.atualizarNome(medicoLogado.getCrm(), scanner.nextLine());
                }
                case "2" -> {
                    System.out.print("Novo endereço: ");
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
                case "6" -> System.out.println("⚠ CRM não pode ser alterado!");
                case "7" -> {
                    System.out.print("Nova data de contratação (yyyy-MM-dd): ");
                    try {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        Date data = df.parse(scanner.nextLine().trim());
                        LocalDate localDate = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        medicoService.atualizarDataContratacao(medicoLogado.getCrm(), localDate);
                    } catch (ParseException e) {
                        System.out.println("⚠ Data inválida!");
                    }
                }
                case "8" -> System.out.println("⚠ Salário só pode ser alterado por administrador!");
                case "0" -> {
                    editar = false;
                    System.out.println("✔ Alterações salvas!");
                }
                default -> System.out.println("❌ Opção inválida!");
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
