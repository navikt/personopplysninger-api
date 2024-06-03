package no.nav.personopplysninger.util

fun <T> List<T>.firstOrNull(): T? {
    return if (isEmpty()) {
        null
    } else {
        first()
    }
}