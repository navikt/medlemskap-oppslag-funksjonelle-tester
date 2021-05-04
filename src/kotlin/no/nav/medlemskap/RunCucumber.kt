package no.nav.medlemskap

import io.cucumber.core.cli.Main
import org.junit.runner.JUnitCore
import kotlin.system.exitProcess

val args = arrayOf(
    "--threads", "2",
    "-g", "no.nav.medlemskap",
    "-p", "pretty",
    "classpath:features",
    "--tags", "not @ignored "
)

fun main() {
    println("main: Kjører tester")
    AuthenticationTest().runTests()
    Main.main(*args) //Cucumber tests
    exitProcess(0)
}