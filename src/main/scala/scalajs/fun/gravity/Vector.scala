/********************************************************************
 * Two dimensional vectors
 *
 * Pepe Gallardo, 2020.
 * Based on Java code by Sedgewick and Kevin Wayne
 *******************************************************************/

package scalajs.fun.gravity

import scala.math.{pow, sqrt}

class Vector(val x : Double, val y : Double) {
  def *(z : Double) : Vector = Vector(x*z,y*z)

  def +(that : Vector) : Vector = Vector(x+that.x, y+that.y)

  def -(that : Vector) : Vector = Vector(x-that.x, y-that.y)

  def modulus : Double = sqrt(pow(x,2)+pow(y,2))

  def direction : Vector =
    if (modulus==0.0)
      sys.error("Nul vector has no direction")
    else
      this * (1.0/this.modulus)
}

object Vector {
  def apply(x : Double, y : Double) : Vector =
    new Vector(x, y)
}