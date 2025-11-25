package br.com.sistemaPlanoSaude.view.menu;

import java.util.Scanner;

import br.com.sistemaPlanoSaude.view.interfaces.InterfaceAdministrador;
import br.com.sistemaPlanoSaude.view.interfaces.InterfaceInterresado;
import br.com.sistemaPlanoSaude.view.interfaces.InterfaceMedico;
import br.com.sistemaPlanoSaude.view.interfaces.InterfacePaciente;
import br.com.sistemaPlanoSaude.service.AdministradorService;
import br.com.sistemaPlanoSaude.service.MedicoService;
import br.com.sistemaPlanoSaude.service.PacienteService;
import br.com.sistemaPlanoSaude.util.MetodosAuxiliares;
import br.com.sistemaPlanoSaude.util.ConsoleColors;
import br.com.sistemaPlanoSaude.util.MedicoMockUtil;
import br.com.sistemaPlanoSaude.util.PacienteMockUtil;
import br.com.sistemaPlanoSaude.util.AdministradorMockUtil;


public class MenuPrincipal {
    private final Scanner scanner = new Scanner(System.in);

    private final  InterfaceAdministrador admInterface = new InterfaceAdministrador(null);
    private final InterfaceMedico medicoInterface = new InterfaceMedico(null, null, null);
    private final InterfacePaciente pacienteInterface = new InterfacePaciente(null);
    private final InterfaceInterresado interresadoInterface = new InterfaceInterresado();

    public void exibirMenuPrincipal() {
        while (true) {

            MetodosAuxiliares.limparTela();
            System.out.println(ConsoleColors.CYAN + "+============================================================================+" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN + "|" + ConsoleColors.RESET + ConsoleColors.BLUE_BOLD + "                 SISTEMA DE GESTAO DE SAUDE - MENU PRINCIPAL                 " + ConsoleColors.RESET + ConsoleColors.CYAN + "|" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN + "+============================================================================+" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN + "|" + ConsoleColors.RESET + "        Escolha uma das areas abaixo para continuar sua navegacao:        " + ConsoleColors.CYAN + "|" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.CYAN + "+============================================================================+" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN_BOLD + "     -> [1] Area da Administracao" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN_BOLD + "     -> [2] Area dos Medicos" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN_BOLD + "     -> [3] Area dos Pacientes" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN_BOLD + "     -> [4] Sou Interessado" + ConsoleColors.RESET);
            System.out.println(ConsoleColors.GREEN_BOLD + "     -> [0] Sair" + ConsoleColors.RESET);

            System.out.println(ConsoleColors.PURPLE + "----------------------------------------------------------------------------" + ConsoleColors.RESET);
            System.out.print(ConsoleColors.YELLOW_BOLD + "Digite sua opção: " + ConsoleColors.RESET);

            int opcao = lerInteiro();

            MetodosAuxiliares.limparTela();
            System.out.println(ConsoleColors.BLUE + "Carregando..." + ConsoleColors.RESET);
            MetodosAuxiliares.pausarTela();

            switch (opcao) {
                case 1 -> admInterface.exibirAreaAdministrador();
                case 2 -> medicoInterface.exibirAreaMedico();
                case 3 -> pacienteInterface.exibirAreaPaciente();
                case 4 -> interresadoInterface.exibirMenu();
                case 115 -> {
                    AdministradorMockUtil.criarAdministradorPadrao();
                    informarMockGerado("Administrador padrão");
                }
                case 116 -> {
                    MedicoMockUtil.criarCardiologistaPadrao();
                    informarMockGerado("Médico cardiologista");
                }
                case 117 -> {
                    PacienteMockUtil.criarPacienteBasico();
                    informarMockGerado("Paciente básico");
                }
                case 118 -> {
                    carregarMocksIniciais();
                    System.out.println(ConsoleColors.GREEN + "Dados de demonstracao carregados!" + ConsoleColors.RESET);
                    MetodosAuxiliares.pausarTela();
                }
                case 0 -> {
                    System.out.println(ConsoleColors.RED_BOLD + "Sistema encerrado com sucesso." + ConsoleColors.RESET);
                    return;
                }
                default -> {
                    System.out.println(ConsoleColors.RED_BOLD + "Opcao invalida! Tente novamente." + ConsoleColors.RESET);
                    MetodosAuxiliares.pausarTela();
                }
            }
        }
    }

    private int lerInteiro() {
        while (true) {
            try {
                String entrada = scanner.nextLine().trim();
                return Integer.parseInt(entrada);
            } catch (Exception e) {
                System.out.print("Digite um número válido: ");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(
"""
  ▄         ██  ██ ██████ ▄████▄ ██    ██████ ██  ██   ▄█████ ▄████▄ █████▄  ██████ 
▄▄█▄▄       ██████ ██▄▄   ██▄▄██ ██      ██   ██████   ██     ██▄▄██ ██▄▄██▄ ██▄▄   
  █         ██  ██ ██▄▄▄▄ ██  ██ ██████  ██   ██  ██   ▀█████ ██  ██ ██   ██ ██▄▄▄▄ 
"""
);

    int maxGrupos = 10;

        System.out.println("\nCarregando programa:");

    for (int i = 1; i <= maxGrupos; i++) {
        printBarra(i, maxGrupos);
            Thread.sleep(300);
        }

        new MenuPrincipal().exibirMenuPrincipal();
    }

    private static void carregarMocksIniciais() {
        PacienteService pacienteService = new PacienteService();
        AdministradorService administradorService = new AdministradorService();
        MedicoService medicoService = new MedicoService();

        PacienteMockUtil.registrarPacientesIniciais(pacienteService);

        try {
            AdministradorMockUtil.registrarAdministradorPadrao(administradorService);
        } catch (IllegalStateException ignored) {
            // Já existe um administrador mock no banco em memória.
        }

        MedicoMockUtil.registrarMedicosIniciais(medicoService);
    }

    private static void printBarra(int grupos, int total) {
        StringBuilder barra = new StringBuilder();
        int porcentagem = (grupos * 100) / total;

        for (int j = 0; j < grupos; j++) {
            barra.append(" ■ ");
        }

        System.out.print("\r" + barra + " " + porcentagem + "%");
    }

    private static void informarMockGerado(String descricao) {
        System.out.println(ConsoleColors.GREEN + "" + descricao + " gerado com sucesso!" + ConsoleColors.RESET);
        MetodosAuxiliares.pausarTela();
    }
}
