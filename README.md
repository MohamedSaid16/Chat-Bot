#  G11 ChatBot - Phi3 Ollama Integration


G11 ChatBot is an intelligent Arabic/English chatbot powered by the Phi3 model through Ollama. It features a modern UI with conversation history management, real-time typing indicators, and full RTL support for Arabic.


---

##  Features

-  **Powered by Phi3** — Uses Microsoft’s Phi3 model via Ollama for intelligent responses
-  **Bilingual Support** — Responds in Arabic or English based on user input
-  **Conversation History** — Save and manage multiple chat sessions
-  **Responsive Design** — Works on desktop and mobile devices
   **Modern UI** — Beautiful gradient design with smooth animations
-  **Real-time Typing** — Shows typing indicator while generating responses
-  **Session Management** — Create, switch, and delete conversations
-  **Persistent Storage** — Saves conversations in browser `localStorage`

---

## 🛠️ Technology Stack

### Backend
- Java 21
- Spring Boot 3.2.4
- Spring WebFlux (reactive WebClient)
- Ollama API integration

### Frontend
- HTML5 with RTL support  
- CSS3 with gradients and animations  
- Vanilla JavaScript  
- Font Awesome 6 icons  

---

##  Prerequisites

- Java 21+
- Ollama installed and running
- Phi3 model pulled in Ollama

---

##  Installation

### 1) Clone the repository
```bash
git clone https://github.com/yourusername/g11-chatbot.git
cd g11-chatbot
```

### 2) Install Ollama and Phi3
```bash
# Install Ollama (if not installed)
curl -fsSL https://ollama.com/install.sh | sh

# Pull Phi3 model
ollama pull phi3:latest

# Start Ollama server
ollama serve
```

### 3) Configure and run Spring Boot backend
Edit `src/main/resources/application.properties`:

```properties
server.port=8081
spring.application.name=chatbot
```

Run the app:

```bash
# Using Maven
./mvnw spring-boot:run
```

### 4) Open the frontend
Open `indexchatbot.html`:

```
http://localhost:8081
```
Or open it directly from your file system.

---

## Project Structure

```
g11-chatbot/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── example/
│       │           └── demo/
│       │               ├── controller/
│       │               │   └── ChatController.java
│       │               ├── service/
│       │               │   ├── ChatService.java
│       │               │   └── OllamaService.java
│       │               ├── model/
│       │               │   ├── ChatRequest.java
│       │               │   └── ChatResponse.java
│       │               └── ChatbotBackendApplication.java
│       └── resources/
│           └── application.properties
├── frontend/
│   ├── indexchatbot.html
│   ├── chat.js
│   └── style.css
├── pom.xml
└── README.md
```

---

##  Usage

### Starting the application

1. Start Ollama:
   ```bash
   ollama serve
   ```

2. Start Spring Boot:
   ```bash
   ./mvnw spring-boot:run
   ```

3. Open the chat interface:
   - Open `indexchatbot.html`
   - Click the ☰ menu to view history
   - Start chatting!

---

## 🔌 API Endpoints

| Method | Endpoint | Description |
|-------|----------|-------------|
| GET | `/chat` | Test endpoint |
| POST | `/chat` | Send message to chatbot |

### POST Request Example
```json
{
  "message": "What is your name?",
  "sessionId": "optional-session-id"
}
```

### Response Example
```json
{
  "reply": "I'm Phi, an AI developed by Microsoft. How can I help you today?"
}
```

---

##  UI Features

- **Sidebar Menu (☰)** — Hidden by default, appears on click  
- **New Chat Button** — Start fresh conversations  
- **Conversation History** — Saved automatically  
- **Delete Option** — Remove conversations  
- **Typing Indicator** — Shows when bot is thinking  
- **Responsive Design** — Works on all screen sizes  

---

##  Configuration

### Change the model
In `OllamaService.java`:

```java
private final String activeModel = "phi3:latest"; // or "llama2", "mistral", etc.
```

### Change the port
In `application.properties`:

```properties
server.port=8081
```

### Update API URL in frontend
In `indexchatbot.html` and `chat.js`:

```js
const API_URL = 'http://localhost:8081/chat';
```

---

##  Troubleshooting

### “Ollama is not connected”
- Make sure Ollama is running: `ollama serve`
- Check model: `ollama list`
- Verify the API URL in `OllamaService.java`

### “Timeout error”
- Increase `.timeout()` in `OllamaService.java`
- Check system resources
- Try simpler prompts

### Messages not saving
- Clear browser `localStorage`
- Check browser console for errors
- Ensure JavaScript is enabled

---

##  Contributing


1. Fork the project  
2. Create your feature branch  
   ```bash
   git checkout -b feature/AmazingFeature
   ```
3. Commit your changes  
   ```bash
   git commit -m "Add some AmazingFeature"
   ```
4. Push to the branch  
   ```bash
   git push origin feature/AmazingFeature
   ```
5. Open a Pull Request  

---



##  Author

**Djebbour Mohamed Walid**

---



