package no.nav.personopplysninger.utils

fun <T> List<T>.firstOrNull(): T? {
    return if (isEmpty()) {
        null
    } else {
        first()
    }
}