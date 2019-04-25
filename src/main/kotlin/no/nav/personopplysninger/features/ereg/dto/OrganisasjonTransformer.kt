package no.nav.personopplysninger.features.ereg.dto

import no.nav.personopplysninger.features.ereg.EregOrganisasjon
import no.nav.personopplysninger.features.ereg.dto.outbound.Organisasjon

object OrganisasjonTransformer {

    fun toOutbound(inbound: EregOrganisasjon) = Organisasjon(

            navn = inbound.redigertnavn

    )

}
