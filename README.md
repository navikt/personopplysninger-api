# Lokalt oppsett

For å kjøre opp løsningen lokalt:

## Konfigurasjon i IntelliJ

Sett "dev" som utviklingsprofil. Dette for å få riktige konfigurasjoner fra Fasit.

Edit Configurations -> Active Profiles -> skriv "dev"

Kjør [main-metoden](src/test/java/no/nav/personopplysninger/api/TestLauncher.java)


## Bygging og publisering

For å bygge imaget, kjør `sh build.sh`. Se `sh build.sh --help` for alternativer.

Jenkins-bygg: https://ci.adeo.no/job/team_personbruker/job/personopplysninger-api/

Man deployer de ulike branchene fra oversikten ved å klikke inn på branchen som skal deployes,
og velge "Configure". I pipeline-scriptet litt ned på siden velger man ønsket miljø i "miljo".
Dette må endres hver gang man deployer.

Vi har applikasjonen i Q0, Q1, og T6.

# Deploy fra lokal kommandolinje

Vi bruker scriptet på https://github.com/navikt/personbruker-felles for lokal deploy.

Kommando: ../personbruker-felles/nais/deploy.sh all personopplysninger-api <applikasjon> <miljø>

(Forutsetter at man står i rot-mappen for applikasjonen, og personbruker-felles ligger på samme
nivå)

Merk: Filen 15-appdynamics.sh må ha Unix-linjeskift for at deploy skal fungere OK lokalt.

# Produksjonssetting

Ønsket versjon må ligge i Q0, se steget over. Man kjører så release-jobben fra Jenkins, som deployer 
samme versjon til produksjon.

# Henvendelser

Spørsmål knyttet til koden eller prosjektet kan rettes mot https://github.com/orgs/navikt/teams/personbruker

## For NAV-ansatte

Interne henvendelser kan sendes via Slack i kanalen #team-personbruker.
