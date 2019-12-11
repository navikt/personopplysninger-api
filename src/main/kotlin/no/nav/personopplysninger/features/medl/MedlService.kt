package no.nav.personopplysninger.features.medl

import no.nav.personopplysninger.features.medl.domain.Medlemskapsunntak
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class MedlService @Autowired constructor(

) {
    fun hentMeldemskap(fnr: String): List<Medlemskapsunntak> {
        val list = ArrayList<Medlemskapsunntak>()
        list.add(Medlemskapsunntak(
                ident = fnr,
                unntakId = 3402759,
                fraOgMed = LocalDate.parse("2010-01-01"),
                tilOgMed = LocalDate.parse("2011-01-01"),
                status = "AVST",
                statusaarsak = "Feilregistrert",
                dekning = "Full",
                helsedel = true,
                medlem = false,
                lovvalgsland = "GBR",
                lovvalg = "ENDL",
                grunnlag = "Storbrit_NIrland",
                sporingsinformasjon = null,
                studieinformasjon =null
        ))
        list.add(Medlemskapsunntak(
                ident = fnr,
                unntakId = 3209005,
                fraOgMed = LocalDate.parse("2012-01-01"),
                tilOgMed = LocalDate.parse("2012-12-31"),
                status = "AVST",
                statusaarsak = "Avvist",
                dekning = "FTL_2-7_3_ledd_a",
                helsedel = true,
                medlem = false,
                lovvalgsland = "AFG",
                lovvalg = "ENDL",
                grunnlag = "Australia",
                sporingsinformasjon = null,
                studieinformasjon =null
        ))
        return list
    }
}

