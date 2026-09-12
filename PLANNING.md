Sim. Para um único dia com duas pessoas, eu estruturaria o trabalho como uma sequência de checkpoints em que cada etapa termina com algo executável. Isso é mais importante do que simplesmente distribuir classes entre as duas pessoas: queremos evitar que a Pessoa 1 termine uma classe que a Pessoa 2 ainda não consegue utilizar.

Vou assumir aproximadamente **8 horas efetivas de desenvolvimento**, com pequenas pausas, portanto cerca de **16 pessoa-horas**. O cronograma abaixo é propositalmente agressivo: o objetivo é terminar com um MVP jogável, não com uma arquitetura “completa”.

A divisão também tenta minimizar conflitos no Git. A Pessoa 1 ficará mais concentrada no **domínio/gameplay**, enquanto a Pessoa 2 ficará mais concentrada em **IA/infraestrutura**, e ambas convergem na integração.

---

# Cronograma de implementação — AI Text RPG

## Visão geral

| Checkpoint               |         Horário |        Duração | Entregável                    |
| ------------------------ | --------------: | -------------: | ----------------------------- |
| 0. Preparação            |     09:00–09:30 |           0,5h | Projeto Maven compilando      |
| 1. Domínio básico        |     09:30–10:30 |             1h | Mundo, locais, NPCs e jogador |
| 2. Mundo jogável         |     10:30–11:30 |             1h | Exploração pelo terminal      |
| 3. Integração com Ollama |     09:30–11:30 | 2h em paralelo | Java conversando com LLM      |
| 4. Conversação           |     11:30–13:00 |           1,5h | Conversa livre com NPC        |
| **Almoço**               | **13:00–14:00** |         **1h** |                               |
| 5. Integração completa   |     14:00–15:00 |             1h | Exploração + NPC + LLM        |
| 6. Seed e geração        |     15:00–16:00 |             1h | Mundo determinístico          |
| 7. Conteúdo e coerência  |     16:00–17:00 |             1h | Vila e NPCs interessantes     |
| 8. Testes e correções    |     17:00–18:00 |             1h | MVP estável                   |
| 9. Polimento             |     18:00–19:00 |             1h | Experiência jogável final     |

A sobreposição entre os checkpoints 2 e 3 é intencional. Com duas pessoas, não faz sentido uma ficar esperando a outra terminar.

---

# Checkpoint 0 — Preparação

**09:00–09:30 — 30 minutos**

Objetivo: deixar o ambiente pronto para desenvolvimento paralelo.

### Pessoa 1 — Projeto base

**0,5h**

Criar:

```text
pom.xml
src/main/java/
src/test/java/
```

Definir:

```text
groupId
artifactId
Java version
```

Criar:

```java
Main.java
```

e garantir:

```bash
mvn test
```

e/ou:

```bash
mvn package
```

funcionando.

Também criar o repositório Git.

### Pessoa 2 — Ollama

**0,5h**

Garantir que:

```text
Ollama
↓
modelo escolhido
↓
API local
```

esteja funcionando.

Antes de escrever a integração Java, testar manualmente que o modelo responde.

Por exemplo, a pessoa deve conseguir confirmar:

```text
Ollama está executando
Modelo está disponível
Modelo recebe prompt
Modelo retorna resposta
```

### Entregável

No final de 30 minutos:

```text
✓ Projeto Maven criado
✓ Git funcionando
✓ Java funcionando
✓ Ollama funcionando
✓ Modelo local funcionando
```

**Checkpoint:** ambos conseguem executar o projeto e o ambiente de IA está operacional.

---

# Checkpoint 1 — Modelo de domínio

**09:30–10:30 — 1 hora**

Agora começa o desenvolvimento real.

### Pessoa 1 — Domain

**1h**

Implementar as entidades mínimas:

```text
World
Location
Structure
NPC
Player
Item
Relationship
```

Não gastar tempo criando getters/setters sofisticados ou abstrações desnecessárias.

O objetivo é conseguir representar algo como:

```text
World
└── Valdora
    ├── Praça
    ├── Taverna
    ├── Ferraria
    │
    ├── Aldren
    ├── Mira
    └── Tomas
```

Um NPC deve possuir pelo menos:

```text
name
occupation
personality
backstory
knowledge
location
relationships
```

