package br.com.sistemaPlanoSaude.view.formularios;

import br.com.sistemaPlanoSaude.model.Administrador;
import br.com.sistemaPlanoSaude.model.enums.NivelAcesso;
import br.com.sistemaPlanoSaude.model.enums.Sexo;
import br.com.sistemaPlanoSaude.util.ValidacaoUtil;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class FormularioAdministrador {

    private final Scanner scanner = new Scanner(System.in);

    public Administrador criarAdministrador() {

        System.out.println("======= FORMULÁRIO DE CADASTRO DO ADMINISTRADOR =======");

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
        System.out.print("Senha (hash ou senha normal): ");
        String senhaHash = scanner.nextLine();


        Administrador admin = new Administrador(
                nome, 
                cpf, 
                idade, 
                endereco, 
                telefone, 
                email,
                Sexo.MASCULINO,
                dataDeNascimento, 
                NivelAcesso.ADMINISTRADOR,
                senhaHash,
                LocalDate.now(),
                LocalDate.now()
        );

        System.out.println("\nAdministrador criado com sucesso!");
        System.out.println("Nome: " + admin.getNome());
        System.out.println("CPF: " + admin.getCpf());
        System.out.println("=========================================================");

        return admin;
    }

    private Sexo escolherSexo() {
        while (true) {
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1": return Sexo.MASCULINO;
                case "2": return Sexo.FEMININO;
                default:
                    System.out.print("Opção inválida! Escolha 1, 2 ou 3: ");
            }
        }
    }
}
