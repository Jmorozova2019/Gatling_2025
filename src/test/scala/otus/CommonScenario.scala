package otus

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.http.request.builder.HttpRequestBuilder
import Feeders.usersFeeder


//шаг 3- создать сценарий с запросами (запросы можно повторять) и выполнить apply()
object CommonScenario {
  def apply() = new CommonScenario().scn
}

class  CommonScenario{
  val scn: ScenarioBuilder = scenario("Debug")
    .feed(usersFeeder)
    .exec(Actions.mainPage)
    .exec(Actions.welcome)
    .exec(Actions.nav)
    .exec(Actions.login)
    .exec(Actions.reservations)
    .exec(Actions.selectFlight)
    .exec(Actions.payment)
    .exec(Actions.invoice)
}