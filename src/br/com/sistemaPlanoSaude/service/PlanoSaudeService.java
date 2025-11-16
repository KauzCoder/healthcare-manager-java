package br.com.sistemaPlanoSaude.service;

import br.com.sistemaPlanoSaude.model.PlanoSaude;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import br.com.sistemaPlanoSaude.model.PlanoPremium;

/**
 * Serviço para gerenciar operações relacionadas a planos de saúde.
 * Implementa lógica de negócio para CRUD, cálculo de preços e validações.
 */
public class PlanoSaudeService {
    private List<PlanoSaude> planos = new ArrayList<>();

    /**
     * Criar um novo plano de saúde
     */
    public void criarPlano(PlanoSaude plano) {
        if (plano == null) {
            throw new IllegalArgumentException("Plano não pode ser nulo.");
        }
        if (planoExisteByNome(obterNome(plano))) {
            throw new IllegalArgumentException("Plano com nome " + obterNome(plano) + " já existe.");
        }
        planos.add(plano);
    }

    /**
     * Buscar plano por nome
     */
    public Optional<PlanoSaude> buscarPlanoPorNome(String nome) {
        return planos.stream()
                .filter(p -> obterNome(p).equalsIgnoreCase(nome))
                .findFirst();
    }

    /**
     * Listar todos os planos
     */
    public List<PlanoSaude> listarTodosOsPlanos() {
        return new ArrayList<>(planos);
    }

    
     /* Listar planos premium
     */
    public List<PlanoSaude> listarPlanosPremium() {
        return planos.stream()
                .filter(p -> p instanceof PlanoPremium)
                .collect(Collectors.toList());
    }

    /**
     * Atualizar dados do plano
     */
    public void atualizarPlano(PlanoSaude plano) {
        if (plano == null) {
            throw new IllegalArgumentException("Plano não pode ser nulo.");
        }
        Optional<PlanoSaude> planoExistente = buscarPlanoPorNome(obterNome(plano));
        if (planoExistente.isPresent()) {
            int index = planos.indexOf(planoExistente.get());
            planos.set(index, plano);
        } else {
            throw new IllegalArgumentException("Plano com nome " + obterNome(plano) + " não encontrado.");
        }
    }

    /**
     * Deletar plano por nome
     */
    public void deletarPlano(String nome) {
        Optional<PlanoSaude> plano = buscarPlanoPorNome(nome);
        if (plano.isPresent()) {
            planos.remove(plano.get());
        } else {
            throw new IllegalArgumentException("Plano com nome " + nome + " não encontrado.");
        }
    }

    /**
     * Verificar se plano existe por nome
     */
    public boolean planoExisteByNome(String nome) {
        return planos.stream().anyMatch(p -> obterNome(p).equalsIgnoreCase(nome));
    }

    /**
     * Contar total de planos
     */
    public int contarTotalPlanos() {
        return planos.size();
    }

    /**
     * Obter o nome de um PlanoSaude via reflexão quando não existe um getter público.
     */
    private String obterNome(PlanoSaude plano) {
        if (plano == null) {
            return null;
        }
        try {
            java.lang.reflect.Field field = plano.getClass().getDeclaredField("nome");
            field.setAccessible(true);
            Object value = field.get(plano);
            return value != null ? value.toString() : null;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException("Não foi possível obter o nome do plano via reflexão.", e);
        }
    }

    /**
     * Calcular preço de um plano para um paciente
     * (pode considerar idade, dependentes, etc.)
     */
    public double calcularPrecoPaciente(PlanoSaude plano, int idade, boolean temDependentes) {
        if (plano == null) {
            throw new IllegalArgumentException("Plano não pode ser nulo.");
        }
        
        double preco = plano.getValorMensal();
        
        // Aumentar preço se acima de 50 anos
        if (idade > 50) {
            preco *= 1.15; // +15%
        }
        
        // Aumentar preço se tiver dependentes
        if (temDependentes) {
            preco *= 1.10; // +10%
        }
        
        return Math.round(preco * 100.0) / 100.0;
    }

    /**
     * Listar planos ordenados por preço (crescente)
     */
    public List<PlanoSaude> listarPlanosOrdenadorPorPreco() {
        return planos.stream()
                .sorted((p1, p2) -> Double.compare(p1.getValorMensal(), p2.getValorMensal()))
                .collect(Collectors.toList());
    }
}
