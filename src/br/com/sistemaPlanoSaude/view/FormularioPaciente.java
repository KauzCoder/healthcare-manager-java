package br.com.sistemaPlanoSaude.view;

import java.util.Scanner;

import br.com.sistemaPlanoSaude.model.Paciente;
import br.com.sistemaPlanoSaude.model.PlanoBasico;
import br.com.sistemaPlanoSaude.model.enums.Sexo;
import br.com.sistemaPlanoSaude.model.enums.TipoSanguineo;

public class FormularioPaciente {

	public static Paciente cadastrarPaciente(Scanner scanner) {
		System.out.println("\n=== Cadastro de Paciente ===");

		System.out.print("Nome: ");
		String nome = scanner.nextLine().trim();
		//Trim() para remover espaços em branco extras

		System.out.print("CPF: ");
		String cpf = scanner.nextLine().trim();

		System.out.print("Idade: ");
		int idade = -1;
		while (idade < 0) {
			String idadeStringFormulario = scanner.nextLine().trim();
			try {
				idade = Integer.parseInt(idadeStringFormulario);
				if (idade < 0) {
					System.out.println("Idade inválida. Informe um número inteiro não-negativo.");
				}

                
			} catch (NumberFormatException ex) {
				System.out.println("Entrada inválida. Informe a idade como um número inteiro (ex: 35).");
			}
		}

		System.out.print("Endereço: "); 
		String endereco = scanner.nextLine().trim();

		System.out.print("Telefone: ");
		String telefone = scanner.nextLine().trim();

		System.out.print("E-mail: ");
		String email = scanner.nextLine().trim();

		System.out.print("Sexo (MASCULINO/FEMININO): ");
		Sexo sexo;
		try {
			sexo = Sexo.valueOf(scanner.nextLine().trim().toUpperCase()); 
		} catch (Exception e) {
			sexo = Sexo.MASCULINO;
		}

		// toUpperCase() garante que a entrada seja convertida para letras maiúsculas, permitindo que corresponda exatamente aos nomes do enum.

		// valueOf() tenta converter a string para um valor do enum Sexo. Se a entrada não corresponder a nenhum valor válido, uma exceção será lançada, e o código dentro do catch será executado.


		System.out.print("Data de nascimento (dd/MM/yyyy): ");
		String dataDeNascimento = scanner.nextLine().trim();

		System.out.print("Número da carteirinha: ");
		String numeroCarteirinha = scanner.nextLine().trim();

		System.out.print("Plano de saúde (nome): ");
		String planoSaude = scanner.nextLine().trim();

		System.out.print("Tipo sanguíneo (A_POS/A_NEG/B_POS/B_NEG/AB_POS/AB_NEG/O_POS/O_NEG): ");
		TipoSanguineo tipoSanguineo;
		try {
			tipoSanguineo = TipoSanguineo.valueOf(scanner.nextLine().trim().toUpperCase()); 
		} catch (IllegalArgumentException | NullPointerException e) { //vai tratar tanto entrada inválida quanto nula
			tipoSanguineo = null; // opcional — pode permanecer nulo
		}

		// Validação de peso: solicita até que o usuário insira um número positivo ou deixe vazio para pular
		double peso = 0.0;
		final double PESO_MIN = 0.5; // kg
		final double PESO_MAX = 500.0; // kg
		while (true) {
			System.out.print("Peso (kg) entre " + PESO_MIN + " e " + PESO_MAX + " (deixe vazio para pular): ");
			String scannerPeso = scanner.nextLine().trim();
			if (scannerPeso.isEmpty()) { 
				peso = 0.0; break; 
			}
			try {
				peso = Double.parseDouble(scannerPeso.replace(',', '.'));
				if (peso < PESO_MIN || peso > PESO_MAX) {
					System.out.println("Valor fora da faixa aceitável. Informe um peso entre " + PESO_MIN + " e " + PESO_MAX + ".");
					continue;
				}
				break;
			} 
			catch (NumberFormatException ex) {
				System.out.println("Entrada inválida. Use um número (ex: 70.5) ou deixe vazio para pular.");
			}
		}

		// Validação de altura: solicita até que o usuário insira um número dentro de faixa realista ou deixe vazio para pular
		double altura = 0.0;
		final double ALTURA_MIN = 0.4; // m
		final double ALTURA_MAX = 3.0; // m
		while (true) {
			System.out.print("Altura (m) entre " + ALTURA_MIN + " e " + ALTURA_MAX + " (deixe vazio para pular): ");
			String s = scanner.nextLine().trim();
			if (s.isEmpty()) { altura = 0.0; break; }
			try {
				altura = Double.parseDouble(s.replace(',', '.'));
				if (altura < ALTURA_MIN || altura > ALTURA_MAX) {
					System.out.println("Valor fora da faixa aceitável. Informe uma altura entre " + ALTURA_MIN + " e " + ALTURA_MAX + ".");
					continue;
				}
				break;
			} catch (NumberFormatException ex) {
				System.out.println("Entrada inválida. Use um número (ex: 1.75) ou deixe vazio para pular.");
			}
		}




		// Nível de acesso de pacientes é padrão `PACIENTE` — não é solicitado aqui.
		Paciente novo = new Paciente(
			nome,
			cpf,
			idade,
			endereco,
			telefone,
			email,
			sexo,
			dataDeNascimento,
			numeroCarteirinha,
			planoSaude
		);

		// Atribui tipo sanguíneo se informado (o nível de acesso permanece padrão definido em Pessoa/Paciente)
		if (tipoSanguineo != null) {
			novo.setTipoSanguineo(tipoSanguineo);
		}

		// Atribui peso e altura se informados
		if (peso > 0) novo.setPeso(peso);
		if (altura > 0) novo.setAltura(altura);

		// Coleta condições de saúde (alergias, doenças crônicas, cirurgias, medicamentos)
		coletarAlergias(novo, scanner);
		coletarDoencasCronicas(novo, scanner);
		coletarHistoricoCirurgias(novo, scanner);
		coletarMedicamentosEmUso(novo, scanner);

		System.out.println("\n✅ Paciente cadastrado com sucesso: " + novo.getNome());
		return novo;
	}

