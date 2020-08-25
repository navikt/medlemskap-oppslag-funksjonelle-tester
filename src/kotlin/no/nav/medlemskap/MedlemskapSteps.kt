package no.nav.medlemskap

import io.cucumber.java8.No
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.junit.Assert
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MedlemskapSteps() : No {

    private val logger = KotlinLogging.logger {}

    companion object {

        private val configuration = Configuration()
        private val azureAdClient = AzureAdClient(configuration.azureAd)
        private val medlemskapClient = MedlemskapClient(configuration.medlemskapBaseUrl, azureAdClient)

        fun sendMedlemskapRequest(medlemskapRequest: MedlemskapRequest): MedlemskapResponse =
            runBlocking { medlemskapClient.hentMedlemskapForRequest(medlemskapRequest) }
    }


    private lateinit var medlemskapRequest: MedlemskapRequest
    private lateinit var resultat: Medlemskapresultat

    init {
        Gitt("en søker med inputperiode fra {string} til {string}") { fraDato: String, tilDato: String ->
            val fraDatoAsDate = LocalDate.parse(fraDato, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            val tilDatoAsDate = LocalDate.parse(tilDato, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            medlemskapRequest = MedlemskapRequest(
                configuration.testperson,
                MedlemskapRequest.Periode(fraDatoAsDate, tilDatoAsDate),
                MedlemskapRequest.BrukerInput(false)
            )

            logger.info { "lager MedlemskapRequest for bruker med inputperiode: $fraDato - $tilDato" }
            println("Lager MedlemskapRequest for bruker med inputperiode: $fraDato - $tilDato")
        }

        Når("medlemskap skal beregnes") {
            resultat = sendMedlemskapRequest(medlemskapRequest).resultat
        }

        Så("skal svaret være {string}") { svar: String ->
            Assert.assertEquals(svar, resultat.svar)
        }
    }
}
