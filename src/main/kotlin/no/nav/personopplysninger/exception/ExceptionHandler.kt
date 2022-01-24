package no.nav.personopplysninger.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class ExceptionHandler: ExceptionMapper<Throwable> {

    private var logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun toResponse(e: Throwable): Response {
        logger.error(e.message, e)
        return Response.serverError().build()
    }
}