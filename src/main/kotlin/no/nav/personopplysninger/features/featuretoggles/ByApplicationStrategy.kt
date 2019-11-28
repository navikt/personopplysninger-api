package no.nav.personopplysninger.features.featuretoggles

import no.finn.unleash.strategy.Strategy
import no.nav.sbl.util.EnvironmentUtils

import java.util.Arrays

import java.util.Optional.ofNullable

class ByApplicationStrategy : Strategy {

    override fun getName(): String = "byApplication"

    override fun isEnabled(parameters: Map<String, String>): Boolean {
            return getThisAppName().let { appName ->
                parameters.getActiveAppNames().any { activeAppName ->
                    activeAppName == appName
                }
            }
    }

    private fun getThisAppName(): String? {
        return EnvironmentUtils.getApplicationName().orElse(null)
    }

    private fun Map<String, String>.getActiveAppNames(): List<String> {
        return get("app")
                ?.split(",")
                ?: emptyList()
    }

}
