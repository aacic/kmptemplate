package com.kmp.template.server

import com.kmp.template.dto.MyDto
import io.ktor.http.HttpHeaders
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.routing.routing
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val logger: Logger = LoggerFactory.getLogger("logger")

fun main() {

    val port = (System.getenv("PORT") ?: "5000").toInt()
    embeddedServer(Netty, port = port) {
        install(ContentNegotiation) {
            json()
        }

        install(CORS) {
            allowHeader(HttpHeaders.ContentType)
            anyHost() // This should be limited in production environment
        }

        routing {
            get("/myroute") {
                    logger.info("Send response")
                    call.respond(MyDto("MyText"))
            }

        }
    }.start(wait = true)
}

