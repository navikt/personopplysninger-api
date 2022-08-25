package no.nav.personopplysninger.testutils

import java.io.InputStreamReader

object TestFileReader {
    fun readFile(filename: String): String {
        return InputStreamReader(this.javaClass.getResourceAsStream("/json/$filename")!!).readText()
    }
}