/********************************************************************
 * A body subject to gravitational forces
 *
 * Pepe Gallardo, 2020.
 * Based on Java code by Sedgewick and Kevin Wayne
 *******************************************************************/

package scalajs.fun.gravity

import scala.math._

class Body( private var pos : Vector
          , private var vel : Vector
          , val mass : Double
          , val radius : Double
          ) {

  def move(f : Vector, dt : Double) {
    val a = f * (1/mass)
    vel += a*dt
    pos += vel*dt
  }

  def forceFrom(that : Body) : Vector = {
    val G = 6.67e-11
    val delta = that.pos - this.pos
    val dist = delta.modulus
    val f = (G * this.mass * that.mass) / pow(dist,2)
    return delta.direction * f
  }

  def drawOn(g2D : Graphics2D)  {
    val ctx = g2D.ctx
    ctx.beginPath()
    ctx.arc(pos.x, pos.y, radius, 0, 2 * Pi, false)
    ctx.fillStyle = "#00BB00"
    ctx.fill()
    ctx.lineWidth = 2.5 / g2D.scale
    ctx.strokeStyle = "#0000FF"
    ctx.stroke()
  }
}

object Body {
  def apply(pos : Vector, vel : Vector, mass : Double, radius : Double) : Body =
    new Body(pos, vel, mass, radius)
}