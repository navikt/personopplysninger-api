package no.nav.personopplysninger.features.personalia.pdl.dto.adresse

data class PdlUtenlandskAdresseIFrittFormat (
     val adresselinje1: String?,
     val adresselinje2: String?,
     val adresselinje3: String?,
     val postkode: String?,
     val byEllerStedsnavn: String?,
     val landkode: String
)