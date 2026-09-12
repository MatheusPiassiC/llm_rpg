# AI Text RPG

Um RPG textual experimental baseado em interação livre com personagens controlados por um Large Language Model (LLM) local.

O projeto busca explorar como modelos de linguagem podem ser utilizados como uma camada de interação natural em um jogo estruturado, permitindo que o jogador converse livremente com NPCs em vez de utilizar exclusivamente árvores de diálogo, opções pré-definidas ou comandos rígidos.

O jogo será executado inicialmente inteiramente pelo terminal. O mundo será gerado de forma determinística a partir de uma seed, contendo locais, estruturas e NPCs com características, histórias, conhecimentos e relacionamentos próprios.

A IA será responsável principalmente pela **interpretação e geração de linguagem natural**, enquanto o estado e as regras do jogo permanecerão sob controle do sistema.

---

## 1. Visão geral

A proposta é criar uma pequena aventura textual na qual o jogador possa explorar um mundo e interagir com seus habitantes utilizando linguagem natural.

Em vez de apresentar ao jogador opções como:

```text
1. Perguntar sobre a cidade
2. Perguntar sobre o prefeito
3. Perguntar sobre a guerra
4. Sair
```

o jogador poderá escrever livremente:

```text
> O que você sabe sobre essa cidade?
```

ou:

```text
> Ouvi dizer que o prefeito está escondendo alguma coisa. Você sabe de algo?
```

O NPC deverá responder de acordo com sua personalidade, história, conhecimentos e relacionamento com o jogador.

Por exemplo, um ferreiro pode possuir características como:

```text
Nome: Aldren
Profissão: Ferreiro

Personalidade:
Orgulhoso, reservado e apaixonado por seu trabalho.

História:
Trabalhou durante anos para o exército e forjou armas durante a Guerra do Norte.

Conhecimentos:
- Conhece a história da vila.
- Sabe que existe uma passagem subterrânea.
- Sabe que três soldados desapareceram nessa passagem.
- Suspeita do prefeito.

Relacionamentos:
- Mira: amigável
- Tomas: neutro
- Prefeito: hostil
```

O jogador não precisa conhecer essas informações explicitamente. Elas são utilizadas para construir o contexto fornecido ao LLM.

O objetivo é criar a sensação de que o jogador está conversando com personagens que possuem suas próprias perspectivas, em vez de simplesmente consultando uma base de respostas pré-programadas.

---

# 2. Objetivos

Os principais objetivos do projeto são:

* Explorar a utilização de LLMs em jogos interativos.
* Criar NPCs capazes de responder a linguagem natural de maneira coerente com sua caracterização.
* Experimentar geração procedural determinística de mundos.
* Separar claramente o estado determinístico do jogo da camada generativa de IA.
* Desenvolver uma arquitetura modular que permita substituir componentes sem modificar o restante do sistema.
* Criar uma base que possa posteriormente evoluir para sistemas mais complexos de memória, quests, relacionamentos, ações e simulação de NPCs.

O projeto também possui caráter experimental e educacional. A arquitetura deve ser suficientemente organizada para permitir evolução, mas não deve introduzir complexidade que não seja justificada pelo escopo atual.

---

# 3. Princípio arquitetural fundamental

O princípio mais importante do projeto é:

> **O LLM não é o jogo. O LLM é a interface linguística entre o jogador e o jogo.**

O mundo, as entidades, as regras e o estado da partida pertencem ao sistema do jogo.

O LLM é utilizado para:

* interpretar linguagem natural;
* produzir respostas naturais;
* representar a personalidade dos NPCs;
* utilizar o contexto fornecido pelo jogo para gerar respostas coerentes.

O LLM não deve ser considerado a fonte definitiva da verdade sobre o mundo.

A arquitetura conceitual é:

```text
                    ┌─────────────────┐
                    │   Terminal UI   │
                    └────────┬────────┘
                             │
                             ▼
                    ┌─────────────────┐
                    │   Game Engine   │
                    └────────┬────────┘
                             │
                ┌────────────┴────────────┐
                │                         │
                ▼                         ▼
        ┌───────────────┐         ┌──────────────────┐
        │ Game Domain   │         │ AI / LLM Layer   │
        └───────────────┘         └────────┬─────────┘
                                           │
                                           ▼
                                    ┌──────────────┐
                                    │    Ollama    │
                                    └──────────────┘
```

A camada de domínio não deve possuir dependência direta de Ollama ou de qualquer outro provedor específico de LLM.

---

# 4. Escopo geral

A visão de longo prazo do projeto é um RPG conversacional em que o jogador possa:

* explorar um mundo procedural;
* visitar vilas, cidades e outros locais;
* conversar livremente com NPCs;
* descobrir informações por meio de conversas;
* estabelecer relacionamentos;
* receber ou trocar itens;
* descobrir segredos;
* realizar quests;
* alterar o estado do mundo;
* interagir com estruturas;
* encontrar personagens com objetivos e perspectivas diferentes;
* observar NPCs realizando atividades próprias;
* utilizar memória de longo prazo;
* eventualmente interagir com um mundo persistente e dinâmico.

