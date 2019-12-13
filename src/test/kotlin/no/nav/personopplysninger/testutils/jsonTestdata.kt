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

