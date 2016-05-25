package play.filters.track

import javax.annotation.Nullable
import javax.inject.Inject

import play.api.mvc.{ EssentialAction, EssentialFilter, RequestHeader }
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.trace.IncomingRequestTrace

class TrackingFilter @Inject() (@Nullable incomingRequestTrace: IncomingRequestTrace) extends EssentialFilter {

  def apply(next: EssentialAction) = new EssentialAction {
    def apply(rh: RequestHeader) = {
      incomingRequestTrace.beforeRequestHandled(rh)
      next(rh).map(
        result => {
          incomingRequestTrace.afterRequestHandled(result)
          result
        }
      )
    }
  }
}
