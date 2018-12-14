package no.nav.personopplysninger.features.personalia.dto.transformer

import arrow.core.Option
import arrow.core.getOrElse
import no.nav.tps.person.Adresseinfo

object AdresseinfoTransformer {
    fun toOutbound(inbound: Adresseinfo) = no.nav.personopplysninger.features.personalia.dto.outbound.Adresseinfo(
            boadresse = Option.fromNullable(inbound.boadresse).map { BoadresseTransformer.toOutbound(it) }.getOrElse { null },
            postadresse = Option.fromNullable(inbound.postadresse).map { PostadresseTransformer.toOutbound(it) }.getOrElse { null },
            utenlandskAdresse = Option.fromNullable(inbound.utenlandskAdresse).map { UtenlandskAdresseTransformer.toOutbound(it) }.getOrElse { null }
    )
}