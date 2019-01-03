package no.nav.personopplysninger.features.personalia

import no.nav.tps.person.Tilleggsadresse

object TilleggsadresseObjectMother {
    val allFieldsHaveValues = Tilleggsadresse(
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
            postnummer = "dummy postnr"
    )

}
