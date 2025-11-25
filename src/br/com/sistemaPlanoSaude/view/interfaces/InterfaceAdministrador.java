package br.com.sistemaPlanoSaude.view.interfaces;

import br.com.sistemaPlanoSaude.model.funcionarios.Administrador;
import br.com.sistemaPlanoSaude.service.AdministradorService;
import br.com.sistemaPlanoSaude.service.ConsultaService;
import br.com.sistemaPlanoSaude.service.HorarioService;
import br.com.sistemaPlanoSaude.service.MedicoService;
import br.com.sistemaPlanoSaude.util.ConsoleColors;
import br.com.sistemaPlanoSaude.util.MetodosAuxiliares;
import br.com.sistemaPlanoSaude.view.admin.AdminMedicoView;
import br.com.sistemaPlanoSaude.view.admin.AdminPacienteView;
import br.com.sistemaPlanoSaude.view.admin.AdminPlanoView;
import br.com.sistemaPlanoSaude.view.consulta.GeracaoConsultaAdministrador;

import java.util.Scanner;

public class InterfaceAdministrador {

    private final Scanner scanner = new Scanner(System.in);
    private final AdministradorService adminService = new AdministradorService();
    private final AdminPacienteView pacienteView = new AdminPacienteView();
    private final AdminPlanoView planoView = new AdminPlanoView();
    private final AdminMedicoView medicoView = new AdminMedicoView();

    private final ConsultaService consultaService; // já existente
    private GeracaoConsultaAdministrador consultaView;

    // Recebe o ConsultaService no construtor
    public InterfaceAdministrador(ConsultaService consultaService) {
        this.consultaService = consultaService != null ? consultaService : new ConsultaService(new MedicoService(), new HorarioService());
    }

    public void exibirAreaAdministrador() {
        MetodosAuxiliares.limparTela();
        System.out.println(ConsoleColors.CYAN + "+============================================+" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN + "|        AREA DE LOGIN DO ADMINISTRADOR      |" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN + "+============================================+" + ConsoleColors.RESET);

        System.out.print(ConsoleColors.YELLOW + "CPF do Administrador: " + ConsoleColors.RESET);
        String cpf = scanner.nextLine().trim();

        System.out.print(ConsoleColors.YELLOW + "Senha: " + ConsoleColors.RESET);
        String senha = scanner.nextLine().trim();

        Administrador admin = adminService.autenticarAdministrador(cpf, senha);

        if (admin == null) {
            System.out.println(ConsoleColors.RED + "CPF ou senha invalidos. Acesso negado." + ConsoleColors.RESET);
            MetodosAuxiliares.pausarTela();
            return;
        }

        this.consultaView = new GeracaoConsultaAdministrador(admin, consultaService);
        exibirMenuPrincipal(admin);
    }

    public void exibirMenuPrincipal(Administrador admin) {
        MetodosAuxiliares.limparTela();

        // Inicializa o módulo de consultas com o administrador logado
        consultaView = new GeracaoConsultaAdministrador(admin, consultaService);

        int opcao = -1;

        while (opcao != 0) {
            MetodosAuxiliares.limparTela();

            System.out.println(ConsoleColors.CYAN + "+====================================================+" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN + "|      " + ConsoleColors.BOLD + "PAINEL DO ADMINISTRADOR" + ConsoleColors.RESET + ConsoleColors.CYAN + "                     |" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN + "+====================================================+" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN + "| 1 -> Gerenciar Pacientes                          |" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN + "| 2 -> Gerenciar Planos de Saude                    |" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN + "| 3 -> Gerenciar Medicos                            |" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN + "| 4 -> Gerenciar Consultas                          |" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.BLUE + "| 5 -> Meus Dados Pessoais                          |" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.RED + "| 0 -> Sair                                         |" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN + "+====================================================+" + ConsoleColors.RESET);
            System.out.print("\n" + ConsoleColors.YELLOW + "Digite sua opcao: " + ConsoleColors.RESET);

            opcao = lerInteiro();

            processarOpcao(opcao, admin);
        }

        System.out.println(ConsoleColors.RED + "Saindo do sistema..." + ConsoleColors.RESET);
    }

