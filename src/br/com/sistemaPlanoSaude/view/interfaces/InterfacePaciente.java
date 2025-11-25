package br.com.sistemaPlanoSaude.view.interfaces;

import br.com.sistemaPlanoSaude.model.pessoas.Paciente;
import br.com.sistemaPlanoSaude.service.ConsultaService;
import br.com.sistemaPlanoSaude.service.PacienteService;
import br.com.sistemaPlanoSaude.util.ConsoleColors;
import br.com.sistemaPlanoSaude.util.MetodosAuxiliares;
import br.com.sistemaPlanoSaude.view.consulta.GeracaoConsultaPaciente;

import java.util.Scanner;

public class InterfacePaciente {

    private final Scanner scanner = new Scanner(System.in);
    private final PacienteService pacienteService = new PacienteService();

    private final ConsultaService consultaService;
    private GeracaoConsultaPaciente consultaView;

    public InterfacePaciente(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    // Entry: pede login e abre a area do paciente
    public void exibirAreaPaciente() {
        MetodosAuxiliares.limparTela();
        System.out.println(ConsoleColors.CYAN + "+============================================+" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN + "|             AREA DO PACIENTE               |" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN + "+============================================+" + ConsoleColors.RESET);

        System.out.print(ConsoleColors.YELLOW + "Numero da Carteirinha: " + ConsoleColors.RESET);
        String numeroCarteirinha = scanner.nextLine().trim();

        Paciente paciente = pacienteService.buscarPorCarteirinha(numeroCarteirinha);

        if (paciente == null) {
            System.out.println(ConsoleColors.RED + "Numero da carteirinha invalido. Acesso negado." + ConsoleColors.RESET);
            MetodosAuxiliares.pausarTela();
            return;
        }

        this.consultaView = new GeracaoConsultaPaciente(paciente, consultaService);
        exibirMenu(paciente);
    }

    public void exibirMenu(Paciente paciente) {
        this.consultaView = new GeracaoConsultaPaciente(paciente, consultaService);

        int opcao = -1;
        while (opcao != 0) {
            MetodosAuxiliares.limparTela();

            System.out.println(ConsoleColors.CYAN + "+==============================================+" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN + "|     PAINEL DO PACIENTE - " + paciente.getNome().toUpperCase() + "      |" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN + "+==============================================+" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN + "| 1 -> Consultas                               |" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN + "| 2 -> Meu plano de saude                      |" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN + "| 3 -> Perfil do paciente                      |" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN + "| 4 -> Alterar dados pessoais                  |" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN + "| 5 -> Suporte / Ajuda                         |" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.RED   + "| 0 -> Sair                                    |" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN + "+==============================================+" + ConsoleColors.RESET);

            System.out.print("\n" + ConsoleColors.YELLOW + "Digite sua opcao: " + ConsoleColors.RESET);
            opcao = lerInteiro();

            processarOpcao(opcao, paciente);
        }

        System.out.println(ConsoleColors.RED + "Saindo da area do paciente..." + ConsoleColors.RESET);
    }

    private void processarOpcao(int opcao, Paciente paciente) {
        MetodosAuxiliares.limparTela();

        switch (opcao) {
            case 1 -> abrirMenuConsultas();
            case 2 -> mostrarPlanoSaude(paciente);
            case 3 -> mostrarPerfilPaciente(paciente);
            case 4 -> alterarDadosPaciente(paciente);
            case 5 -> abrirSuporte();
            case 0 -> { return; }
            default -> System.out.println(ConsoleColors.RED + "Opcao invalida! Tente novamente." + ConsoleColors.RESET);
        }

        System.out.println(ConsoleColors.PURPLE + "\nPressione ENTER para voltar ao menu..." + ConsoleColors.RESET);
        scanner.nextLine();
    }

    // -------------------------- MODULOS DO MENU --------------------------

    private void abrirMenuConsultas() {
        if (consultaView == null) {
            System.out.println(ConsoleColors.RED + "Modulo de consultas nao inicializado." + ConsoleColors.RESET);
            return;
        }
        consultaView.iniciar();
    }

    private void mostrarPlanoSaude(Paciente paciente) {
        System.out.println(ConsoleColors.CYAN + "=== MEU PLANO DE SAUDE ===" + ConsoleColors.RESET);

        if (paciente.getPlanoSaude() == null) {
            System.out.println("Nenhum plano de saude registrado.");
            return;
        }

        System.out.println(paciente.getPlanoSaude());
        MetodosAuxiliares.pausarTela();
    }

    private void mostrarPerfilPaciente(Paciente paciente) {
        System.out.println(ConsoleColors.CYAN + "=== PERFIL DO PACIENTE ===" + ConsoleColors.RESET);
        mostrarDadosPaciente(paciente);
        MetodosAuxiliares.pausarTela();
    }

    private void abrirSuporte() {
        System.out.println(ConsoleColors.CYAN + "=== SUPORTE AO PACIENTE ===" + ConsoleColors.RESET);
        System.out.println("Em caso de problemas, entre em contato com o suporte do sistema.");
        System.out.println("E-mail: suporte@sistemasaude.com");
        System.out.println("Telefone: (00) 0000-0000");
        MetodosAuxiliares.pausarTela();
    }

    // ----------------------- ALTERACAO DE DADOS --------------------------

    private void alterarDadosPaciente(Paciente paciente) {
        System.out.println(ConsoleColors.CYAN + "+================================+" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN + "|       ALTERAR DADOS PESSOAIS   |" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN + "+================================+\n" + ConsoleColors.RESET);

        mostrarDadosPaciente(paciente);

        System.out.println("\nDigite os novos valores (deixe vazio para manter):");

        System.out.print("Nome [" + paciente.getNome() + "]: ");
        String nome = scanner.nextLine().trim();
        if (!nome.isEmpty()) paciente.setNome(nome);

        System.out.print("Telefone [" + paciente.getTelefone() + "]: ");
        String telefone = scanner.nextLine().trim();
        if (!telefone.isEmpty()) paciente.setTelefone(telefone);

        System.out.print("E-mail [" + paciente.getEmail() + "]: ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) paciente.setEmail(email);

        System.out.print("Altura [" + paciente.getAltura() + "]: ");
        String alturaInput = scanner.nextLine().trim();
        if (!alturaInput.isEmpty()) {
            try {
                double altura = Double.parseDouble(alturaInput);
                paciente.setAltura(altura);
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.RED + "Valor de altura invalido. Mantendo o valor anterior." + ConsoleColors.RESET);
            }
        }

        System.out.print("Peso [" + paciente.getPeso() + "]: ");
        String pesoInput = scanner.nextLine().trim();
        if (!pesoInput.isEmpty()) {
            try {
                double peso = Double.parseDouble(pesoInput);
                paciente.setPeso(peso);
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.RED + "Valor de peso invalido. Mantendo o valor anterior." + ConsoleColors.RESET);
            }
        }
    }

    // -------------------------- METODOS AUXILIARES --------------------------

    private void mostrarDadosPaciente(Paciente paciente) {
        System.out.println("+================================+");
        System.out.printf("| Nome: %-27s |\n", paciente.getNome());
        System.out.printf("| CPF: %-28s |\n", paciente.getCpf());
        System.out.printf("| Telefone: %-22s |\n", paciente.getTelefone());
        System.out.printf("| E-mail: %-24s |\n", paciente.getEmail());
        System.out.printf("| Altura: %-24.2f |\n", paciente.getAltura());
        System.out.printf("| Peso: %-26.2f |\n", paciente.getPeso());
        System.out.println("+================================+");
        MetodosAuxiliares.pausarTela();
    }

    private int lerInteiro() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.print(ConsoleColors.RED + "Digite um numero valido: " + ConsoleColors.RESET);
            }
        }
    }
}
