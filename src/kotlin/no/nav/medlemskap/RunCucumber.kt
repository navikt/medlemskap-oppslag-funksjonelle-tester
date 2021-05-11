package no.nav.medlemskap

import io.cucumber.core.options.CommandlineOptionsParser
import io.cucumber.core.options.CucumberProperties
import io.cucumber.core.options.CucumberPropertiesParser
import io.cucumber.core.runtime.Runtime
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
    val exitStatus = cucumberMainMethodWithLinkerdShutdown()

    shutdownLinkerdSidecar()
    exitProcess(exitStatus.toInt())
}

private fun cucumberMainMethodWithLinkerdShutdown(): Byte {
    val propertiesFileOptions = CucumberPropertiesParser()
        .parse(CucumberProperties.fromPropertiesFile())
        .build()

    val environmentOptions = CucumberPropertiesParser()
        .parse(CucumberProperties.fromEnvironment())
        .build(propertiesFileOptions)

    val systemOptions = CucumberPropertiesParser()
        .parse(CucumberProperties.fromSystemProperties())
        .build(environmentOptions)

    val runtimeOptions = CommandlineOptionsParser()
        .parse(*args)
        .addDefaultGlueIfAbsent()
        .addDefaultFeaturePathIfAbsent()
        .addDefaultFormatterIfAbsent()
        .addDefaultSummaryPrinterIfAbsent()
        .build(systemOptions)


    val runtime = Runtime.builder()
        .withRuntimeOptions(runtimeOptions)
        .withClassLoader { Thread.currentThread().contextClassLoader }
        .build()

    runtime.run()
    val exitStatus = runtime.exitStatus()
    return exitStatus
}