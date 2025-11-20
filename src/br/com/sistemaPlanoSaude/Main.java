package br.com.sistemaPlanoSaude;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.sistemaPlanoSaude.view.FormularioMedico;
import br.com.sistemaPlanoSaude.view.FormularioPaciente;
import br.com.sistemaPlanoSaude.model.Paciente;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		List<Paciente> pacientes = new ArrayList<>();

		System.out.println("Bem-vindo ao Sistema de Gerenciamento de Plano de Saúde");

		boolean running = true;
		while (running) {
			System.out.println("\n--- Menu Principal ---");
			System.out.println("1) Cadastrar paciente");
			System.out.println("2) Criar paciente exemplo");
			System.out.println("3) Listar pacientes");
			System.out.println("4) Cadastrar médico");
			System.out.println("5) Sair");
			System.out.print("Escolha uma opção: ");

			String opt = scanner.nextLine().trim();
			switch (opt) {
				case "1":
					Paciente p = FormularioPaciente.cadastrarPacienteComPlanoPadrao();
					pacientes.add(p);
					System.out.println("Paciente adicionado à lista.");
					break;
				case "2":
					Paciente exemplo = FormularioPaciente.criarPacienteExemplo();
					pacientes.add(exemplo);
					System.out.println("Paciente exemplo criado e adicionado.");
					break;
				case "3":
					if (pacientes.isEmpty()) {
						System.out.println("Nenhum paciente cadastrado.");
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
					running = false;
					break;
				default:
					System.out.println("Opção inválida. Tente novamente.");
			}
		}

		System.out.println("Encerrando aplicação. Até mais!");
		scanner.close();
	}
}

