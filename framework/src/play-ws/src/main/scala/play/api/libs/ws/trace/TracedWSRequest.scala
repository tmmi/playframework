package play.api.libs.ws.trace

import play.api.libs.ws._

import scala.concurrent.Future
import scala.concurrent.duration.Duration
import play.api.libs.concurrent.Execution.Implicits.defaultContext

class TracedWSRequest(var delegate: WSRequest, outgoingRequestTrace: OutgoingRequestTrace) extends WSRequest {

  override def execute(): Future[WSResponse] = {
    outgoingRequestTrace.beforeRequestSent(this)
    val fResponse = delegate.execute()
    fResponse.map(response => {
      outgoingRequestTrace.afterResponseReceived(response)
      response
    })
  }

  override val auth: Option[(String, String, WSAuthScheme)] = delegate.auth
  override val calc: Option[WSSignatureCalculator] = delegate.calc
  override val url: String = delegate.url
  override val queryString: Map[String, Seq[String]] = delegate.queryString
  override val method: String = delegate.method
  override val followRedirects: Option[Boolean] = delegate.followRedirects
  override val body: WSBody = delegate.body
  override val requestTimeout: Option[Int] = delegate.requestTimeout
  override val virtualHost: Option[String] = delegate.virtualHost
  override val proxyServer: Option[WSProxyServer] = delegate.proxyServer
  override val headers: Map[String, Seq[String]] = delegate.headers

  override def withVirtualHost(vh: String): WSRequest = delegate.withVirtualHost(vh)

  override def withMethod(method: String): WSRequest = delegate.withMethod(method)

  override def withProxyServer(proxyServer: WSProxyServer): WSRequest = delegate.withProxyServer(proxyServer)

  override def withFollowRedirects(follow: Boolean): WSRequest = delegate.withFollowRedirects(follow)

  override def withBody(body: WSBody): WSRequest = delegate.withBody(body)

  override def withHeaders(hdrs: (String, String)*): WSRequest = delegate.withHeaders(hdrs: _*)

  override def withAuth(username: String, password: String, scheme: WSAuthScheme): WSRequest = delegate.withAuth(username, password, scheme)

  override def withQueryString(parameters: (String, String)*): WSRequest = delegate.withQueryString(parameters: _*)

  override def withRequestFilter(filter: WSRequestFilter): WSRequest = delegate.withRequestFilter(filter)

  override def sign(calc: WSSignatureCalculator): WSRequest = delegate.sign(calc)

  override def stream(): Future[StreamedResponse] = delegate.stream()

  override def withRequestTimeout(timeout: Duration): WSRequest = delegate.withRequestTimeout(timeout)
}

