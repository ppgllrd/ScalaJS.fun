/**
 * ******************************************************************
 *
 * Turtle Graphics
 *
 * Pepe Gallardo, 2024.
 */

package scalajs.fun.turtle

import scalajs.fun.util.{Animated, Graphics2D}

import scala.collection.*

case class Segment(x0: Double, y0: Double, x1: Double, y1: Double, width: Double, color: String)

type Color = String

class Turtle(private var segments: mutable.ArrayBuffer[Segment] = mutable.ArrayBuffer()):
  private var x = 0.0
  private var y = 0.0
  private var angle = 0.0
  private var isPenDown = true
  private var width = 2.0
  private var color = "black"

  def forward(d: Double): Unit =
    val x1 = x + d * math.cos(angle)
    val y1 = y - d * math.sin(angle)
    if isPenDown then
      segments.addOne(Segment(x, y, x1, y1, width, color))
    x = x1
    y = y1

  def right(a: Double): Unit =
    angle -= a

  def left(a: Double): Unit =
    angle += a

  def penUp(): Unit =
    isPenDown = false

  def penDown(): Unit =
    isPenDown = true

  def setWidth(w: Double): Unit =
    width = w

  def setColor(c: Color): Unit =
    color = c

  def getSegments: mutable.ArrayBuffer[Segment] =
    segments

  override def clone(): Turtle =
    val t = Turtle(segments)
    t.x = x
    t.y = y
    t.angle = angle
    t.isPenDown = isPenDown
    t.width = width
    t.color = color
    t

def hilbert(): mutable.ArrayBuffer[Segment] =
  def hilbert(t: Turtle, degree: Int, dist: Double, alpha: Double): Unit =
    if degree > 0 then
      t.right(alpha)
      hilbert(t, degree - 1, dist, -alpha)
      t.forward(dist)
      t.left(alpha)
      hilbert(t, degree - 1, dist, alpha)
      t.forward(dist)
      hilbert(t, degree - 1, dist, alpha)
      t.left(alpha)
      t.forward(dist)
      hilbert(t, degree - 1, dist, -alpha)
      t.right(alpha)

  val t = Turtle()
  t.penUp()
  t.right(math.Pi)
  t.forward(450)
  t.right(math.Pi / 2)
  t.forward(450)
  t.right(math.Pi / 2)
  t.penDown()
  hilbert(t, 6, 10, math.Pi / 2)
  t.getSegments

def tree(): mutable.ArrayBuffer[Segment] =
  def tree(t: Turtle, n: Double, cs: List[Color], a: Double): Unit =
    def rama(t: Turtle, dist: Double, esc: Double, rot: Double): Unit =
      t.forward(dist * n)
      t.right(rot)
      tree(t, esc * n, cs.tail, (a - 1) max 1)

    if n > 2 then
      t.setColor(cs.head)
      t.setWidth(a)
      val Array(t1, t2, t3) = Array.fill(3)(t.clone)
      t.forward(n)
      rama(t1, 0.60, 0.76, math.Pi / 6)
      rama(t2, 0.55, 0.33, math.Pi / 4)
      rama(t3, 0.40, 0.50, -math.Pi / 3)

  val ss = List.range(0, 244, 20)
  val colores = (for x <- ss yield s"rgb(${255 - x}, $x, 0)") ++
    (for x <- ss yield s"rgb(0, ${255 - x}, $x)")

  val t = Turtle()
  t.penUp()
  t.right(math.Pi / 2)
  t.forward(200)
  t.left(math.Pi)
  t.penDown()
  tree(t, 400, colores.drop(3), 10)
  t.getSegments

object Turtle extends Animated:
  override lazy val scale: Double = 0.001

  private val segments = tree().toArray
  private val stepSize = (segments.length / 500) max 1
  private var index = -1

  title("Turtle Graphics")

  override def step(elapsed: Double): Unit =
    index = (index + stepSize) min segments.length

  override def drawOn(g2D: Graphics2D): Unit =
    for i <- 0 until index do
      g2D.ctx.beginPath()
      val s = segments(i)
      g2D.ctx.strokeStyle = s.color
      g2D.ctx.lineWidth = s.width
      g2D.ctx.moveTo(s.x0, s.y0)
      g2D.ctx.lineTo(s.x1, s.y1)
      g2D.ctx.stroke()
