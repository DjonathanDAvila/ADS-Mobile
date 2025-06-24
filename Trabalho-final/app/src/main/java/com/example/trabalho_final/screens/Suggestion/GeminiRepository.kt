package com.example.trabalho_final.screens.Suggestion

import com.example.trabalho_final.entity.Travel
import com.example.trabalho_final.entity.enums.toDisplayName
import com.google.ai.client.generativeai.GenerativeModel
import java.time.temporal.ChronoUnit

class GeminiRepository {

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = "AIzaSyA1VLozzFeyBZkpJrHZG2b1GPhZE1a7vig"
    )

    suspend fun generateItinerary(travel: Travel): String {
        val days = ChronoUnit.DAYS.between(travel.startDate, travel.endDate).toInt() + 1

        val prompt = """
            Crie um roteiro de $days dia${if (days > 1) "s" else ""} para uma viagem de ${travel.type.toDisplayName().lowercase()} para ${travel.destination}.
            O roteiro deve incluir:
            - Sugestões de passeios diários
            - Restaurantes ou culinária típica
            - Dicas úteis para o viajante

            Use uma linguagem informal e amigável, como se falasse com um amigo.
        """.trimIndent()

        return try {
            val response = generativeModel.generateContent(prompt)
            response.text ?: "Nenhuma sugestão gerada."
        } catch (e: Exception) {
            "Erro ao gerar roteiro: ${e.localizedMessage}"
        }
    }
}
