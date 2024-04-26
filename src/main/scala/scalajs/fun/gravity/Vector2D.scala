/**
 * ****************************************************************** Two
 * dimensional vectors
 *
 * Pepe Gallardo, 2020. Based on Java code by Sedgewick and Kevin Wayne
 */

package scalajs.fun.gravity

import scala.math.{pow, sqrt}

class Vector2D(val x: Double, val y: Double):
  def *(z: Double): Vector2D = Vector2D(x * z, y * z)

  def /(z: Double): Vector2D =
    require(z != 0, "Division by zero")
    Vector2D(x / z, y / z)

  def +(that: Vector2D): Vector2D = Vector2D(x + that.x, y + that.y)

  def -(that: Vector2D): Vector2D = Vector2D(x - that.x, y - that.y)

  def modulus: Double = sqrt(pow(x, 2) + pow(y, 2))

  def direction: Vector2D =
    val m = this.modulus
    require(m != 0.0, "Nul vector has no direction")
    this * (1.0 / this.modulus)
