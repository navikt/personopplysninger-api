/**
* TPS-PROXY API
* <h4>Api for Tps-Proxy</h4><a href=\"https://confluence.adeo.no/display/FEL/TPS+-+Tjeneste+MQ+S301+-+Hent+Innsynsopplysninger+for+en+person\">Confluence for tps innsyn</a>
*
* OpenAPI spec version: 1.0.0
* 
*
* NOTE: This class is auto generated by the swagger code generator program.
* https://github.com/swagger-api/swagger-codegen.git
* Do not edit the class manually.
*/
package no.nav.personopplysninger.features.personalia.dto.outbound


/**
 * Informasjon om geografisk tilknytning
 * @param bydel Bydel
 * @param datoFraOgMed Dato gyldig gyldig, format (ISO-8601): yyyy-MM-dd
 * @param kilde 
 * @param kommune Kommune
 * @param land Land
 */
data class GeografiskTilknytning (

    /* Bydel */
    val bydel: String? = null,
    /* Dato gyldig gyldig, format (ISO-8601): yyyy-MM-dd */
    val datoFraOgMed: String? = null,
    val kilde: String? = null,
    /* Kommune */
    val kommune: String? = null,
    /* Land */
    val land: String? = null
) {
}