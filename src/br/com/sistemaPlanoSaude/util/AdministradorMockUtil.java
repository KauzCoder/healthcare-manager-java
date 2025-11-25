package br.com.sistemaPlanoSaude.util;

import java.time.LocalDate;

import br.com.sistemaPlanoSaude.model.enums.NivelAcesso;
import br.com.sistemaPlanoSaude.model.enums.Sexo;
import br.com.sistemaPlanoSaude.model.funcionarios.Administrador;
import br.com.sistemaPlanoSaude.service.AdministradorService;

public final class AdministradorMockUtil {

	private AdministradorMockUtil() {
	}

	public static Administrador criarAdministradorPadrao() {
		return new Administrador(
				"Maria Gestora",
				"98765432100",
				45,
				"Rua Central, 10",
				"(11)90000-1111",
				"maria.gestora@example.com",
				Sexo.FEMININO,
				LocalDate.of(1980, 3, 15),
				NivelAcesso.ADMINISTRADOR,
				"hash_senha_padrao",
				LocalDate.now().minusYears(1),
				LocalDate.now());
	}

	public static Administrador registrarAdministradorPadrao(AdministradorService service) {
		if (service == null) {
			throw new IllegalArgumentException("AdministradorService não pode ser nulo");
		}
		Administrador admin = criarAdministradorPadrao();
		boolean criado = service.criarAdministrador(admin);
		if (!criado) {
			throw new IllegalStateException("Administrador mock já cadastrado no banco em memória.");
		}
		return admin;
	}
}
