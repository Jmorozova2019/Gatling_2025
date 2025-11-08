package otus
import io.gatling.core.Predef._
import io.gatling.core.Predef.{Simulation}
import otus.httpProtocol

class FMax extends Simulation{
    setUp(CommonScenario().inject(
      incrementUsersPerSec(1.0)//сколько юзеров в сек добавляется на каждом след уровне
      .times(8)//сколько ступеней
      .eachLevelLasting(60)//продолжительность ступени
      .separatedByRampsLasting(60)//рампы чтобы не было скачков
      .startingFrom(0.0)// начальная скорость, от которой начнутся шаги
      )
    )
      .protocols(httpProtocol)
      .maxDuration(24000)
}
