package no.nav.personopplysninger.consumerutils

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import javax.ws.rs.ProcessingException
import javax.ws.rs.core.Response

inline fun <reified T> Response.unmarshalBody(): T {
    return try {
        jacksonObjectMapper()
                .registerModule(JavaTimeModule())
                .run {
                    readValue(readEntity(String::class.java), T::class.java)
                }
    } catch (e: ProcessingException) {
        throw ConsumerException("Prosesseringsfeil på responsobjekt. Responsklasse: " + T::class.simpleName, e)
    } catch (e: IllegalStateException) {
        throw ConsumerException("Ulovlig tilstand på responsobjekt. Responsklasse: " + T::class.simpleName, e)
    } catch (e: Exception) {
        throw ConsumerException("Uventet feil på responsobjektet. Responsklasse: " + T::class.simpleName, e)
    } finally {
        close()
    }
}

inline fun <reified T> Response.unmarshalList(): List<T> {
    return try {
        jacksonObjectMapper()
                .registerModule(JavaTimeModule())
                .run {
                    val json = readEntity(String::class.java)
                    val type = typeFactory.constructCollectionLikeType(List::class.java, T::class.java)
                    readValue(json, type)
                }
    } catch (e: ProcessingException) {
        throw ConsumerException("Prosesseringsfeil på responsobjekt. Responsklasse: ", e)
    } catch (e: IllegalStateException) {
        throw ConsumerException("Ulovlig tilstand på responsobjekt. Responsklasse: ", e)
    } catch (e: Exception) {
        throw ConsumerException("Uventet feil på responsobjektet. Responsklasse: ", e)
    } finally {
        close()
    }
}

fun <T> Response.unmarshalList(responsklasse: Class<T>): List<T> {
    return try {
        jacksonObjectMapper()
                .registerModule(JavaTimeModule())
                .run {
                    val type = typeFactory.constructCollectionType(List::class.java, responsklasse)
                    readValue(readEntity(String::class.java), type)
                }
    } catch (e: ProcessingException) {
        throw ConsumerException("Prosesseringsfeil på responsobjekt. Responsklasse: ", e)
    } catch (e: IllegalStateException) {
        throw ConsumerException("Ulovlig tilstand på responsobjekt. Responsklasse: ", e)
    } catch (e: Exception) {
        throw ConsumerException("Uventet feil på responsobjektet. Responsklasse: ", e)
    } finally {
        close()
    }
}