### Pessoa 2 — Game state + geração inicial

**1h**

Implementar:

```text
GameState
WorldGenerator
```

Inicialmente, não precisa existir geração procedural sofisticada.

Pode começar com:

```text
generate(seed)
```

e produzir uma vila fixa.

Por exemplo:

```text
generate(1234)
```

produz:

```text
Valdora
├── Praça
├── Taverna
└── Ferraria
```

com alguns NPCs.

A seed pode ainda não fazer muita coisa. A prioridade é estabelecer a interface.

### Entregável

No final:

```text
World
 └── Location
      ├── Structure
      └── NPC

Player
 └── Location

GameState
 ├── World
 └── Player

WorldGenerator
 └── generate(seed)
```

**Checkpoint:** é possível criar programaticamente uma partida contendo jogador, mundo, locais e NPCs.

---

# Checkpoint 2 — Mundo navegável

**10:30–11:30 — 1 hora**

Agora o jogo precisa deixar de ser apenas um conjunto de objetos Java.

### Pessoa 1 — Game Engine

**1h**

Implementar:

```text
Game
GameEngine
CommandProcessor
```

Comandos mínimos:

```text
look
go <local>
talk <npc>
inventory
help
quit
```

O `CommandProcessor` deve transformar a entrada em alguma representação interna.

Não precisa criar um parser sofisticado.

Algo simples é suficiente:

```text
"go tavern"
```

→

```text
MOVE
target = tavern
```

### Pessoa 2 — Terminal UI

**1h**

Implementar:

```text
TerminalUI
```

Responsabilidades:

```text
ler entrada
mostrar saída
mostrar localização
mostrar NPCs
mostrar comandos
```

Criar o game loop:

```text
while (game.isRunning()) {
    input = terminal.read();
    game.process(input);
}
```

### Entregável

Já deve ser possível:

```text
$ java -jar ai-rpg.jar

Você está em Valdora.

Locais:
- Praça
- Taverna
- Ferraria

NPCs:
- Aldren
- Mira
- Tomas

> go ferraria

Você entrou na Ferraria.

> look

Ferraria de Aldren.

NPCs:
- Aldren

> inventory

Inventário vazio.
```

**Checkpoint:** temos um pequeno jogo textual funcional **sem IA**.

Isso é importante. Se Ollama quebrar nesse momento, o jogo ainda possui uma base funcional.

---

# Checkpoint 3 — Integração com Ollama

**10:30–11:30 — em paralelo com o Checkpoint 2**

Aqui a Pessoa 2 trabalha enquanto a Pessoa 1 implementa a engine.

### Pessoa 2 — `LLMClient`

**1h**

Criar:

```text
LLMClient
OllamaLLMClient
```

Conceitualmente:

```java
interface LLMClient {

    String generate(String prompt);

}
```

A implementação deverá fazer a comunicação com Ollama.

A Pessoa 2 deve testar isoladamente:

```text
Java
 ↓
OllamaLLMClient
 ↓
Ollama
 ↓
modelo
 ↓
resposta
```

### Importante

Não criar ainda:

```text
RAG
Memory
Agent
Vector Database
Function Calling
```

Nada disso.

O objetivo é simplesmente:

```text
String → LLM → String
```

### Pessoa 1 — `PromptBuilder`

Enquanto a integração HTTP é feita, a Pessoa 1 pode começar a construir:

```text
PromptBuilder
```

O builder recebe:

```text
NPC
Player
GameState
```

e gera o prompt.

Exemplo:

```text
Você é Aldren, o ferreiro de Valdora.

PERSONALIDADE:
Orgulhoso, reservado e apaixonado por armas.

HISTÓRIA:
Você serviu durante a Guerra do Norte...

CONHECIMENTOS:
- ...
- ...
- ...

RELACIONAMENTOS:
- Você desconfia do prefeito.
- Você respeita Mira.

LOCALIZAÇÃO:
Ferraria de Valdora.

Responda ao jogador como Aldren.
Não invente fatos que contradigam seu conhecimento.
```

### Entregável

No final:

```text
LLMClient
     ↑
OllamaLLMClient

NPC + GameState
     ↓
PromptBuilder
     ↓
String
```

**Checkpoint:** a aplicação Java consegue mandar um prompt para Ollama e receber uma resposta.

