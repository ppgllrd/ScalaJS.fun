/********************************************************************
 * An universe is a collection of bodies
 *
 * Pepe Gallardo, 2020.
 * Based on Java code by Sedgewick and Kevin Wayne
 *******************************************************************/

package scalajs.fun.gravity

class Universe( private val bodies : Array[Body]
              , val radius : Double
              ) {
  def advance(dt : Double) {
    val fs : Array[Vector] = new Array(bodies.length)

    for (i <- 0 until fs.size)
      fs(i) = Vector(0,0)

    for (i <- 0 until fs.length)
      for (j <- 0 until fs.length)
        if (i != j)
          fs(i) += bodies(i).forceFrom(bodies(j))

    for (i <- 0 until bodies.length)
      bodies(i).move(fs(i), dt)
  }

  def drawOn(g2D : Graphics2D): Unit =
    for(p <- bodies)
      p.drawOn(g2D)
}

object Universe {
  def apply(bodies : Array[Body], radius : Double) : Universe =
    new Universe(bodies, radius)
}