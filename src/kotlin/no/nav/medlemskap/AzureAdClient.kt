package no.nav.medlemskap

import com.fasterxml.jackson.annotation.JsonProperty
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.content.*
import io.ktor.http.*
import mu.KotlinLogging
import java.time.LocalDateTime

private val sikkerlogg = KotlinLogging.logger("tjenestekall")

class AzureAdClient(
    private val httpClient: HttpClient,
    private val configuration: Configuration.AzureAd
) {

    suspend fun hentToken(): Token {

        /*
        POST
        Headers:
            Content-Type: application/x-www-form-urlencoded

        Parameters:
            clientid=<client_id>
            scope=api://<client_id>/.default
            client_secret=<client_secret>
            grant_type=client_credentials
         */
        val azureAdUrl = "${configuration.authorityEndpoint}/${configuration.tenant}/oauth2/v2.0/token"
        val clientId = configuration.clientId

        println("endpoint: ${configuration.authorityEndpoint}")
        sikkerlogg.info("sikkerlogg endpoint: ${configuration.authorityEndpoint}")
        val clientSecret = "hentesFraVault"

        val formUrlEncode = listOf(
            "client_id" to clientId,
            "scope" to "api://$clientId/.default",
            "client_secret" to clientSecret,
            "grant_type" to "client_credentials"
        ).formUrlEncode()

        return httpClient.post {
            url(azureAdUrl)
            body = TextContent(formUrlEncode, ContentType.Application.FormUrlEncoded)
        }
    }

    data class Token(
        @JsonProperty(value = "access_token", required = true)
        val token: String,
        @JsonProperty(value = "token_type", required = true)
        val type: String,
        @JsonProperty(value = "expires_in", required = true)
        val expiresIn: Int
    ) {

        private val expirationTime: LocalDateTime = LocalDateTime.now().plusSeconds(expiresIn - 20L)

        fun hasExpired(): Boolean = expirationTime.isBefore(LocalDateTime.now())
    }
}