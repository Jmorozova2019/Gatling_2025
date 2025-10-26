package otus

import io.gatling.core.Predef.{configuration, csv}


object Feeders {
    val usersFeeder = csv("users.csv").circular
}
