package no.nav.medlemskap

import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import no.nav.medlemskap.client.AzureAdClient
import no.nav.medlemskap.client.MedlemskapClient
import no.nav.medlemskap.client.StsRestClient
import no.nav.medlemskap.config.Configuration
import no.nav.medlemskap.domene.MedlemskapRequest
import org.junit.jupiter.api.Assertions
import java.time.LocalDate

class AuthenticationTest {

    private val configuration = Configuration()
    private val azureAdClient = AzureAdClient(configuration.azureAd)
    private val medlemskapClient = MedlemskapClient(configuration.medlemskapBaseUrl, azureAdClient)
    private val logger = KotlinLogging.logger { }

    fun runTests() {
        `no token returns 401`()
        `invalid audience in token returns 401`()
        `expired token returns 401`()
        `valid sts token returns 401`()
    }

    private fun `no token returns 401`() {
        val medlemskapResponse = runBlocking { medlemskapClient.hentMedlemskapMedGittToken(gyldigMedlemskapRequest(), "") }
        Assertions.assertEquals(401, medlemskapResponse.status.value)
        logger.info("No token in request returns 401")
    }

    private fun `invalid audience in token returns 401`() {
        val tokenMedFeilAudience = runBlocking { azureAdClient.hentTokenMedFeilAudience() }
        val medlemskapResponse = runBlocking { medlemskapClient.hentMedlemskapMedGittToken(gyldigMedlemskapRequest(), tokenMedFeilAudience.token) }
        Assertions.assertEquals(401, medlemskapResponse.status.value)
        logger.info("Invalid audience in token in request returns 401")
    }

    private fun `expired token returns 401`() {
        val medlemskapResponse = runBlocking { medlemskapClient.hentMedlemskapMedGittToken(gyldigMedlemskapRequest(), configuration.expiredAzureAdToken) }
        Assertions.assertEquals(401, medlemskapResponse.status.value, "Expired token returns: ${medlemskapResponse.status}")
        logger.info("Expired token in request returns 401")
    }

    private fun `valid sts token returns 401`() {
        val stsRestClient = StsRestClient(configuration.sts.url, configuration.sts.serviceUsername, configuration.sts.password)
        val stsToken = runBlocking { stsRestClient.oidcToken() }
        val medlemskapResponse = runBlocking { medlemskapClient.hentMedlemskapMedGittToken(gyldigMedlemskapRequest(), stsToken.token) }
        Assertions.assertEquals(401, medlemskapResponse.status.value, "Valid STS token returns: ${medlemskapResponse.status}")
        logger.info("Valid STS-Token in request returns 401")
    }

    private fun gyldigMedlemskapRequest(): MedlemskapRequest {
        return MedlemskapRequest(
            configuration.testpersonMedMedlemskap,
            MedlemskapRequest.Periode(LocalDate.now().minusDays(10), LocalDate.now()),
            MedlemskapRequest.BrukerInput(false)
        )
    }
}