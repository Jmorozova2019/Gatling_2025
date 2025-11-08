package otus
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.http.request.builder.HttpRequestBuilder
import scala.collection.immutable.Map

object Actions {

  val sentHeadersMainPage = Map(
    "accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
    "accept-encoding" -> "gzip, deflate",
    "accept-language" -> "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7",
    "connection" -> "keep-alive",
    "user-agent" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/140.0.0.0 Safari/537.36",
  )

  val mainPage: HttpRequestBuilder = http("UC_01_GetMainPage")
    .get("/webtours")
    .headers(sentHeadersMainPage)
    .check(status.is(200))

  val welcome: HttpRequestBuilder = http("UC_02_Welcome")
    .get("/cgi-bin/welcome.pl?signOff=true")
    .check(status.is(200))

  val nav: HttpRequestBuilder = http("UC_03_Nav")
    .get("/cgi-bin/nav.pl?in=home")
    .check(
      status.is(200),
      css("input[name~='userSession']", "value").saveAs("userSession")
  )

  val login: HttpRequestBuilder = http("UC_04_Login")
    .post("/cgi-bin/login.pl")
    .formParamSeq(Seq(
      ("username", "#{login}"),
      ("password", "#{password}"),
      ("login.x", "45"),
      ("login.y", "10"),
      ("JSFormSubmit", "off"),
      ("password", "#{password}"),
      ))
    .check(
      status is 200
      //,css("input[name~='userSession']", "value").exists
    )

  val reservations = http("UC_05_Reservations")
    .get("/cgi-bin/reservations.pl?page=welcome")
    //Выбрать случайные города отправления и прибытия и сохранить
    .check(
      status.is(200),
      css("select[name=\"depart\"]>option", "value").findRandom.saveAs("departCity"),
      css("select[name=\"arrive\"]>option", "value").findRandom.saveAs("arriveCity"),
      css("input[name=\"departDate\"]", "value").findRandom.saveAs("departDate"),
      css("input[name=\"returnDate\"]", "value").findRandom.saveAs("returnDate"),
      css("input[name=\"seatType\"]", "value").findRandom.saveAs("seatType"),
      css("input[name=\"seatPref\"]", "value").findRandom.saveAs("seatPref")
  )

  val selectFlight: HttpRequestBuilder = http("UC_06_Select_trace")
    .post("/cgi-bin/reservations.pl")
    .formParamSeq(Seq(
      ("advanceDiscount", "0"),
      ("depart", "#{departCity}"),
      ("departDate", "#{departDate}"),
      ("arrive", "#{arriveCity}"),
      ("returnDate", "#{returnDate}"),
      ("numPassengers", "1"),
      ("seatPref", "#{seatPref}"),
      ("seatType", "#{seatType}"),
      ("findFlights.x", "45"),
      ("findFlights.y", "10"),
      (".cgifields", "roundtrip"),
      (".cgifields", "seatType"),
      (".cgifields", "seatPref")
    ))
    .check(
      status is 200,
      css("input[name=\"outboundFlight\"]", "value").findRandom.saveAs("outboundFlight")
    )

  val payment: HttpRequestBuilder = http("UC_07_Payment")
    .post("/cgi-bin/reservations.pl")
    .formParamSeq(Seq(
      ("firstName", "#{firstName}"),
      ("lastName", "#{lastName}"),
      ("address1", "#{street}"),
      ("address2", "#{city}"),
      ("pass1", "#{firstName} #{lastName}"),
      ("creditCard", "#{creditCard}"),
      ("expDate", "#{expData}"),
      ("numPassengers", "1"),
      ("seatType", "#{seatType}"),
      ("seatPref", "#{seatPref}"),
      ("outboundFlight", "#{outboundFlight}"),
      ("advanceDiscount", "0"),
      ("JSFormSubmit", "off"),
      ("buyFlights.x", "45"),
      ("buyFlights.y", "10"),
      (".cgifields", "saveCC")
    ))
    .check(
      status is 200,
      bodyString.saveAs("RESPONSE_BODY")
    )

  val invoice: HttpRequestBuilder = http("UC_08_Invoice")
    .post("/cgi-bin/reservations.pl")
    .formParamSeq(Seq(
      ("Book Another.x", "45"),
      ("Book Another.y", "10")
    ))
    .check(
      status is 200,
      css("title").is("Flight Selections")
    )
}
