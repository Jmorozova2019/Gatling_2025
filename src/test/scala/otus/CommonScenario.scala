package otus

import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import Feeders.usersFeeder


object CommonScenario {
  def apply() = new CommonScenario().scn
}

class  CommonScenario{
  val user = Feeders.usersFeeder

  val enter: ChainBuilder = group("enter")(
    feed(user)
      .exec(Actions.mainPage)
      .exec(Actions.welcome)
      .exec(Actions.nav)
      .exec(Actions.login)
  )

  val selectFlight: ChainBuilder = group("selectFlight")(
    feed(user)
      .exec(Actions.reservations)
      .exec(Actions.selectFlight)
  )

  val payment: ChainBuilder = group("payment")(
    feed(user)
      .exec(Actions.payment)
  )

  val toStartPage: ChainBuilder = group("toStartPage")(
    feed(user)
      .exec(Actions.invoice)
  )

  val scn: ScenarioBuilder = scenario("Debug")
    .feed(user)
      .exec(enter)
      .exec(selectFlight)
      .exec(payment)
      .exec(toStartPage)
}