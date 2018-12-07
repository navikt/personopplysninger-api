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
package no.nav.personopplysninger.features.personalia.dto.out


/**
 * Informasjon om innvandring og utvandring
 * @param innvandretDato Dato innvandret, format (ISO-8601): yyyy-MM-dd
 * @param innvandretKilde Fra hvor informasjonen er sendt fra
 * @param innvandretLand 
 * @param utvandretDato Dato utvandret, format (ISO-8601): yyyy-MM-dd
 * @param utvandretKilde Fra hvor informasjonen er sendt fra
 * @param utvandretLand 
 */
data class InnvandringUtvandring (

    /* Dato innvandret, format (ISO-8601): yyyy-MM-dd */
    val innvandretDato: kotlin.String? = null,
    /* Fra hvor informasjonen er sendt fra */
    val innvandretKilde: kotlin.String? = null,
    val innvandretLand: Kode? = null,
    /* Dato utvandret, format (ISO-8601): yyyy-MM-dd */
    val utvandretDato: kotlin.String? = null,
    /* Fra hvor informasjonen er sendt fra */
    val utvandretKilde: kotlin.String? = null,
    val utvandretLand: Kode? = null
) {
}