/**
 * ******************************************************************
 *
 * Sierpinski triangle
 *
 * Pepe Gallardo, 2020.
 */

package scalajs.fun.sierpinski

import scalajs.fun.util.{Animated, Graphics2D}

object Sierpinski extends Animated:

  private case class Point(x: Double, y: Double)

  private val h = math.sqrt(3) / 2
  private val vertices = Array(Point(-0.5, h / 2), Point(0.5, h / 2), Point(0, -h / 2))

  private var point = vertices(0)

  private def center(p1: Point, p2: Point): Point =
    val x = (p1.x + p2.x) / 2
    val y = (p1.y + p2.y) / 2
    Point(x, y)

  private val colors =
    Array("red", "green", "blue", "black", "purple", "orange", "brown")

  private val color = colors(scala.util.Random.nextInt(colors.length))

  title("Sierpinski Triangle")

  override val clearCanvas: Option[String] = None

  override lazy val scale: Double = 1

  override def drawOn(g2D: Graphics2D): Unit =
    val ctx = g2D.ctx
    ctx.fillStyle = color
    for i <- 0 until 100 do
      ctx.fillRect(point.x, point.y, 0.0005, 0.0005)
      val i = scala.util.Random.nextInt(3)
      point = center(point, vertices(i))
