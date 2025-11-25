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
    private final Medico medicoLogado;

    private final SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

    // Construtor que recebe o médico logado
    public FormularioAgendaMedico(Medico medico) {
        this.medicoLogado = medico;
    }

    // ===========================================================
    //                       MENU PRINCIPAL
    // ===========================================================
    public void abrirMenu() {

        while (true) {

            System.out.println("\n+====================================+");
            System.out.println("|          AGENDA DO MEDICO          |");
            System.out.println("+====================================+");
            System.out.println("|  1 -> Adicionar horario            |");
            System.out.println("|  2 -> Remover horario              |");
            System.out.println("|  3 -> Listar horarios              |");
            System.out.println("|  4 -> Voltar                       |");
            System.out.println("+====================================+");
            System.out.print("\nEscolha uma opcao: ");

            String entrada = scanner.nextLine();
            int opcao;

            try {
                opcao = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Digite um numero valido!");
                continue;
            }

            switch (opcao) {
                case 1 -> adicionarHorario();
                case 2 -> removerHorario();
                case 3 -> listarHorarios();
                case 4 -> { return; }
                default -> System.out.println("Opcao invalida!");
            }
        }
    }

    // ===========================================================
    //                       ESCOLHER MEDICO
    // ===========================================================
    private Medico selecionarMedico() {

        List<Medico> medicos = medicoService.listarTodos();

        if (medicos == null || medicos.isEmpty()) {
            System.out.println("Nenhum medico cadastrado!");
            return null;
        }

        System.out.println("\n--- Medicos Cadastrados ---");
        for (int i = 0; i < medicos.size(); i++) {
            Medico m = medicos.get(i);
            System.out.println((i + 1) + ". " + m.getNome() + " (CRM: " + m.getCrm() + ")");
        }

        System.out.print("Escolha o numero do medico: ");
        String entrada = scanner.nextLine();

        int indice;
        try {
            indice = Integer.parseInt(entrada);
        } catch (Exception e) {
            System.out.println("Entrada invalida!");
            return null;
        }

        if (indice < 1 || indice > medicos.size()) {
            System.out.println("Numero fora da lista!");
            return null;
        }

        return medicos.get(indice - 1);
    }

    // ===========================================================
    //                     ADICIONAR HORARIO
    // ===========================================================
    private void adicionarHorario() {

        System.out.println("\n--- Adicionar Novo Horario ---");
        System.out.print("Informe a data (dd/MM/yyyy): ");
        String dataStr = scanner.nextLine().trim();
        
        System.out.print("Informe o horario (HH:mm): ");
        String horaStr = scanner.nextLine().trim();
        
        String dataHoraCompleta = dataStr + " " + horaStr;

        SimpleDateFormat formatoCompleto = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date dataHoraFormatada;
        
        try {
            dataHoraFormatada = formatoCompleto.parse(dataHoraCompleta);
        } catch (ParseException e) {
            System.out.println("Formato invalido! Use dd/MM/yyyy para data e HH:mm para hora.");
            System.out.println("Exemplo: 25/11/2025 14:30");
            return;
        }

        boolean ok = horarioService.criarHorario(dataHoraFormatada, true, medicoLogado.getCrm());

        if (ok) {
            System.out.println("Horario adicionado com sucesso!");
            System.out.println("Medico: " + medicoLogado.getNome());
            System.out.println("Data/Hora: " + formatoCompleto.format(dataHoraFormatada));
        } else {
            System.out.println("Erro: Horario invalido ou ja existente!");
        }
    }

    // ===========================================================
    //                     REMOVER HORARIO
    // ===========================================================
    private void removerHorario() {

        List<Horario> horarios = horarioService.listarHorariosPorMedico(medicoLogado.getCrm());

        if (horarios.isEmpty()) {
            System.out.println("Este medico nao possui horarios cadastrados!");
            return;
        }

        System.out.println("\n--- Horarios do Medico ---");
        SimpleDateFormat formatoCompleto = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        for (Horario h : horarios) {
            String status = h.isDisponibilidade() ? "DISPONIVEL" : "OCUPADO";
            System.out.printf("ID: %d | %s | %s%n", 
                h.getIdHorario(), 
                formatoCompleto.format(h.getData()), 
                status);
        }

        System.out.print("Digite o ID do horario que deseja remover: ");
        String entrada = scanner.nextLine();

        int idHorario;
        try {
            idHorario = Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            System.out.println("ID invalido!");
            return;
        }

        boolean ok = horarioService.removerHorario(medicoLogado.getCrm(), idHorario);

        if (ok)
            System.out.println("Horario removido!");
        else
            System.out.println("Horario nao encontrado!");
    }

    // ===========================================================
    //                     LISTAR HORARIOS
    // ===========================================================
    private void listarHorarios() {

        List<Horario> horarios = horarioService.listarHorariosPorMedico(medicoLogado.getCrm());

        System.out.println("\n--- Horarios do medico " + medicoLogado.getNome() + " ---");

        if (horarios.isEmpty()) {
            System.out.println("Nenhum horario cadastrado.");
            return;
        }

        SimpleDateFormat formatoCompleto = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        for (Horario h : horarios) {
            String disponivel = h.isDisponibilidade() ? "DISPONIVEL" : "OCUPADO";
            System.out.printf("ID: %d | %s | Status: %s%n", 
                h.getIdHorario(), 
                formatoCompleto.format(h.getData()), 
                disponivel);
        }
    }
}
