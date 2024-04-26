/**
 * ****************************************************************** A body
 * subject to gravitational forces
 *
 * Pepe Gallardo, 2020. Based on Java code by Sedgewick and Kevin Wayne
 */

package scalajs.fun.gravity

import scala.math.*

object Body:
  val G = 6.67e-11

class Body(
    private var pos: Vector2D,
    private var vel: Vector2D,
    val mass: Double,
    val radius: Double
):

  def move(f: Vector2D, dt: Double): Unit =
    val acc = f / mass
    vel += acc * dt
    pos += vel * dt

  def forceFrom(that: Body): Vector2D =
    val delta = that.pos - this.pos
    val dist = delta.modulus
    val f = (Body.G * this.mass * that.mass) / pow(dist, 2)
    delta.direction * f

  def drawOn(g2D: Graphics2D): Unit =
    val ctx = g2D.ctx
    ctx.beginPath()
    ctx.arc(pos.x, pos.y, radius, 0, 2 * Pi, false)
    ctx.fillStyle = "#00BB00"
    ctx.fill()
    ctx.lineWidth = 2.5 / g2D.scale
    ctx.strokeStyle = "#0000FF"
    ctx.stroke()
    ctx.closePath()
