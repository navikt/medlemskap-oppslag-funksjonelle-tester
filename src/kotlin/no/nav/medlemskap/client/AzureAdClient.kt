package no.nav.medlemskap.client

import io.ktor.client.request.*
import io.ktor.content.*
import io.ktor.http.*
import no.nav.medlemskap.config.Configuration
import no.nav.medlemskap.domene.Token

class AzureAdClient(private val configuration: Configuration.AzureAd) {

    suspend fun hentToken(): Token {
        val formUrlEncode = listOf(
            "client_id" to configuration.clientId,
            "scope" to "api://${configuration.audience}/.default",
            "client_secret" to configuration.clientSecret,
            "grant_type" to "client_credentials"
        ).formUrlEncode()

        return httpClient.post {
            url(configuration.tokenEndpoint)
            body = TextContent(formUrlEncode, ContentType.Application.FormUrlEncoded)
        }
    }

    suspend fun hentTokenMedFeilAudience(): Token {

        val formUrlEncode = listOf(
            "client_id" to configuration.clientId,
            "scope" to "api://${configuration.clientId}/.default",
            "client_secret" to configuration.clientSecret,
            "grant_type" to "client_credentials"
        ).formUrlEncode()

        return apacheHttpClient.post {
            url(configuration.tokenEndpoint)
            body = TextContent(formUrlEncode, ContentType.Application.FormUrlEncoded)
        }
    }

}