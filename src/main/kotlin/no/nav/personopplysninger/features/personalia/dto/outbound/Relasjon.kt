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
 * Objekt retunert fra relasjon, og barn-endepunktet
 * @param alder Alder
 * @param datoFraOgMed Dato gyldig gyldig, format (ISO-8601): yyyy-MM-dd
 * @param doedsdato 
 * @param egenansatt 
 * @param foedselsdato Fødselsdato, format (ISO-8601): yyyy-MM-dd
 * @param forkortetNavn Personidentifikator
 * @param harSammeAdresse Har samme adresse som Nav-Personidentifikator
 * @param ident Personidentifikator
 * @param kilde 
 * @param kjoenn Kjønn
 * @param relasjonsType 
 * @param spesiellOpplysning 
 * @param statsborgerskap 
 */
data class Relasjon (

    /* Alder */
    val alder: kotlin.Int? = null,
    /* Dato gyldig gyldig, format (ISO-8601): yyyy-MM-dd */
    val datoFraOgMed: kotlin.String? = null,
    val doedsdato: Doedsdato? = null,
    val egenansatt: Egenansatt? = null,
    /* Fødselsdato, format (ISO-8601): yyyy-MM-dd */
    val foedselsdato: kotlin.String? = null,
    /* Personidentifikator */
    val forkortetNavn: kotlin.String? = null,
    /* Har samme adresse som Nav-Personidentifikator */
    val harSammeAdresse: kotlin.Boolean? = null,
    /* Personidentifikator */
    val ident: kotlin.String? = null,
    val kilde: kotlin.String? = null,
    /* Kjønn */
    val kjoenn: kotlin.String? = null,
    val relasjonsType: Kode? = null,
    val spesiellOpplysning: KodeMedDatoOgKilde? = null,
    val statsborgerskap: KodeMedDatoOgKilde? = null
) {
}