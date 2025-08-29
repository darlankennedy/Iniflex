package br.com.empresa.desafio.repository;

import br.com.empresa.desafio.domain.Funcionario;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Implementação simples via JDBC/SQLite.
 * Observação: Para o desafio de console, a versão em memória já atende.
 * Esta classe é opcional e demonstra o padrão Repository com persistência.
 */
public class SQLiteFuncionarioRepository implements FuncionarioRepository {

    private final String url;

    public SQLiteFuncionarioRepository(String dbPath) {
        this.url = "jdbc:sqlite:" + dbPath;
        init();
    }

    private void init() {
        try (Connection conn = DriverManager.getConnection(url);
             Statement st = conn.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS funcionarios (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nome TEXT NOT NULL, " +
                    "data_nascimento TEXT NOT NULL, " +
                    "salario TEXT NOT NULL, " +
                    "funcao TEXT NOT NULL)");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inicializar SQLite", e);
        }
    }

    @Override
    public void saveAll(List<Funcionario> funcionarios) {
        try (Connection conn = DriverManager.getConnection(url)) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO funcionarios(nome, data_nascimento, salario, funcao) VALUES(?,?,?,?)")) {
                for (Funcionario f : funcionarios) {
                    ps.setString(1, f.getNome());
                    ps.setString(2, f.getDataNascimento().toString());
                    ps.setString(3, f.getSalario().toPlainString());
                    ps.setString(4, f.getFuncao());
                    ps.addBatch();
                }
                ps.executeBatch();
                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar em lote", e);
        }
    }

    @Override
    public void save(Funcionario f) {
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO funcionarios(nome, data_nascimento, salario, funcao) VALUES(?,?,?,?)")) {
            ps.setString(1, f.getNome());
            ps.setString(2, f.getDataNascimento().toString());
            ps.setString(3, f.getSalario().toPlainString());
            ps.setString(4, f.getFuncao());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar funcionario", e);
        }
    }

    @Override
    public void deleteByNome(String nome) {
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement ps = conn.prepareStatement("DELETE FROM funcionarios WHERE lower(nome)=lower(?)")) {
            ps.setString(1, nome);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar por nome", e);
        }
    }

    @Override
    public List<Funcionario> findAll() {
        List<Funcionario> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement ps = conn.prepareStatement("SELECT nome, data_nascimento, salario, funcao FROM funcionarios");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String nome = rs.getString(1);
                LocalDate nasc = LocalDate.parse(rs.getString(2));
                BigDecimal sal = new BigDecimal(rs.getString(3));
                String func = rs.getString(4);
                list.add(new Funcionario(nome, nasc, sal, func));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar todos", e);
        }
        return list;
    }

    @Override
    public List<Funcionario> findAll(Predicate<Funcionario> predicate) {
        return findAll().stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public Optional<Funcionario> findByNome(String nome) {
        return findAll().stream().filter(f -> f.getNome().equalsIgnoreCase(nome)).findFirst();
    }

    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(url);
             Statement st = conn.createStatement()) {
            st.executeUpdate("DELETE FROM funcionarios");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao limpar tabela", e);
        }
    }

	@Override
	public void updateSalarioByNome(String nome, BigDecimal novoSalario) {
	    try (Connection conn = DriverManager.getConnection(url);
	            PreparedStatement ps = conn.prepareStatement(
	                "UPDATE funcionarios SET salario=? WHERE lower(nome)=lower(?)")) {
	           ps.setString(1, novoSalario.toPlainString());
	           ps.setString(2, nome);
	           ps.executeUpdate();
	       } catch (SQLException e) {
	           throw new RuntimeException("Erro ao atualizar salário", e);
	       }
		
	}
}
