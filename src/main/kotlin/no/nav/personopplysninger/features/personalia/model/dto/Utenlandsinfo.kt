package no.nav.personopplysninger.features.personalia.model.dto

data class Utenlandsinfo(
        val datoFraOgMed: String,
        val familienavnFodt: String,
        val farsFamilenavn: String,
        val farsFornavn: String,
        val foedested: String,
        val fornavnFodt: String,
        val idOff: String,
        val institusjon: String,
        val institusjonNavn: String,
        val kilde: String,
        val kildePin: String,
        val land: String,
        val morsFamilenavn: String,
        val morsFornavn: String,
        val nasjonalId: String,
        val nasjonalitet: String,
        val sedRef: String,
        val sektor: String
)
