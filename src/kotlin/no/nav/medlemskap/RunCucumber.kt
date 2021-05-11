package no.nav.medlemskap

import io.cucumber.core.cli.Main
import no.nav.medlemskap.client.shutdownLinkerdSidecar
import kotlin.system.exitProcess

val args = arrayOf(
    "--threads", "2",
    "-g", "no.nav.medlemskap",
    "-p", "pretty",
    "classpath:features",
    "--tags", "not @ignored "
)

fun main() {
    AuthenticationTest().runTests()
    Main.main(*args) //Cucumber tests
    shutdownLinkerdSidecar()
    exitProcess(0)
}