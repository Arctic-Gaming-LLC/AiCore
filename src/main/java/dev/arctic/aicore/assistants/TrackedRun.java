package dev.arctic.aicore.assistants;

import com.theokanning.openai.OpenAiResponse;
import com.theokanning.openai.messages.Message;
import com.theokanning.openai.messages.MessageContent;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * The TrackedRun class represents a tracked run with a unique identifier, assistant ID, thread ID, and run ID.
 * It is used to keep track of the runs created by the AssistantService.
 * It also stores the output of the run.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackedRun {
    /**
     * The unique identifier for the tracked run. It is generated when a new tracked run is created.
     */
    private UUID uuid;

    /**
     * The ID of the assistant associated with the tracked run. It is provided when a new tracked run is created.
     */
    private String assistantID;

    /**
     * The ID of the thread associated with the tracked run. It is provided when a new tracked run is created.
     */
    private String threadID;

    /**
     * The ID of the run associated with the tracked run. It is provided when a new tracked run is created.
     */
    private String runID;

    /**
     * The output of the run. It is set after the run is completed.
     * -- SETTER --
     *  Sets the output of the run.
     *
     * @param content The output of the run.

     */
    @Setter @Getter
    private OpenAiResponse<Message> output;
    private Date lastUpdated;

    /**
     * Creates a new tracked run with a random UUID and the given assistant ID, thread ID, and run ID.
     * The new tracked run is then returned.
     *
     * @param assistantID The ID of the assistant.
     * @param threadID    The ID of the thread.
     * @param runID       The ID of the run.
     * @return The newly created tracked run.
     */
    public TrackedRun createTrackedRun(String assistantID, String threadID, String runID) {
        this.uuid = UUID.randomUUID();
        this.assistantID = assistantID;
        this.threadID = threadID;
        this.runID = runID;
        this.lastUpdated = new Date();

        return this;
    }
    /**
     * Retrieves the last message from the assistant in the tracked run.
     * It filters the messages to only include those from the assistant, then retrieves the first one.
     * If there is no message from the assistant, it returns null.
     *
     * @return The content of the last message from the assistant, or null if there is no such message.
     */
    public String getLastMessage() {
        Message latestMessage = output.getData().stream()
                .filter(message -> "assistant".equals(message.getRole()))
                .findFirst()
                .orElse(null);

        String lastMessageContent;

        if (latestMessage != null) {
            List<MessageContent> content = latestMessage.getContent();
            lastMessageContent = content.get(content.size() - 1).getText().getValue();
        } else {
            lastMessageContent = null;
        }
        return lastMessageContent;
    }
}