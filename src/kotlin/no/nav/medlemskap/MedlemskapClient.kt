package no.nav.medlemskap

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import mu.KotlinLogging
import java.time.LocalDate
import java.util.*

private val secureLogger = KotlinLogging.logger("tjenestekall")

class MedlemskapClient(
    private val baseUrl: String,
    private val azureAdClient: AzureAdClient
) {

    suspend fun hentMedlemskapForRequest(medlemskapRequest: MedlemskapRequest): MedlemskapResponse {
        val token = azureAdClient.hentToken()

        return httpClient.post {
            url("$baseUrl/")
            header(HttpHeaders.Authorization, "Bearer ${token.token}")
            header("Nav-Call-Id", UUID.randomUUID())
            contentType(ContentType.Application.Json)
            body = medlemskapRequest
        }
    }

    suspend fun hentMedlemskap(fnr: String): MedlemskapResponse {
        val token = azureAdClient.hentToken()
        println("hentet token")
        val medlemskapRequest = MedlemskapRequest(
            fnr,
            MedlemskapRequest.Periode(LocalDate.now().minusDays(10), LocalDate.now()),
            MedlemskapRequest.BrukerInput(false)
        )

        println("fnr.length: " + fnr.length)
        println("kaller: $baseUrl/")
        return apacheHttpClient.post {
            url("$baseUrl/")
            header(HttpHeaders.Authorization, "Bearer ${token.token}")
            header("Nav-Call-Id", UUID.randomUUID())
            contentType(ContentType.Application.Json)
            body = medlemskapRequest
        }
    }

    suspend fun healthCheck(): HttpResponse {
        return httpClient.get {
            url("$baseUrl/isAlive")
            header("Nav-Consumer-Id", "medlemskap-funksjonelle-tester")
            header(HttpHeaders.Accept, ContentType.Application.Json)
        }
    }
}
