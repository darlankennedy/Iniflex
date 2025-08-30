package br.com.empresa.desafio;

import br.com.empresa.desafio.controller.ConsoleMenu;
import br.com.empresa.desafio.controller.FuncionarioController;
import br.com.empresa.desafio.repository.FuncionarioRepository;
import br.com.empresa.desafio.repository.InMemoryFuncionarioRepository;
import br.com.empresa.desafio.repository.SQLiteFuncionarioRepository;
import br.com.empresa.desafio.service.FuncionarioService;

public class App {
    public static void main(String[] args) {
        String repoChoice = System.getProperty("repo", "sqlite").toLowerCase();
        FuncionarioRepository repo;

        if ("sqlite".equals(repoChoice)) {
            String dbPath = System.getProperty("db.path", "./data/teste.db");
            ensureParentDir(dbPath);
            repo = new SQLiteFuncionarioRepository(dbPath);
        } else {
            repo = new InMemoryFuncionarioRepository();
        }

        FuncionarioService service = new FuncionarioService(repo);
        FuncionarioController controller = new FuncionarioController(service);
        ConsoleMenu menu = new ConsoleMenu(service);
        controller.runAll();
        menu.start();
    }

    private static void ensureParentDir(String path) {
        try {
            java.io.File f = new java.io.File(path).getAbsoluteFile();
            java.io.File parent = f.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
        } catch (Exception e) {
            System.err.println("Não foi possível criar diretório do banco: " + e.getMessage());
        }
    }
}