package no.nav.personopplysninger.personalia.transformer

import no.nav.personopplysninger.personalia.consumer.dto.Postadresse
import no.nav.personopplysninger.personalia.dto.outbound.NavkontorPostadresse

object NavkontorPostadresseTransformer {
    fun toOutbound(inbound: Postadresse?) = NavkontorPostadresse(
            type = inbound?.type,
            postnummer = inbound?.postnummer,
            poststed = inbound?.poststed,
            postboksnummer = inbound?.postboksnummer,
            postboksanlegg = inbound?.postboksanlegg,
            gatenavn = inbound?.gatenavn,
            husnummer = inbound?.husnummer,
            husbokstav = inbound?.husbokstav,
            adresseTilleggsnavn = inbound?.adresseTilleggsnavn
    )
}
