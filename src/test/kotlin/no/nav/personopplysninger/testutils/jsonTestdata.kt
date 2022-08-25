package no.nav.personopplysninger.testutils

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

fun pdlJson() = """
            {
              "data": {
                "person": {
                  "telefonnummer": [
                    {
                      "landskode": "+47",
                      "nummer": "22334455",
                      "prioritet": 1,
                      "metadata": {
                        "opplysningsId": "b2cf4a5c-99e9-46e5-88d9-65d79aee3bb0"
                      }
                    }
                  ],
                  "kontaktadresse": [
                    {
                      "gyldigFraOgMed": "2020-03-24T00:00",
                      "gyldigTilOgMed": null,
                      "type": "Innland",
                      "coAdressenavn": null,
                      "postboksadresse": null,
                      "vegadresse": null,
                      "postadresseIFrittFormat": {
                        "adresselinje1": "Linjeveien 1",
                        "adresselinje2": "1234 LINJE",
                        "adresselinje3": "Norge",
                        "postnummer": null
                      },
                      "utenlandskAdresse": null,
                      "utenlandskAdresseIFrittFormat": null,
                      "folkeregistermetadata": {
                        "ajourholdstidspunkt": null,
                        "gyldighetstidspunkt": "2020-03-24T00:00",
                        "opphoerstidspunkt": null,
                        "kilde": "KILDE_DSF",
                        "aarsak": null,
                        "sekvens": null
                      },
                      "metadata": {
                        "opplysningsId": "abcd1234-1234-abcd-1234-123456abcdef",
                        "master": "Freg",
                        "endringer": [
                          {
                            "type": "OPPRETT",
                            "registrert": "2020-04-24T13:07:20",
                            "registrertAv": "Folkeregisteret",
                            "systemkilde": "FREG",
                            "kilde": "KILDE_DSF"
                          }
                        ],
                        "historisk": false
                      }
                    }
                  ]
                }
              }
            }
        """.trimIndent()

fun telefonnummerJson() = """
            {
              "landskode": "+47",
              "nummer": "22334455",
              "type": "MOBIL"
            }
        """.trimIndent()