package br.com.sistemaPlanoSaude.view;

import java.util.Scanner;
import br.com.sistemaPlanoSaude.model.Paciente;

public class FormularioPaciente {

    private static final Scanner scanner = new Scanner(System.in);

    public static Paciente cadastrarPaciente() {
        System.out.println("\n=== Cadastro de Paciente ===");

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

        System.out.print("Número da carteirinha: ");
        String carteirinha = scanner.nextLine();

        System.out.print("Plano de saúde: ");
        String planoSaude = scanner.nextLine();

        System.out.print("Possui dependentes? (true/false): ");
        boolean possuiDependentes = Boolean.parseBoolean(scanner.nextLine());

        Paciente novo = new Paciente(
            nome,
            cpf,
            idade,
            endereco,
            telefone,
            email,
            carteirinha,
            planoSaude,
            possuiDependentes
        );

        System.out.println("\n✅ Paciente cadastrado com sucesso!");
        System.out.println(novo);
        return novo;
    }
}