---

# Checkpoint 4 — Conversação

**11:30–13:00 — 1,5 hora**

Esse é o checkpoint mais importante do projeto.

Aqui o protótipo começa a cumprir sua proposta.

### Pessoa 1 — `NPCInteractionService`

**1,5h**

Implementar:

```text
NPCInteractionService
```

Fluxo:

```text
interact(
    player,
    npc,
    message
)
```

Internamente:

```text
Player
NPC
GameState
message
   ↓
PromptBuilder
   ↓
LLMClient
   ↓
response
```

### Pessoa 2 — Contexto e qualidade dos prompts

**1,5h**

Enquanto a Pessoa 1 integra, a Pessoa 2 trabalha nos prompts e nos dados dos NPCs.

Criar os primeiros NPCs realmente interessantes.

Exemplo:

```text
Aldren
```

deve ter uma opinião específica sobre:

```text
prefeito
cidade
guerra
passagem subterrânea
outros NPCs
```

E:

```text
Mira
```

deve possuir informações diferentes.

Isso é fundamental para demonstrar que não estamos simplesmente usando:

```text
"Você é um NPC de RPG."
```

O prompt deve fazer o personagem parecer uma pessoa específica.

### Entregável

O jogador consegue:

```text
> talk Aldren

Você está conversando com Aldren.

> O que você sabe sobre essa cidade?

Aldren:
...

> E sobre o prefeito?

Aldren:
...

> Por que você não gosta dele?

Aldren:
...
```

**Checkpoint crítico:** a conversa precisa funcionar de ponta a ponta.

---

# ALMOÇO — 13:00–14:00

Aqui eu faria uma regra:

**não implementar durante o almoço.**

O projeto precisa ser retomado com uma versão funcional.

---

# Checkpoint 5 — Integração completa

**14:00–15:00 — 1 hora**

Agora juntamos tudo.

### Pessoa 1 — Game flow

**1h**

Garantir que:

```text
explorar
↓
encontrar NPC
↓
talk
↓
conversar
↓
sair da conversa
↓
continuar explorando
```

funcione sem quebrar o estado.

Por exemplo:

```text
> go tavern

> talk Mira

> Quem é o prefeito?

Mira:
...

> exit

> go square
```

### Pessoa 2 — Robustez da integração

**1h**

Tratar:

```text
Ollama indisponível
modelo inexistente
timeout
resposta vazia
NPC inexistente
local inexistente
comando inválido
```

Não precisa criar um sistema de exceptions sofisticado.

Uma mensagem como:

```text
Não foi possível obter uma resposta do NPC.
Verifique se o Ollama está funcionando.
```

já é suficiente para o MVP.

### Entregável

Uma partida completa:

```text
iniciar
 ↓
explorar
 ↓
entrar em local
 ↓
encontrar NPC
 ↓
conversar
 ↓
sair
 ↓
continuar explorando
```

**Checkpoint:** temos um jogo jogável de verdade.

---

# Checkpoint 6 — Seed e geração procedural

**15:00–16:00 — 1 hora**

Agora adicionamos o componente que dá identidade ao projeto.

### Pessoa 1 — `WorldGenerator`

**1h**

Melhorar:

```text
WorldGenerator.generate(seed)
```

A seed deve determinar pelo menos parte da configuração:

```text
nome da vila
nomes dos NPCs
profissões
locais
relações
```

Não é necessário gerar tudo proceduralmente.

Uma estratégia excelente para o MVP é:

```text
templates predefinidos
       +
RNG
       ↓
mundo
```

Por exemplo:

```text
possible NPCs:

Aldren
Mira
Tomas
Elian
Garrick
```

A seed escolhe uma combinação.

### Pessoa 2 — Testes de determinismo

**1h**

Executar:

```text
seed = 12345
```

duas vezes.

Comparar:

```text
NPCs
locais
relações
```

Depois:

```text
seed = 98765
```

e verificar se existe variação.

### Entregável

```text
Seed 12345
→ Mundo A

Seed 12345
→ Mundo A novamente

Seed 98765
→ Mundo B
```

**Checkpoint:** o mundo inicial é reproduzível e existe alguma variação entre seeds.

---

# Checkpoint 7 — Conteúdo e coerência

