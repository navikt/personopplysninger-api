package no.nav.personopplysninger.features.medl

import no.nav.personopplysninger.features.medl.domain.Medlemskapsunntak
import no.nav.personopplysninger.oppslag.kodeverk.KodeverkConsumer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class MedlService @Autowired constructor(
        private var kodeverkConsumer: KodeverkConsumer
) {
    fun hentMeldemskap(fnr: String): List<Medlemskapsunntak> {
        val list = ArrayList<Medlemskapsunntak>()
        list.add(Medlemskapsunntak(
                ident = fnr,
                unntakId = 3402759,
                fraOgMed = LocalDate.parse("2010-01-01"),
                tilOgMed = LocalDate.parse("2011-01-01"),
                status = "GYLD",
                statusaarsak = null,
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
                dekning = "FTL_2-9_1_ledd_a",
                helsedel = true,
                medlem = false,
                lovvalgsland = "AFG",
                lovvalg = "ENDL",
                grunnlag = "Australia",
                sporingsinformasjon = null,
                studieinformasjon =null
        ))
        list.add(Medlemskapsunntak(
                ident = fnr,
                unntakId = 3295894,
                fraOgMed = LocalDate.parse("2012-01-01"),
                tilOgMed = LocalDate.parse("2013-12-14"),
                status = "UAVK",
                statusaarsak = "Migrert",
                dekning = "FTL_2-9_2_ld_jfr_1a",
                helsedel = true,
                medlem = true,
                lovvalgsland = "NOR",
                lovvalg = "ENDL",
                grunnlag = "Storbrit_NIrland",
                sporingsinformasjon = null,
                studieinformasjon =null
        ))
        list.add(Medlemskapsunntak(
                ident = fnr,
                unntakId = 3294706,
                fraOgMed = LocalDate.parse("2012-01-01"),
                tilOgMed = LocalDate.parse("2013-10-10"),
                status = "AVST",
                statusaarsak = "Feilregistrert",
                dekning = "FTL_2-6",
                helsedel = true,
                medlem = true,
                lovvalgsland = "NOR",
                lovvalg = "FORL",
                grunnlag = "Storbrit_NIrland_6_1",
                sporingsinformasjon = null,
                studieinformasjon =null
        ))
        val dekningKV = kodeverkConsumer.hentDekningMedl()
        val grunnlagKV = kodeverkConsumer.hentGrunnlagMedl()
        val lovvalgKV = kodeverkConsumer.hentLovvalgMedl()
        val landKV = kodeverkConsumer.hentLandKoder()
        val statusKV = kodeverkConsumer.hentPeriodestatusMedl()
        val statusAarsakKV = kodeverkConsumer.hentStatusaarsakMedl()

        return list.map {
            it.copy(
                    dekning = dekningKV.term(it.dekning),
                    grunnlag = grunnlagKV.term(it.grunnlag),
                    lovvalg = lovvalgKV.term(it.lovvalg),
                    lovvalgsland = landKV.term(it.lovvalgsland),
                    status = statusKV.term(it.status),
                    statusaarsak = statusAarsakKV.term(it.statusaarsak)
            )
        }
    }
}

