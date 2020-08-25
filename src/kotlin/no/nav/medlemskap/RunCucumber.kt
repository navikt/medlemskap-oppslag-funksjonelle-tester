package no.nav.medlemskap

import io.cucumber.core.cli.Main
import org.junit.runner.JUnitCore

val args = arrayOf(
    "--threads", "2",
    "-g", "no.nav.medlemskap",
    "-p", "pretty",
    "classpath:features",
    "--tags", "not @ignored "
)

fun main() {
    Main.main(*args)
    JUnitCore().run(AuthenticationTest::class.java)
}