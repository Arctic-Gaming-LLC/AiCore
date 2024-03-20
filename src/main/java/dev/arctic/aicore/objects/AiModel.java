package dev.arctic.aicore.objects;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents an AI model configuration for OpenAI API requests.
 */
@Data
@AllArgsConstructor
public class AiModel {
    /**
     * The model to use for the request. This is a string representing the model ID.
     */
    public String model;

    /**
     * The number of completions to generate for the request. This is an integer.
     */
    public int n;

    /**
     * The maximum number of tokens to generate for the request. This is an integer.
     */
    public int maxTokens;

    /**
     * The temperature to use for the request. This is a double representing the randomness of the output.
     */
    public double temperature;
}