package br.com.sistemaPlanoSaude.view;

import java.util.Scanner;

import br.com.sistemaPlanoSaude.model.Paciente;
import br.com.sistemaPlanoSaude.model.PlanoBasico;
import br.com.sistemaPlanoSaude.model.PlanoSaude;
import br.com.sistemaPlanoSaude.model.enums.Sexo;
import br.com.sistemaPlanoSaude.model.enums.TipoSanguineo;

public class FormularioPaciente {

	private static final Scanner scanner = new Scanner(System.in);

	public static Paciente cadastrarPaciente() {
		System.out.println("\n=== Cadastro de Paciente ===");

		System.out.print("Nome: ");
		String nome = scanner.nextLine().trim();

		System.out.print("CPF: ");
		String cpf = scanner.nextLine().trim();

		System.out.print("Idade: ");
		int idade = Integer.parseInt(scanner.nextLine().trim());

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
		} catch (IllegalArgumentException | NullPointerException e) {
			tipoSanguineo = null; // opcional — pode permanecer nulo
		}

		// Validação de peso: solicita até que o usuário insira um número positivo ou deixe vazio para pular
		double peso = 0.0;
		while (true) {
			System.out.print("Peso (kg) (deixe vazio para pular): ");
			String s = scanner.nextLine().trim();
			if (s.isEmpty()) { peso = 0.0; break; }
			try {
				peso = Double.parseDouble(s.replace(',', '.'));
				if (peso <= 0) {
					System.out.println("Insira um peso positivo (ex: 70.5) ou deixe vazio para pular.");
					continue;
				}
				break;
			} catch (NumberFormatException ex) {
				System.out.println("Entrada inválida. Use um número (ex: 70.5) ou deixe vazio para pular.");
			}
		}

		// Validação de altura: solicita até que o usuário insira um número positivo ou deixe vazio para pular
		double altura = 0.0;
		while (true) {
			System.out.print("Altura (m) (deixe vazio para pular): ");
			String s = scanner.nextLine().trim();
			if (s.isEmpty()) { altura = 0.0; break; }
			try {
				altura = Double.parseDouble(s.replace(',', '.'));
				if (altura <= 0) {
					System.out.println("Insira uma altura positiva (ex: 1.75) ou deixe vazio para pular.");
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

		System.out.println("\n✅ Paciente cadastrado com sucesso: " + novo.getNome());
		return novo;
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
	public static Paciente cadastrarPacienteComPlanoPadrao() {
		PlanoBasico plano = criarPlanoBasicoPadrao();
		System.out.println("\nSerá utilizado o plano padrão: " + plano.getNomePlano());
		Paciente p = cadastrarPaciente();
		p.vincularPlano(plano);
		return p;
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
}

