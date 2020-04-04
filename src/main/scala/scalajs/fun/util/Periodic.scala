package scalajs.fun.util

import org.scalajs.dom

trait Periodic {
  val Hz: Int

  def onStart(): Unit = {}
  def onTick(): Unit = {}
  def onStop(): Unit = {}

  def finished: Boolean = false

  private var handleOpt: Option[Int] = None
  private def go(): Unit = {
    onStart()
    val timeout = 1000.0 / Hz
    val handle = dom.window.setInterval(() => {
      if(finished)
        stop()
      else
        onTick()
    }, timeout)
    handleOpt = Some(handle)
  }

  def start(): Unit = {
    handleOpt match {
      case None =>
        go()
      case Some(handle) =>
        stop()
        go()
    }
  }

  def stop(): Unit = handleOpt match {
    case None =>
      ;
    case Some(handle) =>
      onStop()
      dom.window.clearInterval(handle)
      handleOpt = None
  }
}
