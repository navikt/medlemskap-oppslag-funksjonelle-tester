package no.nav.medlemskap.domene

import java.time.LocalDateTime

class MedlemskapResponse(
    val tidspunkt: LocalDateTime,
    val versjonTjeneste: String,
    val versjonRegler: String,
    val datagrunnlag: Map<String, Any>,
    val resultat: Medlemskapresultat
)

data class Medlemskapresultat(
    val identifikator: String?,
    val avklaring: String,
    val begrunnelse: String,
    val svar: String,
    val delresultat: List<Medlemskapresultat>
)