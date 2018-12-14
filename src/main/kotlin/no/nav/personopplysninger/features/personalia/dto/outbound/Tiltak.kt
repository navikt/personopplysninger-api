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
 * Informasjon on tiltak
 * @param datoFraOgMed Dato gyldig gyldig, format (ISO-8601): yyyy-MM-dd
 * @param datoTil Dato til når informasjonen er gyldig, format (ISO-8601): yyyy-MM-dd
 * @param kilde 
 * @param type 
 */
data class Tiltak (

    /* Dato gyldig gyldig, format (ISO-8601): yyyy-MM-dd */
    val datoFraOgMed: kotlin.String? = null,
    /* Dato til når informasjonen er gyldig, format (ISO-8601): yyyy-MM-dd */
    val datoTil: kotlin.String? = null,
    val kilde: kotlin.String? = null,
    val type: Kode? = null
) {
}