Entretanto, essas funcionalidades representam a direção futura do projeto e **não fazem parte do MVP**.

O projeto deve evoluir incrementalmente.

---

# 5. MVP

## 5.1 Definição

O MVP consiste em:

> **Criar uma aventura textual determinística na qual o jogador pode explorar uma pequena vila e conversar livremente com NPCs controlados por um LLM local, sendo que cada NPC possui personalidade, história, conhecimentos e relacionamentos predefinidos.**

O jogador deverá conseguir iniciar uma partida, receber uma seed, explorar uma pequena vila e conversar com seus habitantes.

---

## 5.2 Funcionalidades do MVP

O MVP deverá possuir:

### Mundo

* Geração determinística baseada em seed.
* Uma pequena vila.
* Aproximadamente 3–5 locais ou estruturas.
* Aproximadamente 4–5 NPCs.
* Relações básicas entre NPCs.

### Jogador

* Nome.
* Localização atual.
* Inventário básico.
* Capacidade de navegar entre locais.

### NPCs

Cada NPC deverá possuir pelo menos:

* nome;
* profissão;
* personalidade;
* história;
* conhecimentos;
* localização;
* relacionamentos.

### Interação

O jogador deverá poder:

* visualizar o local atual;
* mover-se para outros locais;
* visualizar NPCs presentes;
* iniciar uma conversa;
* enviar mensagens em linguagem natural;
* receber respostas geradas pelo LLM.

### IA

* Ollama executando localmente.
* Um modelo conversacional/instruction-tuned.
* Integração através de uma abstração `LLMClient`.
* Construção dinâmica do prompt a partir do estado do jogo e dos dados do NPC.

### Interface

A interface será exclusivamente via terminal.

---

# 6. Fora do escopo do MVP

As seguintes funcionalidades não deverão ser implementadas inicialmente:

* RAG;
* banco vetorial;
* embeddings;
* agentes autônomos;
* NPC schedules;
* simulação de rotina diária;
* combate;
* sistema complexo de atributos;
* sistema completo de quests;
* economia;
* crafting;
* mapa gráfico;
* interface gráfica;
* persistência em banco de dados;
* multiplayer;
* memória de longo prazo sofisticada;
* múltiplas cidades complexas;
* geração completa do conteúdo narrativo pelo LLM;
* alteração arbitrária do mundo pelo LLM.

Essas funcionalidades podem ser adicionadas posteriormente caso o núcleo do projeto esteja funcionando.

---

# 7. Stack tecnológica

## Linguagem

**Java**

Java será utilizada devido à familiaridade com a linguagem e à possibilidade de estruturar o projeto de maneira modular, além de facilitar experimentações posteriores com diferentes padrões arquiteturais.

## Build e gerenciamento de dependências

**Maven**

Responsável por:

* gerenciamento de dependências;
* compilação;
* execução de testes;
* organização do projeto;
* eventual empacotamento da aplicação.

## LLM

**Ollama**

Ollama será utilizado como runtime local para execução do modelo de linguagem.

A aplicação Java não deverá depender diretamente da implementação interna do Ollama. A comunicação deverá ser encapsulada por `LLMClient` e sua implementação `OllamaLLMClient`.

## Interface

**CLI / Terminal**

A primeira versão não terá interface gráfica.

---

# 8. Arquitetura

A aplicação será dividida conceitualmente em quatro áreas principais:

```text
src/main/java/
└── com/piassi/rpg/
    │
    ├── domain/
    │
    ├── game/
    │
    ├── generation/
    │
    ├── ai/
    │
    └── ui/
```

A responsabilidade de cada módulo é descrita a seguir.

---

# 9. Módulo `domain`

O módulo `domain` contém o modelo do mundo do jogo.

Ele representa as entidades e conceitos fundamentais da aplicação.

Estrutura inicial:

```text
domain/
├── World.java
├── Location.java
├── Structure.java
├── NPC.java
├── Player.java
├── Item.java
└── Relationship.java
```

Essas classes devem ser independentes da interface, do Ollama e da infraestrutura externa.

---

# 10. `World`

Representa o mundo da partida.

Responsabilidades:

* armazenar as localidades existentes;
* permitir acesso às localidades;
* representar a identidade do mundo por meio da seed.

Atributos conceituais:

```text
seed
name
locations
```

Exemplo:

```text
World
├── seed: 183742
├── name: "As Terras de Eldoria"
└── locations:
    ├── Valdora
    ├── Floresta
    └── Ruínas
```

Possíveis métodos:

```text
getLocation(...)
addLocation(...)
```

`World` não deve ser responsável por conversar com NPCs, processar comandos ou chamar o LLM.

---

# 11. `Location`

Representa um local do mundo.

Exemplo:

```text
Location: Valdora

NPCs:
- Aldren
- Mira
- Tomas

Structures:
- Ferraria
- Taverna
- Praça
```

