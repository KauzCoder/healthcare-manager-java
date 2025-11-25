package br.com.sistemaPlanoSaude.util;

import java.time.LocalDate;

import br.com.sistemaPlanoSaude.model.enums.Especialidades;
import br.com.sistemaPlanoSaude.model.enums.NivelAcesso;
import br.com.sistemaPlanoSaude.model.enums.Sexo;
import br.com.sistemaPlanoSaude.model.funcionarios.Medico;
import br.com.sistemaPlanoSaude.service.MedicoService;

public final class MedicoMockUtil {

	private MedicoMockUtil() {
	}

	public static Medico criarCardiologistaPadrao() {
		return new Medico(
				"Dra. Ana Cardio",
				"32165498700",
				40,
				"Av. Paulista, 500",
				"(11)98888-7777",
				"ana.cardio@example.com",
				Sexo.FEMININO,
				LocalDate.of(1985, 5, 20),
				Especialidades.CARDIOLOGIA,
				"12345-SP",
				LocalDate.of(2020, 1, 10),
				22000,
				NivelAcesso.MEDICO);
	}

	public static Medico registrarCardiologistaPadrao(MedicoService service) {
		if (service == null) {
			throw new IllegalArgumentException("MedicoService não pode ser nulo");
		}
		Medico medico = criarCardiologistaPadrao();
		boolean criado = service.cadastrar(medico);
		if (!criado) {
			throw new IllegalStateException("Médico mock já cadastrado no banco em memória.");
		}
		return medico;
	}

	public static void registrarMedicosIniciais(MedicoService service) {
		if (service == null) {
			throw new IllegalArgumentException("MedicoService não pode ser nulo");
		}

		Medico[] medicosDemo = new Medico[] {
			criarCardiologistaPadrao(),
			new Medico(
				"Dr. Bruno Ortopedista",
				"65498732100",
				38,
				"Rua Saúde, 200",
				"(11)97777-6666",
				"bruno.orto@example.com",
				Sexo.MASCULINO,
				LocalDate.of(1987, 8, 15),
				Especialidades.ORTOPEDIA,
				"54321-SP",
				LocalDate.of(2019, 3, 5),
				18000,
				NivelAcesso.MEDICO),
			new Medico(
				"Dra. Carla Neuro",
				"15975348600",
				45,
				"Av. Central, 750",
				"(11)96666-5555",
				"carla.neuro@example.com",
				Sexo.FEMININO,
				LocalDate.of(1980, 11, 2),
				Especialidades.NEUROLOGIA,
				"67890-SP",
				LocalDate.of(2015, 7, 18),
				25000,
				NivelAcesso.MEDICO),
			new Medico(
				"Dr. Daniel Pediatria",
				"74125896300",
				33,
				"Rua das Crianças, 12",
				"(11)95555-4444",
				"daniel.ped@example.com",
				Sexo.MASCULINO,
				LocalDate.of(1992, 2, 10),
				Especialidades.PEDIATRIA,
				"11223-SP",
				LocalDate.of(2021, 6, 1),
				19000,
				NivelAcesso.MEDICO),
			new Medico(
				"Dra. Elisa Dermato",
				"96385274100",
				36,
				"Av. das Palmeiras, 80",
				"(11)94444-3333",
				"elisa.dermato@example.com",
				Sexo.FEMININO,
				LocalDate.of(1989, 4, 25),
				Especialidades.DERMATOLOGIA,
				"33445-SP",
				LocalDate.of(2018, 9, 30),
				21000,
				NivelAcesso.MEDICO)
		};

		for (Medico medico : medicosDemo) {
			service.cadastrar(medico);
		}
	}
}
