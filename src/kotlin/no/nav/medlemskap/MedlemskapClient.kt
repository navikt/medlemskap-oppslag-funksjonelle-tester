package no.nav.medlemskap

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import mu.KotlinLogging
import java.time.LocalDate

private val secureLogger = KotlinLogging.logger("tjenestekall")

class MedlemskapClient(
    private val baseUrl: String,
    private val azureAdClient: AzureAdClient,
    private val httpClient: HttpClient
) {

    suspend fun hentMedlemskap(): HttpResponse {
        val token = azureAdClient.hentToken()
        secureLogger.info("Hentet AzureAd-token")
        val medlemskapRequest = MedlemskapRequest(
            "lastesFraVault",
            MedlemskapRequest.Periode(LocalDate.now().minusDays(10), LocalDate.now()),
            MedlemskapRequest.BrukerInput(false)
        )

        return httpClient.post {
            url("$baseUrl/")
            header(HttpHeaders.Authorization, "Bearer ${token.token}")
            header("Nav-Call-Id", "123456")
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
