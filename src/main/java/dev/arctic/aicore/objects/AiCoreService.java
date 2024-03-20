package dev.arctic.aicore.objects;

import com.theokanning.openai.service.OpenAiService;
import dev.arctic.aicore.AiCore;
import dev.arctic.aicore.assistants.AssistantService;
import dev.arctic.aicore.chat.ChatService;
import lombok.Getter;

import java.time.Duration;

/**
 * Provides services for interacting with OpenAI and managing chat and assistant functionalities.
 */
public class AiCoreService {
    /**
     * The OpenAI service used for making API requests.
     */
    private OpenAiService service;

    /**
     * The chat service used for managing chat functionalities.
     */
    @Getter
    private ChatService chatService;

    /**
     * The assistant service used for managing assistant functionalities.
     */
    @Getter
    private AssistantService assistantService;
    /**
     * The name of the service or plugin
     */
    @Getter
    public String name;

    /**
     * Constructs a new AiCoreService instance. Intended 1 key per plugin
     *
     * @param apiKey The API key used for making OpenAI API requests.
     */
    public AiCoreService(String apiKey, String name) {
        // Initialize the services with the given API key
        this.service = new OpenAiService(apiKey, Duration.ofSeconds(30));
        this.chatService = new ChatService(service);
        this.assistantService = new AssistantService(service);

        AiCore.openServices.add(this);
    }

    public void removeService(){
        AiCore.openServices.remove(this);
    }
}
