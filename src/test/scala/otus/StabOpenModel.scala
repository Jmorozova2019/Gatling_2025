package otus
import io.gatling.core.Predef.{Simulation, atOnceUsers, rampUsers, _}
import otus.httpProtocol

import scala.concurrent.duration.DurationInt



class StabOpenModel extends Simulation{
  val level_duration = 10
  setUp(CommonScenario().inject(
      incrementUsersPerSec(1.0/otus.fMaxLevelDuration)
        .times(5)
        .eachLevelLasting(otus.fMaxLevelDuration)
        .separatedByRampsLasting(otus.fMaxLevelDuration)
        .startingFrom(10)
    )
  )
  //setUp(CommonScenario().inject(//закрытая модель
 //   rampConcurrentUsers(0).to(10).during(10 seconds), // Разгон
  //  constantConcurrentUsers(10).during(1 minutes)// Постоянная нагрузка
  //  ).protocols(httpProtocol)
 // ).assertions(global.successfulRequests.percent.gt(95))
}
