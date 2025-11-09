package otus
import io.gatling.core.Predef._
import io.gatling.core.Predef.{Simulation}
import otus.httpProtocol

import scala.concurrent.duration.DurationInt

class FMax extends Simulation{
  setUp(CommonScenario().inject(
    incrementConcurrentUsers(1)
      .times(12)
      .eachLevelLasting(60)
      .separatedByRampsLasting(60)
      .startingFrom(0)

  ).protocols(httpProtocol)
  )
    /*setUp(CommonScenario().inject(
      incrementUsersPerSec(1.0)//сколько юзеров в сек добавляется на каждом след уровне
      .times(8)//сколько ступеней
      .eachLevelLasting(60)//продолжительность ступени
      .separatedByRampsLasting(60)//рампы чтобы не было скачков
      .startingFrom(0.0)// начальная скорость, от которой начнутся шаги
      )
    )
      .protocols(httpProtocol)
      .maxDuration(24000)
     */
}
