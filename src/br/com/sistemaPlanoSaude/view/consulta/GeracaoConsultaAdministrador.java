package br.com.sistemaPlanoSaude.view.consulta;

import br.com.sistemaPlanoSaude.model.consulta.Consulta;
import br.com.sistemaPlanoSaude.model.funcionarios.Administrador;
import br.com.sistemaPlanoSaude.model.funcionarios.Medico;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GeracaoConsultaAdministrador {

    private final Administrador administrador;
    private final Scanner sc = new Scanner(System.in);

    public GeracaoConsultaAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public void iniciar() {

        int opcao;

        while (true) {

            limparTela();

            System.out.println("╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                                                              ║");
            System.out.println("║          🩺  SISTEMA DE CONSULTAS - HEALTH CARE  🩺          ║");
            System.out.println("║                                                              ║");
            System.out.println("║      Aqui você gerencia, consulta e visualiza todas as       ║");
            System.out.println("║      consultas registradas pelos pacientes e médicos.         ║");
            System.out.println("║                                                              ║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.println();

            System.out.println("📌 **Opções Disponíveis:**");
            System.out.println();
            System.out.println(" [ 1 ] ➜ Consultar consultas por CPF");
            System.out.println(" [ 2 ] ➜ Listar todas as consultas");
            System.out.println(" [ 0 ] ➜ Sair");
            System.out.println();

            System.out.print("👉 Digite sua opção: ");

            opcao = lerInteiro();

            processarOpcao(opcao);
        }
    }

    private void processarOpcao(int opcao) {

        limparTela();

        switch (opcao) {

            case 1:
                exibirCabecalho("🔎 CONSULTAR CONSULTAS POR CPF");
                consultarPorCPF();
                pausar();
                break;

            case 2:
                exibirCabecalho("📋 TODAS AS CONSULTAS REGISTRADAS");
                listarTodasConsultas();
                pausar();
                break;

            case 0:
                System.out.println("\nObrigado por utilizar o sistema de consultas da Health Care! 💚");
                System.out.println("Desejamos um excelente dia de trabalho.\n");
                return;

            default:
                System.out.println("❌ Opção inválida! Tente novamente.");
                pausar();
        }
    }

    // ============================================================
    //                 CONSULTAR CONSULTA POR CPF
    // ============================================================
    private void consultarPorCPF() {

        System.out.print("Digite o CPF do paciente: ");
        String cpf = sc.nextLine().trim();

        List<Consulta> consultas = administrador.consultarConsultaPorCPF(cpf);

        if (consultas.isEmpty()) {
            System.out.println("\n❌ Nenhuma consulta encontrada para o CPF informado.");
            return;
        }

        System.out.println("\n✨ Consultas encontradas:");
        exibirListaConsultas(consultas);
    }

    // ============================================================
    //                 LISTAR TODAS CONSULTAS
    // ============================================================
    private void listarTodasConsultas() {

        List<Consulta> consultas;

        try {
            Field f = administrador.getClass().getDeclaredField("consultas");
            f.setAccessible(true);
            List<Consulta> raw = (List<Consulta>) f.get(administrador);
            consultas = raw == null ? new ArrayList<>() : new ArrayList<>(raw);

        } catch (Exception e) {
            System.out.println("❌ Erro ao acessar lista de consultas: " + e.getMessage());
            return;
        }

        if (consultas.isEmpty()) {
            System.out.println("❌ Nenhuma consulta cadastrada até o momento.");
            return;
        }

        exibirListaConsultas(consultas);
    }

    // ===============================================================
    // CANCELAR CONSULTA  
    // ===============================================================
    private void cancelarConsulta(Medico medico) {

        List<Consulta> consultas = medico.listarConsultas();

        System.out.println("\n========== CANCELAR CONSULTA ==========");

        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta disponível para cancelamento.");
            return;
        }

        System.out.print("Digite o ID da consulta para cancelar: ");
        int id = lerInteiro();

        // CHAMADA DO SEU MÉTODO REAL
        medico.cancelarConsulta(id);

        System.out.println("✔ Consulta cancelada com sucesso!");
    }



    // ============================================================
    //                 EXIBIÇÃO DETALHADA DAS CONSULTAS
    // ============================================================
    private void exibirListaConsultas(List<Consulta> consultas) {

        for (Consulta c : consultas) {

            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║           📄 DADOS DA CONSULTA       ║");
            System.out.println("╚══════════════════════════════════════╝");

            System.out.println("• 🆔 ID da Consulta: " + c.getIdConsulta());
            System.out.println("• 📅 Data: " + c.getData());
            System.out.println("• ⏰ Horário: " + c.getHorario());
            System.out.println("• 📌 Status: " + c.getStatus());

            System.out.println("\n👤 **Paciente**");
            System.out.println("   • Nome: " + c.getPaciente().getNome());
            System.out.println("   • CPF: " + c.getPaciente().getCpf());

            System.out.println("\n🩺 **Médico**");
            System.out.println("   • Nome: " + c.getMedico().getNome());
            System.out.println("   • Especialidade: " + c.getMedico().getEspecialidade());

            System.out.println("\n──────────────────────────────────────────────");
        }
    }

    // ============================================================
    //                 FUNÇÕES AUXILIARES
    // ============================================================
    private void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void exibirCabecalho(String titulo) {

        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║   " + titulo);
        System.out.println("╚════════════════════════════════════════════════════╝\n");
    }

    private int lerInteiro() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("❌ Valor inválido! Digite um número: ");
            }
        }
    }

    private void pausar() {
        System.out.print("\n👉 Pressione ENTER para continuar...");
        sc.nextLine();
    }
}
