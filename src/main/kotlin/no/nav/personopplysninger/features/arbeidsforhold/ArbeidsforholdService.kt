package no.nav.personopplysninger.features.arbeidsforhold

import no.nav.personopplysninger.features.arbeidsforhold.dto.outbound.ArbeidsforholdDto
import no.nav.personopplysninger.features.arbeidsforhold.dto.transformer.ArbeidsforholdTransformer
import no.nav.personopplysninger.features.sts.STSConsumer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ArbeidsforholdService @Autowired constructor(
        private var arbeidsforholdConsumer: ArbeidsforholdConsumer,
        private var stsConsumer: STSConsumer,
        private var eregConsumer: EregConsumer

) {

    private val log = LoggerFactory.getLogger(ArbeidsforholdService::class.java)
    private val tokenbodyindex = 17
    private val tokenbodyend = 42
    private val organisasjon = "Organisasjon"

    fun hentFSSToken(): String {
        val fssToken = stsConsumer.fssToken
        val strippedToken = fssToken.substring(tokenbodyindex, fssToken.length - tokenbodyend)
        log.warn("TOKEN er" + strippedToken)
        return strippedToken
    }

    fun hentArbeidsforhold(fodselsnr: String, fssToken: String): List<ArbeidsforholdDto> {
        val inbound = arbeidsforholdConsumer.hentArbeidsforholdmedFnr(fodselsnr, fssToken)
        var arbeidsforholdDtos = mutableListOf<ArbeidsforholdDto>()
        for (af in inbound) {
            log.warn("Eregoppslag " + af.arbeidsgiver?.organisasjonsnummer)
            var arbgivnavn = af.arbeidsgiver?.organisasjonsnummer
            if (af.arbeidsgiver?.type.equals(organisasjon)) {
                arbgivnavn = eregConsumer.hentOrgnavn(af.arbeidsgiver?.organisasjonsnummer).redigertnavn
            }
            arbeidsforholdDtos.add(ArbeidsforholdTransformer.toOutbound(af, arbgivnavn))
        }
        return arbeidsforholdDtos
    }

    fun hentEttArbeidsforholdmedId(fodselsnr: String, id: Int, fssToken: String): List<ArbeidsforholdDto> {
        val inbound = arbeidsforholdConsumer.hentArbeidsforholdmedId(fodselsnr, id, fssToken)
        var arbeidsforholdDtos = mutableListOf<ArbeidsforholdDto>()
        for (af in inbound) {
            log.warn("Eregoppslag " + af.arbeidsgiver?.organisasjonsnummer)
            var arbgivnavn = af.arbeidsgiver?.organisasjonsnummer
            if (af.arbeidsgiver?.type.equals(organisasjon)) {
                arbgivnavn = eregConsumer.hentOrgnavn(af.arbeidsgiver?.organisasjonsnummer).redigertnavn
            }
            arbeidsforholdDtos.add(ArbeidsforholdTransformer.toOutbound(af, arbgivnavn))
        }
        return arbeidsforholdDtos

    }
}