Atributos:

```text
id
name
description
npcs
structures
connectedLocations
```

Possíveis métodos:

```text
addNPC(...)
removeNPC(...)
addStructure(...)
addConnection(...)
getNPC(...)
getStructure(...)
```

A localização deve representar o estado estrutural do lugar, não a lógica de interação do jogador.

---

# 12. `Structure`

Representa estruturas ou pontos de interesse do mundo.

Exemplos:

```text
Ferraria
Taverna
Casa do Prefeito
Praça
Portão da Cidade
```

Atributos mínimos:

```text
id
name
description
```

No MVP, estruturas podem ser essencialmente elementos exploráveis ou descritivos.

A implementação de interações complexas com estruturas fica para versões futuras.

---

# 13. `NPC`

`NPC` é uma das principais entidades do domínio.

Atributos iniciais:

```text
id
name
occupation
personality
backstory
knowledge
location
relationships
inventory
```

Exemplo:

```text
NPC

id:
aldren

name:
Aldren

occupation:
Ferreiro

personality:
Orgulhoso, reservado e apaixonado por armas antigas.

backstory:
Serviu como ferreiro do exército durante a Guerra do Norte.

knowledge:
- Conhece a história da vila.
- Sabe sobre a passagem subterrânea.
- Suspeita do prefeito.

location:
Ferraria

relationships:
- Mira: +40
- Tomas: +10
- Prefeito: -70
```

Inicialmente, `personality`, `backstory` e `knowledge` podem ser representados por `String` e coleções simples.

Não criar classes específicas para cada um desses conceitos sem necessidade.

A abstração deve evoluir conforme surjam comportamentos próprios.

---

# 14. `Relationship`

Representa o relacionamento de uma entidade com outra.

No MVP, uma relação pode ser representada por:

```text
target
affinity
description
```

Exemplo:

```text
target:
Prefeito

affinity:
-70

description:
"Desconfia do prefeito porque acredita que ele esteja
envolvido no desaparecimento dos soldados."
```

O valor de afinidade pode seguir uma escala simples:

```text
-100 → hostilidade extrema
  0  → neutralidade
+100 → amizade extrema
```

A descrição qualitativa pode fornecer contexto adicional ao LLM.

---

# 15. `Player`

Representa o jogador.

Atributos iniciais:

```text
name
currentLocation
inventory
relationships
```

O MVP não necessita de:

```text
health
mana
strength
dexterity
level
experience
```

Esses elementos só devem ser introduzidos quando houver uma mecânica que realmente dependa deles.

---

# 16. `Item`

Representa um item que pode pertencer ao jogador ou a um NPC.

Atributos mínimos:

```text
id
name
description
```

Exemplo:

```text
id:
iron_sword

name:
Espada de ferro

description:
Uma espada simples, porém bem conservada.
```

O inventário do MVP pode ser simplesmente uma coleção de `Item`.

---

# 17. Módulo `game`

O módulo `game` contém a lógica de execução da partida.

Estrutura:

```text
game/
├── Game.java
├── GameState.java
├── GameEngine.java
└── CommandProcessor.java
```

Esse módulo conecta a interface com o domínio e coordena os casos de uso.

---

# 18. `GameState`

Representa o estado atual da partida.

Atributos conceituais:

```text
world
player
currentTime
flags
```

Exemplo:

```text
GameState

world:
Valdora

player:
Piassi

currentLocation:
Ferraria

flags:
- talked_to_aldren = true
- discovered_passage = false
```

A separação entre `World` e `GameState` permite distinguir:

```text
World
→ estrutura do universo

GameState
→ estado atual da simulação
```

Essa distinção se tornará importante em versões futuras.

---

# 19. `Game`

Representa uma partida.

Pode conter:

```text
gameState
running
```

Sua função é representar a instância atual do jogo.

A lógica complexa não deve ser concentrada nessa classe.

---

# 20. `GameEngine`

A `GameEngine` é responsável por orquestrar a execução do jogo.

Ela recebe entradas da interface e coordena os componentes necessários para processá-las.

Fluxo conceitual:

```text
Input
  ↓
GameEngine
  ↓
interpretação
  ↓
execução
  ↓
alteração do GameState
  ↓
Output
```

Possíveis responsabilidades:

* processar comandos;
* iniciar interações;
* realizar movimentações;
* consultar o estado atual;
* delegar conversas para `NPCInteractionService`.

A `GameEngine` não deve implementar diretamente a comunicação HTTP com Ollama.

---

# 21. `CommandProcessor`

Responsável por interpretar comandos estruturais da CLI.

Exemplos:

```text
look
go tavern
talk Aldren
inventory
quit
```

O sistema deve diferenciar comandos de jogo de linguagem natural.

Por exemplo:

```text
> go tavern
```

é um comando estruturado.

Já:

```text
> Você sabe alguma coisa sobre o prefeito?
```

é uma mensagem destinada a um NPC.

O MVP pode utilizar comandos simples e explícitos para navegação e interação.

---

