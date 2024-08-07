package com.kmp.template.server

import com.kmp.template.dto.MyDto
import com.kmp.template.routes.MyRouteParams
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
import com.kmp.template.routes.Routes

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
            get("/${Routes.MY_ROUTE.value}") {
                    logger.info("Send response")
                    val param = call.parameters[Routes.MY_ROUTE.params<MyRouteParams>().myParamName]
                    call.respond(MyDto("MyText: $param"))
            }

        }
    }.start(wait = true)
}

