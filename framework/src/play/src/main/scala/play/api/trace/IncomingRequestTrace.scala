package play.api.trace

import play.api.mvc.{ RequestHeader, Result }

trait IncomingRequestTrace {
  def beforeRequestHandled(rh: RequestHeader): Unit

  def afterRequestHandled(result: Result): Unit
}