**16:00–17:00 — 1 hora**

Nesse momento eu pararia de adicionar arquitetura.

Agora é hora de fazer o jogo parecer um jogo.

### Pessoa 1 — Conteúdo

**1h**

Criar uma pequena situação narrativa.

Por exemplo:

```text
Existe um desaparecimento recente na vila.
```

Não precisa criar um sistema de quest.

Basta que:

```text
Aldren
```

saiba uma coisa.

```text
Mira
```

saiba outra.

```text
Tomas
```

tenha outra perspectiva.

E:

```text
Prefeito
```

evite determinado assunto.

Isso cria um incentivo para conversar com múltiplas pessoas.

### Pessoa 2 — Prompt engineering

**1h**

Testar perguntas inesperadas:

```text
O que aconteceu aqui?

Quem é o prefeito?

Você gosta dele?

Por que?

O que aconteceu três dias atrás?

Você conhece Tomas?

Você pode me levar até lá?

Mentir é algo que você costuma fazer?

O que você não quer me contar?
```

Verificar se o NPC mantém sua personalidade.

### Entregável

O jogo deve ter uma pequena “teia de informações”.

Exemplo:

```text
Aldren ──desconfia──> Prefeito
   │
   └──conhece──> passagem subterrânea

Mira ──ouve rumores──> desaparecimento

Tomas ──protege──> Prefeito
```

O jogador deve conseguir descobrir essas informações conversando.

---

# Checkpoint 8 — Testes e correções

**17:00–18:00 — 1 hora**

Agora entra uma fase deliberadamente dedicada a quebrar o jogo.

### Pessoa 1 — Testes de gameplay

**1h**

Testar:

```text
go inexistente
talk inexistente
local sem NPC
NPC em outro local
inventory
quit
entrada vazia
comandos inválidos
```

Também testar seeds diferentes.

### Pessoa 2 — Testes de IA

**1h**

Testar:

```text
resposta muito longa
alucinação
contradição
NPC confundindo identidade
NPC revelando conhecimento que não deveria possuir
Ollama desligado
modelo errado
```

A meta não é eliminar todas as alucinações.

A meta é identificar problemas graves do MVP.

### Entregável

Lista de bugs críticos:

```text
[ ] jogo trava
[ ] conversa trava
[ ] movimento quebra estado
[ ] NPC errado responde
[ ] Ollama não responde
[ ] seed não funciona
```

Corrigir tudo que impeça uma partida normal.

---

# Checkpoint 9 — Polimento

**18:00–19:00 — 1 hora**

Essa última hora é deliberadamente aberta.

Não criar novas grandes funcionalidades.

### Pessoa 1

**1h**

Polir:

```text
mensagens
comandos
navegação
formatação
help
início da aventura
```

Adicionar algo como:

```text
Commands:

look
go <location>
talk <npc>
inventory
help
quit
```

### Pessoa 2

**1h**

Polir:

```text
prompts
respostas
personagens
tratamento de erros
configuração do modelo
README
```

Também documentar:

```text
como instalar
como iniciar Ollama
qual modelo utilizar
como executar o projeto
```

### Entregável final

Uma pessoa que nunca viu o código deve conseguir fazer:

```text
1. clonar
2. instalar dependências
3. iniciar Ollama
4. iniciar o jogo
5. jogar uma aventura
```

---

# Divisão final das responsabilidades

A divisão completa fica:

```text
                    PESSOA 1
                       │
       ┌───────────────┼────────────────┐
       ↓               ↓                ↓
     Domain         Game Engine    World Generation
       │               │                │
       └───────────────┼────────────────┘
                       │
                       ▼
                 GameState
```

e:

```text
                    PESSOA 2
                       │
       ┌───────────────┼────────────────┐
       ↓               ↓                ↓
    Ollama          LLMClient      PromptBuilder
       │               │                │
       └───────────────┼────────────────┘
                       │
                       ▼
             NPCInteractionService
```

A integração acontece no meio:

```text
             PESSOA 1              PESSOA 2
                 │                     │
                 │                     │
                 ▼                     ▼
            GameEngine          NPCInteraction
                 │                     │
                 └──────────┬──────────┘
                            │
                            ▼
                        GameState
                            │
                            ▼
                      PromptBuilder
                            │
                            ▼
                        LLMClient
                            │
                            ▼
                          Ollama
```

