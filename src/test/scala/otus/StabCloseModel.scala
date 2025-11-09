package otus

import io.gatling.core.Predef.{Simulation, rampConcurrentUsers, _}
import otus.httpProtocol
import scala.concurrent.duration.DurationInt


class StabCloseModel extends Simulation{
  setUp(CommonScenario().inject(
    rampConcurrentUsers(0).to(9).during(30 seconds), // Разгон, время подобрано, чтобы не было всплеска
    constantConcurrentUsers(9).during(60 minutes)// Постоянная нагрузка
  ).protocols(httpProtocol)
  ).assertions(global.successfulRequests.percent.gt(95))
}
