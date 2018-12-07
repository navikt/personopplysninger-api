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

data class Navn(
        /* Format (ISO-8601): yyyy-MM-dd */
        val datoFraOgMed: String? = null,
        val forkortetNavn: String? = null,
        val fornavn: String? = null,
        val kilde: String? = null,
        val mellomnavn: String? = null,
        val slektsnavn: String? = null,
        val slektsnavnUgift: String? = null
) {
}