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

        System.out.println("╔════════════════════════ CADASTRO DO MÉDICO ════════════════════════╗");
        System.out.println("║   Preencha as informações abaixo para registrar um novo médico.    ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝\n");

        // ============================================================
        //                         NOME
        // ============================================================
        System.out.println("╔══════════════════════ INFORMAÇÕES PESSOAIS ═══════════════════════╗");

        String nome;
        while (true) {
            System.out.print("🧍 Nome completo: ");
            nome = scanner.nextLine();
            if (ValidacaoUtil.validarNome(nome)) { nome = nome.trim(); break; }
            System.out.println("❌ Nome inválido. Informe ao menos 10 caracteres e apenas letras.");
        }

        // ============================================================
        //                         CPF
        // ============================================================
        String cpf;
        while (true) {
            System.out.print("🪪 CPF: ");
            cpf = scanner.nextLine().trim();
            if (ValidacaoUtil.validarCPF(cpf)) break;
            System.out.println("❌ CPF inválido! Digite um CPF com 11 dígitos.");
        }

        // ============================================================
        //                         IDADE
        // ============================================================
        int idade;
        while (true) {
            System.out.print("🎂 Idade: ");
            String idadeInput = scanner.nextLine().trim();
            try {
                idade = Integer.parseInt(idadeInput);
                if (ValidacaoUtil.validarIdade(idade)) break;
                System.out.println("❌ Idade inválida. Digite um número entre 1 e 150.");
            } catch (Exception ex) {
                System.out.println("❌ Entrada inválida. Apenas números.");
            }
        }

        // ============================================================
        //                      ENDEREÇO
        // ============================================================
        System.out.print("🏠 Endereço: ");
        String endereco = scanner.nextLine().trim();

        // ============================================================
        //                      TELEFONE
        // ============================================================
        String telefone;
        while (true) {
            System.out.print("📞 Telefone: ");
            String telefoneInput = scanner.nextLine().trim();
            String formatado = ValidacaoUtil.validarEFormatarTelefone(telefoneInput);
            if (formatado != null) {
                telefone = formatado;
                break;
            }
            System.out.println("❌ Telefone inválido. Formatos aceitos: 11999990000 ou (11)99999-0000.");
        }

        // ============================================================
        //                         EMAIL
        // ============================================================
        System.out.print("📧 E-mail (opcional): ");
        String email = scanner.nextLine().trim();
        if (email.isEmpty()) email = "não informado";
        else if (!ValidacaoUtil.validarEmail(email))
            System.out.println("⚠ Aviso: e-mail fora do padrão, mas será registrado.");

        // ============================================================
        //                          SEXO
        // ============================================================
        System.out.print("⚧ Sexo (MASCULINO/FEMININO): ");
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
            System.out.print("📅 Data de nascimento (dd/MM/yyyy): ");
            String dataStr = scanner.nextLine().trim();
            if (ValidacaoUtil.validarDataNascimento(dataStr)) {
                dataDeNascimento = LocalDate.parse(dataStr, fmt);
                break;
            }
            System.out.println("❌ Data inválida! Use o formato dd/MM/yyyy.");
        }

        System.out.println("╚════════════════════════════════════════════════════════════════════╝\n");

        // ============================================================
        //                         CRM
        // ============================================================
        System.out.println("╔══════════════════════ DADOS PROFISSIONAIS ════════════════════════╗");

        String crm;
        while (true) {
            System.out.print("🆔 CRM: ");
            crm = scanner.nextLine().trim();

            if (!ValidacaoUtil.validarCRM(crm)) {
                System.out.println("❌ CRM inválido! Exemplo válido: 12345-PA.");
                continue;
            }

            if (medicoService.buscarPorCrm(crm) != null) {
                System.out.println("❌ Já existe um médico cadastrado com este CRM!");
                continue;
            }

            break;
        }

        // ============================================================
        //                     ESPECIALIDADE
        // ============================================================
        System.out.println("\n📚 ESPECIALIDADES DISPONÍVEIS:");
        for (Especialidades esp : Especialidades.values()) {
            System.out.println(" - " + esp);
        }

        Especialidades especialidade = null;
        while (especialidade == null) {
            System.out.print("\n🩺 Digite a especialidade: ");
            String entrada = scanner.nextLine().trim();
            especialidade = buscarEspecialidade(entrada);

            if (especialidade == null) {
                System.out.println("❌ Especialidade inválida! Tente novamente.");
            }
        }

        System.out.println("✔ Especialidade selecionada: " + especialidade);

        // ============================================================
        //                 DATA DE CONTRATAÇÃO
        // ============================================================
        LocalDate dataContratacao;
        while (true) {
            System.out.print("📅 Data de contratação (dd/MM/yyyy): ");
            String inputData = scanner.nextLine().trim();
            try {
                dataContratacao = LocalDate.parse(inputData, fmt);
                break;
            } catch (Exception e) {
                System.out.println("❌ Data inválida! Tente novamente.");
            }
        }

        // ============================================================
        //                         SALÁRIO
        // ============================================================
        double salario;
        while (true) {
            System.out.print("💰 Salário: ");
            try {
                salario = Double.parseDouble(scanner.nextLine().trim());
                break;
            } catch (Exception e) {
                System.out.println("❌ Valor inválido. Digite algo como 15000.50");
            }
        }

        System.out.println("╚════════════════════════════════════════════════════════════════════╝\n");

        // ============================================================
        //               CRIAÇÃO DO OBJETO MÉDICO
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
        System.out.println("╔══════════════════════ RESULTADO DO CADASTRO ═══════════════════════╗");

        if (!sucesso) {
            System.out.println("❌ Erro ao cadastrar médico! CRM já existe no sistema.");
            System.out.println("╚════════════════════════════════════════════════════════════════════╝");
            return null;
        }

        System.out.println("✔ Médico cadastrado com sucesso!");
        System.out.println("👤 Nome: " + medico.getNome());
        System.out.println("🆔 CRM: " + medico.getCrm());
        System.out.println("🩺 Especialidade: " + medico.getEspecialidade());

        System.out.println("╚════════════════════════════════════════════════════════════════════╝\n");

        return medico;
    }

    public static Medico cadastrarMedico() {
        return cadastrarMedico(new Scanner(System.in));
    }

    // ============================================================
    //                      MÉTODOS AUXILIARES
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
        System.out.println("╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                     🩺 CADASTRO DE MÉDICO — ADMIN                 ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════╣");
        System.out.println("║   Registre novos profissionais no sistema HealthCare Plus.         ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝\n");
    }

}
