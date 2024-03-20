# AiCore
A library and utilities for working with OpenAI a little easier in Minecraft.
Note that this README was mostly generated with Github CoPilot... good luck :thumbs_up:
## Intended Usage
AiCore is designed to simplify the process of integrating OpenAI into your Minecraft projects. It provides a set of utilities and a library that abstracts the complexities of the OpenAI API, allowing developers to focus on implementing AI functionalities in their Minecraft mods or plugins.

## How to Use the API
To use the AiCore API, you need to create an instance of the `AiCoreService` class with your OpenAI API key and the name of your service or plugin. Here's a basic example:

```java
import dev.arctic.aicore.objects.AiCoreService;

AiCoreService coreService = new AiCoreService("your-openai-api-key", "your-service-name");
```

## Using the ChatService
The `ChatService` is used to manage chat interactions with the OpenAI API. Here's how you can use it:

```java
import dev.arctic.aicore.objects.AiModel;
import dev.arctic.aicore.chat.ChatService;

AiModel model = new AiModel("text-davinci-002", 1, 100, 0.5);
ChatService chatService = coreService.getChatService();

// Get the last response from OpenAI
String response = chatService.getLastResponse("your-prompt", model);

// Get all responses from OpenAI
List<ChatCompletionChoice> responses = chatService.getAllResponses("your-prompt", model);
```

## Using the AssistantService
The `AssistantService` is used to manage assistant interactions with the OpenAI API. Here's how you can use it:

```java
import dev.arctic.aicore.assistants.AssistantService;
import dev.arctic.aicore.assistants.TrackedRun;

AssistantService assistantService = coreService.getAssistantService();

// Create a new run
TrackedRun run = assistantService.createNewRun("assistant-id", "input-text!");

// Send a message to the run
run = assistantService.sendRun(run, "next message!");

// Create a run completion
assistantService.createRunCompletion(run);
```

Please note that the actual methods for `createNewRun`, `sendRun`, and `createRunCompletion` might be different based on your implementation in `AssistantService`. Replace them with the correct method names and parameters.

## Dependencies
AiCore uses the following dependencies:

- PaperMC API
- OpenAI GPT-3 Java Service (https://github.com/TheoKanning/openai-java)
- Project Lombok

This Plugin is required on the server to use the AiCore API.
