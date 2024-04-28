/**
 * ****************************************************************** Main entry
 *
 * Pepe Gallardo, 2020.
 */

package scalajs.fun

import org.scalajs.dom
import scalajs.fun.sierpinski.Sierpinski

object Main:
  def main(args: Array[String]): Unit =
    val query = dom.window.location.search

    val opt =
      if query.startsWith("?") then
        try
          query.tail.toInt
        catch
          case _ => 0
      else
        0

    opt match
      case 0 => gravity.Gravity.run(args)
      case 1 =>
        val rw = randomWalks.RandomWalk(15)
        rw.periodic.start()
      case 2 =>
        life.Life.run(args)
      case 3 =>
        Sierpinski.start() 
