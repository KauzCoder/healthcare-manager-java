package br.com.sistemaPlanoSaude.view.formularios;

import br.com.sistemaPlanoSaude.model.enums.Sexo;
import br.com.sistemaPlanoSaude.model.enums.TipoSanguineo;
import br.com.sistemaPlanoSaude.model.pessoas.Paciente;
import br.com.sistemaPlanoSaude.model.planos.PlanoBasico;
import br.com.sistemaPlanoSaude.model.planos.PlanoPremium;
import br.com.sistemaPlanoSaude.model.planos.PlanoSaude;
import br.com.sistemaPlanoSaude.util.ValidacaoUtil;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class FormularioPaciente {

    public static Paciente cadastrarPaciente(Scanner scanner) {

        System.out.println("\n=== Cadastro de Paciente ===");

        // Nome
        String nome;
        while (true) {
            System.out.print("Nome completo: ");
            nome = scanner.nextLine();
            if (ValidacaoUtil.validarNome(nome)) { nome = nome.trim(); break; }
            System.out.println("Nome inválido. Informe um nome com pelo menos 10 caracteres e apenas letras.");
        }

        // CPF
        String cpf;
        while (true) {
            System.out.print("CPF: ");
            cpf = scanner.nextLine().trim();
            if (ValidacaoUtil.validarCPF(cpf)) break;
            System.out.println("CPF inválido. Informe um CPF válido (11 dígitos).");
        }

        // Idade
        int idade;
        while (true) {
            System.out.print("Idade: ");
            String idadeInput = scanner.nextLine().trim();
            try {
                idade = Integer.parseInt(idadeInput);
                if (ValidacaoUtil.validarIdade(idade)) break;
                System.out.println("Idade inválida. Informe um número inteiro entre 1 e 150.");
            } catch (NumberFormatException ex) {
                System.out.println("Entrada inválida. Informe a idade como um número inteiro (ex: 35).");
            }
        }

        // Endereço
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine().trim();

        // Telefone (validação e formatação via Pessoa)
        String telefone;
        while (true) {
            System.out.print("Telefone: ");
            String telefoneInput = scanner.nextLine().trim();
            String formatado =  ValidacaoUtil.validarEFormatarTelefone(telefoneInput);
            if (formatado != null) { 
				telefone = formatado; break; 
			}
            System.out.println("Telefone inválido. Informe apenas dígitos ou formato comum (ex: (11)99999-0000).");
        }

        // E-mail (opcional)
        System.out.print("E-mail (opcional): ");
        String email = scanner.nextLine().trim();
        if (email == null || email.isEmpty()) email = "não informado";
        else if (!ValidacaoUtil.validarEmail(email)) System.out.println("Aviso: formato de e-mail parece inválido, mas será registrado.");

        // Sexo

		// toUpperCase() garante que a entrada seja convertida para letras maiúsculas, permitindo que corresponda exatamente aos nomes do enum.

		// valueOf() tenta converter a string para um valor do enum Sexo. Se a entrada não corresponder a nenhum valor válido, uma exceção será lançada, e o código dentro do catch será executado.

		
        System.out.print("Sexo (MASCULINO/FEMININO): ");
        Sexo sexo;
        try {
            sexo = Sexo.valueOf(scanner.nextLine().trim().toUpperCase());
        } catch (Exception e) {
            sexo = Sexo.MASCULINO;
        }

        // Data de nascimento
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataDeNascimento;
        while (true) {
            System.out.print("Data de nascimento (dd/MM/yyyy): ");
            String dataStr = scanner.nextLine().trim();
            if (ValidacaoUtil.validarDataNascimento(dataStr)) {
                dataDeNascimento = LocalDate.parse(dataStr, fmt);
                break;
            }   
            System.out.println("Data inválida. Use dd/MM/yyyy e não informe uma data futura.");
        }

        // Seleção do plano (Básico / Premium)
        br.com.sistemaPlanoSaude.model.planos.PlanoSaude planoSelecionado = null;
        while (planoSelecionado == null) {
            System.out.println("Selecione o plano do paciente:");
            System.out.println("  [1] Básico");
            System.out.println("  [2] Premium");
            System.out.print("Escolha: ");
            String escolhaPlano = scanner.nextLine().trim();
            switch (escolhaPlano) {
                case "1" -> planoSelecionado = new PlanoBasico();
                case "2" -> planoSelecionado = new PlanoPremium();
                default -> System.out.println("Opção inválida. Digite 1 ou 2.");
            }
        }

        // Número da carteirinha e registro no plano selecionado
        String numeroCarteirinha;
        while (true) {
            System.out.print("Número da carteirinha: ");
            numeroCarteirinha = scanner.nextLine().trim();

            if (numeroCarteirinha.isEmpty()) {
                System.out.println("Número de carteirinha não pode ser vazio.");
                continue;
            }

            boolean jaRegistrada = ValidacaoUtil.validarCarteirinhaSaude(
                numeroCarteirinha,
                PlanoSaude.listarCarteirinhasBasico(),
                PlanoSaude.listarCarteirinhasPremium()
            );

            if (jaRegistrada) {
                System.out.println("Esta carteirinha já está associada a um plano. Informe outro código.");
                continue;
            }

            if (planoSelecionado.registrarCarteirinhaPaciente(numeroCarteirinha)) {
                break;
            }

            System.out.println("Não foi possível registrar a carteirinha. Tente novamente.");
        }


        // Tipo sanguíneo
        System.out.print("Tipo sanguíneo (A_POS/A_NEG/B_POS/B_NEG/AB_POS/AB_NEG/O_POS/O_NEG): ");
        TipoSanguineo tipoSanguineo;
        try {
            tipoSanguineo = TipoSanguineo.valueOf(scanner.nextLine().trim().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            tipoSanguineo = null;
			//vai tratar tanto entrada inválida quanto nula
        }

        // Peso
		// solicita até que o usuário insira um número positivo ou deixe vazio para pular
        double peso = 0.0;
        final double PESO_MIN = 0.5;
        final double PESO_MAX = 500.0;
        while (true) {
            System.out.print("Peso (kg) entre " + PESO_MIN + " e " + PESO_MAX + " (deixe vazio para pular): ");
            String scannerPeso = scanner.nextLine().trim();
            if (scannerPeso.isEmpty()) { peso = 0.0; break; }
            try {
                peso = Double.parseDouble(scannerPeso.replace(',', '.'));
                if (peso < PESO_MIN || peso > PESO_MAX) { System.out.println("Valor fora da faixa aceitável."); continue; }
                break;
            } catch (NumberFormatException ex) {
                System.out.println("Entrada inválida. Use um número (ex: 70.5) ou deixe vazio para pular.");
            }
        }

       	// Validação de altura: solicita até que o usuário insira um número dentro de faixa realista ou deixe vazio para pular
        double altura = 0.0;
        final double ALTURA_MIN = 0.4;
        final double ALTURA_MAX = 3.0;
        while (true) {
            System.out.print("Altura (m) entre " + ALTURA_MIN + " e " + ALTURA_MAX + " (deixe vazio para pular): ");
            String s = scanner.nextLine().trim();
            if (s.isEmpty()) { altura = 0.0; break; }
            try {
                altura = Double.parseDouble(s.replace(',', '.'));
                if (altura < ALTURA_MIN || altura > ALTURA_MAX) { System.out.println("Valor fora da faixa aceitável."); continue; }
                break;
            } catch (NumberFormatException ex) {
                System.out.println("Entrada inválida. Use um número (ex: 1.75) ou deixe vazio para pular.");
            }
        }

        Paciente novo = new Paciente(
            nome,
            cpf,
            idade,
            endereco,
            telefone,
            email,
            sexo,
            dataDeNascimento,
            numeroCarteirinha
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



    /**
     * Método de conveniência que cria um paciente exemplo (não interativo) vinculado a um PlanoBasico.
     */
    public static Paciente criarPacienteExemplo() {
        PlanoBasico plano = new PlanoBasico();
        plano.registrarCarteirinhaPaciente("CAR-0001");

        Paciente novoPaciente = new Paciente(
            "João Silva",
            "12345678901",
            35,
            "Rua das Flores, 100",
            "(11)99999-0000",
            "joao.silva@example.com",
            Sexo.MASCULINO,
            LocalDate.of(1990, 1, 1),
            "CAR-0001"
        );
        // Valores de exemplo para peso e altura
        novoPaciente.setPeso(70.0);
        novoPaciente.setAltura(1.75);
        return novoPaciente;
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

