# Stock Assistant POC

A **POC** showing how to bring **Spring AI** into a traditional Spring Boot application, using an **API-first** design and a **clean, hexagonal architecture**.

The idea is to explore how conversational interfaces can simplify existing workflows like managing stock without changing the core of a working system.

---

## 1. Overview & Rationale

This project started as a regular Spring Boot inventory system. Then we asked: *what if you could use natural language to interact with it no dropdowns, no complex UI, just plain English?*

We added **Spring AI v1.0.0-M6** to the mix and exposed the business logic through **OpenAPI-generated clients**. That made it simple to wrap existing operations as `@Tool`s for the AI to call.

The goal isn’t to reinvent the app it’s to test how AI fits in. And it turns out, even small things like replacing a search form with a prompt (e.g. *"Find all products in ELECTRONICS under 30 euros"*) make the whole experience feel lighter.

By keeping the architecture clean and modular, we can evolve this pattern further or reuse it elsewhere.

---

## 2. Project Structure

```
stock-assistant/
├── pom.xml             # Parent Maven POM and project descriptor
├── stock-spec/         # OpenAPI spec (openapi.yaml) used to generate server and client code
├── stock-server/       # Hexagonal-architecture Spring Boot app (server stub, customized logic)
└── stock-ai-client/    # AI client module (generated client, tool wrappers, ChatController)
```

* **stock-spec/**: Defines REST endpoints, schemas, and parameters the source of truth.
* **stock-server/**: Implements business logic, adapters, and domain entities using hexagonal architecture.
* **stock-ai-client/**: Contains generated client code, `@Tool` wrappers, and the AI chat interface.

---

## 3. AI Chat Endpoint

Users interact with the backend using plain-language prompts sent to the chat endpoint:

```http
POST /api/chat/process
Content-Type: application/json

{ "prompt": "Add a new product called \"Wireless Mouse\" with SKU \"MOU-987\", category \"ELECTRONICS\", unit \"UNIT\", price 29.99." }
```

### 💬 Example Prompts

#### ➕ Add Product

*Prompt:*

```text
Add a product named "Wireless Mouse" with SKU "MOU-987", category ELECTRONICS, unit UNIT, price 29.99.
```

*Generated API Call:*

```http
POST /api/v1/products
Content-Type: application/json
{
  "sku": "MOU-987",
  "name": "Wireless Mouse",
  "category": "ELECTRONICS",
  "unitOfMeasure": "UNIT",
  "price": 29.99
}
```

#### 🔍 Search Product

*Prompt:*

```text
Find product with name "Wireless Mouse".
```

*Generated API Call:*

```http
GET /api/v1/products?name=Wireless%20Mouse
```

#### 📝 Update Product

*Prompt:*

```text
Update the price of the product with UUID '550e8400-e29b-41d4-a716-446655440000' to 34.99.
```

*Generated API Call (example):*

```http
PUT /api/v1/products/{uuid}
Content-Type: application/json
{
  "price": 34.99
}
```

#### ❌ Delete Product

*Prompt:*

```text
Delete the product with UUID "550e8400-e29b-41d4-a716-446655440000".
```

*Generated API Call:*

```http
DELETE /api/v1/products/{uuid}
```

---

## 4. OpenAPI Client & Tools Generation

1. **Generate** client interfaces using the OpenAPI Generator Maven plugin.
2. **Configure** clients as Spring Beans:

```java
@Configuration
public class ApiClientConfig {
    @Value("${stock.api.base-url}")
    private String baseUrl;

    @Bean
    public RestTemplate restTemplate() { return new RestTemplate(); }

    @Bean
    public ApiClient apiClient(RestTemplate rt) {
        return new ApiClient(rt).setBasePath(baseUrl);
    }

    @Bean public ProductsApi productsApi(ApiClient c) { return new ProductsApi(c); }
    // ... InventoryApi, WarehousesApi
}
```

3. **Wrap** API calls using `@Tool`:

```java
@Component
@RequiredArgsConstructor
public class ProductTools {
    private final ProductsApi productsApi;

    @Tool(name = "create_product", description = "Create a new product")
    public Product createProduct(/*...*/) {
        ProductRequest req = new ProductRequest()
            .name(name).sku(sku).price(price)
            .category(category).unitOfMeasure(unit);
        return productsApi.createProduct(req);
    }
}
```

---

## 5. AI Tool Registration

Register `@Tool` beans with Spring AI’s `ChatClient`:

```java
@Configuration
public class AiConfig {
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, ProductTools pt, InventoryTools it, WarehousesTools wt) {
        return builder.defaultTools(pt, it, wt).build();
    }
}
```

---

## 6. Unit Testing

```java
@SpringBootTest
@ActiveProfiles("test")
class ProductChatControllerTest {
    @Autowired
    private ChatClient chatClient;

    @Test
    void getProductDetails_ValidName_ReturnsDetails() {
        // WireMock stub and test setup
    }
}
```

---

## 7. Running the Project

### ✅ Option A: Docker Compose

Use `docker-compose.yml` to start all services and populate **initial test data** automatically:

```bash
docker-compose up -d
```

Services:

* `postgres` (DB)
* `ollama` (AI)
* `stock-server` (Spring Boot app)
* `stock-ai-client` (AI client)

Endpoint:

```
http://localhost:8080/api/chat/process
```

---

### 🧑‍💻 Option B: Run Locally

```bash
# 1. Clone & build
mvn clean install

# 2. Start the server
cd stock-server
mvn spring-boot:run

# 3. Start the AI client
cd ../stock-ai-client
mvn spring-boot:run
```

Chat endpoint:

```
http://localhost:8080/api/chat/process
```

---

The application is now up and running. You can manage inventory items through conversational prompts—or better, use this foundation to extend your own AI-enhanced features on top of a familiar business flow.
