package no.nav.medlemskap

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions
import java.time.LocalDate

class AuthenticationTest {

    private val configuration = Configuration()
    private val azureAdClient = AzureAdClient(configuration.azureAd)
    private val medlemskapClient = MedlemskapClient(configuration.medlemskapBaseUrl, azureAdClient)

    @Test
    fun `invalid audience in token returns 401`() {
        println("test run")
        val tokenMedFeilAudience = runBlocking { azureAdClient.hentTokenMedFeilAudience() }
        val medlemskapResponse = runBlocking { medlemskapClient.hentMedlemskapMedGittToken(gyldigMedlemskapRequest(), tokenMedFeilAudience.token) }
        Assertions.assertEquals(401, medlemskapResponse.status)
    }

    @Test
    fun `expired token returns 401`() {
        val medlemskapResponse = runBlocking { medlemskapClient.hentMedlemskapMedGittToken(gyldigMedlemskapRequest(), configuration.expiredAzureAdToken) }
        Assertions.assertEquals(401, medlemskapResponse.status)
    }

    private fun gyldigMedlemskapRequest(): MedlemskapRequest {
        return MedlemskapRequest(
            configuration.testpersonMedMedlemskap,
            MedlemskapRequest.Periode(LocalDate.now().minusDays(10), LocalDate.now()),
            MedlemskapRequest.BrukerInput(false)
        )
    }
}