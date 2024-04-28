/**
 * ****************************************************************** An
 * universe is a collection of bodies
 *
 * Pepe Gallardo, 2020. Based on Java code by Sedgewick and Kevin Wayne
 */

package scalajs.fun.gravity

import scalajs.fun.util.Graphics2D

class Universe(private val bodies: Array[Body], val radius: Double):

  def advance(dt: Double, elapsed: Double): Unit =
    val fs = Array.fill[Vector2D](bodies.length)(Vector2D(0, 0))

    for i <- fs.indices do
      for j <- fs.indices do
        if i != j then
          fs(i) += bodies(i).forceFrom(bodies(j))

    for i <- bodies.indices do
      bodies(i).move(fs(i), dt * elapsed / 5)

  def drawOn(g2D: Graphics2D): Unit =
    for body <- bodies do
      body.drawOn(g2D)
