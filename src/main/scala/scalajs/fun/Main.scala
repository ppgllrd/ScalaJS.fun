/********************************************************************
 * Main entry
 *
 * Pepe Gallardo, 2020.
 *******************************************************************/

package scalajs.fun

object Main {
  def main(args: Array[String]): Unit = {
    // gravity.Gravity.run(args)
    val rw = randomWalks.RandomWalk(15)
    rw.periodic.start()
  }
}
