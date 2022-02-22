package no.nav.medlemskap.client

import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import no.nav.medlemskap.config.Configuration


suspend fun shutdown() : HttpResponse {
    val configuration = Configuration()
    return httpClient.post {
        url("${configuration.linkerdSidecarBaseUrl}/shutdown")
    }
}

fun shutdownLinkerdSidecar() {
    runBlocking {
        shutdown()
    }

}