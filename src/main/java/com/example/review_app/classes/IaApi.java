package com.example.review_app.classes;


import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import io.github.cdimascio.dotenv.Dotenv;

public class IaApi {
    //This classe will connect with gemini api and send the question for ia generate a json string
    //And convert that string in a json file.

    String apiKey; // If you clone the project put you api key of gemini here.
    Client client; // And here you put Client.builder().apiKey(apiKey).build();
    //And delete the constructor

    public IaApi() {
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("GEMINI_API_KEY");

        //.trim removes spaces at the beginning and end
        if(apiKey == null || apiKey.trim().isEmpty()){
            throw new IllegalStateException("Error returning API KEY");
        }

        client = Client.builder().apiKey(apiKey).build();
    }

    public String getAnswer(String subject){
        String askQ = "Your response MUST be ONLY the JSON object that represents a single trivia question and its answers.\n" +
                "It must follow the exact format of the example below.\n" +
                "Do not include any text, explanation, introductory phrases, or markdown formatting like ```json.\n" +
                "The raw text of your response must start with '{' and end with '}'.\n" +
                "\n" +
                "Example format:\n" +
                "{\n" +
                "  \"question\": \"What is the capital of France?\",\n" +
                "  \"incorrect_answers\": [\n" +
                "    \"London\",\n" +
                "    \"Berlin\",\n" +
                "    \"Madrid\",\n" +
                "    \"Rome\"\n" +
                "  ],\n" +
                "  \"correct_answer\": \"Paris\"\n" +
                "}\n" +
                "\n ALWAYS RETURN 5 ANSWERS 1 CORRECT 4 FALSE" +
                "Now, generate a new, different trivia question about: " + subject;

        GenerateContentResponse response = client.models.generateContent("gemini-2.5-flash", askQ, null);

        return response.text();
    }


}
