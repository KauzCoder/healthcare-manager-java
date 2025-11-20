package br.com.sistemaPlanoSaude;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.sistemaPlanoSaude.view.FormularioMedico;
import br.com.sistemaPlanoSaude.view.FormularioPaciente;
import br.com.sistemaPlanoSaude.model.Paciente;

public class Main {

    // =============================================================
    //      Cores para melhorar a interface do sistema
    // =============================================================

	public static final String RESET = "\u001B[0m";
	public static final String BLACK = "\u001B[30m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String BLUE = "\u001B[34m";
	public static final String PURPLE = "\u001B[35m";
	public static final String CYAN = "\u001B[36m";
	public static final String WHITE = "\u001B[37m";

	public static void main(String[] args) throws InterruptedException {
		Scanner scanner = new Scanner(System.in);
		List<Paciente> pacientes = new ArrayList<>();

System.out.println(
"""
███╗░░░███╗███████╗██████╗░░█████╗░░█████╗░██████╗░███████╗  ░██████╗██╗░░░██╗░██████╗████████╗███████╗███╗░░░███╗
████╗░████║██╔════╝██╔══██╗██╔══██╗██╔══██╗██╔══██╗██╔════╝  ██╔════╝╚██╗░██╔╝██╔════╝╚══██╔══╝██╔════╝████╗░████║
██╔████╔██║█████╗░░██║░░██║██║░░╚═╝███████║██████╔╝█████╗░░  ╚█████╗░░╚████╔╝░╚█████╗░░░░██║░░░█████╗░░██╔████╔██║
██║╚██╔╝██║██╔══╝░░██║░░██║██║░░██╗██╔══██║██╔══██╗██╔══╝░░  ░╚═══██╗░░╚██╔╝░░░╚═══██╗░░░██║░░░██╔══╝░░██║╚██╔╝██║
██║░╚═╝░██║███████╗██████╔╝╚█████╔╝██║░░██║██║░░██║███████╗  ██████╔╝░░░██║░░░██████╔╝░░░██║░░░███████╗██║░╚═╝░██║
╚═╝░░░░░╚═╝╚══════╝╚═════╝░░╚════╝░╚═╝░░╚═╝╚═╝░░╚═╝╚══════╝  ╚═════╝░░░░╚═╝░░░╚═════╝░░░░╚═╝░░░╚══════╝╚═╝░░░░░╚═╝

                      ░█░█░▀█▀░█▀▄░█▀▄░█░█░█▀▀░█░█
                      ░█░█░░█░░█▀▄░█░█░█░█░█▀▀░░█░
                      ░▀▀▀░▀▀▀░▀░▀░▀▀░░▀▀▀░▀▀▀░░▀░
"""
);



		int maxGrupos = 10;

        System.out.println("\nCarregando programa:");

        for (int i = 1; i <= maxGrupos; i++) {
            printBarra(i);
            Thread.sleep(300);
        }
		System.out.println(GREEN + "\n Bem-vindo ao Sistema de Gerenciamento de Plano de Saúde" + RESET);


		boolean loopMenu = true;
		while (loopMenu) {
			System.out.println(YELLOW + "\n--- Menu Principal ---" + RESET);
			System.out.println(CYAN + "1) Cadastrar paciente" + RESET);
			System.out.println(CYAN + "2) Criar paciente exemplo" + RESET);
			System.out.println(CYAN + "3) Listar pacientes" + RESET);
			System.out.println(CYAN + "4) Cadastrar médico" + RESET);
			System.out.println(RED + "5) Sair" + RESET);

			System.out.print("\n Escolha uma opção: ");

			String opcaoNumero = scanner.nextLine().trim();
			switch (opcaoNumero) {
				case "1":
					Paciente p = FormularioPaciente.cadastrarPacienteComPlanoPadrao(scanner);
					pacientes.add(p);
					System.out.println(GREEN + "Paciente adicionado à lista." + RESET);
					break;

				case "2":
					Paciente exemplo = FormularioPaciente.criarPacienteExemplo();
					pacientes.add(exemplo);
					System.out.println(GREEN + "Paciente exemplo criado e adicionado." + RESET);
					break;

				case "3":
					if (pacientes.isEmpty()) {
						System.out.println(YELLOW + "Nenhum paciente cadastrado." + RESET);
					} else {
						System.out.println("\nLista de pacientes cadastrados:");
						for (int i = 0; i < pacientes.size(); i++) {
							System.out.println("\n--- Paciente " + (i + 1) + " ---");
							pacientes.get(i).exibirInfo();
						}
					}
					break;

				case "4":
					FormularioMedico.cadastrarMedico();
					break;

				case "5":
					loopMenu = false;
					break;

				default:
					System.out.println(RED + "Opção inválida. Tente novamente." + RESET);
			}
		}

		System.out.println("Encerrando aplicação. Até mais!");
		scanner.close();
	}

	 private static void printBarra(int grupos) {
        StringBuilder barra = new StringBuilder();
        for (int j = 0; j < grupos; j++) {
            barra.append(" ■  ■  ■ ");
        }
        System.out.print("\r" + barra.toString() + "    ");
    }
}

