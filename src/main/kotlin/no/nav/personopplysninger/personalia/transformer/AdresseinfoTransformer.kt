package no.nav.personopplysninger.personalia.transformer

import no.nav.pdl.generated.dto.HentPersonQuery
import no.nav.personopplysninger.personalia.dto.PersonaliaKodeverk
import no.nav.personopplysninger.personalia.dto.outbound.Adresser
import no.nav.personopplysninger.util.firstOrNull

fun HentPersonQuery.Result.toOutbound(kodeverk: PersonaliaKodeverk): Adresser {
    val kontaktadresse = person!!.kontaktadresse
    val bostedsadresse = person.bostedsadresse.firstOrNull()
    val oppholdsadresse = person.oppholdsadresse
    val deltBosted = person.deltBosted.firstOrNull()

    val kontaktadresseKodeverk = kodeverk.kontaktadresseKodeverk
    val bostedsadresseKodeverk = kodeverk.bostedsadresseKodeverk
    val oppholdsadresseKodeverk = kodeverk.oppholdsadresseKodeverk
    val deltBostedKodeverk = kodeverk.deltBostedKodeverk

    return Adresser(
        kontaktadresser = kontaktadresse.zip(kontaktadresseKodeverk)
            .mapNotNull { pair -> pair.first.toOutbound(pair.second) },
        bostedsadresse = bostedsadresse?.toOutbound(bostedsadresseKodeverk),
        oppholdsadresser = oppholdsadresse.zip(oppholdsadresseKodeverk)
            .mapNotNull { pair -> pair.first.toOutbound(pair.second) },
        deltBosted = deltBosted?.toOutbound(deltBostedKodeverk),
    )
}