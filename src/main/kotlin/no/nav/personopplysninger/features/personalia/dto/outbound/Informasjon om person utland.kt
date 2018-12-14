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
 * 
 * @param datoFraOgMed Dato gyldig gyldig, format (ISO-8601): yyyy-MM-dd
 * @param familienavnFodt Familienavn
 * @param farsFamilenavn Etternavn far
 * @param farsFornavn Fornavn far
 * @param foedested Fødested
 * @param fornavnFodt Fornavn
 * @param idOff Utenlandsk id
 * @param institusjon Instutisjon
 * @param institusjonNavn InstutisjonNavn
 * @param kilde 
 * @param kildePin Kilde pinnummer
 * @param land 
 * @param morsFamilenavn Etternavn mor
 * @param morsFornavn Fornavn mor
 * @param nasjonalId Nasjonal id
 * @param nasjonalitet 
 * @param sedRef 
 * @param sektor 
 */
data class Informasjon_om_person_utland (

    /* Dato gyldig gyldig, format (ISO-8601): yyyy-MM-dd */
    val datoFraOgMed: kotlin.String? = null,
    /* Familienavn */
    val familienavnFodt: kotlin.String? = null,
    /* Etternavn far */
    val farsFamilenavn: kotlin.String? = null,
    /* Fornavn far */
    val farsFornavn: kotlin.String? = null,
    /* Fødested */
    val foedested: kotlin.String? = null,
    /* Fornavn */
    val fornavnFodt: kotlin.String? = null,
    /* Utenlandsk id */
    val idOff: kotlin.String? = null,
    /* Instutisjon */
    val institusjon: kotlin.String? = null,
    /* InstutisjonNavn */
    val institusjonNavn: kotlin.String? = null,
    val kilde: kotlin.String? = null,
    /* Kilde pinnummer */
    val kildePin: kotlin.String? = null,
    val land: Kode? = null,
    /* Etternavn mor */
    val morsFamilenavn: kotlin.String? = null,
    /* Fornavn mor */
    val morsFornavn: kotlin.String? = null,
    /* Nasjonal id */
    val nasjonalId: kotlin.String? = null,
    val nasjonalitet: Kode? = null,
    val sedRef: kotlin.String? = null,
    val sektor: Kode? = null
) {
}