package play.api.libs.ws.trace

import play.api.libs.ws.WSResponse

trait OutgoingRequestTrace {
  def afterResponseReceived(response: WSResponse)

  def beforeRequestSent(request: TracedWSRequest)
}
