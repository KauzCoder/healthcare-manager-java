package br.com.sistemaPlanoSaude.view;

import br.com.sistemaPlanoSaude.model.Medico;
import java.util.Scanner;
import java.time.LocalDate;

public class FormularioMedico {

    private static final Scanner scanner = new Scanner(System.in);

    public static void cadastrarMedico() {
        System.out.println("\n=== Cadastro de Médico ===");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("Idade: ");
        int idade = Integer.parseInt(scanner.nextLine());

        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("E-mail: ");
        String email = scanner.nextLine();

        System.out.print("Especialidade: ");
        String especialidade = scanner.nextLine();

        System.out.print("CRM: ");
        String crm = scanner.nextLine();

        System.out.print("Data de Contratação (YYYY-MM-DD): ");
        String dataContratacaoStr = scanner.nextLine();
        LocalDate dataContratacao = LocalDate.parse(dataContratacaoStr);

        System.out.print("Salário: ");
        int salario = Integer.parseInt(scanner.nextLine());

        Medico novo = new Medico(
            nome,
            cpf,
            idade,
            endereco,
            telefone,
            email,
            especialidade,
            crm,
            dataContratacao,
            salario
        );

        System.out.println("\n✅ Médico cadastrado com sucesso!");
        System.out.println(novo);
    }
}