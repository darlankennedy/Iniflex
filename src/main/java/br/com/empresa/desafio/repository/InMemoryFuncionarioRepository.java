package br.com.empresa.desafio.repository;

import br.com.empresa.desafio.domain.Funcionario;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InMemoryFuncionarioRepository implements FuncionarioRepository {

    private final List<Funcionario> data = new ArrayList<>();

    @Override
    public void saveAll(List<Funcionario> funcionarios) {
        data.addAll(funcionarios);
    }

    @Override
    public void save(Funcionario funcionario) {
        data.add(funcionario);
    }

    @Override
    public void deleteByNome(String nome) {
        data.removeIf(f -> f.getNome().equalsIgnoreCase(nome));
    }

    @Override
    public List<Funcionario> findAll() {
        return new ArrayList<>(data);
    }

    @Override
    public List<Funcionario> findAll(Predicate<Funcionario> predicate) {
        return data.stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public Optional<Funcionario> findByNome(String nome) {
        return data.stream().filter(f -> f.getNome().equalsIgnoreCase(nome)).findFirst();
    }

    @Override
    public void clear() {
        data.clear();
    }

	@Override
	public void updateSalarioByNome(String nome, BigDecimal novoSalario) {
	    data.stream()
        .filter(f -> f.getNome().equalsIgnoreCase(nome))
        .findFirst()
        .ifPresent(f -> f.setSalario(novoSalario));
		
	}
}
