package br.com.sistemaPlanoSaude.view.formularios;

import br.com.sistemaPlanoSaude.model.enums.NivelAcesso;
import br.com.sistemaPlanoSaude.model.enums.Sexo;
import br.com.sistemaPlanoSaude.model.funcionarios.Administrador;
import br.com.sistemaPlanoSaude.service.AdministradorService;
import br.com.sistemaPlanoSaude.util.ConsoleColors;
import br.com.sistemaPlanoSaude.util.ValidacaoUtil;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class FormularioAdministrador {

    private final AdministradorService adminService = new AdministradorService();
    private final Scanner scanner = new Scanner(System.in);

    public Administrador criarAdministrador() {

        System.out.println(ConsoleColors.CYAN + "+==============================================+" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN + "|          FORMULARIO DO ADMINISTRADOR         |" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN + "+==============================================+\n" + ConsoleColors.RESET);

        // Nome
        String nome;
        while (true) {
            System.out.print(ConsoleColors.YELLOW + "Nome completo: " + ConsoleColors.RESET);
            nome = scanner.nextLine();
            if (ValidacaoUtil.validarNome(nome)) break;
            System.out.println(ConsoleColors.RED + "Nome invalido! Digite pelo menos 10 caracteres e apenas letras." + ConsoleColors.RESET);
        }

        // CPF
        String cpf;
        while (true) {
            System.out.print(ConsoleColors.YELLOW + "CPF: " + ConsoleColors.RESET);
            cpf = scanner.nextLine();
            if (ValidacaoUtil.validarCPF(cpf)) break;
            System.out.println(ConsoleColors.RED + "CPF invalido! Deve conter 11 digitos." + ConsoleColors.RESET);
        }

        // Idade
        int idade;
        while (true) {
            System.out.print(ConsoleColors.YELLOW + "Idade: " + ConsoleColors.RESET);
            try {
                idade = Integer.parseInt(scanner.nextLine());
                if (ValidacaoUtil.validarIdade(idade)) break;
                System.out.println(ConsoleColors.RED + "Idade invalida! Digite entre 1 e 150." + ConsoleColors.RESET);
            } catch (Exception e) {
                System.out.println(ConsoleColors.RED + "Digite um numero valido!" + ConsoleColors.RESET);
            }
        }

        // Endereco
        System.out.print(ConsoleColors.YELLOW + "Endereco: " + ConsoleColors.RESET);
        String endereco = scanner.nextLine();

        // Telefone
        String telefone;
        while (true) {
            System.out.print(ConsoleColors.YELLOW + "Telefone: " + ConsoleColors.RESET);
            String entrada = scanner.nextLine();
            String fmt = ValidacaoUtil.validarEFormatarTelefone(entrada);
            if (fmt != null) { telefone = fmt; break; }
            System.out.println(ConsoleColors.RED + "Telefone invalido!" + ConsoleColors.RESET);
        }

        // E-mail
        System.out.print(ConsoleColors.YELLOW + "E-mail (opcional): " + ConsoleColors.RESET);
        String email = scanner.nextLine().trim();
        if (email.isEmpty()) email = "nao informado";
        else if (!ValidacaoUtil.validarEmail(email))
            System.out.println(ConsoleColors.RED + "E-mail parece invalido, mas sera registrado." + ConsoleColors.RESET);

        // Sexo
        Sexo sexo;
        try {
            System.out.print(ConsoleColors.YELLOW + "Sexo (MASCULINO/FEMININO): " + ConsoleColors.RESET);
            sexo = Sexo.valueOf(scanner.nextLine().trim().toUpperCase());
        } catch (Exception e) {
            sexo = Sexo.MASCULINO;
        }

        // Data de nascimento
        LocalDate dataNasc;
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            System.out.print(ConsoleColors.YELLOW + "Data de nascimento (dd/MM/yyyy): " + ConsoleColors.RESET);
            String texto = scanner.nextLine();
            if (ValidacaoUtil.validarDataNascimento(texto)) {
                dataNasc = LocalDate.parse(texto, fmt);
                break;
            }
            System.out.println(ConsoleColors.RED + "Data invalida!" + ConsoleColors.RESET);
        }

        // Senha
        System.out.print(ConsoleColors.YELLOW + "Senha ou hash: " + ConsoleColors.RESET);
        String senhaHash = scanner.nextLine();

        adminService.criarAdministrador(new Administrador(
            nome, cpf, idade, endereco, telefone, email,
            sexo, dataNasc, NivelAcesso.ADMINISTRADOR, senhaHash, null, null
        )); 

        System.out.println(ConsoleColors.GREEN + "\nAdministrador criado com sucesso!" + ConsoleColors.RESET);
        return adminService.buscarPorCpf(cpf);
    }
}
