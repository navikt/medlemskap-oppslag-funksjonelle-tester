package no.nav.medlemskap

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
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

    logger.info("Funksjonelle tester started")
    println("Funksjonelle tester startet")
    /*
    val httpClient = HttpClient {
        install(JsonFeature) {
            serializer = JacksonSerializer {
                this.registerModule(JavaTimeModule())
                    .configure(SerializationFeature.INDENT_OUTPUT, true)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
            }
        }
    }
    val configuration = Configuration()
    val azureAdClient = AzureAdClient(httpClient, configuration.azureAd)

    logger.info("Azure Ad Client created")
    val medlemskapClient = MedlemskapClient("http://localhost:8080", azureAdClient, httpClient)

    val medlemskapResponse = medlemskapClient.hentMedlemskap()
    println("medlemskap-kall: " + medlemskapResponse.status.value + " " + medlemskapResponse.status.description)
    Main.main(*args)

     */
}