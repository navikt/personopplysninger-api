package no.nav.personopplysninger.consumer.pdl.dto.personalia

enum class PdlSivilstandstype(val beskrivelse: String) {
    UOPPGITT("Uoppgitt"),
    UGIFT("Ugift"),
    GIFT("Gift"),
    ENKE_ELLER_ENKEMANN("Enke/-mann"),
    SKILT("Skilt"),
    SEPARERT("Separert"),
    REGISTRERT_PARTNER("Registrert partner"),
    SEPARERT_PARTNER("Separert partner"),
    SKILT_PARTNER("Skilt partner"),
    GJENLEVENDE_PARTNER("Gjenlevende partner"),
}
