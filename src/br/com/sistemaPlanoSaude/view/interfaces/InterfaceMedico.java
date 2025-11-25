package br.com.sistemaPlanoSaude.view.interfaces;

import br.com.sistemaPlanoSaude.model.enums.Especialidades;
import br.com.sistemaPlanoSaude.model.funcionarios.Medico;
import br.com.sistemaPlanoSaude.model.consulta.Horario;
import br.com.sistemaPlanoSaude.service.HorarioService;
import br.com.sistemaPlanoSaude.service.MedicoService;
import br.com.sistemaPlanoSaude.util.MetodosAuxiliares;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class InterfaceMedico {

    private final Scanner scanner = new Scanner(System.in);
    private Medico medicoLogado; // médico atualmente logado
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private final MedicoService medicoService;
    private final HorarioService horarioService;

    public InterfaceMedico(MedicoService medicoService, HorarioService horarioService) {
        this.medicoService = medicoService;
        this.horarioService = horarioService;
    }

    // =========================
    // Exibir menu principal
    // =========================
    public void exibirMenu() {
        MetodosAuxiliares.limparTela();

        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║        🩺 ÁREA DO MÉDICO 🩺           ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        System.out.println("📌 **Opções Disponíveis:**\n");
        System.out.println(" [1] ➜ Visualizar agenda de horários");
        System.out.println(" [2] ➜ Registrar atendimento");
        System.out.println(" [3] ➜ Editar dados do médico");
        System.out.println(" [0] ➜ Voltar ao menu principal\n");

        System.out.print("👉 Digite sua opção: ");
        String opcao = scanner.nextLine().trim();

        processarOpcao(opcao);
    }

    // =========================
    // Processar opção do menu
    // =========================
    private void processarOpcao(String opcao) {
        MetodosAuxiliares.limparTela();

        switch (opcao) {
            case "1":
                if (medicoLogado == null) {
                    System.out.println("❌ Nenhum médico logado!");
                    break;
                }

                exibirAgenda();

                break;

            case "2":
                System.out.println("📌 Registrar atendimento *Em desenvolvimento*");
                break;

            case "3":
                editarDadosMedico();
                break;

            case "0":
                System.out.println("Retornando ao menu principal...");
                return;

            default:
                System.out.println("❌ Opção inválida!");
        }

        System.out.println("\n👉 Pressione ENTER para continuar...");
        scanner.nextLine();
        exibirMenu();
    }

    // =========================
    // Exibir agenda de horários
    // =========================
    private void exibirAgenda() {
        List<Horario> horarios = horarioService.listarHorariosPorMedico(medicoLogado.getCrm());

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║       🗓 AGENDA DE HORÁRIOS         ║");
        System.out.println("╚══════════════════════════════════════╝\n");

        if (horarios.isEmpty()) {
            System.out.println("Nenhum horário cadastrado ainda.");
        } else {
            System.out.println("Horários cadastrados:");
            int count = 1;
            for (Horario h : horarios) {
                String status = h.isDisponibilidade() ? "Disponível" : "Ocupado";
                String dataFormatada = formatter.format(h.getData());
                System.out.println(count + ". " + dataFormatada + " - " + status);
                count++;
            }
        }

        System.out.print("\nDeseja adicionar um novo horário? (S/N): ");
        String respostaHorario = scanner.nextLine().trim().toUpperCase();
        if (respostaHorario.equals("S")) {
            try {
                System.out.print("Digite a data e hora do novo horário (yyyy-MM-dd HH:mm): ");
                Date novaData = formatter.parse(scanner.nextLine().trim());

                boolean sucesso = horarioService.criarHorario(novaData, true, medicoLogado.getCrm());
                if (sucesso) {
                    System.out.println("✅ Horário adicionado com sucesso: " + formatter.format(novaData));
                } else {
                    System.out.println("❌ Não foi possível adicionar o horário.");
                }

            } catch (ParseException e) {
                System.out.println("⚠ Data/hora inválida! Use o formato yyyy-MM-dd HH:mm");
            }
        }
    }

    // =========================
    // Editar dados do médico
    // =========================
    private void editarDadosMedico() {
        if (medicoLogado == null) {
            System.out.println("❌ Nenhum médico logado!");
            return;
        }

        // Mostra dados atuais
        medicoLogado.exibirInfo();

        System.out.print("Deseja alterar algum dado? (S/N): ");
        String resposta = scanner.nextLine().trim().toUpperCase();
        if (!resposta.equals("S")) {
            System.out.println("Operação cancelada.");
            return;
        }

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
                case "1":
                    System.out.print("Novo nome: ");
                    medicoService.atualizarNome(medicoLogado.getCrm(), scanner.nextLine());
                    break;
                case "2":
                    System.out.print("Novo endereço: ");
                    medicoService.atualizarEndereco(medicoLogado.getCrm(), scanner.nextLine());
                    break;
                case "3":
                    System.out.print("Novo telefone: ");
                    medicoService.atualizarTelefone(medicoLogado.getCrm(), scanner.nextLine());
                    break;
                case "4":
                    System.out.print("Novo email: ");
                    medicoService.atualizarEmail(medicoLogado.getCrm(), scanner.nextLine());
                    break;
                case "5":
                    System.out.println("Escolha especialidade:");
                    for (Especialidades especialidade : Especialidades.values()) {
                        System.out.println("- " + especialidade);
                    }
                    System.out.print("Especialidade: ");
                    medicoService.atualizarEspecialidade(
                            medicoLogado.getCrm(),
                            Especialidades.valueOf(scanner.nextLine().trim().toUpperCase())
                    );
                    break;
                case "6":
                    System.out.println("⚠ CRM não pode ser alterado!");
                    break;
                case "7":
                    System.out.print("Nova data de contratação (yyyy-MM-dd): ");
                    try {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        Date data = df.parse(scanner.nextLine().trim());
                        LocalDate localDate = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        medicoService.atualizarDataContratacao(medicoLogado.getCrm(), localDate);
                    } catch (ParseException e) {
                        System.out.println("⚠ Data inválida!");
                    }
                    break;
                case "8":
                    System.out.println("⚠ Salário só pode ser alterado por administrador!");
                    break;
                case "0":
                    editar = false;
                    System.out.println("✔ Alterações salvas com sucesso!");
                    break;
                default:
                    System.out.println("❌ Opção inválida!");
            }
        }
    }

    
    

    // =========================
    // Método para definir o médico logado
    // =========================
    public void setMedicoLogado(Medico medico) {
        this.medicoLogado = medico;
    }
}
