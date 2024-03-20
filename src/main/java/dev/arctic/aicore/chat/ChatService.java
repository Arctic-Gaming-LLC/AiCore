package dev.arctic.aicore.chat;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import dev.arctic.aicore.objects.AiModel;

import java.util.List;

/**
 * Provides chat services by interfacing with the OpenAI API to generate responses to prompts.
 */
public class ChatService {

    private OpenAiService service;

    /**
     * Constructs a new ChatService instance. Created by instantiating an AiCoreService instance.
     *
     * @param service The OpenAiService instance used for making API requests.
     */
    public ChatService(OpenAiService service) {
        this.service = service;
    }

    /**
     * Retrieves the last response from OpenAI based on the given prompt and AI model configuration.
     *
     * @param prompt The input prompt for the chat completion.
     * @param model  The AI model configuration to use for the request.
     * @return The last chat completion response from OpenAI. Returns "No response from OpenAI" if there are no responses.
     */
    public String getLastResponse(String prompt, AiModel model) {
        ChatMessage message = new ChatMessage("user", prompt);

        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model(model.getModel())
                .n(model.getN())
                .maxTokens(model.getMaxTokens())
                .temperature(model.getTemperature())
                .messages(List.of(message))
                .build();

        List<ChatCompletionChoice> results = service.createChatCompletion(request).getChoices();
        if (!results.isEmpty()) {
            String response = results.get(0).getMessage().getContent();
            return response;
        }
        shutdownService();
        return "No response from OpenAI";
    }

    /**
     * Retrieves all responses from OpenAI based on the given prompt and AI model configuration.
     *
     * @param prompt The input prompt for the chat completion.
     * @param model  The AI model configuration to use for the request.
     * @return A list of ChatCompletionChoices, each representing a response from OpenAI.
     */
    public List<ChatCompletionChoice> getAllResponses(String prompt, AiModel model) {
        ChatMessage message = new ChatMessage("user", prompt);

        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model(model.getModel())
                .n(model.getN())
                .maxTokens(model.getMaxTokens())
                .temperature(model.getTemperature())
                .messages(List.of(message))
                .build();

        return service.createChatCompletion(request).getChoices();
    }

    /**
     * Shuts down the executor service used by the OpenAiService, effectively stopping any ongoing requests.
     */
    public void shutdownService() {
        service.shutdownExecutor();
    }
}
