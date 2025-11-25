package br.com.sistemaPlanoSaude.view.formularios;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.com.sistemaPlanoSaude.model.enums.Sexo;
import br.com.sistemaPlanoSaude.model.enums.TipoSanguineo;
import br.com.sistemaPlanoSaude.model.pessoas.Paciente;
import br.com.sistemaPlanoSaude.util.ValidacaoUtil;
import br.com.sistemaPlanoSaude.util.MetodosAuxiliares;

public class FormularioPaciente {

    public static Paciente cadastrarPaciente(Scanner scanner) {

        MetodosAuxiliares.limparTela();
        exibirCabecalhoPrincipal();

        // ============================================================
        //                       INFORMACOES PESSOAIS
        // ============================================================
        System.out.println("+====================== INFORMACOES PESSOAIS ======================+");

        // Nome
        String nome;
        while (true) {
            System.out.print("Nome completo: ");
            nome = scanner.nextLine();
            if (ValidacaoUtil.validarNome(nome)) {
                nome = nome.trim();
                break;
            }
            System.out.println("Nome invalido. Informe ao menos 10 caracteres e apenas letras.");
        }

        // CPF
        String cpf;
        while (true) {
            System.out.print("CPF: ");
            cpf = scanner.nextLine().trim();
            if (ValidacaoUtil.validarCPF(cpf)) break;
            System.out.println("CPF invalido! Digite um CPF com 11 digitos.");
        }

        // Idade
        int idade;
        while (true) {
            System.out.print("Idade: ");
            String idadeInput = scanner.nextLine().trim();
            try {
                idade = Integer.parseInt(idadeInput);
                if (ValidacaoUtil.validarIdade(idade)) break;
                System.out.println("Idade invalida. Digite um numero entre 1 e 150.");
            } catch (Exception ex) {
                System.out.println("Entrada invalida. Apenas numeros.");
            }
        }

        // Endereco
        System.out.print("Endereco: ");
        String endereco = scanner.nextLine().trim();

        // Telefone
        String telefone;
        while (true) {
            System.out.print("Telefone: ");
            String telefoneInput = scanner.nextLine().trim();
            String formatado = ValidacaoUtil.validarEFormatarTelefone(telefoneInput);

            if (formatado != null) {
                telefone = formatado;
                break;
            }
            System.out.println("Telefone invalido. Ex: 11999990000 ou (11)99999-0000.");
        }

        // E-mail
        System.out.print("E-mail (opcional): ");
        String email = scanner.nextLine().trim();
        if (email.isEmpty()) email = "nao informado";
        else if (!ValidacaoUtil.validarEmail(email))
            System.out.println("Aviso: e-mail fora do padrao, mas sera registrado.");

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
            System.out.println("Data invalida! Siga o formato dd/MM/yyyy.");
        }

        System.out.println("+===================================================================+\n");

        // ============================================================
        //                        DADOS ADICIONAIS
        // ============================================================
        System.out.println("+========================== DADOS MEDICOS ==========================+");

        // Tipo sanguineo
        System.out.println("Tipos sanguineos aceitos:");
        System.out.println("A_POS, A_NEG, B_POS, B_NEG, AB_POS, AB_NEG, O_POS, O_NEG");

        TipoSanguineo tipoSanguineo = null;
        try {
            System.out.print("Tipo sanguineo: ");
            tipoSanguineo = TipoSanguineo.valueOf(scanner.nextLine().trim().toUpperCase());
        } catch (Exception ignored) {
            tipoSanguineo = null;
        }

        // Peso
        final double PESO_MIN = 0.5;
        final double PESO_MAX = 500.0;
        double peso = 0.0;

        while (true) {
            System.out.print("Peso (kg) entre " + PESO_MIN + " e " + PESO_MAX + " (Enter para pular): ");
            String entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) break;

            try {
                peso = Double.parseDouble(entrada.replace(',', '.'));
                if (peso < PESO_MIN || peso > PESO_MAX) {
                    System.out.println("Valor fora do intervalo permitido.");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Entrada invalida! Exemplo: 70.5");
            }
        }

