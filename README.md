# Stock Assistant POC

A **Proof‑of‑Concept** demonstrating how conversational AI can streamline inventory management by automating CRUD operations via natural language.

---

## 1. Overview & Rationale

* **Goal:** Enable users to manage inventory with plain‑English prompts instead of raw API calls.
* **Approach:**

    1. **OpenAPI‑first**: Generate a type‑safe Java client and server stubs from the spec.
    2. **Tools layer**: Wrap generated client APIs (`ProductsApi`, `InventoryApi`, etc.) in Spring `@Tool` classes.
    3. **AI ChatClient**: Expose these tools through a chat endpoint that interprets natural language.

---

## 2. Project Structure

```
stock-assistant/
├── pom.xml             # Parent Maven POM and project descriptor
├── stock-spec/         # OpenAPI spec (openapi.yaml) used to generate server and client code
├── stock-server/       # Hexagonal-architecture Spring Boot app (server stub, customized logic)
└── stock-ai-client/    # AI client module (generated client, tool wrappers, ChatController)
```

* **stock-spec/**: Defines REST endpoints, schemas, parameters; serves as the single source of truth.
* **stock-server/**: Implements controllers, services, adapters, and domain logic following hexagonal architecture.
* **stock-ai-client/**: Contains the AI integration—generated OpenAPI client, `@Tool` wrappers, and chat controller.

---

## 3. AI Chat Endpoint

Users send JSON‑wrapped prompts to the chat endpoint:

```http
POST /api/chat/process
Content-Type: application/json

{ "prompt": "Add a new product called \"Wireless Mouse\" with SKU \"MOU-987\", category \"ELECTRONICS\", unit \"UNIT\", price 29.99." }
```

The **ChatController** relays the prompt to Spring AI’s `ChatClient`:

```java
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatClient chatClient;

    @PostMapping("/process")
    public ResponseEntity<String> process(@RequestBody PromptRequest req) {
        String aiJson = chatClient
                .prompt(req.getPrompt())
                .call()
                .content();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(aiJson);
    }
}
```

**Example: Add Product**

* **Prompt:**

  ```text
  Add a new product called "Wireless Mouse" with SKU "MOU-987", category "ELECTRONICS", unit "UNIT", price 29.99.
  ```
* **Generated API Call:**

  ```http
  POST /api/v1/products
  Content-Type: application/json

  {
    "sku":"MOU-987",
    "name":"Wireless Mouse",
    "category":"ELECTRONICS",
    "unitOfMeasure":"UNIT",
    "price":29.99
  }
  ```
* **AI Response:**

  ```json
  { "message": "Product 'Wireless Mouse' (SKU MOU‑987) added successfully." }
  ```

**Example: Search Products**

* **Prompt:**

  ```text
  Find all products with name "Wireless Mouse" or SKU "MOU-987".
  ```
* **Generated API Call:**

  ```http
  GET /api/v1/products?name=Wireless%20Mouse&sku=MOU-987
  ```
* **AI Response:**

  ```json
  {
    "data":[
      { "uuid":"abc123","sku":"MOU-987","name":"Wireless Mouse","category":"ELECTRONICS","unitOfMeasure":"UNIT","price":29.99 }
    ],
    "total":1,
    "page":1,
    "pageSize":20
  }
  ```

---

## 4. OpenAPI Client & Tools Generation

1. **Generate** Java client from `openapi.yaml` using the OpenAPI Generator Maven plugin (e.g., `resttemplate` library, `interfaceOnly`, `useSpringBoot3`, etc.).
2. **Configure** generated APIs as Spring beans in `ApiClientConfig.java`:

   ```java
   @Configuration
   public class ApiClientConfig {
       @Value("${stock.api.base-url}")
       private String baseUrl;
       @Bean
       public RestTemplate restTemplate() { /* ... */ }
       @Bean
       public ApiClient apiClient(RestTemplate rt) { /* ... */ }
       @Bean public ProductsApi productsApi(ApiClient c) { return new ProductsApi(c); }
       // ... InventoryApi, WarehousesApi
   }
   ```
3. **Wrap** each API in a `@Tool` class:

   ```java
   @Component
   @RequiredArgsConstructor
   public class ProductTools {
       private final ProductsApi productsApi;

       @Tool(name="create_product",description="Create a new product")
       public Product createProduct(
           @ToolParam("Name") String name,
           @ToolParam("SKU")  String sku,
           @ToolParam("Price") float price,
           @ToolParam("Category") ProductRequest.CategoryEnum category,
           @ToolParam("Unit") ProductRequest.UnitOfMeasureEnum unit
       ) {
           ProductRequest req = new ProductRequest()
               .name(name).sku(sku).price(price)
               .category(category).unitOfMeasure(unit);
           return productsApi.createProduct(req);
       }
       // ... other tool methods
   }
   ```

---

## 5. Expose These APIs as AI Tools

Register all `*Tools` beans with `ChatClient` in `AiConfig.java`:

```java
@Configuration
public class AiConfig {
    @Bean
    public ChatClient chatClient(
            ChatClient.Builder builder,
            InventoryTools inventoryTools,
            ProductTools productTools,
            WarehousesTools warehousesTools
    ) {
        return builder
                .defaultTools(inventoryTools, productTools, warehousesTools)
                .build();
    }
}
```

This setup:

1. Discovers all `@Tool` methods.
2. Maps natural‑language prompts to appropriate API calls.
3. Returns JSON responses back to the user.

---

## 6. Unit Testing the AI Component

We leverage **WireMock** and **Testcontainers** (Ollama) to test end‑to‑end AI-driven prompts:

```java
@SpringBootTest
@ActiveProfiles("test")
class ProductChatControllerTest {
    @Autowired
    private ChatClient chatClient;

    @Test
    void getProductDetails_ValidName_ReturnsDetails() {
        String productName = "AlphaPhone";
        WireMock.stubFor(WireMock.get("/products")
                                 .withQueryParam("name", WireMock.equalTo(productName))
                                 .willReturn(aResponse()
                                                     .withStatus(200)
                                                     .withBody("{ \"data\":[{\"name\":\"AlphaPhone\"}] }")
                                                     .withHeader("Content-Type","application/json")
                                 )
        );

        String response = chatClient
                .prompt("Give me the detail of the product '"+productName+"'")
                .call()
                .content();

        assertTrue(response.contains(productName));
    }
}
```

---

## 7. Running the Project

You can start the entire POC **either** with Docker Compose **or** by running modules locally.

### Option A: Docker Compose

A `docker-compose.yml` is provided to spin up all services in one command:

```bash
# From the root of the project:
docker-compose up -d
```

This brings up:

* **postgres** (PostgreSQL database on 5432)
* **ollama** (AI model server on 11434)
* **stock-server** (Spring Boot backend on 8080)
* **stock-ai-client** (AI client on 8060)

Once healthy, the chat endpoint is at:

```
http://localhost:8080/api/chat/process
```

### Option B: Local CLI

If you prefer running modules locally without containers, execute:

```bash
# 1. Build all modules
git clone https://github.com/saberdaagi/stock-assistant
c
mvn clean install

# 2. Start the server
cd stock-server
mvn spring-boot:run

# 3. Start the AI client in a new terminal
cd stock-ai-client
mvn spring-boot:run
```

The chat endpoint remains at:

```
http://localhost:8080/api/chat/process
```

---

This completes the setup. Users can now add, search, update, and delete inventory items using conversational prompts that drive real API calls under the hood.
