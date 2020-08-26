package no.nav.medlemskap.client

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import no.nav.medlemskap.domene.MedlemskapRequest
import no.nav.medlemskap.domene.MedlemskapResponse
import java.util.*

class MedlemskapClient(
    private val baseUrl: String,
    private val azureAdClient: AzureAdClient
) {

    suspend fun hentMedlemskapForRequest(medlemskapRequest: MedlemskapRequest): MedlemskapResponse {
        val token = azureAdClient.hentToken()
        val randomUUID = UUID.randomUUID()
        println("Kaller medlemskap-oppslag med Nav-Call-Id: $randomUUID")
        return httpClient.post {
            url("$baseUrl/")
            header(HttpHeaders.Authorization, "Bearer ${token.token}")
            header("Nav-Call-Id", randomUUID)
            contentType(ContentType.Application.Json)
            body = medlemskapRequest
        }
    }

    suspend fun hentMedlemskapMedGittToken(medlemskapRequest: MedlemskapRequest, token: String): HttpResponse {
        val randomUUID = UUID.randomUUID()
        println("Kaller medlemskap-oppslag med Nav-Call-Id: $randomUUID")
        return httpClient.post {
            url("$baseUrl/")
            header(HttpHeaders.Authorization, "Bearer $token")
            header("Nav-Call-Id", randomUUID)
            contentType(ContentType.Application.Json)
            body = medlemskapRequest
        }
    }
}
