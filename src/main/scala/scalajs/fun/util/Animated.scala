/**
 * ****************************************************************** Simple
 * Animate step by step
 *
 * Pepe Gallardo, 2020.
 */

package scalajs.fun.util

import org.scalajs.dom
import org.scalajs.dom.html.Canvas

trait Animated extends Periodic:
  def step(elapsed: Double): Unit = {}

  def drawOn(g2D: Graphics2D): Unit = {}

  lazy val scale: Double

  val clearCanvas: Option[String] = Some("white")

  val canvas: Canvas = dom.document.getElementById("canvas").asInstanceOf[Canvas]
  private val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  val g2D: Graphics2D = Graphics2D(ctx)

  private lazy val (width, height) =
    val percent = 0.8
    ((percent * dom.window.innerWidth).toInt, (percent * dom.window.innerHeight).toInt)
  canvas.width = width
  canvas.height = height

  private lazy val xyScale = (width min height) * scale
  private val xOffset = width / 2
  private val yOffset = height / 2

  override final def onTick(elapsed: Double): Unit =
    step(elapsed)
    ctx.setTransform(1, 0, 0, 1, 0, 0)
    clearCanvas match
      case Some(color) =>
        ctx.fillStyle = color
        ctx.fillRect(0, 0, width, height)
      case None => ;
    ctx.translate(xOffset, yOffset)
    g2D.scale = xyScale
    drawOn(g2D)

  def title(title: String): Unit =
    dom.document.title = title
    val h3 = dom.document.createElement("h3")
    h3.innerText = title
    val controls = dom.document.getElementById("controls")
    controls.appendChild(h3)
