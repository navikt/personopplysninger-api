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
 * Informasjon om utenlandsk bank
 * @param adresse1 Bank adresselinje 1
 * @param adresse2 Bank adresselinje 2
 * @param adresse3 Bank adreeselinje 3
 * @param bankkode Bankkode
 * @param banknavn Banknavn
 * @param datoFraOgMed Dato gyldig gyldig, format (ISO-8601): yyyy-MM-dd
 * @param iban IBAN
 * @param kilde 
 * @param kontonummer Kontonummer
 * @param land 
 * @param swiftkode Swift kode
 * @param valuta 
 */
data class UtenlandskBank (

    /* Bank adresselinje 1 */
    val adresse1: kotlin.String? = null,
    /* Bank adresselinje 2 */
    val adresse2: kotlin.String? = null,
    /* Bank adreeselinje 3 */
    val adresse3: kotlin.String? = null,
    /* Bankkode */
    val bankkode: kotlin.String? = null,
    /* Banknavn */
    val banknavn: kotlin.String? = null,
    /* Dato gyldig gyldig, format (ISO-8601): yyyy-MM-dd */
    val datoFraOgMed: kotlin.String? = null,
    /* IBAN */
    val iban: kotlin.String? = null,
    val kilde: kotlin.String? = null,
    /* Kontonummer */
    val kontonummer: kotlin.String? = null,
    val land: Kode? = null,
    /* Swift kode */
    val swiftkode: kotlin.String? = null,
    val valuta: Kode? = null
) {
}