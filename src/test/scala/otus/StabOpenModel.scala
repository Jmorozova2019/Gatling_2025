package otus
import io.gatling.core.Predef.{Simulation, atOnceUsers, rampUsers, _}
import otus.httpProtocol


class StabOpenModel extends Simulation{
  setUp(CommonScenario().inject(
    rampUsers(10).during(30),
    constantUsersPerSec(10).during(3600)
    ).protocols(httpProtocol)
  ).assertions(global.successfulRequests.percent.gt(95))
}