# 22. Módulo `generation`

Responsável pela geração procedural do mundo.

Estrutura:

```text
generation/
└── WorldGenerator.java
```

---

# 23. `WorldGenerator`

Responsável por construir um `World` a partir de uma seed.

Interface conceitual:

```text
generate(seed)
```

Fluxo:

```text
Seed
 ↓
Random
 ↓
WorldGenerator
 ↓
World
 ├── Locations
 ├── NPCs
 ├── Structures
 └── Relationships
```

A mesma seed deverá produzir o mesmo mundo.

Exemplo:

```text
seed = 12345
```

deve produzir sempre a mesma configuração para fins de reprodução e debugging.

A geração pode ser híbrida.

Nem todo conteúdo precisa ser gerado completamente por RNG.

Por exemplo, templates de NPC podem ser predefinidos:

```text
BLACKSMITH
TAVERN_KEEPER
GUARD
MERCHANT
MAYOR
```

A seed pode determinar quais combinações serão utilizadas.

---

# 24. Módulo `ai`

O módulo `ai` contém a integração com modelos de linguagem.

Estrutura:

```text
ai/
├── LLMClient.java
├── OllamaLLMClient.java
├── PromptBuilder.java
└── NPCInteractionService.java
```

---

# 25. `LLMClient`

Interface responsável por abstrair o provedor de LLM.

Conceitualmente:

```text
LLMClient
    │
    └── generate(...)
```

O restante do sistema deverá depender dessa abstração, e não diretamente do Ollama.

Isso permite substituir posteriormente:

```text
Ollama
```

por:

```text
API externa
outro runtime local
outro modelo
mock para testes
```

sem alterar a lógica principal do jogo.

---

# 26. `OllamaLLMClient`

Implementação concreta de `LLMClient`.

Responsabilidades:

* comunicar-se com Ollama;
* enviar prompts;
* receber respostas;
* converter a resposta para o formato esperado pela aplicação;
* tratar erros de comunicação.

Nenhuma regra de jogo deve ser implementada nessa classe.

Ela não deve decidir:

```text
se o NPC gosta do jogador;
se um item pode ser entregue;
se uma porta está aberta;
se uma quest foi concluída.
```

Sua função é apenas interagir com o serviço de LLM.

---

# 27. `PromptBuilder`

Responsável por transformar o estado do jogo em contexto para o LLM.

Entrada conceitual:

```text
NPC
Player
GameState
PlayerMessage
```

Saída:

```text
Prompt
```

Exemplo de contexto:

```text
Você é Aldren, um ferreiro de 57 anos.

PERSONALIDADE:
Orgulhoso, reservado e apaixonado por seu trabalho.

HISTÓRIA:
Serviu como ferreiro do exército durante a Guerra do Norte.

CONHECIMENTOS:
- Conhece a história da vila.
- Sabe que existe uma passagem subterrânea.
- Sabe que três soldados desapareceram nela.

RELACIONAMENTOS:
- É amigável com Mira.
- Desconfia do prefeito.

LOCALIZAÇÃO:
Ferraria de Valdora.

O jogador disse:

"Você sabe alguma coisa sobre o prefeito?"
```

O `PromptBuilder` deve ser a principal responsável pela construção desse contexto, evitando prompts espalhados pela aplicação.

---

# 28. `NPCInteractionService`

Representa o caso de uso:

> Conversar com um NPC.

Responsabilidades:

1. Receber o NPC e o jogador.
2. Consultar o estado atual.
3. Construir o contexto.
4. Gerar o prompt.
5. Chamar o `LLMClient`.
6. Retornar a resposta.

Fluxo:

```text
Player
NPC
GameState
   │
   ▼
NPCInteractionService
   │
   ▼
PromptBuilder
   │
   ▼
LLMClient
   │
   ▼
Ollama
   │
   ▼
Response
```

Essa classe é importante porque impede que `NPC`, `GameEngine` ou `TerminalUI` precisem conhecer detalhes da comunicação com o LLM.

---

# 29. Módulo `ui`

Responsável pela interação com o usuário.

Estrutura:

```text
ui/
└── TerminalUI.java
```

---

# 30. `TerminalUI`

Responsabilidades:

* exibir informações;
* ler entrada do usuário;
* apresentar respostas;
* mostrar erros;
* apresentar o estado atual do jogo.

Ela não deve conter lógica de domínio.

Por exemplo, a `TerminalUI` não deve decidir se:

```text
o jogador pode entrar na ferraria;
o NPC conhece determinada informação;
uma conversa deve alterar um relacionamento.
```

Essas decisões pertencem às camadas apropriadas.

---

# 31. Dependências entre módulos

A direção geral das dependências deverá ser:

```text
UI
 ↓
Game
 ↓
Domain

Game
 ↓
AI
 ↓
Ollama

Generation
 ↓
Domain
```

De maneira simplificada:

