package no.nav.medlemskap

import com.natpryce.konfig.*
import mu.KotlinLogging
import java.io.File
import java.io.FileNotFoundException

private val logger = KotlinLogging.logger { }

private val defaultProperties = ConfigurationMap(
    mapOf(
        "MEDLEMSKAP_BASE_URL" to "localhost:8080/",
        "AZURE_TENANT" to "",
        "AZURE_AUTHORITY_ENDPOINT" to "",
        "SERVICE_USER_USERNAME" to "test",
        "MEDLEMSKAP_REGLER_URL" to "",
        "SERVICE_USER_PASSWORD" to "",
        "NAIS_APP_NAME" to "",
        "NAIS_CLUSTER_NAME" to "",
        "NAIS_APP_IMAGE" to "",
        "AZURE_CLIENT_ID" to "",
        "FNR_MED_MEDLEMSKAP" to "",
        "FNR_UAVKLART_MEDLEMSKAP" to ""
    )
)

private val config = ConfigurationProperties.systemProperties() overriding
        EnvironmentVariables overriding
        defaultProperties

private fun String.configProperty(): String = config[Key(this, stringType)]

private fun hentCommitSha(image: String): String {
    val parts = image.split(":")
    if (parts.size == 1) return image
    return parts[1].substring(0, 7)
}

private fun String.readFile() =
    try {
        logger.info { "Leser fra azure-fil $this" }
        File(this).readText(Charsets.UTF_8)
    } catch (err: FileNotFoundException) {
        logger.warn { "Azure fil ikke funnet" }
        null
    }

data class Configuration(
    val azureAd: AzureAd = AzureAd(),
    val cluster: String = "NAIS_CLUSTER_NAME".configProperty(),
    val commitSha: String = hentCommitSha("NAIS_APP_IMAGE".configProperty()),
    val medlemskapBaseUrl: String = "MEDLEMSKAP_BASE_URL".configProperty(),
    val testpersonMedMedlemskap: String = "/var/run/secrets/nais.io/test/fnr_med_medlemskap".readFile() ?: "FNR_MED_MEDLEMSKAP".configProperty(),
    val testpersonUavklartMedlemskap: String = "/var/run/secrets/nais.io/test/fnr_uavklart_medlemskap".readFile() ?: "FNR_UAVKLART_MEDLEMSKAP".configProperty()
) {

    data class AzureAd(
        val clientId: String = "/var/run/secrets/nais.io/azuread/client_id".readFile()
            ?: "AZURE_CLIENT_ID".configProperty(),
        val clientSecret: String = "/var/run/secrets/nais.io/azuread/client_secret".readFile()
            ?: "AZURE_CLIENT_SECRET".configProperty(),
        val audience: String = "AZURE_MEDLEMSKAP_ID".configProperty(),
        val tenant: String = "AZURE_TENANT".configProperty(),
        val authorityEndpoint: String = "AZURE_AUTHORITY_ENDPOINT".configProperty().removeSuffix("/")
    )
}