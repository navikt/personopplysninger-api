package no.nav.personopplysninger.features.featuretoggles

import no.finn.unleash.strategy.Strategy
import no.nav.sbl.util.EnvironmentUtils

import java.util.Arrays

import java.util.Optional.ofNullable

class ByApplicationStrategy : Strategy {

    override fun getName(): String {
        return "byApplication"
    }

    override fun isEnabled(parameters: Map<String, String>): Boolean {
        return ofNullable(parameters)
                .map { p -> p[APP_PARAMETER] }
                .map { appParameter -> appParameter!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray() }
                .map { this.match(it) }
                .orElse(false)
    }

    private fun match(activeApplications: Array<String>): Boolean {
        return EnvironmentUtils.getApplicationName()
                .map { applicationName -> Arrays.stream(activeApplications).anyMatch{ applicationName == it } }
                .orElse(false)
    }

    companion object {

        internal val APP_PARAMETER = "app"
    }

}
