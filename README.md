# CryptoTracker 📱

Um aplicativo Android moderno para rastrear preços de criptomoedas em tempo real, construído com Jetpack Compose e arquitetura moderna.

## 🚀 Funcionalidades

- Lista de criptomoedas com preços atualizados
- Gráficos detalhados de preços
- Suporte a temas claro/escuro
- Layout adaptativo para tablets
- Atualização em tempo real dos preços

## 🛠️ Tecnologias Utilizadas

- **Kotlin** - Linguagem principal
- **Jetpack Compose** - UI moderna e declarativa
- **Material 3** - Design system
- **Ktor** - Cliente HTTP
- **Koin** - Injeção de dependência
- **Coroutines** - Programação assíncrona
- **Flow** - Streams de dados reativos

## 📱 Screenshots

[Screenshots do aplicativo serão adicionados aqui]

## 🏗️ Arquitetura

O projeto segue a Clean Architecture com MVVM:

```
app/
├── core/                  # Componentes centrais
│   ├── data/             # Implementações de dados
│   └── domain/           # Regras de negócio
├── crypto/               # Feature de criptomoedas
│   ├── data/             # Camada de dados
│   ├── domain/           # Lógica de negócio
│   └── presentation/     # UI e ViewModels
└── ui/                   # Componentes de UI compartilhados
```

## 📲 Componentes Principais

### CoinListScreen

Lista principal de criptomoedas:

```kotlin
@Composable
fun CoinListScreen(
    state: CoinListState,
    onAction: (CoinListAction) -> Unit
) {
    LazyColumn {
        items(state.coins) { coin ->
            CoinListItem(coin = coin)
        }
    }
}
```

### LineChart

Gráfico de preços interativo:

```kotlin
@Composable
fun LineChart(
    dataPoints: List<DataPoint>,
    modifier: Modifier = Modifier
) {
    // Configuração do gráfico
    Canvas(modifier = modifier) {
        // Desenho do gráfico
    }
}
```

## 🔄 Fluxo de Dados

1. **ViewModel** solicita dados via `CoinDataSource`
2. **DataSource** faz chamada à API usando Ktor
3. Dados são mapeados para modelos de domínio
4. UI é atualizada via `StateFlow`

Exemplo:
```kotlin
class CoinListViewModel(
    private val dataSource: CoinDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(CoinListState())
    val state = _state.asStateFlow()

    fun loadCoins() {
        viewModelScope.launch {
            dataSource.getCoins()
                .onSuccess { coins ->
                    _state.update { it.copy(coins = coins) }
                }
        }
    }
}
```

## 🌐 Networking

A comunicação com a API é feita usando Ktor:

```kotlin
class RemoteCoinDataSource(
    private val client: HttpClient
) : CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>> {
        return safeCall {
            client.get("/v1/coins")
        }
    }
}
```

## 🎨 Theming

O app suporta temas claro e escuro usando Material 3:

```kotlin
@Composable
fun CryptoTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}
```

## 🚦 Começando

1. Clone o repositório
```bash
git clone https://github.com/seu-usuario/CryptoTracker.git
```

2. Abra o projeto no Android Studio

3. Configure a API Key no `local.properties`:
```properties
API_KEY=sua_chave_aqui
```

4. Execute o app

## 📱 Requisitos

- Android Studio Arctic Fox ou superior
- Android 6.0 (API 23) ou superior
- Kotlin 1.8.0 ou superior

## 🤝 Contribuindo

1. Fork o projeto
2. Crie sua feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está licenciado sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

## ✨ Reconhecimentos

- [CoinCap API](https://docs.coincap.io/) - API de dados de criptomoedas
- [Material 3](https://m3.material.io/) - Design system
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Modern Android UI toolkit
