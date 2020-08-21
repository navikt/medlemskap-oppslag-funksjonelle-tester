package no.nav.medlemskap

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import mu.KotlinLogging
import org.apache.http.impl.conn.SystemDefaultRoutePlanner
import java.net.ProxySelector

internal val apacheHttpClient = HttpClient(Apache) {
    install(JsonFeature) {
        serializer = JacksonSerializer {
            this.registerModule(JavaTimeModule())
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
        }
    }

    engine {
        socketTimeout = 45000

        customizeClient { setRoutePlanner(SystemDefaultRoutePlanner(ProxySelector.getDefault())) }
    }
}

internal val httpClient = HttpClient {
    install(JsonFeature) {
        serializer = JacksonSerializer {
            this.registerModule(JavaTimeModule())
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
        }
    }
    val sikkerlogg = KotlinLogging.logger("tjenestekall")
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) = sikkerlogg.info { message }
        }
        level = LogLevel.ALL
    }
}