package no.nav.personopplysninger.kontaktinformasjon.mapper.testdata

import no.nav.personopplysninger.consumer.digdirkrr.dto.DigitalKontaktinformasjon
import no.nav.personopplysninger.consumer.digdirkrr.dto.SikkerDigitalPostkasse

val defaultSikkerDigitalPostkasse = SikkerDigitalPostkasse(
    adresse = "Testsvingen 51",
    leverandoerAdresse = "Testveien 15",
    leverandoerSertifikat = "leverandoersertifikat"
)

val defaultDigitalKontaktinfo = DigitalKontaktinformasjon(
    aktiv = true,
    spraak = "spraak",
    epostadresse = "hurra@test.no",
    kanVarsles = true,
    mobiltelefonnummer = "12345678",
    personident = "12345678",
    sikkerDigitalPostkasse = defaultSikkerDigitalPostkasse,
    reservert = false
)
