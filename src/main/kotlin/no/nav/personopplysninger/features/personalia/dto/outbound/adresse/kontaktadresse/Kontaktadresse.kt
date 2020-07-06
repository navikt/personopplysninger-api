package no.nav.personopplysninger.features.personalia.dto.outbound.adresse.kontaktadresse

import no.nav.personopplysninger.features.personalia.dto.outbound.adresse.kontaktadresse.KontaktadresseType.*


data class Kontaktadresse constructor(
        val type: KontaktadresseType,
        val vegadresse: Vegadresse? = null,
        val postadresseIFrittFormat: PostAdresseIFrittFormat? = null,
        val postboksadresse: Postboksadresse? = null,
        val utenlandskAdresse: UtenlandskAdresse? = null,
        val utenlandskAdresseIFrittFormat: UtenlandskAdresseIFrittFormat? = null
) {
    companion object {

        fun withVegadresse(adresse: Vegadresse)
                = Kontaktadresse(VEGADRESSE, vegadresse = adresse)

        fun withPostadresseIFrittFormat(adresse: PostAdresseIFrittFormat)
                = Kontaktadresse(POSTADRESSE_I_FRITT_FORMAT, postadresseIFrittFormat = adresse)

        fun withPostboksadresse(adresse: Postboksadresse)
                = Kontaktadresse(POSTBOKSADRESSE, postboksadresse = adresse)

        fun withUtenlandskAdresse(adresse: UtenlandskAdresse)
                = Kontaktadresse(UTENLANDSK_ADRESSE, utenlandskAdresse = adresse)

        fun withUtenlandskAdresseIFrittFormat(adresse: UtenlandskAdresseIFrittFormat)
                = Kontaktadresse(UTENLANDSK_ADRESSE_I_FRITT_FORMAT, utenlandskAdresseIFrittFormat = adresse)
    }
}