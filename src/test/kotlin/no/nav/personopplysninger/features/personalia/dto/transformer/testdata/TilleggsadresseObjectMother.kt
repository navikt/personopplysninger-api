package no.nav.personopplysninger.features.personalia.dto.transformer.testdata

import no.nav.tps.person.Tilleggsadresse

object TilleggsadresseObjectMother {
    val withValuesInAllFields = Tilleggsadresse(
            adresse1 = "dummy adr1",
            adresse2 = "dummy adr2",
            adresse3 = "dummy adr3",
            kilde = "kilde for Tilleggsadresse",
            datoFraOgMed = "dummy dato",
            bolignummer = "dummy bolignr",
            bydel = "dummy bydel",
            datoTilOgMed = "dummy dato",
            gateKode = "dummy gatekode",
            husbokstav = "dummy husbokstav",
            husnummer = "dummy husnr",
            kommunenummer = "dummy kommunenr",
            postboksanlegg = "dummy postboksanlegg",
            postboksnummer = "dummy postboksnr",
            postnummer = "2974"
    )

    val nullObject = Tilleggsadresse(
            adresse1 = null,
            adresse2 = null,
            adresse3 = null,
            kilde = null,
            datoFraOgMed = null,
            bolignummer = null,
            bydel = null,
            datoTilOgMed = null,
            gateKode = null,
            husbokstav = null,
            husnummer = null,
            kommunenummer = null,
            postboksanlegg = null,
            postboksnummer = null,
            postnummer = null
    )

}
