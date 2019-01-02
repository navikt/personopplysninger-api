package no.nav.personopplysninger.features.personalia.dto.transformer

import arrow.core.toOption
import no.nav.tps.person.Adresseinfo

object AdresseinfoTransformer {
    fun toOutbound(inbound: Adresseinfo) = no.nav.personopplysninger.features.personalia.dto.outbound.Adresser(
            boadresse = inbound.boadresse.toOption().map { BoadresseTransformer.toOutbound(it) }.orNull(),
            postadresse = inbound.postadresse.toOption().map { PostadresseTransformer.toOutbound(it) }.orNull(),
            utenlandskAdresse = inbound.utenlandskAdresse.toOption().map { UtenlandskAdresseTransformer.toOutbound(it) }.orNull()
    )
}