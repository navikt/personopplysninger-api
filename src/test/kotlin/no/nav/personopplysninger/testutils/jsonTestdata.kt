package no.nav.personopplysninger.testutils

fun testklasseJson() = """
            {
                "dato": "1900-01-01",
                "tekst": "foo bar"
            }
        """.trimIndent()

fun tpsNavnJson() = """
            {
                "kortNavn": "AMIZIC VINAYAGUM-MASK",
                "fornavn": "VINAYAGUM-MASK",
                "etternavn": "AMIZIC"
            }
        """.trimIndent()

fun instJson() = """
            {
                "organisasjonsnummer": "00974707330",
                "institusjonsnavn": "SAGENEHJEMMET AS",
                "institusjonstype": "FO",
                "kategori": "F",
                "startdato": "2019-11-30",
                "faktiskSluttdato": "2019-11-30",
                "registreringstidspunkt": "2019-10-29T14:12:48.113"
            }
        """.trimIndent()

fun medlJson() = """
            {
                "unntakId": 3402759,
                "ident": "11111111111",
                "fraOgMed": "2010-01-01",
                "tilOgMed": "2011-01-01",
                "status": "AVST",
                "statusaarsak": "Feilregistrert",
                "dekning": "Full",
                "helsedel": true,
                "medlem": false,
                "lovvalgsland": "GBR",
                "lovvalg": "ENDL",
                "grunnlag": "Storbrit_NIrland",
                "sporingsinformasjon": {
                    "versjon": 2,
                    "registrert": "2015-06-06",
                    "besluttet": "2015-06-06",
                    "kilde": "srvgosys",
                    "kildedokument": "Dokument",
                    "opprettet": "2015-06-06T16:18:08.000223",
                    "opprettetAv": "S113611",
                    "sistEndret": "2015-06-06T16:18:08.000223",
                    "sistEndretAv": "S113611"
                }
            }
        """.trimIndent()

fun endringJson() = """
            {
                "endringstype": "OPPRETT",
                "ident": "12345678910",
                "lineage": "cdbd4444-f851-4adb-b22f-f8794825bb22",
                "opplysningsId": null,
                "status": {
                    "endringId": 2113,
                    "statusType": "DONE"
                },
                "innmeldtEndring": {"kilde": "BRUKER SELV", "utenlandskKontoInformasjon":null, "value": "11112233333"}
            }
        """.trimIndent()

fun utenlandskKontonummerJson() = """
            {
                "@type": "KONTONUMMER",
                "utenlandskKontoInformasjon": {
                    "landkode": "SWE",
                    "valuta": "EURO",
                    "SWIFT": "1234"
                },
                "value": "11112233333"
            }
        """.trimIndent()


