# Desafio Prático - Java (Funcionários)

Projeto **apenas em Java** (console) com camadas **Domain / Repository / Service / Controller (DDD)** e opção de persistência **SQLite** via JDBC.

## Requisitos atendidos
- Classe `Pessoa(nome, dataNascimento)`.
- Classe `Funcionario` estende `Pessoa` com `salario` e `funcao`.
- `Principal` (`App`) executa todos os itens (3.1 a 3.12), incluindo:
  - Inserção da lista original
  - Remoção do funcionário **"João"**
  - Impressão com **data** em `dd/MM/yyyy` e **salário** no padrão brasileiro
  - Aumento de **10%**
  - **Agrupamento por função** (Map)
  - **Aniversariantes** dos meses **10** e **12**
  - **Mais velho** (nome e idade)
  - **Ordenação alfabética**
  - **Total** de salários
  - **Qtd. de salários mínimos** (R$ 1212,00)

> A lista utilizada é a clássica desse teste:
```
Maria    18/10/2000  2009.44  Operador
João     12/05/1990  2284.38  Operador
Caio     02/05/1961  9836.14  Coordenador
Miguel   14/10/1988  19119.88 Diretor
Alice    05/01/1995  2234.68  Recepcionista
Heitor   19/11/1999  1582.72  Operador
Arthur   31/03/1993  4071.84  Contador
Laura    08/07/1994  3017.45  Gerente
Heloísa  24/05/2003  1606.85  Eletricista
Helena   02/09/1996  2799.93  Gerente
```

## Como executar (Maven)
Requisitos: **JDK 17+** e **Maven**.

```bash
cd desafio-funcionarios
mvn -q -DskipTests package
java -jar target/desafio-funcionarios-1.0.0.jar
```

### (Opcional) Usando repositório SQLite
Por padrão roda em memória. Para gravar/ler em **SQLite**, execute:

```bash
java -Drepo=sqlite -Ddb.path=./data/teste.db -jar target/desafio-funcionarios-1.0.0.jar
```

- O arquivo será criado se não existir (tabela `funcionarios`).
- As operações do desafio são executadas **sobre os dados carregados**.

## Estrutura de pastas
```
src/main/java/br/com/empresa/desafio/
  App.java
  controller/FuncionarioController.java
  domain/{Pessoa.java, Funcionario.java}
  repository/{FuncionarioRepository.java, InMemoryFuncionarioRepository.java, SQLiteFuncionarioRepository.java}
  service/FuncionarioService.java
  util/FormatUtils.java
```

## Observações
- Projeto console simples, sem frameworks web.
- Sinta-se à vontade para evoluir/estender o design.