    private void processarOpcao(int opcao, Administrador admin) {
        MetodosAuxiliares.limparTela();

        switch (opcao) {
            case 1 -> pacienteView.exibirMenu(admin);
            case 2 -> planoView.exibirMenu();
            case 3 -> medicoView.exibirMenu(admin);
            case 4 -> consultaView.iniciar();
            case 5 -> exibirPainelPessoal(admin);
            case 0 -> { return; }
            default -> System.out.println(ConsoleColors.RED + "Opcao invalida! Tente novamente." + ConsoleColors.RESET);
        }

        System.out.println(ConsoleColors.PURPLE + "\nPressione ENTER para voltar ao menu principal..." + ConsoleColors.RESET);
        scanner.nextLine();
    }

    // ===============================================================
    // PAINEL PESSOAL DO ADMINISTRADOR
    // ===============================================================
    private void exibirPainelPessoal(Administrador admin) {
        int opcao = -1;
        while (opcao != 0) {
            MetodosAuxiliares.limparTela();
            System.out.println(ConsoleColors.CYAN + "+====================================================+" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN + "|         PAINEL PESSOAL DO ADMINISTRADOR            |" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN + "+====================================================+" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN + "| 1 -> Ver meus dados pessoais                      |" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN + "| 2 -> Alterar meus dados pessoais                  |" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN + "| 3 -> Ver log de registro                          |" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.RED + "| 0 -> Voltar                                       |" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN + "+====================================================+" + ConsoleColors.RESET);
            System.out.print("\n" + ConsoleColors.YELLOW + "Digite sua opcao: " + ConsoleColors.RESET);
            opcao = lerInteiro();

            switch (opcao) {
                case 1 -> verDadosPessoais(admin);
                case 2 -> alterarDadosPessoais(admin);
                case 3 -> verLogRegistro(admin);
                case 0 -> { return; }
                default -> System.out.println(ConsoleColors.RED + "Opcao invalida!" + ConsoleColors.RESET);
            }

            if (opcao != 0) {
                System.out.println(ConsoleColors.PURPLE + "\nPressione ENTER para continuar..." + ConsoleColors.RESET);
                scanner.nextLine();
            }
        }
    }

    private void verDadosPessoais(Administrador admin) {
        MetodosAuxiliares.limparTela();
        System.out.println(ConsoleColors.CYAN + "+========================================+" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN + "|        MEUS DADOS PESSOAIS             |" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN + "+========================================+" + ConsoleColors.RESET);
        admin.exibirInfo();
    }

    private void alterarDadosPessoais(Administrador admin) {
        MetodosAuxiliares.limparTela();
        System.out.println(ConsoleColors.CYAN + "+========================================+" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN + "|      ALTERAR DADOS PESSOAIS            |" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN + "+========================================+\n" + ConsoleColors.RESET);

        System.out.println("Deixe em branco para manter o valor atual.\n");

        System.out.print("Nome [" + admin.getNome() + "]: ");
        String nome = scanner.nextLine().trim();
        if (!nome.isEmpty()) admin.setNome(nome);

        System.out.print("Telefone [" + admin.getTelefone() + "]: ");
        String telefone = scanner.nextLine().trim();
        if (!telefone.isEmpty()) admin.setTelefone(telefone);

        System.out.print("Email [" + admin.getEmail() + "]: ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) admin.setEmail(email);

        System.out.print("Endereco [" + admin.getEndereco() + "]: ");
        String endereco = scanner.nextLine().trim();
        if (!endereco.isEmpty()) admin.setEndereco(endereco);

        System.out.println(ConsoleColors.GREEN + "\nDados atualizados com sucesso!" + ConsoleColors.RESET);
    }

    private void verLogRegistro(Administrador admin) {
        MetodosAuxiliares.limparTela();
        System.out.println(ConsoleColors.CYAN + "+========================================+" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN + "|          LOG DE REGISTRO               |" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN + "+========================================+" + ConsoleColors.RESET);

        System.out.println("\nData de cadastro no sistema: " + admin.getDataCadastro());
        System.out.println("\nPacientes registrados: " + admin.getPacientes().size());
        System.out.println("Medicos registrados: " + admin.getMedicos().size());

        System.out.println("\n" + ConsoleColors.YELLOW + "--- Ultimas atividades ---" + ConsoleColors.RESET);
        System.out.println("(Log detalhado sera implementado em versao futura)");
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
