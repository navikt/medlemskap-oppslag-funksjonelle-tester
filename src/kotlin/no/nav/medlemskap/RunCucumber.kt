package no.nav.medlemskap

import io.cucumber.core.cli.Main
//import mu.KotlinLogging

//private val log = KotlinLogging.logger {}

val args = arrayOf(
        "--threads", "2",
        "-g", "no.nav.medlemskap",
        "-p", "pretty",
        "classpath:features",
        "--tags", "not @ignored "
)

fun main() {
    Main.main(*args)
}