package br.com.sistemaPlanoSaude.view.interfaces;

import br.com.sistemaPlanoSaude.model.funcionarios.Administrador;
import br.com.sistemaPlanoSaude.service.AdministradorService;
import br.com.sistemaPlanoSaude.service.ConsultaService;
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
        this.consultaService = consultaService;
    }

    public void exibirMenuPrincipal(Administrador admin) {
        System.out.println("Digite suas credenciais para acessar o painel do Administrador.");
        System.out.print(ConsoleColors.YELLOW + "CPF: " + ConsoleColors.RESET); 
        String Cpf = scanner.nextLine();
        System.out.println(ConsoleColors.YELLOW + "Senha ou hash:" + ConsoleColors.RESET); 
        String senhaHash = scanner.nextLine();

        if (!adminService.autenticarAdministrador(Cpf, senhaHash)) {
            System.out.println(ConsoleColors.RED + "❌Autenticação falhou! Acesso negado." + ConsoleColors.RESET);
            return;
        }

        // Inicializa o módulo de consultas com o administrador logado
        consultaView = new GeracaoConsultaAdministrador(admin, consultaService);

        int opcao = -1;

        while (opcao != 0) {
            MetodosAuxiliares.limparTela();

            System.out.println(ConsoleColors.CYAN + "╔════════════════════════════════════════════════════╗" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN + "║      " + ConsoleColors.BOLD + "PAINEL DO ADMINISTRADOR" + ConsoleColors.RESET + ConsoleColors.CYAN + "                     ║" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN + "╠════════════════════════════════════════════════════╣" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN + "║ 1 ➜ Gerenciar Pacientes                           ║" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN + "║ 2 ➜ Gerenciar Planos de Saúde                      ║" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN + "║ 3 ➜ Gerenciar Médicos                              ║" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN + "║ 4 ➜ Gerenciar Consultas                             ║" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.RED + "║ 0 ➜ Sair                                           ║" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN + "╚════════════════════════════════════════════════════╝" + ConsoleColors.RESET);
            System.out.print("\n" + ConsoleColors.YELLOW + "Digite sua opção: " + ConsoleColors.RESET);

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
            case 0 -> { return; }
            default -> System.out.println(ConsoleColors.RED + "❌ Opção inválida! Tente novamente." + ConsoleColors.RESET);
        }

        System.out.println(ConsoleColors.PURPLE + "\nPressione ENTER para voltar ao menu principal..." + ConsoleColors.RESET);
        scanner.nextLine();
    }

    private int lerInteiro() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.print(ConsoleColors.RED + "Digite um número válido: " + ConsoleColors.RESET);
            }
        }
    }
}
