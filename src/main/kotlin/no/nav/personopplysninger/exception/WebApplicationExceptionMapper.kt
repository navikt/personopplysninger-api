package no.nav.personopplysninger.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class WebApplicationExceptionMapper: ExceptionMapper<WebApplicationException> {

    private var logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun toResponse(e: WebApplicationException): Response {
        logger.error(e.message, e)
        return e.response
    }
}