```text
             ┌──────────┐
             │    UI    │
             └────┬─────┘
                  ↓
             ┌──────────┐
             │   Game   │
             └───┬──┬───┘
                 │  │
                 ↓  ↓
          ┌───────┐ ┌───────┐
          │Domain │ │  AI   │
          └───────┘ └───┬───┘
                         ↓
                      Ollama

          Generation
               ↓
            Domain
```

O princípio fundamental é:

```text
Domain
  ↓
não conhece
  ↓
AI
UI
Ollama
```

O domínio deve permanecer independente de detalhes externos.

---

# 32. Fluxo de inicialização

Ao iniciar o programa:

```text
Main
 ↓
solicita ou gera seed
 ↓
WorldGenerator
 ↓
World
 ↓
Player
 ↓
GameState
 ↓
GameEngine
 ↓
TerminalUI
 ↓
Game Loop
```

Exemplo:

```text
$ java -jar ai-rpg.jar

Seed: 183742

Você chegou à vila de Valdora.

Locais:
- Praça
- Taverna
- Ferraria

Pessoas próximas:
- Aldren, o ferreiro
- Mira, a taverneira
- Tomas, o guarda

>
```

---

# 33. Fluxo de exploração

Quando o jogador executa:

```text
> go ferraria
```

o fluxo é:

```text
TerminalUI
    ↓
GameEngine
    ↓
CommandProcessor
    ↓
MOVE
    ↓
GameState
    ↓
Player.currentLocation
    ↓
TerminalUI
```

O LLM não precisa participar desse processo.

Comandos determinísticos devem ser tratados diretamente pelo jogo.

---

# 34. Fluxo de conversa

Quando o jogador diz:

```text
> talk Aldren
```

o jogo inicia uma interação com o NPC.

Posteriormente:

```text
> O que você sabe sobre essa cidade?
```

O fluxo é:

```text
TerminalUI
      ↓
GameEngine
      ↓
NPCInteractionService
      ↓
PromptBuilder
      ↓
LLMClient
      ↓
Ollama
      ↓
LLM Response
      ↓
NPCInteractionService
      ↓
GameEngine
      ↓
TerminalUI
```

O jogador recebe algo como:

```text
Aldren:

"Valdora é mais antiga do que parece. A maioria das pessoas
não sabe disso, mas algumas das casas daqui foram construídas
sobre estruturas muito mais antigas..."
```

---

# 35. Contexto enviado ao LLM

O prompt deverá conter apenas as informações relevantes para a interação.

O contexto mínimo de um NPC deve incluir:

```text
Identidade
Personalidade
História
Conhecimentos
Relacionamentos relevantes
Localização
Estado relevante
Mensagem do jogador
```

O prompt deve instruir o modelo a permanecer consistente com essas informações.

Uma regra importante é:

> O NPC não deve afirmar como fato algo que contradiga o conhecimento ou estado fornecido pelo jogo.

Entretanto, o sistema não deve depender exclusivamente dessa instrução para garantir a consistência.

O estado determinístico do jogo continua sendo a fonte de verdade.

---

# 36. RAG

RAG (**Retrieval-Augmented Generation**) não fará parte do MVP.

O motivo é que o problema inicial não exige recuperação semântica de grandes volumes de informação.

O conhecimento dos NPCs será pequeno e poderá ser incluído diretamente no contexto.

A arquitetura futura poderá utilizar:

```text
Player Message
      ↓
Retriever
      ↓
Relevant Knowledge
      ↓
PromptBuilder
      ↓
LLM
```

RAG poderá ser introduzido quando o tamanho do mundo tornar inviável enviar todo o conhecimento relevante diretamente ao modelo.

Nesse cenário:

```text
RAG
→ recupera informações relevantes

GameState
→ representa o estado verdadeiro do mundo

LLM
→ transforma essas informações em linguagem natural
```

Essas três responsabilidades devem permanecer conceitualmente separadas.

---

# 37. O LLM não deve controlar diretamente o estado

No MVP, o LLM deverá produzir principalmente texto.

Por exemplo:

```text
Jogador:
"Você pode me dar uma espada?"

LLM:
"Aldren pega uma espada simples e a coloca sobre o balcão."
```

Essa resposta não deve automaticamente modificar o inventário do jogador.

Em versões futuras, poderá existir um mecanismo estruturado:

```text
Player Message
      ↓
LLM
      ↓
Structured Intent / Action
      ↓
Game Engine
      ↓
Validation
      ↓
GameState
      ↓
LLM
      ↓
Natural Language Response
```

Exemplo:

```json
{
  "action": "GIVE_ITEM",
  "item": "iron_sword"
}
```

A `GameEngine` verificaria se a ação é válida antes de modificar o estado.

Esse mecanismo deve ser implementado somente após o funcionamento do MVP.

---

# 38. Determinismo

A geração do mundo deve ser determinística.

Uma seed deve produzir o mesmo mundo:

```text
seed = 12345
```

deve gerar sempre:

```text
Valdora
├── Ferraria
├── Taverna
├── Praça
├── Aldren
├── Mira
└── Tomas
```

A seed deverá ser utilizada pelo gerador procedural.

