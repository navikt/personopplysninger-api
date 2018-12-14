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
 * Informasjon om veiadresse
 * @param bokstav Husbokstav
 * @param bolignummer Bolignummer
 * @param gatekode Gatekode
 * @param husnummer Husnummer
 */
data class Veiadresse (

    /* Husbokstav */
    val bokstav: kotlin.String? = null,
    /* Bolignummer */
    val bolignummer: kotlin.String? = null,
    /* Gatekode */
    val gatekode: kotlin.String? = null,
    /* Husnummer */
    val husnummer: kotlin.String? = null
) {
}