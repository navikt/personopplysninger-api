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
                status = "Gyldig",
                statusaarsak = "Gyldig",
                dekning = "Full",
                helsedel = true,
                medlem = false,
                lovvalgsland = "STORBRITANNIA",
                lovvalg = "Endelig",
                grunnlag = "Avtale - Storbritannia og Nord-Irland",
                sporingsinformasjon = null,
                studieinformasjon =null
        ))
        list.add(Medlemskapsunntak(
                ident = fnr,
                unntakId = 3209005,
                fraOgMed = LocalDate.parse("2012-01-01"),
                tilOgMed = LocalDate.parse("2012-12-31"),
                status = "Avvist",
                statusaarsak = "Avvist",
                dekning = "Folketrygdloven § 2-9, 1. ledd bokstav a",
                helsedel = true,
                medlem = false,
                lovvalgsland = "AFGHANISTAN",
                lovvalg = "Endelig",
                grunnlag = "Avtale - Australia",
                sporingsinformasjon = null,
                studieinformasjon =null
        ))
        list.add(Medlemskapsunntak(
                ident = fnr,
                unntakId = 3295894,
                fraOgMed = LocalDate.parse("2012-01-01"),
                tilOgMed = LocalDate.parse("2013-12-14"),
                status = "Uavklart",
                statusaarsak = "Feilregistrert",
                dekning = "Folketrygdloven § 2-9, annet ledd, jfr. 1. ledd bokstav a",
                helsedel = true,
                medlem = true,
                lovvalgsland = "NORGE",
                lovvalg = "Endelig",
                grunnlag = "Avtale - Storbritannia og Nord-Irland",
                sporingsinformasjon = null,
                studieinformasjon =null
        ))
        list.add(Medlemskapsunntak(
                ident = fnr,
                unntakId = 3294706,
                fraOgMed = LocalDate.parse("2012-01-01"),
                tilOgMed = LocalDate.parse("2013-10-10"),
                status = "Avvist",
                statusaarsak = "Feilregistrert",
                dekning = "Folketrygdloven § 2-6",
                helsedel = true,
                medlem = true,
                lovvalgsland = "NORGE",
                lovvalg = "Foreløpig",
                grunnlag = "Avtale - Storbritannia og Nord-Irland - 6.1",
                sporingsinformasjon = null,
                studieinformasjon =null
        ))
        return list
    }
}

