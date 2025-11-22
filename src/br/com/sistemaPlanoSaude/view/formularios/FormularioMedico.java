package br.com.sistemaPlanoSaude.view.formularios;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.com.sistemaPlanoSaude.model.Medico;
import br.com.sistemaPlanoSaude.model.enums.NivelAcesso;
import br.com.sistemaPlanoSaude.model.enums.Sexo;
import br.com.sistemaPlanoSaude.model.enums.Especialidades;
import br.com.sistemaPlanoSaude.util.ValidacaoUtil;

public class FormularioMedico {

    public static Medico cadastrarMedico(Scanner scanner) {
        System.out.println("\n=== Cadastro de Médico ===");

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


        // CRM
        String crm;
        while (true) {
            System.out.print("CRM: ");
            crm = scanner.nextLine().trim();
            if (ValidacaoUtil.validarCRM(crm)) break;
            System.out.println("CRM inválido. Informe algo como 12345-PA.");
        }

        // Especialidade
        System.out.println("\n=== Especialidades Disponíveis ===");
        for (Especialidades esp : Especialidades.values()) {
            System.out.println("- " + esp);
        }

        Especialidades especialidade = null;
        while (especialidade == null) {
            System.out.print("Digite a especialidade: ");
            String entrada = scanner.nextLine().trim();
            especialidade = buscarEspecialidade(entrada);

            if (especialidade == null) {
                System.out.println("❌ Especialidade inválida. Tente novamente.\n");
            }
        }

        System.out.println("✔ Especialidade selecionada: " + especialidade);

        // Data de contratação
        LocalDate dataContratacao;
        while (true) {
            System.out.print("Data de contratação (dd/MM/yyyy): ");
            String inputData = scanner.nextLine().trim();
            try {
                dataContratacao = LocalDate.parse(inputData, fmt);
                break;
            } catch (Exception e) {
                System.out.println("Data inválida. Tente novamente.");
            }
        }

        // Salário
        double salario;
        while (true) {
            System.out.print("Salário: ");
            String input = scanner.nextLine().trim();
            try {
                salario = Double.parseDouble(input);
                break;
            } catch (Exception e) {
                System.out.println("Informe um valor numérico válido (ex: 15000.50).");
            }
        }

        // Criando o objeto Médico
        Medico medico = new Medico(
            nome,
            cpf,
            idade,
            endereco,
            telefone,
            email,
            sexo,
            dataDeNascimento,
            especialidade,
            crm,
            dataContratacao,
            (int) Math.round(salario),
            NivelAcesso.MEDICO
        );

        System.out.println("\nMédico cadastrado com sucesso!");
        System.out.println("Nome: " + medico.getNome());
        System.out.println("CRM: " + medico.getCrm());
        System.out.println("Especialidade: " + medico.getEspecialidade());
        System.out.println("=========================================");

        return medico;
    }

    public static Medico cadastrarMedico() {
        return cadastrarMedico(new Scanner(System.in));
    }

    // -------------------------------
    // MÉTODOS DE APOIO
    // -------------------------------

    private static Especialidades buscarEspecialidade(String entrada) {
        for (Especialidades esp : Especialidades.values()) {
            if (esp.name().equalsIgnoreCase(entrada)) {
                return esp;
            }
        }
        return null;
    }
}
