package no.nav.personopplysninger.testutils

fun readJsonFile(name: String): String {
    return {}.javaClass.getResource(name)!!.readText()
}
