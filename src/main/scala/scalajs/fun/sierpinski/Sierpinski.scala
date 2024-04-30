/**
 * ******************************************************************
 *
 * Sierpinski triangle
 *
 * Pepe Gallardo, 2020.
 */

package scalajs.fun.sierpinski

import org.scalajs.dom
import org.scalajs.dom.html.Canvas
import scalajs.fun.util.{Graphics2D, Periodic}

object Sierpinski extends Periodic:
  val canvas: Canvas = dom.document.getElementById("canvas").asInstanceOf[Canvas]
  private val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  private val g2D: Graphics2D = Graphics2D(ctx)

  private val (width, height) =
    val percent = 0.8
    ((percent * dom.window.innerWidth).toInt, (percent * dom.window.innerHeight).toInt)
  canvas.width = width
  canvas.height = height

  private val scale = (width min height)
  private val xOffset = width / 2
  private val yOffset = height / 2

  ctx.setTransform(1, 0, 0, 1, 0, 0)
  ctx.fillStyle = "white"
  ctx.fillRect(0, 0, width, height)
  ctx.translate(xOffset, yOffset)
  g2D.scale = scale

  private case class Point(x: Double, y: Double)

  private val h = math.sqrt(3) / 2
  private val points = Array(Point(-0.5, h / 2), Point(0.5, h / 2), Point(0, -h / 2))

  private var point = points(0)

  private def center(p1: Point, p2: Point): Point =
    val x = (p1.x + p2.x) / 2
    val y = (p1.y + p2.y) / 2
    Point(x, y)

  private val colors =
    Array("red", "green", "blue", "black", "purple", "orange", "brown")

  private val color = colors(scala.util.Random.nextInt(colors.length))

  val h3 = dom.document.createElement("h3")
  h3.innerText = "Sierpinsky Triangle"
  val controls = dom.document.getElementById("controls")
  controls.appendChild(h3)

  override def onTick(time: Double): Unit =
    ctx.fillStyle = color
    for i <- 1 to 50 do
      g2D.ctx.fillRect(point.x, point.y, 0.0005, 0.0005)
      val i = scala.util.Random.nextInt(3)
      point = center(point, points(i))
