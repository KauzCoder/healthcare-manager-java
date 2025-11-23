package br.com.sistemaPlanoSaude.view.interfaces;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Scanner;

import br.com.sistemaPlanoSaude.model.enums.Especialidades;
import br.com.sistemaPlanoSaude.model.funcionarios.Medico;
import br.com.sistemaPlanoSaude.model.consulta.Horario;

public class InterfaceMedico {

    private final Scanner scanner = new Scanner(System.in);
    private Medico medicoLogado; // médico atualmente logado
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    // =========================
    // Exibir menu principal
    // =========================
    public void exibirMenu() {
        limparTela();

        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║        🩺 ÁREA DO MÉDICO 🩺           ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();

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
        limparTela();

        switch (opcao) {
            case "1":
                if (medicoLogado == null) {
                    System.out.println("❌ Nenhum médico logado!");
                    break;
                }

                System.out.println("╔══════════════════════════════════════╗");
                System.out.println("║       🗓 AGENDA DE HORÁRIOS         ║");
                System.out.println("╚══════════════════════════════════════╝\n");

                if (medicoLogado.getHorarioAtendimento().isEmpty()) {
                    System.out.println("Nenhum horário cadastrado ainda.");
                } else {
                    System.out.println("Horários cadastrados:");
                    int count = 1;
                    for (Horario h : medicoLogado.getHorarioAtendimento()) {
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

                        // Cria novo Horario e adiciona
                        Horario novoHorario = new Horario(novaData, true);
                        medicoLogado.adicionarHorario(novoHorario);

                        System.out.println("✅ Horário adicionado com sucesso: " + formatter.format(novaData));

                    } catch (ParseException e) {
                        System.out.println("⚠ Data/hora inválida! Use o formato yyyy-MM-dd HH:mm");
                    }
                }
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
                    medicoLogado.setNome(scanner.nextLine());
                    break;
                case "2":
                    System.out.print("Novo endereço: ");
                    medicoLogado.setEndereco(scanner.nextLine());
                    break;
                case "3":
                    System.out.print("Novo telefone: ");
                    medicoLogado.setTelefone(scanner.nextLine());
                    break;
                case "4":
                    System.out.print("Novo email: ");
                    medicoLogado.setEmail(scanner.nextLine());
                    break;
                case "5":
                    System.out.println("Escolha especialidade:");
                    for (Especialidades e : Especialidades.values()) {
                        System.out.println("- " + e);
                    }
                    System.out.print("Especialidade: ");
                    medicoLogado.setEspecialidade(
                        Especialidades.valueOf(scanner.nextLine().trim().toUpperCase())
                    );
                    break;
                case "6":
                    System.out.print("Novo CRM: ");
                    medicoLogado.setCrm(scanner.nextLine());
                    break;
                case "7":
                    System.out.print("Nova data de contratação (yyyy-MM-dd): ");
                    try {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        Date data = df.parse(scanner.nextLine().trim());
                        LocalDate localDate = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        medicoLogado.setDataContratacao(localDate);
                    } catch (ParseException e) {
                        System.out.println("⚠ Data inválida!");
                    }
                    break;
                case "8":
                    System.out.print("Novo salário: ");
                    try {
                        medicoLogado.setSalario(Integer.parseInt(scanner.nextLine().trim()));
                    } catch (Exception e) {
                        System.out.println("⚠ Salário inválido!");
                    }
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
    // Limpar tela
    // =========================
    private void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // =========================
    // Método para definir o médico logado
    // =========================
    public void setMedicoLogado(Medico medico) {
        this.medicoLogado = medico;
    }
}
