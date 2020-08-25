package no.nav.medlemskap

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
    println("Token type: " + hentToken.type)
    val medlemskapClient = MedlemskapClient(configuration.medlemskapBaseUrl, azureAdClient)

    val medlemskapResponse = medlemskapClient.hentMedlemskap(configuration.testperson)
    println("Resultat: " + medlemskapResponse.resultat.svar)
    Main.main(*args)
}