        // Altura
        final double ALTURA_MIN = 0.4;
        final double ALTURA_MAX = 3.0;
        double altura = 0.0;

        while (true) {
            System.out.print("Altura (m) entre " + ALTURA_MIN + " e " + ALTURA_MAX + " (Enter para pular): ");
            String entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) break;

            try {
                altura = Double.parseDouble(entrada.replace(',', '.'));
                if (altura < ALTURA_MIN || altura > ALTURA_MAX) {
                    System.out.println("Valor fora do intervalo permitido.");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Entrada invalida! Exemplo: 1.75");
            }
        }

        System.out.println("+===================================================================+\n");

        // ============================================================
        //         COLETA DE LISTAS (alergias, cirurgias, etc.)
        // ============================================================
        Paciente novo = new Paciente(
                nome, cpf, idade, endereco, telefone, email, sexo, dataDeNascimento
        );

        if (tipoSanguineo != null) novo.setTipoSanguineo(tipoSanguineo);
        if (peso > 0) novo.setPeso(peso);
        if (altura > 0) novo.setAltura(altura);

        coletarAlergias(novo, scanner);
        coletarDoencasCronicas(novo, scanner);
        coletarHistoricoCirurgias(novo, scanner);
        coletarMedicamentosEmUso(novo, scanner);

        // ============================================================
        //                         RESULTADO
        // ============================================================
        System.out.println("\n+==================== RESUMO DO PRE-CADASTRO =====================+");
        System.out.println("Dados coletados com sucesso! Confirme o plano para finalizar.");
        System.out.println("Nome: " + novo.getNome());
        System.out.println("CPF: " + novo.getCpf());
        System.out.println("Telefone: " + novo.getTelefone());
        System.out.println("+===================================================================+\n");

        MetodosAuxiliares.pausarTela();

        return novo;
    }

    // ============================================================
    //                    METODOS DE COLETA
    // ============================================================

    private static void coletarAlergias(Paciente paciente, Scanner scanner) {
        System.out.println("\nAdicionar alergias (Enter para finalizar):");
        while (true) {
            System.out.print(" - Alergia: ");
            String scannerAlergia = scanner.nextLine().trim();
            if (scannerAlergia.isEmpty()) break;
            paciente.adicionarAlergia(scannerAlergia);
        }
    }

    private static void coletarDoencasCronicas(Paciente paciente, Scanner scanner) {
        System.out.println("\nAdicionar doencas cronicas (Enter para finalizar):");
        while (true) {
            System.out.print(" - Doenca: ");
            String scannerDoenca = scanner.nextLine().trim();
            if (scannerDoenca.isEmpty()) break;
            paciente.adicionarDoencaCronica(scannerDoenca);
        }
    }

    private static void coletarHistoricoCirurgias(Paciente paciente, Scanner scanner) {
        System.out.println("\nAdicionar historico de cirurgias (Enter para finalizar):");
        while (true) {
            System.out.print(" - Cirurgia: ");
            String scannerCirurgia = scanner.nextLine().trim();
            if (scannerCirurgia.isEmpty()) break;
            paciente.adicionarHistoricoCirurgia(scannerCirurgia);
        }
    }

    private static void coletarMedicamentosEmUso(Paciente paciente, Scanner scanner) {
        System.out.println("\nAdicionar medicamentos em uso (Enter para finalizar):");
        while (true) {
            System.out.print(" - Medicamento: ");
            String scannerMedicamento = scanner.nextLine().trim();
            if (scannerMedicamento.isEmpty()) break;
            paciente.adicionarMedicamento(scannerMedicamento);
        }
    }

    // ============================================================
    //                       CABECALHO
    // ============================================================

    private static void exibirCabecalhoPrincipal() {
        System.out.println("+==================================================================+");
        System.out.println("|                 CADASTRO DE PACIENTE - SISTEMA                  |");
        System.out.println("+------------------------------------------------------------------+");
        System.out.println("| Preencha corretamente para garantir a seguranca dos atendimentos.|");
        System.out.println("+==================================================================+\n");
    }
}