	// Versão sem parâmetros para compatibilidade (usa System.in)
	public static Paciente cadastrarPaciente() {
		return cadastrarPaciente(new Scanner(System.in));
	}

	/**
	 * Cria um plano básico padrão e o retorna.
	 */
	public static PlanoBasico criarPlanoBasicoPadrao() {
		return new PlanoBasico();
	}

	/**
	 * Cadastra um paciente interativamente e vincula um plano básico padrão a ele.
	 */
	public static Paciente cadastrarPacienteComPlanoPadrao(Scanner scanner) {
		PlanoBasico plano = criarPlanoBasicoPadrao();
		System.out.println("\nSerá utilizado o plano padrão: " + plano.getNomePlano());
		Paciente p = cadastrarPaciente(scanner);
		p.vincularPlano(plano);
		return p;
	}

	/**
	 * Versão sem Scanner (compatibilidade) — cria um scanner temporário.
	 */
	public static Paciente cadastrarPacienteComPlanoPadrao() {
		return cadastrarPacienteComPlanoPadrao(new Scanner(System.in));
	}

	/**
	 * Método de conveniência que cria um paciente exemplo (não interativo) vinculado a um PlanoBasico.
	 */
	public static Paciente criarPacienteExemplo() {
		PlanoBasico plano = criarPlanoBasicoPadrao();
		Paciente p = new Paciente(
			"João Silva",
			"12345678901",
			35,
			"Rua das Flores, 100",
			"(11)99999-0000",
			"joao.silva@example.com",
			Sexo.MASCULINO,
			"01/01/1990",
			"CAR-0001",
			plano.getNomePlano()
		);
		p.vincularPlano(plano);
		// Valores de exemplo para peso e altura
		p.setPeso(70.0);
		p.setAltura(1.75);
		return p;
	}

	// ------------------ Coleta de listas (interativas) ------------------

	public static void coletarAlergias(Paciente paciente, Scanner scanner) {
		if (paciente == null || scanner == null) return; 
		//Essa condicional faz o processo ser interrompido se paciente ou scanner forem nulos, porem nao faz o programa travar e nem perder o scanner dado.
		System.out.println("\nAdicionar alergias (pressione Enter para terminar):");
		while (true) {
			System.out.print("Alergia: ");
			String s = scanner.nextLine();
			if (s == null) break;
			s = s.trim();
			if (s.isEmpty()) break;
			paciente.adicionarAlergia(s);
		}
	}

	public static void coletarDoencasCronicas(Paciente paciente, Scanner scanner) {
		if (paciente == null || scanner == null) return;
		System.out.println("\nAdicionar doenças crônicas (pressione Enter para terminar):");
		while (true) {
			System.out.print("Doença crônica: ");
			String s = scanner.nextLine();
			if (s == null) break;
			s = s.trim();
			if (s.isEmpty()) break;
			paciente.adicionarDoencaCronica(s);
		}
	}

	public static void coletarHistoricoCirurgias(Paciente paciente, Scanner scanner) {
		if (paciente == null || scanner == null) return;
		System.out.println("\nAdicionar histórico de cirurgias (pressione Enter para terminar):");
		while (true) {
			System.out.print("Cirurgia: ");
			String s = scanner.nextLine();
			if (s == null) break;
			s = s.trim();
			if (s.isEmpty()) break;
			paciente.adicionarHistoricoCirurgia(s);
		}
	}

	public static void coletarMedicamentosEmUso(Paciente paciente, Scanner scanner) {
		if (paciente == null || scanner == null) return;
		System.out.println("\nAdicionar medicamentos em uso (pressione Enter para terminar):");
		while (true) {
			System.out.print("Medicamento: ");
			String s = scanner.nextLine();
			if (s == null) break;
			s = s.trim();
			if (s.isEmpty()) break;
			paciente.adicionarMedicamento(s);
		}
	}
}