Em Java:

```text
Random(seed)
```

pode ser utilizado como base para a geração.

O determinismo é importante para:

* debugging;
* testes;
* reprodução de bugs;
* comparação entre execuções;
* compartilhamento de aventuras.

O comportamento do LLM, entretanto, pode não ser perfeitamente determinístico dependendo das configurações utilizadas. Portanto, o conceito de determinismo do projeto refere-se principalmente à **estrutura do mundo e ao estado inicial gerado pela seed**.

---

# 39. Geração procedural

O mundo deverá utilizar uma abordagem híbrida.

Nem tudo deve ser gerado livremente por um LLM.

Exemplo:

```text
Templates
    ↓
RNG + Seed
    ↓
Composição
    ↓
World
```

Templates podem definir arquétipos:

```text
BLACKSMITH
GUARD
MERCHANT
TAVERN_KEEPER
MAYOR
```

A seed pode determinar:

* quais NPCs aparecem;
* seus nomes;
* profissões;
* relações;
* determinados conhecimentos;
* quais estruturas existem;
* conexões entre locais.

Essa abordagem permite gerar variedade sem depender completamente da IA generativa.

---

# 40. Modelo conceitual do domínio

A primeira versão pode ser representada assim:

```text
                  ┌──────────────┐
                  │    World     │
                  └──────┬───────┘
                         │
                         │ contains
                         ▼
                  ┌──────────────┐
                  │   Location   │
                  └──────┬───────┘
                         │
              ┌──────────┴──────────┐
              │                     │
              ▼                     ▼
        ┌───────────┐         ┌────────────┐
        │    NPC    │         │ Structure  │
        └─────┬─────┘         └────────────┘
              │
       ┌──────┴──────┐
       │             │
       ▼             ▼
Relationship      Inventory
                     │
                     ▼
                   Item


        ┌──────────────┐
        │    Player    │
        └──────┬───────┘
               │
               ├── Location
               └── Inventory
```

---

# 41. Organização completa de pacotes

A estrutura inicial esperada é:

```text
src/
└── main/
    └── java/
        └── com/
            └── piassi/
                └── rpg/
                    │
                    ├── Main.java
                    │
                    ├── domain/
                    │   ├── World.java
                    │   ├── Location.java
                    │   ├── Structure.java
                    │   ├── NPC.java
                    │   ├── Player.java
                    │   ├── Item.java
                    │   └── Relationship.java
                    │
                    ├── game/
                    │   ├── Game.java
                    │   ├── GameState.java
                    │   ├── GameEngine.java
                    │   └── CommandProcessor.java
                    │
                    ├── generation/
                    │   └── WorldGenerator.java
                    │
                    ├── ai/
                    │   ├── LLMClient.java
                    │   ├── OllamaLLMClient.java
                    │   ├── PromptBuilder.java
                    │   └── NPCInteractionService.java
                    │
                    └── ui/
                        └── TerminalUI.java
```

Essa estrutura não deve ser considerada definitiva. Ela é o ponto inicial para o MVP.

---

# 42. Princípios arquiteturais

O projeto deverá seguir alguns princípios básicos.

## Separação de responsabilidades

Cada componente deve possuir uma responsabilidade clara.

Exemplo:

```text
NPC
→ representa um personagem.

PromptBuilder
→ constrói o contexto para o LLM.

OllamaLLMClient
→ comunica-se com Ollama.

NPCInteractionService
→ coordena uma interação.

TerminalUI
→ interage com o usuário.
```

Evitar classes que concentrem todas essas responsabilidades.

---

## Baixo acoplamento

Componentes devem depender de abstrações quando apropriado.

Exemplo:

```text
NPCInteractionService
        ↓
    LLMClient
        ↑
OllamaLLMClient
```

e não:

```text
NPCInteractionService
        ↓
OllamaLLMClient
```

diretamente.

---

## Alta coesão

Cada classe deve agrupar responsabilidades relacionadas.

Uma classe `NPC` não deve cuidar de HTTP.

Uma classe `OllamaLLMClient` não deve modificar o inventário.

Uma classe `TerminalUI` não deve implementar regras do jogo.

---

## Domínio independente

As entidades do domínio não devem depender de:

* Ollama;
* HTTP;
* terminal;
* Maven;
* detalhes de infraestrutura.

Isso facilita testes e futuras substituições.

---

# 43. Casos de uso do MVP

Os casos de uso principais são:

```text
1. Criar aventura
2. Visualizar local atual
3. Mover-se entre locais
4. Encontrar NPCs
5. Iniciar conversa
6. Enviar mensagem ao NPC
7. Receber resposta do NPC
8. Consultar inventário
9. Encerrar aventura
```

Esses casos de uso devem orientar a evolução da arquitetura.

Não criar funcionalidades arquiteturais que não estejam relacionadas a algum caso de uso real.

---

# 44. Testabilidade

A arquitetura deverá favorecer testes unitários das partes determinísticas.

Exemplos:

