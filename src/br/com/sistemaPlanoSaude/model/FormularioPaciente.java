package br.com.sistemaPlanoSaude.model;
import java.util.Scanner;


public class FormularioPaciente  {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Cadastro de Paciente ===");

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Idade: ");
        int idade = sc.nextInt();
        sc.nextLine(); // limpar buffer

        System.out.print("CPF: ");
        String cpf = sc.nextLine();

        System.out.print("Telefone: ");
        String telefone = sc.nextLine();

        System.out.print("Endereço: ");
        String endereco = sc.nextLine();

        System.out.print("Possui plano de saúde? (sim/nao): ");
        String resposta = sc.nextLine().toLowerCase();

        String planoSaude;
        if (resposta.equals("sim")) {
            System.out.print("Informe o nome do plano: ");
            planoSaude = sc.nextLine();
        } else {
            planoSaude = "Nenhum";
        }

        // Criando o objeto paciente
        PacienteFormulario paciente  = new PacienteFormulario (nome, idade, cpf, telefone, endereco, planoSaude);

        System.out.println("\nCadastro concluído!");
        System.out.println(paciente);
    }
}
