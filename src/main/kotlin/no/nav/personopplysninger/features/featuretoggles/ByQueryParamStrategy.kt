package no.nav.personopplysninger.features.featuretoggles

import no.finn.unleash.strategy.Strategy

import javax.inject.Provider
import javax.servlet.http.HttpServletRequest
import java.util.Arrays
import java.util.Collections

class ByQueryParamStrategy(private val httpServletRequestProvider: Provider<HttpServletRequest>) : Strategy {

    override fun getName(): String {
        return "byQueryParam"
    }

    override fun isEnabled(parameters: Map<String, String>): Boolean {
        val queryParam = parameters["queryParam"]
        val parameterValues = httpServletRequestProvider.get().getParameterValues(queryParam)
        val queryValues = (parameters as java.util.Map<String, String>).getOrDefault("queryValues", "").split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        return parameterValues != null && queryValues != null && !Collections.disjoint(Arrays.asList(*parameterValues), Arrays.asList(*queryValues))
    }

}
