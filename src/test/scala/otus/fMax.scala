package otus
import io.gatling.core.Predef._
import io.gatling.core.Predef.{Simulation, atOnceUsers}
import io.gatling.core.Predef.{Simulation, constantUsersPerSec, rampUsers}
import otus.httpProtocol

/*
Реализовать ступенчатый тест от 0 до 100% (0 RPS и до момента деградации системы — увеличение числа ошибок или времени отклика операций)
  с шагом в 10% (от найденного максимума), используя Gatling (длительность теста любая, лучше не меньше 20 минут).
    В результате должен быть код сценария + html-отчет о тестах (или ссылка на Grafana).
Реализовать тест надежности от 80% от максимальной производительности в течение часа.
*/
class fMax extends Simulation{
    setUp(CommonScenario().inject(
     incrementUsersPerSec(1.0)//сколько юзеров в сек добавляется на каждом сле уровне
       .times(25)//сколько ступеней
       .eachLevelLasting(30)//продолжительность ступени
       .separatedByRampsLasting(5)//рампы чтобы не было скачков
       .startingFrom(0.0)// начальная скорость, от которой начнуться шаги
      )
    )
      .protocols(httpProtocol)
      .maxDuration(750)// примерно вдвое дольше, чем продолжительность теста
}
