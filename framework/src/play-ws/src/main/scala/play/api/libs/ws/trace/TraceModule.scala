package play.api.libs.ws.trace

import javax.inject.Singleton

import play.api.libs.ws.WSClient
import play.api.trace.IncomingRequestTrace
import play.api.{ Configuration, Environment }
import play.api.inject.Module

class TraceModule extends Module {
  def bindings(environment: Environment, configuration: Configuration) = {
    Seq(bind[IncomingRequestTrace].toInstance(null),
      bind[OutgoingRequestTrace].toInstance(null),
      bind[WSClient].to[WSClientWithTrace].in[Singleton])
  }
}