```text
WorldGenerator
→ mesma seed produz mesma estrutura.

CommandProcessor
→ "go tavern" produz comando MOVE.

Location
→ NPC pode ser adicionado/removido.

Player
→ item pode ser adicionado ao inventário.

Relationship
→ afinidade pode ser alterada.

PromptBuilder
→ informações do NPC aparecem corretamente no prompt.
```

A comunicação com Ollama deverá poder ser substituída por um mock/fake de `LLMClient`.

Por exemplo:

```text
NPCInteractionService
        ↓
    LLMClient
        ↑
   FakeLLMClient
```

Isso permite testar a lógica sem depender de um modelo real.

---

# 45. Estratégia de desenvolvimento

O desenvolvimento deverá ocorrer incrementalmente.

Uma ordem recomendada:

```text
1. Criar projeto Maven
2. Criar entidades do domínio
3. Implementar geração simples de mundo
4. Implementar Player e GameState
5. Criar CLI
6. Implementar movimentação
7. Criar LLMClient
8. Integrar Ollama
9. Criar PromptBuilder
10. Implementar conversação
11. Adicionar seed
12. Refinar NPCs
13. Testar diferentes modelos
14. Polir experiência
```

A integração com IA não deve ser o primeiro componente implementado.

Primeiro deve existir um pequeno jogo determinístico capaz de funcionar sem IA.

Isso cria uma base mais fácil de testar.

---

# 46. Critério de sucesso do MVP

O MVP será considerado funcional quando for possível executar algo equivalente a:

```text
$ java -jar ai-rpg.jar

Seed: 183742

Você está na vila de Valdora.

Locais:
- Praça
- Taverna
- Ferraria

Pessoas:
- Aldren, o ferreiro
- Mira, a taverneira
- Tomas, o guarda

> go ferraria

Você entrou na Ferraria de Aldren.

> talk Aldren

Aldren está trabalhando em uma espada.

> O que você sabe sobre essa cidade?

Aldren:
"Valdora é mais antiga do que parece..."

> Por que você não gosta do prefeito?

Aldren:
"Isso é uma pergunta que pode colocar você em problemas..."

> inventory

Seu inventário está vazio.

> quit
```

O jogador deve sentir que está conversando com personagens, e não simplesmente selecionando respostas de um menu.

---

# 47. O que caracteriza um bom NPC

Um NPC do projeto não deve ser definido apenas por uma frase genérica como:

```text
"Você é um ferreiro."
```

Ele deverá possuir contexto suficiente para produzir comportamento consistente.

Um NPC mínimo deve possuir:

```text
Identidade
    ↓
Personalidade
    ↓
História
    ↓
Conhecimento
    ↓
Relacionamentos
    ↓
Contexto atual
```

Isso permite que duas pessoas respondam à mesma pergunta de maneira diferente.

Por exemplo:

```text
Pergunta:
"O que você acha do prefeito?"
```

Ferreiro:

```text
"Não confio nele."
```

Guarda:

```text
"O prefeito mantém a ordem. Não vejo motivo para reclamar."
```

Taverneira:

```text
"Prefiro não falar sobre política."
```

A informação pode ser a mesma, mas a perspectiva é diferente.

---

# 48. Evolução futura

Depois do MVP, o projeto poderá evoluir para um sistema mais sofisticado.

Uma possível evolução:

```text
MVP
│
├── LLM conversacional
│
├── Mundo procedural
│
└── NPCs estáticos
│
▼
Fase 2
│
├── ações estruturadas
├── inventário funcional
├── relacionamentos dinâmicos
└── quests
│
▼
Fase 3
│
├── memória dos NPCs
├── eventos
├── objetivos
└── estado dinâmico
│
▼
Fase 4
│
├── NPC schedules
├── simulação de rotina
├── economia
└── mundo persistente
│
▼
Fase 5
│
├── RAG
├── conhecimento em larga escala
└── mundos muito maiores
```

---

# 49. Possível arquitetura futura para ações

Uma evolução importante será permitir que o LLM interprete intenções.

Atualmente:

```text
Jogador
   ↓
LLM
   ↓
Texto
```

Futuramente:

```text
Jogador
   ↓
LLM
   ↓
Intent / Action
   ↓
GameEngine
   ↓
Validação
   ↓
GameState
   ↓
LLM
   ↓
Texto
```

Por exemplo:

```text
Jogador:

"Me dê uma espada, eu preciso proteger a vila."
```

O LLM poderia interpretar:

```json
{
  "action": "REQUEST_ITEM",
  "target": "aldren",
  "item": "iron_sword"
}
```

A engine verificaria:

```text
Aldren possui o item?
Aldren pode entregá-lo?
O jogador pode recebê-lo?
```

Somente depois o estado seria alterado.

Essa arquitetura evita que uma alucinação do modelo consiga modificar arbitrariamente o mundo.

---

# 50. Possível arquitetura futura de memória

NPCs futuramente poderão possuir memória.

Exemplo:

