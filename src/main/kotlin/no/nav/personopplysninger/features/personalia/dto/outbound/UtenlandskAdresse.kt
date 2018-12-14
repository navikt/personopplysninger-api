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
 * Informasjon om utenlandsk adresse
 * @param adresse1 Adresselinje 1
 * @param adresse2 Adresselinje 2
 * @param adresse3 Adresselinje 3
 * @param datoFraOgMed Dato gyldig gyldig, format (ISO-8601): yyyy-MM-dd
 * @param datoTilOgMed Dato til når informasjonen er gyldig, format (ISO-8601): yyyy-MM-dd
 * @param kilde 
 * @param land Landkode
 */
data class UtenlandskAdresse (

    /* Adresselinje 1 */
    val adresse1: kotlin.String? = null,
    /* Adresselinje 2 */
    val adresse2: kotlin.String? = null,
    /* Adresselinje 3 */
    val adresse3: kotlin.String? = null,
    /* Dato gyldig gyldig, format (ISO-8601): yyyy-MM-dd */
    val datoFraOgMed: kotlin.String? = null,
    /* Dato til når informasjonen er gyldig, format (ISO-8601): yyyy-MM-dd */
    val datoTilOgMed: kotlin.String? = null,
    val kilde: kotlin.String? = null,
    /* Landkode */
    val land: kotlin.String? = null
) {
}