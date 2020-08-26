package no.nav.medlemskap.client

import io.ktor.client.request.*
import io.ktor.http.*
import no.nav.medlemskap.domene.Token
import java.util.*

class StsRestClient(
    private val baseUrl: String,
    private val username: String,
    private val password: String
) {
    suspend fun oidcToken(): Token {

        return httpClient.get {
            url("$baseUrl/rest/v1/sts/token")
            header(HttpHeaders.Authorization, "Basic ${credentials()}")
            parameter("grant_type", "client_credentials")
            parameter("scope", "openid")
        }
    }

    private fun credentials() =
        Base64.getEncoder().encodeToString("${username}:${password}".toByteArray(Charsets.UTF_8))

}