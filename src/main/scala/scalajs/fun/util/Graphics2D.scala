/**
 * ****************************************************************** Simple
 * graphics context
 *
 * Pepe Gallardo, 2020.
 */

package scalajs.fun.util

import org.scalajs.dom

class Graphics2D(val ctx: dom.CanvasRenderingContext2D):
  private var _scale = 1.0

  def scale: Double = _scale

  def scale_=(sc: Double): Unit =
    _scale = sc
    ctx.scale(sc, sc)
