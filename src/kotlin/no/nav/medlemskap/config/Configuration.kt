package no.nav.medlemskap.config

import com.natpryce.konfig.*
import mu.KotlinLogging
import java.io.File
import java.io.FileNotFoundException

private val logger = KotlinLogging.logger { }

private val defaultProperties = ConfigurationMap(
    mapOf(
        "MEDLEMSKAP_BASE_URL" to "localhost:8080/",
        "AZURE_AUTHORITY_ENDPOINT" to "",
        "MEDLEMSKAP_REGLER_URL" to "",
        "NAIS_APP_NAME" to "",
        "NAIS_CLUSTER_NAME" to "",
        "NAIS_APP_IMAGE" to "",
        "AZURE_CLIENT_ID" to "",
        "FNR_MED_MEDLEMSKAP" to "",
        "FNR_UAVKLART_MEDLEMSKAP" to "",
        "FNR_MED_GYLDIG_OPPHOLDSTILLATELSE" to "",
        "EXPIRED_AZURE_AD_TOKEN" to "",
        "SECURITY_TOKEN_SERVICE_REST_URL" to "",
        "SERVICEUSER_USERNAME" to "",
        "SERVICEUSER_PASSWORD" to ""
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
        File(this).readText(Charsets.UTF_8)
    } catch (err: FileNotFoundException) {
        logger.warn { "Azure fil $this ikke funnet" }
        null
    }

data class Configuration(
    val azureAd: AzureAd = AzureAd(),
    val cluster: String = "NAIS_CLUSTER_NAME".configProperty(),
    val commitSha: String = hentCommitSha("NAIS_APP_IMAGE".configProperty()),
    val medlemskapBaseUrl: String = "MEDLEMSKAP_BASE_URL".configProperty(),
    val linkerdSidecarBaseUrl: String = "LINKERD_SIDECAR_BASE_URL".configProperty(),
    val testpersonMedMedlemskap: String = "FNR_MED_MEDLEMSKAP".configProperty(),
    val testpersonUavklartMedlemskap: String = "FNR_UAVKLART_MEDLEMSKAP".configProperty(),
    val testpersonMedGyldigOppholdstillatelse: String = "FNR_MED_GYLDIG_OPPHOLDSTILLATELSE".configProperty(),
    val expiredAzureAdToken: String = "EXPIRED_AZURE_AD_TOKEN".configProperty(),
    val sts: Sts = Sts()
) {

    data class AzureAd(
        val clientId: String = "AZURE_APP_CLIENT_ID".configProperty(),
        val clientSecret: String = "AZURE_APP_CLIENT_SECRET".configProperty(),
        val audience: String = "MEDLEMSKAP_OPPSLAG_CLIENT_ID".configProperty(),
        val tokenEndpoint: String = "AZURE_OPENID_CONFIG_TOKEN_ENDPOINT".configProperty().removeSuffix("/")
    )

    data class Sts(
        val url: String = "SECURITY_TOKEN_SERVICE_REST_URL".configProperty(),
        val serviceUsername: String = "SERVICEUSER_USERNAME".configProperty(),
        val password: String = "SERVICEUSER_PASSWORD".configProperty()
    )
}