package dev.arctic.aicore.assistants;

import com.theokanning.openai.OpenAiResponse;
import com.theokanning.openai.assistants.Assistant;
import com.theokanning.openai.messages.Message;
import com.theokanning.openai.messages.MessageRequest;
import com.theokanning.openai.runs.Run;
import com.theokanning.openai.runs.RunCreateRequest;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.threads.Thread;
import com.theokanning.openai.threads.ThreadRequest;
import lombok.NonNull;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import static dev.arctic.aicore.AiCore.plugin;

/**
 * The AssistantService class is responsible for managing the interactions with the OpenAI service.
 * It maintains a collection of TrackedRun instances, which can be queried statically.
 * This class should be instantiated as part of the AiCoreService.
 */
public class AssistantService {

    private OpenAiService service;
    /**
     * A collection of TrackedRun instances, keyed by their UUID. These can be queried statically.
     * See TrackedRun for more information.
     */
    public HashMap<UUID, TrackedRun> trackedRuns = new HashMap<>();

    /**
     * Constructs a new AssistantService with the given OpenAiService.
     * This was instantiated by the AiCoreService.
     *
     * @param service The OpenAiService to use for interactions with the OpenAI API.
     */
    public AssistantService(OpenAiService service) {
        this.service = service;
    }

    /**
     * Creates a new run with the given assistant ID and input, and tracks it.
     * To retrieve an output, use the createRunCompletion method.
     *
     * @param AssistantID The ID of the assistant to use for the run.
     * @param input       The input to provide to the assistant.
     * @return The newly created and tracked run.
     */
    public TrackedRun createNewRun(@NonNull String AssistantID, String input) {
        Assistant assistant = service.retrieveAssistant(AssistantID);

        Thread thread = service.createThread(ThreadRequest.builder().build());

        Message message = service.createMessage(thread.getId(), MessageRequest.builder().content(input).build());

        RunCreateRequest runCreateRequest = RunCreateRequest.builder()
                .assistantId(assistant.getId()).build();

        Run run = service.createRun(thread.getId(), runCreateRequest);

        TrackedRun trackedRun = new TrackedRun().createTrackedRun(assistant.getId(), thread.getId(), run.getId());
        trackedRuns.put(trackedRun.getUuid(), trackedRun);

        return trackedRun;
    }

    /**
     * Sends a new message to the run associated with the given UUID and updates the corresponding TrackedRun object in the trackedRuns HashMap.
     * If no TrackedRun object is found with the given UUID, an IllegalArgumentException is thrown.
     *
     * @param trackedRunUUID The UUID of the tracked run to send a message to.
     * @param input The input to provide to the assistant.
     * @return The updated TrackedRun object.
     * @throws IllegalArgumentException If no TrackedRun object is found with the given UUID.
     */
    public TrackedRun sendRun(UUID trackedRunUUID, String input) {
        TrackedRun trackedRun = trackedRuns.get(trackedRunUUID);
        if (trackedRun == null) {
            throw new IllegalArgumentException("No tracked run found with the provided UUID.");
        }

        Message message = service.createMessage(trackedRun.getThreadID(), MessageRequest.builder().content(input).build());
        trackedRun.setOutput(service.listMessages(trackedRun.getThreadID()));
        trackedRun.setLastUpdated(new Date());

        trackedRuns.put(trackedRunUUID, trackedRun);

        return trackedRun;
    }
    /**
     * Sends a new message to the run associated with the given TrackedRun object and updates the object in the trackedRuns HashMap.
     *
     * @param run The TrackedRun object to send a message to.
     * @param input The input to provide to the assistant.
     * @return The updated TrackedRun object.
     */
    public TrackedRun sendRun(TrackedRun run, String input) {
        Message message = service.createMessage(run.getThreadID(), MessageRequest.builder().content(input).build());
        run.setOutput(service.listMessages(run.getThreadID()));
        run.setLastUpdated(new Date());

        trackedRuns.put(run.getUuid(), run);

        return run;
    }

    /**
     * Creates a completion for the given tracked run. This method is asynchronous and will continue to poll
     * the OpenAI API until the run is completed, failed, or requires action.
     * <p>
     * This will need to be called directly after creating a new run.
     *
     * @param trackedRun The tracked run to create a completion for.
     */

    public void createRunCompletion(@NonNull TrackedRun trackedRun) {
        new BukkitRunnable() {
            public void run() {

                Run retrievedRun = service.retrieveRun(trackedRun.getThreadID(), trackedRun.getRunID());
                if (!"completed".equals(retrievedRun.getStatus()) && !"failed".equals(retrievedRun.getStatus()) && !"requires_action".equals(retrievedRun.getStatus())) {
                    new BukkitRunnable() {
                        public void run() {
                            createRunCompletion(trackedRun);
                        }
                    }.runTaskLaterAsynchronously(plugin, 20);
                    return;
                }
                trackedRun.setOutput(service.listMessages(trackedRun.getThreadID()));
                trackedRun.setLastUpdated(new Date());
            }
        }.runTaskAsynchronously(plugin);
    }
}
