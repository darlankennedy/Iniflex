# Iniflex — Desafio Prático (Java)

Projeto console em **Java 17** (Maven), com camadas **Domain / Repository / Service / Controller**, **menu interativo** via `Scanner` e persistência **opcional** em **SQLite**.

> Repositório: https://github.com/darlankennedy/Iniflex

---

## ✨ Funcionalidades (resumo)

- Modelo:
  - `Pessoa(nome, dataNascimento)`
  - `Funcionario(salario, funcao)` estende `Pessoa`
- Operações solicitadas:
  - Inserir lista base (3.1)
  - Remover “João” (3.2)
  - Imprimir com **formatação BR** de data e moeda (3.3)
  - **+10%** em todos os salários (3.4)
  - **Agrupar por função** + imprimir (3.5 / 3.6)
  - **Aniversariantes** nos meses **10** e **12** (3.8)
  - **Mais velho**: nome e idade (3.9)
  - **Ordem alfabética** (3.10)
  - **Total de salários** (3.11)
  - **Salários mínimos** por funcionário (3.12)

---

## 📦 Baixar e construir

```bash
git clone https://github.com/darlankennedy/Iniflex.git
cd Iniflex
mvn -q -DskipTests clean package
```

Isso gera um JAR “fat/uber” em `target/`.

---

## ▶️ Executar

### Windows (PowerShell)

- **Menu interativo** (padrão):
```powershell
java -jar (Get-ChildItem .\target\*.jar | Select-Object -First 1).FullName
```

- **Modo automático** (executa tudo de uma vez):
```powershell
java -Dmode=auto -jar (Get-ChildItem .\target\*.jar | Select-Object -First 1).FullName
```

- **Com SQLite** (arquivo padrão `.\data\teste.db`):
```powershell
java -Drepo=sqlite -Ddb.path=.\data\teste.db -jar (Get-ChildItem .\target\*.jar | Select-Object -First 1).FullName
```

### Linux / macOS

- **Menu interativo**:
```bash
java -jar target/*.jar
```

- **Modo automático**:
```bash
java -Dmode=auto -jar target/*.jar
```

- **Com SQLite**:
```bash
java -Drepo=sqlite -Ddb.path=./data/teste.db -jar target/*.jar
```

> 🔎 Dica: se o projeto estiver configurado para **SQLite como padrão**, o app já cria `./data/teste.db`.  
> Para rodar só em memória, use `-Drepo=memory`.

---

## 🧭 Menu (opções)

1. Carregar lista padrão  
2. Listar funcionários (formatação BR)  
3. **Adicionar** funcionário  
4. **Remover** por nome  
5. Aplicar **+10%**  
6. **Agrupar por função** (e imprimir)  
7. **Aniversariantes** (10 e 12)  
8. **Mais velho**  
9. **Ordem alfabética**  
10. **Total** dos salários  
11. **Salários mínimos**  
0. Sair

---

## 🗄️ Banco (SQLite)

- Caminho padrão: `./data/teste.db` (Windows: `.\data\teste.db`)
- A pasta **é criada automaticamente** se não existir.
- Para trocar o caminho:
  ```bash
  -Ddb.path=C:/temp/meu.db
  # ou
  -Ddb.path=/home/usuario/meu.db
  ```

---

## 🧪 Exemplo de saída (trecho)

```
=== Funcionários (formatação BR) ===
Nome: Maria | Nascimento: 18/10/2000 | Salário: R$ 2.009,44 | Função: Operador
...
lista atualizada
=== Funcionários (formatação BR) ===
Nome: Maria | Nascimento: 18/10/2000 | Salário: R$ 2.210,38 | Função: Operador
...
=== Total dos salários ===
R$ 50.906,82
```

---

## 🛠️ Executar no Eclipse

1. `File → Import… → Maven → Existing Maven Projects` → aponte para a pasta do projeto  
2. `Project → Clean…` e `Maven → Update Project… (Alt+F5)`  
3. Rodar:
   - **Menu**: `App.java → Run As → Java Application`
   - **Modo auto**: `Run Configurations… → VM arguments: -Dmode=auto`
   - **SQLite**: `VM arguments: -Drepo=sqlite -Ddb.path=./data/teste.db`

---

## 🧰 Solução de problemas

- **SLF4J (“StaticLoggerBinder”)**  
  O projeto já inclui binding SLF4J (NOP ou Simple). Se aparecer, atualize dependências:
  ```bash
  mvn -U clean package
  ```

- **“Unable to access jarfile …”**  
  Rode `mvn package` e use o JAR de `target/`. Nos exemplos, usamos **wildcard** para não depender do nome exato.

- **Versão do Java**  
  Use **JDK 17+** (não apenas JRE).

---

## 🗂️ Estrutura (resumo)

```
src/main/java/br/com/empresa/desafio/
  App.java
  controller/
    ConsoleMenu.java
    FuncionarioController.java
  domain/
    Pessoa.java
    Funcionario.java
  repository/
    FuncionarioRepository.java
    InMemoryFuncionarioRepository.java
    SQLiteFuncionarioRepository.java
  service/
    FuncionarioService.java
  util/
    FormatUtils.java
```

---

## 📜 Licença

Este projeto é disponibilizado para fins de avaliação técnica e estudos.
