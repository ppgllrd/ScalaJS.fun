/**
 * ****************************************************************** Simple
 * Animate step by step
 *
 * Pepe Gallardo, 2020.
 */

package scalajs.fun.util

import org.scalajs.dom
import org.scalajs.dom.html.Canvas

trait Animated:
  def step(elapsed: Double): Unit
  def drawOn(g2D: Graphics2D): Unit
  val scale: Double
  val clearCanvas: Option[String] = Some("white")

class Animation(val animated: Animated) extends Periodic:

  val canvas: Canvas = dom.document.getElementById("canvas").asInstanceOf[Canvas]
  private val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  val g2D: Graphics2D = Graphics2D(ctx)

  private val (width, height) =
    val percent = 0.8
    ((percent * dom.window.innerWidth).toInt, (percent * dom.window.innerHeight).toInt)
  canvas.width = width
  canvas.height = height

  private val scale = (width min height) * animated.scale
  private val xOffset = width / 2
  private val yOffset = height / 2

  override def onTick(elapsed: Double): Unit =
    animated.step(elapsed)
    ctx.setTransform(1, 0, 0, 1, 0, 0)
    animated.clearCanvas match
      case Some(color) =>
        ctx.fillStyle = color
        ctx.fillRect(0, 0, width, height)
      case None => ;
    ctx.translate(xOffset, yOffset)
    g2D.scale = scale
    animated.drawOn(g2D)

