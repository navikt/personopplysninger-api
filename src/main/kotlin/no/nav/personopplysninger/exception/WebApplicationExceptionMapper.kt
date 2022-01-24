package no.nav.personopplysninger.exception

import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
class WebApplicationExceptionMapper : ExceptionMapper<WebApplicationException> {

    override fun toResponse(e: WebApplicationException): Response {
        return e.response
    }
}