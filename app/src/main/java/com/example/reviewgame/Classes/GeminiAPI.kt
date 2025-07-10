package com.example.reviewgame.Classes

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

class GeminiAPI {
    fun main() {
        val client = Client.builder().apiKey("GEMINI_API_KEY").build()


        //val reponse = client.models.generateContent("gemini-2.5-flash", , null)


    }
}