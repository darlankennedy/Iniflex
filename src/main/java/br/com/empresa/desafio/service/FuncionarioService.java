package br.com.empresa.desafio.service;

import br.com.empresa.desafio.domain.Funcionario;
import br.com.empresa.desafio.repository.FuncionarioRepository;
import br.com.empresa.desafio.util.FormatUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FuncionarioService {

    private final FuncionarioRepository repository;
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");

    public FuncionarioService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    // 3.1 Inserir todos os funcionários
    public void inserirFuncionariosIniciais() {
        List<Funcionario> base = List.of(
            new Funcionario("Maria",   LocalDate.of(2000,10,18), new BigDecimal("2009.44"), "Operador"),
            new Funcionario("João",    LocalDate.of(1990, 5,12), new BigDecimal("2284.38"), "Operador"),
            new Funcionario("Caio",    LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
            new Funcionario("Miguel",  LocalDate.of(1988,10,14), new BigDecimal("19119.88"), "Diretor"),
            new Funcionario("Alice",   LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
            new Funcionario("Heitor",  LocalDate.of(1999,11,19), new BigDecimal("1582.72"), "Operador"),
            new Funcionario("Arthur",  LocalDate.of(1993, 3,31), new BigDecimal("4071.84"), "Contador"),
            new Funcionario("Laura",   LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
            new Funcionario("Heloísa", LocalDate.of(2003, 5,24), new BigDecimal("1606.85"), "Eletricista"),
            new Funcionario("Helena",  LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
        );
        repository.clear();
        repository.saveAll(base);
    }

    // 3.2 Remover o funcionário "João"
    public void removerJoao() {
        repository.deleteByNome("João");
    }

    // 3.3 Imprimir todos os funcionários
    public String imprimirTodosFormatado() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Funcionários (formatação BR) ===\n");
        for (Funcionario f : repository.findAll()) {
            sb.append(String.format(Locale.ROOT,
                    "Nome: %s | Nascimento: %s | Salário: %s | Função: %s\n",
                    f.getNome(),
                    f.getDataNascimento().format(FormatUtils.DATE_BR),
                    FormatUtils.formatCurrency(f.getSalario()),
                    f.getFuncao()));
        }
        return sb.toString();
    }

    // 3.4 Aumento de 10%
    public void aplicarAumentoDezPorCento() {
    	   for (var f : repository.findAll()) {
    	        BigDecimal novo = f.getSalario()
    	            .multiply(new BigDecimal("1.10"))
    	            .setScale(2, RoundingMode.HALF_UP);
    	        repository.updateSalarioByNome(f.getNome(), novo); // <-- persiste
    	    }
    }

    // 3.5 Agrupar por função
    public Map<String, List<Funcionario>> agruparPorFuncao() {
        return repository.findAll().stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao, TreeMap::new, Collectors.toList()));
    }

    // 3.6 Imprimir agrupado por função
    public String imprimirAgrupadoPorFuncao(Map<String, List<Funcionario>> mapa) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Funcionários por Função ===\n");
        mapa.forEach((funcao, lista) -> {
            sb.append("- ").append(funcao).append(":\n");
            for (Funcionario f : lista) {
                sb.append("   • ").append(f.getNome()).append(" - ")
                        .append(FormatUtils.formatCurrency(f.getSalario())).append("\n");
            }
        });
        return sb.toString();
    }

    // 3.8 Aniversariantes meses 10 e 12
    public String imprimirAniversariantesMes(int... meses) {
        Set<Integer> alvo = Arrays.stream(meses).boxed().collect(Collectors.toCollection(TreeSet::new));
        StringBuilder sb = new StringBuilder();
        sb.append("=== Aniversariantes (meses ").append(alvo).append(") ===\n");
        for (Funcionario f : repository.findAll()) {
            int mes = f.getDataNascimento().getMonthValue();
            if (alvo.contains(mes)) {
                sb.append(String.format("• %s (%s)\n", f.getNome(),
                        f.getDataNascimento().format(FormatUtils.DATE_BR)));
            }
        }
        return sb.toString();
    }

    // 3.9 Mais velho
    public String imprimirMaisVelho() {
        Optional<Funcionario> maisVelho = repository.findAll().stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento)); // menor data => mais velho
        if (maisVelho.isEmpty()) return "Nenhum funcionário encontrado.\n";
        Funcionario f = maisVelho.get();
        int idade = Period.between(f.getDataNascimento(), LocalDate.now()).getYears();
        return String.format("=== Mais velho ===%nNome: %s | Idade: %d%n", f.getNome(), idade);
    }

    // 3.10 Ordem alfabética
    public String imprimirOrdenadoPorNome() {
        List<Funcionario> ordenado = new ArrayList<>(repository.findAll());
        ordenado.sort(Comparator.comparing(Funcionario::getNome, FormatUtils.collator()));
        StringBuilder sb = new StringBuilder("=== Ordenado por Nome ===\n");
        for (Funcionario f : ordenado) {
            sb.append("• ").append(f.getNome()).append(" - ").append(f.getFuncao()).append("\n");
        }
        return sb.toString();
    }

    // 3.11 Total dos salários
    public String imprimirTotalSalarios() {
        BigDecimal total = repository.findAll().stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
        return "=== Total dos salários ===\n" + FormatUtils.formatCurrency(total) + "\n";
    }

    // 3.12 Qtde salários mínimos
    public String imprimirSalariosMinimos() {
        StringBuilder sb = new StringBuilder("=== Salários Mínimos (R$ 1.212,00) ===\n");
        for (Funcionario f : repository.findAll()) {
            BigDecimal qtd = f.getSalario().divide(SALARIO_MINIMO, 2, RoundingMode.HALF_UP);
            sb.append(String.format(Locale.ROOT, "• %s: %s salários mínimos%n", f.getNome(),
                    FormatUtils.formatNumber(qtd)));
        }
        return sb.toString();
    }

    public String getRepositorioDescricao() {
        return repository.getClass().getSimpleName();
    }


    public void removerPorNome(String nome) {
        repository.deleteByNome(nome);
    }

    public void adicionarFuncionario(Funcionario f) {
        repository.save(f);
    }
}
