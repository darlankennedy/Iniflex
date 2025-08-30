
package br.com.empresa.desafio.controller;

import br.com.empresa.desafio.domain.Funcionario;
import br.com.empresa.desafio.service.FuncionarioService;
import br.com.empresa.desafio.util.FormatUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;

public class ConsoleMenu {

    private final FuncionarioService service;
    private final Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ConsoleMenu(FuncionarioService service) {
        this.service = service;
    }

    public void start() {
        System.out.println("==== Menu - Funcionários ====");
        System.out.println("(Repositório: " + service.getRepositorioDescricao() + ")");
        int opcao;
        do {
            printMenu();
            opcao = readInt("Escolha uma opção: ");
            handleOption(opcao);
        } while (opcao != 0);
        System.out.println("Encerrado.");
    }

    private void printMenu() {
        System.out.println("\n--- Opções ---");
        System.out.println("1) criar lista padrão (3.1)");
        System.out.println("2) Listar funcionários formatado (3.3)");
        System.out.println("3) Adicionar funcionário");
        System.out.println("4) Remover funcionário por nome");
        System.out.println("5) Aplicar aumento de 10% (3.4)");
        System.out.println("6) Imprimir agrupado por função (3.5/3.6)");
        System.out.println("7) Aniversariantes meses 10 e 12 (3.8)");
        System.out.println("8) Funcionário mais velho (3.9)");
        System.out.println("9) Listar por ordem alfabética (3.10)");
        System.out.println("10) Total dos salários (3.11)");
        System.out.println("11) Salários mínimos por funcionário (3.12)");
        System.out.println("0) Sair");
    }

    private void handleOption(int opcao) {
        switch (opcao) {
            case 1 -> service.inserirFuncionariosIniciais();
            case 2 -> System.out.print(service.imprimirTodosFormatado());
            case 3 -> adicionarFuncionario();
            case 4 -> {
                String nome = readLine("Nome para remover: ");
                service.removerPorNome(nome);
            }
            case 5 -> service.aplicarAumentoDezPorCento();
            case 6 -> System.out.print(service.imprimirAgrupadoPorFuncao(service.agruparPorFuncao()));
            case 7 -> System.out.print(service.imprimirAniversariantesMes(10, 12));
            case 8 -> System.out.print(service.imprimirMaisVelho());
            case 9 -> System.out.print(service.imprimirOrdenadoPorNome());
            case 10 -> System.out.print(service.imprimirTotalSalarios());
            case 11 -> System.out.print(service.imprimirSalariosMinimos());
            case 0 -> { /* sair */ }
            default -> System.out.println("Opção inválida.");
        }
    }

    private void adicionarFuncionario() {
        String nome = readLine("Nome: ");
        LocalDate nasc = readDate("Data de nascimento (dd/MM/yyyy): ");
        BigDecimal salario = readBigDecimal("Salário (use vírgula ou ponto): ");
        String funcao = readLine("Função: ");

        service.adicionarFuncionario(new Funcionario(nome, nasc, salario, funcao));
        System.out.println("Adicionado com sucesso!");
    }

    private int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String s = scanner.nextLine();
                return Integer.parseInt(s.trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro.");
            }
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private LocalDate readDate(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String s = scanner.nextLine();
                return LocalDate.parse(s.trim(), formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido. Ex.: 24/12/1990");
            }
        }
    }

    private BigDecimal readBigDecimal(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String s = scanner.nextLine().trim().replace(".", "").replace(",", ".");
                return new BigDecimal(s);
            } catch (Exception e) {
                System.out.println("Valor inválido. Ex.: 1.234,56");
            }
        }
    }
}
