package br.com.empresa.desafio.repository;

import br.com.empresa.desafio.domain.Funcionario;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface FuncionarioRepository {
    void saveAll(List<Funcionario> funcionarios);
    void save(Funcionario funcionario);
    void deleteByNome(String nome);
    void updateSalarioByNome(String nome, BigDecimal novoSalario);
    List<Funcionario> findAll();
    List<Funcionario> findAll(Predicate<Funcionario> predicate);
    Optional<Funcionario> findByNome(String nome);
    void clear();
}
