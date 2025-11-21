package br.com.sistemaPlanoSaude.view.formularios;

import br.com.sistemaPlanoSaude.model.Medico;
import br.com.sistemaPlanoSaude.model.enums.Sexo;
import br.com.sistemaPlanoSaude.model.enums.Especialidades;
import br.com.sistemaPlanoSaude.model.enums.NivelAcesso;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

        System.out.print("Sexo (MASCULINO/FEMININO): ");
        Sexo sexo;
        try {
            sexo = Sexo.valueOf(scanner.nextLine().trim().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            sexo = Sexo.MASCULINO;
        }

        System.out.print("Data de Nascimento (dd/MM/yyyy): ");
        String dataDeNascimentoStr = scanner.nextLine().trim();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataDeNascimento = LocalDate.parse(dataDeNascimentoStr, fmt);

        System.out.print("Especialidade (CARDIOLOGIA/PEDIATRIA/...): ");
        String especialidade = scanner.nextLine().trim();
        Especialidades especialidadeEnum;
        try {
            especialidadeEnum = Especialidades.valueOf(especialidade.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            especialidadeEnum = Especialidades.CARDIOLOGIA;
        }

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
            sexo,
            dataDeNascimento,
            especialidadeEnum,
            crm,
            dataContratacao,
            salario,
            NivelAcesso.MEDICO
        );

        System.out.println("\n✅ Médico cadastrado com sucesso!");
        System.out.println(novo);
    }
}