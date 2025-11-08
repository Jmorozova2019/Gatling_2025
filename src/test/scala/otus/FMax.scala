package otus
import io.gatling.core.Predef._
import io.gatling.core.Predef.{Simulation}
import otus.httpProtocol
import io.gatling.http.Predef._

/*
Реализовать ступенчатый тест от 0 до 100% (0 RPS и до момента деградации системы —
увеличение числа ошибок или времени отклика операций)
  с шагом в 10% (от найденного максимума), используя Gatling длительность теста не меньше 20 минут).
    В результате должен быть код сценария + html-отчет о тестах (или ссылка на Grafana).

Реализовать тест надежности от 80% от максимальной производительности в течение часа.
Assertions на тесте надежности
Использование forever + maxDuration
Вынос переменных в класс одноименный названию пакета или использование SimulationConfig из https://github.com/Tinkoff/gatling-picatinny
*/

class FMax extends Simulation{
    setUp(CommonScenario().inject(
      incrementUsersPerSec(1.0)//сколько юзеров в сек добавляется на каждом след уровне
      .times(13)//сколько ступеней
      .eachLevelLasting(80)//продолжительность ступени
      .separatedByRampsLasting(80)//рампы чтобы не было скачков
      .startingFrom(0.0)// начальная скорость, от которой начнутся шаги
      )
    )
      .protocols(httpProtocol)
      .maxDuration(26000)// примерно вдвое дольше, чем продолжительность теста
}
