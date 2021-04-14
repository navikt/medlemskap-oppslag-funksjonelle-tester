package no.nav.medlemskap.domene

import java.time.LocalDate

/*
{
 "fnr":"lastesFraVault",
 "førsteDagForYtelse": "2020-07-01",
 "periode": {
 	"fom": "2020-07-01",
 	"tom": "2020-07-07"
 },
 "brukerinput": {
 	"arbeidUtenforNorge": false
 }
}
 */

data class MedlemskapRequest(
    val fnr: String,
    val førsteDagForYtelse: LocalDate,
    val periode: Periode,
    val brukerinput: BrukerInput
) {
    data class Periode (val fom: LocalDate,val tom: LocalDate)
    data class BrukerInput(val arbeidUtenforNorge: Boolean)
}



