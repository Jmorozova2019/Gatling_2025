package otus

import io.gatling.core.Predef._

//Профиль нагрузки
object Debug extends Simulation{
    setUp(
      CommonScenario() //действия пользователя
        .inject(atOnceUsers(1))//как и сколько пользователей запускать
    ).protocols(otus.httpProtocol) //куда и с какими настройками
}

