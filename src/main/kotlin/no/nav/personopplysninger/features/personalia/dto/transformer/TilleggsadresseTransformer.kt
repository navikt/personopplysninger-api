package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.personalia.dto.outbound.Tilleggsadresse

object TilleggsadresseTransformer {
    fun toOutbound(inbound: no.nav.tps.person.Tilleggsadresse): Tilleggsadresse = Tilleggsadresse(
            adresse1 = inbound.adresse1,
            adresse2 = inbound.adresse2,
            adresse3 = inbound.adresse3,
            datoFraOgMed = inbound.datoFraOgMed,
            datoTilOgMed = inbound.datoTilOgMed,
            bydel = inbound.bydel,
            postnummer = inbound.postnummer,
            postboksnummer = inbound.postboksnummer,
            postboksanlegg = inbound.postboksanlegg,
            kommunenummer = inbound.kommunenummer,
            husnummer = inbound.husnummer,
            husbokstav = inbound.husbokstav,
            gateKode = inbound.gateKode,
            bolignummer = inbound.bolignummer
    )

}
