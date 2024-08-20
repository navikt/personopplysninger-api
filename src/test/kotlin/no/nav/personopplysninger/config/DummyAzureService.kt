package no.nav.personopplysninger.config

import no.nav.tms.token.support.azure.exchange.AzureService

class DummyAzureService : AzureService {
    override suspend fun getAccessToken(targetApp: String): String {
        return ""
    }
}