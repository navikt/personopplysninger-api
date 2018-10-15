package no.nav.personopplysninger.features.personalia

import no.nav.personopplysninger.features.personalia.model.Personalia
import no.nav.personopplysninger.features.personalia.model.dto.PersonInformasjon

class PersonaliaTransformer{

    fun toInternal(external: PersonInformasjon): Personalia  {
        return Personalia(external.navn?.fornavn,external.navn?.slektsnavn,external.ident)
    }
}
