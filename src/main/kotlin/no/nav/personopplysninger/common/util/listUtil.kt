package no.nav.personopplysninger.common.util

fun <T> List<T>.firstOrNull(): T? {
    return if (isEmpty()) {
        null
    } else {
        first()
    }
}