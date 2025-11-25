package br.com.sistemaPlanoSaude.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import br.com.sistemaPlanoSaude.model.enums.Sexo;
import br.com.sistemaPlanoSaude.model.enums.TipoSanguineo;
import br.com.sistemaPlanoSaude.model.pessoas.Paciente;
import br.com.sistemaPlanoSaude.model.planos.PlanoBasico;
import br.com.sistemaPlanoSaude.model.planos.PlanoPremium;
import br.com.sistemaPlanoSaude.model.planos.PlanoSaude;
import br.com.sistemaPlanoSaude.service.PacienteService;

public final class PacienteMockUtil {

    private static boolean pacientesIniciaisRegistrados = false;

    private PacienteMockUtil() {
    }

    public static Paciente criarPacienteBasico() {
        return construirPaciente(
            "Jeanzinho do Grau",
            "12345678900",
            Sexo.MASCULINO,
            TipoSanguineo.O_POS,
            new PlanoBasico(),
            78.5,
            1.78,
            LocalDate.of(1992, 4, 12));
    }

    public static Paciente criarPacientePremium() {
        return construirPaciente(
                "Maria Premium",
                "09876543211",
                Sexo.FEMININO,
            TipoSanguineo.AB_POS,
            new PlanoPremium(),
            63.2,
            1.68,
            LocalDate.of(1988, 9, 30));
    }

    public static void registrarPacientesIniciais(PacienteService pacienteService) {
        if (pacienteService == null) {
            throw new IllegalArgumentException("PacienteService não pode ser nulo");
        }

        if (pacientesIniciaisRegistrados) {
            return;
        }

        List<Paciente> pacientesDemo = criarPacientesDemo();
        for (Paciente paciente : pacientesDemo) {
            pacienteService.cadastrarPaciente(paciente);
        }

        pacientesIniciaisRegistrados = true;
    }

    public static List<Paciente> criarPacientesDemo() {
        return List.of(
                criarPacienteBasico(),
                criarPacientePremium(),
                construirPaciente(
                        "Lucas Andrade",
                        "12345678909",
                        Sexo.MASCULINO,
                        TipoSanguineo.A_POS,
                        new PlanoBasico(),
                        82.4,
                        1.80,
                        LocalDate.of(1989, 3, 11)),
                construirPaciente(
                        "Paula Nascimento",
                        "11144477735",
                        Sexo.FEMININO,
                        TipoSanguineo.B_NEG,
                        new PlanoPremium(),
                        65.0,
                        1.67,
                        LocalDate.of(1993, 12, 3)),
                construirPaciente(
                        "Renato Sousa",
                        "98765432100",
                        Sexo.MASCULINO,
                        TipoSanguineo.O_NEG,
                        new PlanoBasico(),
                        90.2,
                        1.85,
                        LocalDate.of(1985, 7, 22)),
                construirPaciente(
                        "Carolina Batista",
                        "24681357928",
                        Sexo.FEMININO,
                        TipoSanguineo.AB_POS,
                        new PlanoPremium(),
                        58.9,
                        1.70,
                        LocalDate.of(1996, 5, 9)),
                construirPaciente(
                        "Diego Ferreira",
                        "13579246828",
                        Sexo.MASCULINO,
                        TipoSanguineo.A_NEG,
                        new PlanoBasico(),
                        76.3,
                        1.75,
                        LocalDate.of(1991, 10, 18)),
                construirPaciente(
                        "Amanda Ribeiro",
                        "10293847541",
                        Sexo.FEMININO,
                        TipoSanguineo.B_POS,
                        new PlanoPremium(),
                        70.5,
                        1.72,
                        LocalDate.of(1987, 1, 27))
        );
    }

    private static Paciente construirPaciente(String nome,
                                              String cpf,
                                              Sexo sexo,
                                              TipoSanguineo tipoSanguineo,
                                              PlanoSaude plano,
                                              double peso,
                                              double altura,
                                              LocalDate nascimento) {

        Paciente paciente = new Paciente(
                nome,
                cpf,
                calcularIdade(nascimento),
                "Rua das Flores, 100",
                "(11)99999-0000",
                nome.toLowerCase().replace(" ", ".") + "@example.com",
                sexo,
                nascimento);

        paciente.setTipoSanguineo(tipoSanguineo);
        paciente.setPeso(peso);
        paciente.setAltura(altura);
        paciente.setPlanoSaude(plano);
        paciente.setNumeroCarteirinha(plano.getCodigo());

        paciente.adicionarAlergia("Poeira");
        paciente.adicionarDoencaCronica("Hipertensão controlada");
        paciente.adicionarMedicamento("Losartana 50mg");
        paciente.adicionarHistoricoCirurgia("Apendicectomia em 2010");

        return paciente;
    }

    private static int calcularIdade(LocalDate nascimento) {
        return Period.between(nascimento, LocalDate.now()).getYears();
    }
}
