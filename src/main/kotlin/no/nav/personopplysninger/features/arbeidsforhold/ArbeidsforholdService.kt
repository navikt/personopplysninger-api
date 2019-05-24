package no.nav.personopplysninger.features.arbeidsforhold

import no.nav.personopplysninger.features.arbeidsforhold.dto.outbound.ArbeidsforholdDto
import no.nav.personopplysninger.features.arbeidsforhold.dto.transformer.ArbeidsforholdTransformer
import no.nav.personopplysninger.features.arbeidsforhold.dto.transformer.EnkeltArbeidsforholdTransformer
import no.nav.personopplysninger.features.ereg.EregOrganisasjon
import no.nav.personopplysninger.features.ereg.dto.outbound.Organisasjon
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
        return strippedToken
    }

    fun hentArbeidsforhold(fodselsnr: String, fssToken: String): List<ArbeidsforholdDto> {
        val inbound = arbeidsforholdConsumer.hentArbeidsforholdmedFnr(fodselsnr, fssToken)
        var arbeidsforholdDtos = mutableListOf<ArbeidsforholdDto>()
        for (arbeidsforhold in inbound) {
            var arbgivnavn = arbeidsforhold.arbeidsgiver?.organisasjonsnummer
            if (arbeidsforhold.arbeidsgiver?.type.equals(organisasjon)) {
                val organisasjon = eregConsumer.hentOrgnavn(arbeidsforhold.arbeidsgiver?.organisasjonsnummer)
                val navn = organisasjon.navn
                arbgivnavn = navn?.navnelinje1 + navn?.navnelinje2 + navn?.navnelinje3 + navn?.navnelinje4 + navn?.navnelinje5
            }
            arbeidsforholdDtos.add(ArbeidsforholdTransformer.toOutbound(arbeidsforhold, arbgivnavn))
        }
        return arbeidsforholdDtos
    }

    fun hentEttArbeidsforholdmedId(fodselsnr: String, id: Int, fssToken: String): ArbeidsforholdDto {
        val arbeidsforhold = arbeidsforholdConsumer.hentArbeidsforholdmedId(fodselsnr, id, fssToken)
        var arbeidsforholdDto: ArbeidsforholdDto
        var arbgivnavn = arbeidsforhold.arbeidsgiver?.organisasjonsnummer
        if (arbeidsforhold.arbeidsgiver?.type.equals(organisasjon)) {
            val organisasjon = eregConsumer.hentOrgnavn(arbeidsforhold.arbeidsgiver?.organisasjonsnummer)
            val navn = organisasjon.navn
            arbgivnavn = navn?.navnelinje1 + navn?.navnelinje2 + navn?.navnelinje3 + navn?.navnelinje4 + navn?.navnelinje5
        }
        arbeidsforholdDto = EnkeltArbeidsforholdTransformer.toOutbound(arbeidsforhold, arbgivnavn)
        return arbeidsforholdDto
    }
}