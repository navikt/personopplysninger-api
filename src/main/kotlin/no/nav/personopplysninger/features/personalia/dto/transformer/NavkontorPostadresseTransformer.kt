package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.norg2.domain.Postadresse
import no.nav.personopplysninger.features.personalia.dto.outbound.NavkontorPostadresse

object NavkontorPostadresseTransformer {
    fun toOutbound(inbound: Postadresse?) = NavkontorPostadresse(
            type = inbound?.type,
            postnummer = inbound?.postnummer,
            poststed = inbound?.poststed,
            gatenavn = inbound?.gatenavn,
            husnummer = inbound?.husnummer,
            husbokstav = inbound?.husbokstav
    )
}