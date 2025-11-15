package br.com.sistemaPlanoSaude.model;
import java.util.ArrayList;
import java.util.List;

public class Admnistrador {
    private String nome;
    private String codigoAcesso;
    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    private List<Medico> medicos = new ArrayList<>();
    private List<PlanoSaude> planos = new ArrayList<>();

    public Admnistrador(String nome, String codigoAcesso){
        this.nome = nome;
        this.codigoAcesso = codigoAcesso;
    }

    public void cadastrarMedico(Medico m){
        medicos.add(m);
        System.out.println("Plano de saúde cadastrado: " + m.getNome());
    }

    public void listarMedicos(){
        System.out.println("\n--- Lista de Médicos ---");
        for (Medico m : medicos){
            System.out.println(m);
        }
    }

    public void listarPlanos(){
        System.out.println("\n--- Lista de Planos ---");
        for (PlanoSaude p: planos) {
            System.out.println(p);
        }
    }

    public void removerMedico(String crm) {
        medicos.removeIf(m -> m.getCrm().equals(crm));
        System.out.println("Médico removido: " + crm);
    }
    
    public void removerPlano(String nomePlano){
        planos.removeIf(p -> p.getNome().equalsIgnoreCase(nomePlano));
        System.out.println("Plano removido: "+ nomePlano);

    }

    public void autorizarPlano(Paciente b ,  String procedimento) {
        if (b.temPlano()) {
            System.out.println("Autorização procedimento " + procedimento + "para: " +b.getNome());
        }else{
            System.out.println("NEGADO! Beneficiário" +b.getNome() + "não possui plano");
        }
    }
}
