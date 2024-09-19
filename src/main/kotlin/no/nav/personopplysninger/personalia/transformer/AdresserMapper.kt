package no.nav.personopplysninger.personalia.transformer

import no.nav.pdl.generated.dto.hentpersonquery.Kontaktadresse
import no.nav.pdl.generated.dto.hentpersonquery.Oppholdsadresse
import no.nav.pdl.generated.dto.hentpersonquery.Person
import no.nav.personopplysninger.personalia.dto.AdresseKodeverk
import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.dto.outbound.Adresser
import no.nav.personopplysninger.util.firstOrNull

fun Person.toOutboundAdresser(kodeverk: PersonaliaKodeverk) = with(kodeverk) {
    Adresser(
        kontaktadresser = mapAdresseWithKodeverk(
            adresser = kontaktadresse,
            kodeverk = kontaktadresseKodeverk,
            mapper = Kontaktadresse::toOutbound
        ),
        bostedsadresse = bostedsadresse.firstOrNull()?.toOutbound(bostedsadresseKodeverk),
        oppholdsadresser = mapAdresseWithKodeverk(
            adresser = oppholdsadresse,
            kodeverk = oppholdsadresseKodeverk,
            mapper = Oppholdsadresse::toOutbound
        ),
        deltBosted = deltBosted.firstOrNull()?.toOutbound(deltBostedKodeverk),
    )
}

private fun <T, S> mapAdresseWithKodeverk(
    adresser: List<T>,
    kodeverk: List<AdresseKodeverk>,
    mapper: (T, AdresseKodeverk) -> S?
): List<S> {
    return adresser.zip(kodeverk) { a, k -> mapper(a, k) }.filterNotNull()
}