```text
Aldren
│
├── Knowledge
├── Relationships
├── Memories
├── Goals
└── Beliefs
```

Após uma conversa:

```text
Player:
"Meu nome é Piassi."

```

o sistema poderia registrar:

```text
Memory:
"Aldren descobriu que o nome do jogador é Piassi."
```

Em uma conversa futura:

```text
Aldren:
"Piassi, você voltou."
```

Esse mecanismo deverá ser implementado separadamente do contexto imediato da conversa.

---

# 51. Possível arquitetura futura de RAG

Se o mundo crescer significativamente:

```text
                  Player Message
                         │
                         ▼
                    Retriever
                         │
                ┌────────┴────────┐
                ▼                 ▼
          World Knowledge      Memories
                │                 │
                └────────┬────────┘
                         ▼
                    PromptBuilder
                         │
                         ▼
                        LLM
```

O RAG não deverá substituir o `GameState`.

O banco de conhecimento responde:

> “Que informações são relevantes?”

O `GameState` responde:

> “Qual é o estado atual do mundo?”

O LLM responde:

> “Como o personagem expressaria isso?”

---

# 52. Limites arquiteturais

Durante o desenvolvimento, evitar:

* colocar lógica de jogo dentro do `PromptBuilder`;
* colocar lógica de jogo dentro do `OllamaLLMClient`;
* fazer `NPC` chamar diretamente o LLM;
* fazer `TerminalUI` modificar entidades diretamente;
* fazer o LLM ser a fonte de verdade do estado;
* criar abstrações sem necessidade;
* introduzir RAG antes de existir um problema real de recuperação;
* criar sistemas de agentes antes de existir uma necessidade concreta;
* transformar cada atributo simples em uma classe independente.

A arquitetura deve acompanhar a complexidade real do problema.

---

# 53. Regra para novas funcionalidades

Antes de adicionar uma nova classe, responder:

1. Qual responsabilidade essa classe possui?
2. Essa responsabilidade pertence a alguma classe existente?
3. A nova classe possui estado ou comportamento próprio?
4. Qual módulo deve ser responsável por ela?
5. Quais componentes precisam conhecê-la?
6. Ela introduz uma dependência desnecessária?
7. Existe um caso de uso real que justifique sua existência?

Antes de adicionar uma tecnologia, responder:

> **Qual problema concreto essa tecnologia resolve?**

Por exemplo:

```text
RAG
→ necessário apenas quando o conhecimento se tornar grande.

Banco de dados
→ necessário quando persistência se tornar necessária.

Framework web
→ necessário apenas se surgir uma interface remota.

Agentes
→ necessários apenas quando NPCs precisarem executar comportamentos autônomos complexos.
```

---

# 54. Filosofia do projeto

O projeto deve seguir uma abordagem incremental.

A prioridade inicial é construir um núcleo pequeno que funcione bem:

```text
Mundo
+
NPCs
+
Exploração
+
Conversação
+
LLM
```

A complexidade deve surgir a partir das necessidades do jogo, e não ser adicionada antecipadamente.

A arquitetura inicial deve ser suficientemente organizada para permitir evolução, mas suficientemente simples para que todo o sistema possa ser compreendido por uma única pessoa.

O objetivo do MVP não é construir o RPG definitivo.

O objetivo é validar a seguinte hipótese:

> **É possível criar uma experiência de RPG textual interessante quando o jogador pode utilizar linguagem natural para interagir livremente com NPCs que possuem contexto, personalidade e conhecimento próprios?**

Se essa hipótese for validada, novas mecânicas poderão ser construídas sobre essa fundação.

---

# 55. Resumo arquitetural

```text
                         ┌────────────────────┐
                         │    Terminal UI     │
                         └─────────┬──────────┘
                                   │
                                   ▼
                         ┌────────────────────┐
                         │    Game Engine     │
                         └───────┬─────┬──────┘
                                 │     │
                     ┌───────────┘     └───────────┐
                     ▼                             ▼
              ┌─────────────┐              ┌───────────────┐
              │ Game State  │              │ NPC Interaction│
              └──────┬──────┘              │    Service     │
                     │                     └───────┬────────┘
                     ▼                             │
              ┌─────────────┐                      ▼
              │   Domain    │              ┌───────────────┐
              │             │              │ PromptBuilder │
              │ World       │              └───────┬───────┘
              │ Location    │                      │
              │ NPC         │                      ▼
              │ Player      │              ┌───────────────┐
              │ Item        │              │   LLMClient   │
              │ Relationship│              └───────┬───────┘
              └─────────────┘                      │
                                                   ▼
                                            ┌───────────────┐
                                            │    Ollama     │
                                            └───────────────┘

              ┌─────────────────┐
              │ World Generator │
              └────────┬────────┘
                       │
                       ▼
                    Domain
```

O sistema pode ser resumido em uma frase:

> **O domínio representa o mundo, a Game Engine controla a partida, a camada de IA traduz o estado do mundo em contexto para o LLM, e a interface permite ao jogador interagir com tudo isso por linguagem natural.**
