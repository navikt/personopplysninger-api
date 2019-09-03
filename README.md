[![CircleCI](https://circleci.com/gh/navikt/personopplysninger-api.svg?style=svg&circle-token=deaa249d91e9850bdcf347c0aa29240c50d76e3a)](https://circleci.com/gh/navikt/personopplysninger-api)

# Personopplysninger-API

Spring Boot backend som skal gi brukeren innsikt i informasjonen NAV har lagret. <br>
Applikasjonen har ingen avhengigheter til nexus og kan kjøres lokalt på laptop.


## Deployering

Applikasjonen bygges automatisk til dev / https://www-q0.nav.no/person/personopplysninger-api ved merge til master eller ved manuell godkjenning i [CircleCI](https://circleci.com/gh/navikt/workflows/personopplysninger-api). <br><br>
For å lansere applikasjonen til produksjon / https://www.nav.no/person/personopplysninger-api, knytt en commit til en [Git tag](https://git-scm.com/book/en/v2/Git-Basics-Tagging):

```
git tag -a vX.X.X -m "Din melding"
```

Push den nye versjonen til GitHub og merge til master.

```
git push --tags
```

Godkjenn produksjonssettingen i [CircleCI](https://circleci.com/gh/navikt/workflows/personopplysninger-api).

## Lokalt Kjøring

For å kjøre opp løsningen lokalt <br>
Kjør [TestLauncher](src/test/java/no/nav/personopplysninger/api/TestLauncher.java).

## Teste endepunkter i Q

Endepunkter for å oppdatere brukeropplysninger kan gjøres via CLI med følgende kommando:

```
curl --insecure -XPOST "https://www-q0.nav.no/person/personopplysninger-api/endreTelefonnummer" -H "accept: application/json" -H "Authorization: Bearer OIDC-TOKEN" -H "Nav-Call-Id: 123456" -H "Nav-Consumer-Token: Bearer STS-TOKEN" -H "Nav-Consumer-Id: personbruker-personopplysninger-api" -H "Nav-Personident: FNR" -H "Content-Type: application/json" -d "{ \"landskode\": \"+47\", \"nummer\": 12345678, \"type\": \"MOBIL\"}"
```

Erstatt følgende:
* OIDC-TOKEN med cookie "selvbetjening-idtoken" etter å ha logget på https://www-q0.nav.no/person/personopplysninger.
* FNR for pålogget bruker.
* STS-TOKEN for systembruker. Kan hentes ut via kall:

```
curl --insecure --user srvpersonopplysnin:PASSORD "https://security-token-service.nais.preprod.local/rest/v1/sts/token?grant_type=client_credentials&scope=openid" --header "accept: application/json"
```

* PASSORD slås opp i vault.


## Logging

Feil ved API-kall blir logget via frontendlogger og vises i Kibana<br>
[https://logs.adeo.no](https://logs.adeo.no/app/kibana#/discover/ad01c200-4af4-11e9-a5a6-7fddb220bd0c)

## Henvendelser

Spørsmål knyttet til koden eller prosjektet kan rettes mot https://github.com/orgs/navikt/teams/personbruker

## For NAV-ansatte

Interne henvendelser kan sendes via Slack i kanalen #team-personbruker.

