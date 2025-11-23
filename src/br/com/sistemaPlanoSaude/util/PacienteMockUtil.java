package br.com.sistemaPlanoSaude.util;

import br.com.sistemaPlanoSaude.model.pessoas.Paciente;
import br.com.sistemaPlanoSaude.model.planos.PlanoBasico;
import br.com.sistemaPlanoSaude.model.planos.PlanoPremium;
import br.com.sistemaPlanoSaude.model.enums.Sexo;
import java.time.LocalDate;

public class PacienteMockUtil {

    public static Paciente criarPacientePlanoBasicoExemplo() {

        PlanoBasico plano = new PlanoBasico();

        Paciente pacienteMock = new Paciente(
            "João Silva",
            "12345678901",
            35,
            "Rua das Flores, 100",
            "(11)99999-0000",
            "joao.silva@example.com",
            Sexo.MASCULINO,
            LocalDate.of(1990, 1, 1)
        );

        pacienteMock.setPeso(70.0);
        pacienteMock.setAltura(1.75);
        pacienteMock.setPlanoSaude(plano);

        return pacienteMock;
    }

    public static Paciente criarPacientePlanoPremiumExemplo() {

        PlanoPremium plano = new PlanoPremium();

        Paciente pacienteMock = new Paciente(
            "João Silva",
            "12345678901",
            35,
            "Rua das Flores, 100",
            "(11)99999-0000",
            "joao.silva@example.com",
            Sexo.MASCULINO,
            LocalDate.of(1990, 1, 1)
        );

        pacienteMock.setPeso(70.0);
        pacienteMock.setAltura(1.75);
        pacienteMock.setPlanoSaude(plano);

        return pacienteMock;
    }


}
