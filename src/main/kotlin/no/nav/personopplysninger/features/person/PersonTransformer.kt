package no.nav.personopplysninger.features.person

import no.nav.personopplysninger.features.person.model.Person
import no.nav.personopplysninger.features.person.model.dto.PersonInformasjon

class PersonTransformer{

    fun toInternal(external: PersonInformasjon): Person  {
        return Person(external.navn?.fornavn,external.navn?.slektsnavn);
    }
}
