package no.nav.personopplysninger.consumer.kodeverk.dto

class GetKodeverkKoderBetydningerResponse {

    var betydninger: Map<String, List<Betydning>> = emptyMap()
        set(betydninger) {
            field = LinkedHashMap(betydninger)
        }

}
