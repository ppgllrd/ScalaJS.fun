/**
 * ****************************************************************** Simple
 * periodic objects in scalaJS
 *
 * Pepe Gallardo, 2020.
 */

package scalajs.fun.util

import org.scalajs.dom

trait Periodic:

  def onStart(): Unit = {}
  def onTick(elapsed: Double): Unit = {}
  def onStop(): Unit = {}

  def finished: Boolean = false

  private var handleOpt: Option[Int] = None

  private def request(): Unit =
    val handle = dom.window.requestAnimationFrame(frame)
    handleOpt = Some(handle)

  private def cancel(handle: Int): Unit =
    dom.window.cancelAnimationFrame(handle)
    handleOpt = None
    startTime = -1

  private var startTime: Double = -1

  private def frame(time: Double): Unit =
    if finished then
      stop()
    else
      val elapsed =
        if startTime < 0 then
          0
        else
          time - startTime
      startTime = time
      onTick(elapsed)
      request()

  private def go(): Unit =
    onStart()
    request()

  def start(): Unit =
    handleOpt match
      case None =>
        go()
      case Some(handle) =>
        stop()
        go()

  def stop(): Unit = handleOpt match
    case None =>
      ;
    case Some(handle) =>
      onStop()
      cancel(handle)
