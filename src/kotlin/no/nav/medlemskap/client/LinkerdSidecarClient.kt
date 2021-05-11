package no.nav.medlemskap.client

import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import no.nav.medlemskap.config.Configuration


suspend fun shutdown() {
    val configuration = Configuration()
    return httpClient.post {
        url("${configuration.linkerdSidecarBaseUrl}/shutdown")
    }
}

fun shutdownLinkerdSidecar() {
    runBlocking { shutdown() }
}