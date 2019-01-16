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
 * Informasjon om vergemål
 * @param datoFraOgMed Dato gyldig gyldig, format (ISO-8601): yyyy-MM-dd
 * @param egenansatt Verge egenansatt
 * @param embete 
 * @param fnr Fødselsnummer
 * @param forkortetNavn Verge forkortet navn
 * @param id Id
 * @param kilde 
 * @param mandattype 
 * @param saksId Saksid
 * @param sakstype 
 * @param spesreg 
 * @param type 
 * @param vedtaksdato Dato for vedtak, format (ISO-8601): yyyy-MM-dd
 */
data class Vergemaal (

    /* Dato gyldig gyldig, format (ISO-8601): yyyy-MM-dd */
    val datoFraOgMed: String? = null,
    /* Verge egenansatt */
    val egenansatt: kotlin.Boolean? = null,
    val embete: Kode? = null,
    /* Fødselsnummer */
    val fnr: String? = null,
    /* Verge forkortet navn */
    val forkortetNavn: String? = null,
    /* Id */
    val id: String? = null,
    val kilde: String? = null,
    val mandattype: Kode? = null,
    /* Saksid */
    val saksId: String? = null,
    val sakstype: Kode? = null,
    val spesreg: Kode? = null,
    val type: Kode? = null,
    /* Dato for vedtak, format (ISO-8601): yyyy-MM-dd */
    val vedtaksdato: String? = null
) {
}