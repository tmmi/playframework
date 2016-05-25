package play.api.libs.ws.trace

import javax.inject.{ Inject, Named }

import play.api.libs.ws.{ WSClient, WSRequest }

class WSClientWithTrace @Inject() (@Named("baseWSClient") wsclient: WSClient, outgoingRequestTrace: OutgoingRequestTrace)
    extends WSClient {

  def underlying[T] = wsclient.asInstanceOf[T]

  override def url(url: String): WSRequest = {
    new TracedWSRequest(wsclient.url(url), outgoingRequestTrace)
  }

  def close() = super.close()
}
