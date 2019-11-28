package no.nav.personopplysninger.features.featuretoggles

import no.finn.unleash.strategy.Strategy

import javax.inject.Provider
import javax.servlet.http.HttpServletRequest
import java.util.Arrays
import java.util.Collections

class ByQueryParamStrategy(private val httpServletRequestProvider: Provider<HttpServletRequest>) : Strategy {

    override fun getName(): String = "byQueryParam"

    override fun isEnabled(parameters: Map<String, String>): Boolean {

        val parameterValues = parameters.getParameterValues()
        val queryValues = parameters.getQueryValues()

        return parameterValues.any { parameterValue ->
            queryValues.contains(parameterValue)
        }
    }

    private fun  Map<String, String>.getParameterValues(): List<String> {
        return get("queryParam")?.let { queryParam ->
            httpServletRequestProvider.get().getParameterValues(queryParam)?.asList()
        }?: emptyList()
    }

    private fun  Map<String, String>.getQueryValues(): List<String> {
        return get("queryValues")
                ?.split(",")
                ?: emptyList()
    }

}
