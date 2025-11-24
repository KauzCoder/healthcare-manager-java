package br.com.sistemaPlanoSaude.service;

import br.com.sistemaPlanoSaude.database.ConsultaDatabase;
import br.com.sistemaPlanoSaude.model.consulta.Consulta;
import br.com.sistemaPlanoSaude.model.consulta.Horario;
import br.com.sistemaPlanoSaude.model.enums.ConsultaStatus;
import br.com.sistemaPlanoSaude.model.funcionarios.Medico;
import br.com.sistemaPlanoSaude.model.pessoas.Paciente;

import java.util.List;

public class ConsultaService {

    private final MedicoService medicoService;
    private final HorarioService horarioService;

    public ConsultaService(MedicoService medicoService, HorarioService horarioService) {
        this.medicoService = medicoService;
        this.horarioService = horarioService;
    }
    //Instaciacao dos serviços necessários para operar consultas

    // ===============================
    //           CREATE
    // ===============================
    public boolean agendarConsulta(Paciente paciente, String crmMedico, int idHorario, String descricao) {
        Medico medico = medicoService.buscarPorCrm(crmMedico);
        if (medico == null) return false;

        Horario horario = horarioService.buscarHorario(crmMedico, idHorario);
        if (horario == null || !horario.isDisponibilidade()) return false;

        horarioService.vincularPaciente(crmMedico, idHorario, paciente);

        Consulta consulta = new Consulta(paciente, medico, horario, descricao, null, ConsultaStatus.AGENDADA);
        ConsultaDatabase.adicionarConsulta(consulta);
        //coloquei a receita null pois nao faz sentido gerar receita no agendamento da consulta
        return true;
    }

    // ===============================
    //           READ
    // ===============================
    public Consulta buscarPorId(int idConsulta) {
        return ConsultaDatabase.buscarPorId(idConsulta);
    }

    public List<Consulta> listarPorMedico(String crmMedico) {
        return ConsultaDatabase.buscarPorMedico(crmMedico);
    }

    public List<Consulta> listarPorPaciente(String cpfPaciente) {
        return ConsultaDatabase.buscarPorPaciente(cpfPaciente);
    }

    public List<Consulta> listarTodas() {
        return ConsultaDatabase.listarTodas();
    }

    // ===============================
    //           UPDATE
    // ===============================
    public boolean atualizarConsultaInteira(int idConsulta, String novaDescricao, String novaReceita,
                                     ConsultaStatus novoStatus, String novasAnotacoes) {
        Consulta consulta = buscarPorId(idConsulta);
        if (consulta == null ) return false;

        return ConsultaDatabase.atualizarConsulta(consulta, novaDescricao, novaReceita, novoStatus, novasAnotacoes);
        //Se voce chegou aqui e nao ta entendendo esse construtor, ele serve para atualizar os campos editaveis da consulta. Da uma olhada la na ConsultaDatabase na metodo atualizarConsulta
    }

    public boolean atualizarDescricao(int idConsulta, String novaDescricao) {
        Consulta consulta = buscarPorId(idConsulta);
        if (consulta == null) return false;
        consulta.setDescricao(novaDescricao);
        return true;
    }
    public boolean atualizarReceita(int idConsulta, String novaReceita){
        Consulta consulta = buscarPorId(idConsulta);
        if (consulta == null) return false;
        consulta.setReceita(novaReceita);
        return true;
    }
    public boolean atualizarStatus(int idConsulta, ConsultaStatus novoStatus){
        Consulta consulta = buscarPorId(idConsulta);
        if (consulta == null) return false;
        consulta.setStatus(novoStatus);
        return true;
    }
    public boolean atualizarAnotacoes(int idConsulta, String novasAnotacoes){
        Consulta consulta = buscarPorId(idConsulta);
        if (consulta == null) return false;
        consulta.setAnotacoes(novasAnotacoes);
        return true;
    }

    public boolean reagendarConsulta(int idConsulta, int novoIdHorario) {
        Consulta consulta = buscarPorId(idConsulta);
        if (consulta == null) return false;

        String crm = consulta.getMedico().getCrm();
        Horario novoHorario = horarioService.buscarHorario(crm, novoIdHorario);
        if (novoHorario == null || !novoHorario.isDisponibilidade()) return false;

        // libera horário antigo
        Horario antigo = consulta.getHorario();
        horarioService.removerPaciente(crm, antigo.getIdHorario());

        // reserva novo horário
        horarioService.vincularPaciente(crm, novoIdHorario, consulta.getPaciente());

        // atualiza consulta
        consulta.setHorario(novoHorario);

        return true;
    }

    public boolean realizarConsulta(int idConsulta) {
    Consulta consulta = buscarPorId(idConsulta);
    if (consulta == null) return false;

    consulta.realizarConsultaStatus();

    // Agora realmente salva a mudança no banco
    ConsultaDatabase.atualizarConsulta(
            consulta,
            consulta.getDescricao(),
            consulta.getReceita(),
            consulta.getStatus(),
            consulta.getAnotacoes()
    );

    return true;
}

    public boolean cancelarConsulta(int idConsulta) {
        Consulta consulta = buscarPorId(idConsulta);
        if (consulta == null) return false;

        // Muda o status
        consulta.cancelarConsultaStatus();

        // Libera o horário do médico
        String crm = consulta.getMedico().getCrm();
        int idHorario = consulta.getHorario().getIdHorario();
        horarioService.removerPaciente(crm, idHorario);

        // Remove do “banco”
        ConsultaDatabase.removerConsulta(consulta);

        return true;
    }
}
