package no.nav.medlemskap

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.cucumber.core.cli.Main
import mu.KotlinLogging

val args = arrayOf(
    "--threads", "2",
    "-g", "no.nav.medlemskap",
    "-p", "pretty",
    "classpath:features",
    "--tags", "not @ignored "
)

private val logger = KotlinLogging.logger {}

suspend fun main() {


    println("Funksjonelle tester startet")
    logger.info("Funksjonelle tester started")

    val configuration = Configuration()
    val azureAdClient = AzureAdClient(configuration.azureAd)

    val hentToken = azureAdClient.hentToken()
    /*
    logger.info("Azure Ad Client created")
    val medlemskapClient = MedlemskapClient("http://localhost:8080", azureAdClient, httpClient)

    val medlemskapResponse = medlemskapClient.hentMedlemskap()
    println("medlemskap-kall: " + medlemskapResponse.status.value + " " + medlemskapResponse.status.description)
    Main.main(*args)

     */
}