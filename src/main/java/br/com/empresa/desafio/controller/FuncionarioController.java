package br.com.empresa.desafio.controller;

import br.com.empresa.desafio.service.FuncionarioService;

import java.util.Map;

public class FuncionarioController {

    private final FuncionarioService service;

    public FuncionarioController(FuncionarioService service) {
        this.service = service;
    }

    public void runAll() {
        // 3.1
        service.inserirFuncionariosIniciais();

        // 3.2
        service.removerJoao();

        // 3.3
        System.out.print(service.imprimirTodosFormatado());

        // 3.4
        service.aplicarAumentoDezPorCento();
        
        System.out.print("lista atualizada");
        System.out.print(service.imprimirTodosFormatado());

        // 3.5
        Map<String, ?> mapa = service.agruparPorFuncao();

        // 3.6
        System.out.print(service.imprimirAgrupadoPorFuncao((Map) mapa));

        // 3.8
        System.out.print(service.imprimirAniversariantesMes(10, 12));

        // 3.9
        System.out.print(service.imprimirMaisVelho());

        // 3.10
        System.out.print(service.imprimirOrdenadoPorNome());

        // 3.11
        System.out.print(service.imprimirTotalSalarios());

        // 3.12
        System.out.print(service.imprimirSalariosMinimos());
    }
}