---

# Ordem de prioridade

Eu estabeleceria uma regra muito rígida para o dia:

```text
P0 — Obrigatório
P1 — Importante
P2 — Desejável
P3 — Não fazer
```

### P0

```text
✓ Maven
✓ Java
✓ Domain
✓ World
✓ Location
✓ NPC
✓ Player
✓ GameState
✓ Terminal
✓ Movimento
✓ Ollama
✓ LLMClient
✓ PromptBuilder
✓ Conversação
```

### P1

```text
✓ Seed
✓ WorldGenerator
✓ Relationships
✓ Inventory básico
✓ Tratamento de erros
✓ Pequena narrativa
```

### P2

Somente se tudo estiver funcionando:

```text
~ NPC memory simples
~ mudanças de relacionamento
~ itens entregues
~ mais locais
~ comandos adicionais
```

### P3 — proibido no primeiro dia

```text
✗ RAG
✗ Vector database
✗ Agents
✗ Multi-agent
✗ Embeddings
✗ Combat system
✗ Quest engine
✗ NPC schedules
✗ Persistence
✗ GUI
✗ Web application
✗ Multiplayer
```

Essa última categoria é importante. Se vocês terminarem o MVP às 16h, **não significa que agora é hora de implementar RAG**. Primeiro façam o núcleo realmente bom.

---

# O estado esperado ao final do dia

Se o cronograma funcionar, às ~19h vocês deverão ter algo próximo de:

```text
AI Text RPG
│
├── World
│   └── Village
│       ├── Locations
│       ├── Structures
│       └── NPCs
│
├── Player
│
├── GameState
│
├── GameEngine
│
├── CommandProcessor
│
├── WorldGenerator
│
├── NPCInteractionService
│
├── PromptBuilder
│
├── LLMClient
│   └── OllamaLLMClient
│
└── TerminalUI
```

E a experiência:

```text
$ java -jar ai-rpg.jar

Enter seed: 183742

════════════════════════════════════
        THE LANDS OF VALDORA
════════════════════════════════════

Você chegou à vila de Valdora.

Você vê:

  [1] Praça
  [2] Taverna
  [3] Ferraria

Pessoas próximas:

  Aldren — Ferreiro
  Mira   — Taverneira
  Tomas  — Guarda

> go ferraria

Você entrou na Ferraria de Aldren.

Aldren está trabalhando em uma espada.

> talk Aldren

> O que você sabe sobre essa cidade?

Aldren:
"Valdora é mais antiga do que as pessoas imaginam..."

> E sobre o prefeito?

Aldren hesita antes de responder.

"Eu não acho que seja prudente falar dele por aqui."

> Por que você tem medo dele?

Aldren:
"Medo? Não confunda cautela com medo, rapaz..."

> exit

> go tavern

...
```

Se vocês chegarem a esse ponto, **o projeto está concluído como MVP**. Não precisa de combate, RAG, inventário complexo, agentes ou uma centena de NPCs para validar a ideia.

O mais importante é que, ao final do dia, vocês tenham demonstrado o ciclo fundamental:

```text
       ┌──────────────────────────────┐
       │                              │
       ▼                              │
  Explorar mundo                     │
       │                              │
       ▼                              │
  Encontrar NPC                      │
       │                              │
       ▼                              │
  Falar naturalmente                │
       │                              │
       ▼                              │
  Game State ──→ Prompt              │
       │             │                │
       │             ▼                │
       │           LLM                │
       │             │                │
       │             ▼                │
       │          Resposta             │
       │             │                │
       └─────────────┴────────────────┘
```

Esse ciclo é o **produto**. Todo o restante é infraestrutura ou expansão.

Uma recomendação adicional: durante o desenvolvimento, mantenham o README que fizemos anteriormente como o documento de **“o que o sistema deve ser”** e este cronograma como o documento de **“como chegaremos lá”**. Se uma nova ideia aparecer durante o dia — e provavelmente aparecerão várias — vocês podem perguntar: **“isso é necessário para completar o checkpoint atual?”** Se a resposta for não, anotem como *future work* e continuem. Isso é o que impedirá o projeto de um dia de se transformar em um projeto de três meses.
