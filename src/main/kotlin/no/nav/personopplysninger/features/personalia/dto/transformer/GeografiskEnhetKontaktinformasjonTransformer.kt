package no.nav.personopplysninger.features.personalia.dto.transformer

import no.nav.personopplysninger.features.norg2.domain.Norg2EnhetKontaktinfo
import no.nav.personopplysninger.features.personalia.dto.outbound.GeografiskEnhetKontaktInformasjon
import no.nav.personopplysninger.features.personalia.dto.outbound.GeografiskTilknytning
import no.nav.personopplysninger.features.personalia.kodeverk.PersonaliaKodeverk


object GeografiskEnhetKontaktinformasjonTransformer {
    fun toOutbound(inbound: Norg2EnhetKontaktinfo) = GeografiskEnhetKontaktInformasjon(

            enhetsnavn = inbound.stedsbeskrivelse,
            gateadresse = inbound.postadresse?.gatenavn,
            poststed = inbound.postadresse?.poststed,
            aapningmandag = inbound.aapningstider?.dag
            /*aapningtirsdag: String? = null,
            appningonsdag: String? = null,
            aapningtorsdag: String? = null,
            aapningfredag: String? = null,
            andre: String? = null,
            tlfperson: String? = null,
            tlfpensjon: String? = null*/
    )

}