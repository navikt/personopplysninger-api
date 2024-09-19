package no.nav.personopplysninger.personalia.transformer

import no.nav.pdl.generated.dto.HentPersonQuery
import no.nav.pdl.generated.dto.hentpersonquery.Kontaktadresse
import no.nav.pdl.generated.dto.hentpersonquery.Oppholdsadresse
import no.nav.personopplysninger.personalia.dto.AdresseKodeverk
import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.dto.outbound.Adresser
import no.nav.personopplysninger.util.firstOrNull

fun HentPersonQuery.Result.toOutbound(kodeverk: PersonaliaKodeverk): Adresser {
    requireNotNull(person) { "person kan ikke være null" }

    val kontaktadresser = person.kontaktadresse
    val bostedsadresse = person.bostedsadresse.firstOrNull()
    val oppholdsadresser = person.oppholdsadresse
    val deltBosted = person.deltBosted.firstOrNull()

    return with(kodeverk) {
        Adresser(
            kontaktadresser = mapAdresseWithKodeverk(
                adresser = kontaktadresser,
                kodeverk = kontaktadresseKodeverk,
                mapper = Kontaktadresse::toOutbound
            ),
            bostedsadresse = bostedsadresse?.toOutbound(bostedsadresseKodeverk),
            oppholdsadresser = mapAdresseWithKodeverk(
                adresser = oppholdsadresser,
                kodeverk = oppholdsadresseKodeverk,
                mapper = Oppholdsadresse::toOutbound
            ),
            deltBosted = deltBosted?.toOutbound(deltBostedKodeverk),
        )
    }
}

private fun <T, S> mapAdresseWithKodeverk(
    adresser: List<T>,
    kodeverk: List<AdresseKodeverk>,
    mapper: (T, AdresseKodeverk) -> S?
): List<S> {
    return adresser.zip(kodeverk) { a, k -> mapper(a, k) }.filterNotNull()
}