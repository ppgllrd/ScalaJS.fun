/**
 * ******************************************************************
 *
 * Kaleido
 *
 * Pepe Gallardo, 2024.
 */

package scalajs.fun.kaleido

import org.scalajs.dom
import scalajs.fun.util.{Animated, Graphics2D}

import scala.math.*

case class Coordinate(x: Double, y: Double)

def star(slowTime: Double, alpha: Double): Coordinate =
  val sl = sin(slowTime)
  val l = alpha * (sl + 1)
  val s = sin(l)
  val c = cos(l)
  val x = 2 * cos(alpha * c + l)
  val y = 2 * abs(sin(sl * s - l))
  Coordinate(x, y)

val colors = List("yellow", "orange", "red", "purple", "blue", "green")

enum Shape:
  case Poly(vertices: List[Coordinate])
  case WithColor(color: String, shape: Shape)
  case Rotated(alpha: Double, shape: Shape)

def drawShape(ctx: dom.CanvasRenderingContext2D, shape: Shape): Unit =
  shape match
    case Shape.Poly(vertices) =>
      ctx.beginPath()
      ctx.moveTo(vertices.head.x, vertices.head.y)
      for vertex <- vertices.tail do
        ctx.lineTo(vertex.x, vertex.y)
      ctx.closePath()
      ctx.fill()
    case Shape.WithColor(color, shape) =>
      ctx.fillStyle = color
      drawShape(ctx, shape)
    case Shape.Rotated(alpha, shape) =>
      ctx.rotate(alpha)
      drawShape(ctx, shape)
      ctx.rotate(-alpha)

def kaleido(time: Double, n: Int): List[Shape] =
  val slowTime = time / 5
  val rads = List.range(0, n).map(i => 2 * Pi * i / n)
  val stars = rads.map(alpha => star(slowTime, alpha))
  val polys = rads.zip(colors).map((alpha, color) =>
    Shape.Rotated(
      Pi * sin(slowTime),
      Shape.WithColor(color, Shape.Rotated(alpha, Shape.Poly(stars)))
    )
  )
  polys

object Kaleido extends Animated:
  title("Kaleido")

  private var time = 0.0
  override def step(elapsed: Double): Unit =
    time = time + elapsed

  override lazy val scale: Double = 1.0 / 6

  override def drawOn(g2D: Graphics2D): Unit =
    for shape <- kaleido(time / 1000, 6) do
      drawShape(g2D.ctx, shape)
