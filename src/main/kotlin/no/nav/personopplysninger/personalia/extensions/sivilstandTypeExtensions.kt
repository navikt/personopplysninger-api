package no.nav.personopplysninger.personalia.extensions

import no.nav.pdl.generated.dto.enums.Sivilstandstype

val Sivilstandstype.stringValue: String
    get() = when (this) {
        Sivilstandstype.UOPPGITT -> "Uoppgitt"
        Sivilstandstype.UGIFT -> "Ugift"
        Sivilstandstype.GIFT -> "Gift"
        Sivilstandstype.ENKE_ELLER_ENKEMANN -> "Enke/-mann"
        Sivilstandstype.SKILT -> "Skilt"
        Sivilstandstype.SEPARERT -> "Separert"
        Sivilstandstype.REGISTRERT_PARTNER -> "Registrert partner"
        Sivilstandstype.SEPARERT_PARTNER -> "Separert partner"
        Sivilstandstype.SKILT_PARTNER -> "Skilt partner"
        Sivilstandstype.GJENLEVENDE_PARTNER -> "Gjenlevende partner"
        else -> "Ukjent"
    }