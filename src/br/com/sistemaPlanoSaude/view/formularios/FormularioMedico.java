package br.com.sistemaPlanoSaude.view.formularios;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import br.com.sistemaPlanoSaude.util.MetodosAuxiliares;
import br.com.sistemaPlanoSaude.model.enums.NivelAcesso;
import br.com.sistemaPlanoSaude.model.enums.Sexo;
import br.com.sistemaPlanoSaude.model.funcionarios.Medico;
import br.com.sistemaPlanoSaude.model.enums.Especialidades;
import br.com.sistemaPlanoSaude.util.ValidacaoUtil;
import br.com.sistemaPlanoSaude.service.MedicoService;

public class FormularioMedico {

    private static final MedicoService medicoService = new MedicoService();

    public static Medico cadastrarMedico(Scanner scanner) {

        MetodosAuxiliares.limparTela();
        exibirCabecalhoPrincipal();

        System.out.println("                                                                      ");
        System.out.println("+======================== CADASTRO DO MEDICO ========================+");
        System.out.println("|   Preencha as informacoes abaixo para registrar um novo medico.    |");
        System.out.println("+====================================================================+\n");

        // ============================================================
        //                         NOME
        // ============================================================
        System.out.println("+====================== INFORMACOES PESSOAIS =======================+");

        String nome;
        while (true) {
            System.out.print("Nome completo: ");
            nome = scanner.nextLine();
            if (ValidacaoUtil.validarNome(nome)) { nome = nome.trim(); break; }
            System.out.println("Nome invalido. Informe ao menos 10 caracteres e apenas letras.");
        }

        // ============================================================
        //                         CPF
        // ============================================================
        String cpf;
        while (true) {
            System.out.print("CPF: ");
            cpf = scanner.nextLine().trim();
            if (ValidacaoUtil.validarCPF(cpf)) break;
            System.out.println("CPF invalido! Digite um CPF com 11 digitos.");
        }

        // ============================================================
        //                         IDADE
        // ============================================================
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

        // ============================================================
        //                      ENDERECO
        // ============================================================
        System.out.print("Endereco: ");
        String endereco = scanner.nextLine().trim();

        // ============================================================
        //                      TELEFONE
        // ============================================================
        String telefone;
        while (true) {
            System.out.print("Telefone: ");
            String telefoneInput = scanner.nextLine().trim();
            String formatado = ValidacaoUtil.validarEFormatarTelefone(telefoneInput);
            if (formatado != null) {
                telefone = formatado;
                break;
            }
            System.out.println("Telefone invalido. Formatos aceitos: 11999990000 ou (11)99999-0000.");
        }

        // ============================================================
        //                         EMAIL
        // ============================================================
        System.out.print("E-mail (opcional): ");
        String email = scanner.nextLine().trim();
        if (email.isEmpty()) email = "nao informado";
        else if (!ValidacaoUtil.validarEmail(email))
            System.out.println("Aviso: e-mail fora do padrao, mas sera registrado.");

        // ============================================================
        //                          SEXO
        // ============================================================
        System.out.print("Sexo (MASCULINO/FEMININO): ");
        Sexo sexo;
        try {
            sexo = Sexo.valueOf(scanner.nextLine().trim().toUpperCase());
        } catch (Exception e) {
            sexo = Sexo.MASCULINO;
        }

        // ============================================================
        //                   DATA DE NASCIMENTO
        // ============================================================
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataDeNascimento;
        while (true) {
            System.out.print("Data de nascimento (dd/MM/yyyy): ");
            String dataStr = scanner.nextLine().trim();
            if (ValidacaoUtil.validarDataNascimento(dataStr)) {
                dataDeNascimento = LocalDate.parse(dataStr, fmt);
                break;
            }
            System.out.println("Data invalida! Use o formato dd/MM/yyyy.");
        }

        System.out.println("+====================================================================+\n");

        // ============================================================
        //                         CRM
        // ============================================================
        System.out.println("+====================== DADOS PROFISSIONAIS ========================+");

        String crm;
        while (true) {
            System.out.print("CRM: ");
            crm = scanner.nextLine().trim();

            if (!ValidacaoUtil.validarCRM(crm)) {
                System.out.println("CRM invalido! Exemplo valido: 12345-PA.");
                continue;
            }

            if (medicoService.buscarPorCrm(crm) != null) {
                System.out.println("Ja existe um medico cadastrado com este CRM!");
                continue;
            }

            break;
        }

        // ============================================================
        //                     ESPECIALIDADE
        // ============================================================
        System.out.println("\nESPECIALIDADES DISPONIVEIS:");
        for (Especialidades esp : Especialidades.values()) {
            System.out.println(" - " + esp);
        }

        Especialidades especialidade = null;
        while (especialidade == null) {
            System.out.print("\nDigite a especialidade: ");
            String entrada = scanner.nextLine().trim();
            especialidade = buscarEspecialidade(entrada);

            if (especialidade == null) {
                System.out.println("Especialidade invalida! Tente novamente.");
            }
        }
        System.out.println("Especialidade selecionada: " + especialidade);

        // ============================================================
        //                 DATA DE CONTRATACAO
        // ============================================================
        LocalDate dataContratacao;
        while (true) {
            System.out.print("Data de contratacao (dd/MM/yyyy): ");
            String inputData = scanner.nextLine().trim();
            try {
                dataContratacao = LocalDate.parse(inputData, fmt);
                break;
            } catch (Exception e) {
                System.out.println("Data invalida! Tente novamente.");
            }
        }

        // ============================================================
        //                         SALARIO
        // ============================================================
        double salario;
        while (true) {
            System.out.print("Salario: ");
            try {
                salario = Double.parseDouble(scanner.nextLine().trim());
                break;
            } catch (Exception e) {
                System.out.println("Valor invalido. Digite algo como 15000.50");
            }
        }

        System.out.println("+====================================================================+\n");

        // ============================================================
        //               CRIACAO DO OBJETO MEDICO
        // ============================================================
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

        boolean sucesso = medicoService.cadastrar(medico);

        // ============================================================
        //                         RESULTADO
        // ============================================================
        System.out.println("+====================== RESULTADO DO CADASTRO =======================+");

        if (!sucesso) {
            System.out.println("Erro ao cadastrar medico! CRM ja existe no sistema.");
            System.out.println("+====================================================================+");
            return null;
        }

        System.out.println("Medico cadastrado com sucesso!");
        System.out.println("Nome: " + medico.getNome());
        System.out.println("CRM: " + medico.getCrm());
        System.out.println("Especialidade: " + medico.getEspecialidade());

        System.out.println("+====================================================================+\n");
        MetodosAuxiliares.pausarTela();
        
        return medico;
    }

    public static Medico cadastrarMedico() {
        return cadastrarMedico(new Scanner(System.in));
    }

    // ============================================================
    //                      METODOS AUXILIARES
    // ============================================================

    private static Especialidades buscarEspecialidade(String entrada) {
        for (Especialidades esp : Especialidades.values()) {
            if (esp.name().equalsIgnoreCase(entrada)) {
                return esp;
            }
        }
        return null;
    }

    

    private static void exibirCabecalhoPrincipal() {
        System.out.println("+====================================================================+");
        System.out.println("|                     CADASTRO DE MEDICO - ADMIN                    |");
        System.out.println("+--------------------------------------------------------------------+");
        System.out.println("|   Registre novos profissionais no sistema HealthCare Plus.        |");
        System.out.println("+====================================================================+\n");
    }

}
