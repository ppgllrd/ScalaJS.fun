/********************************************************************
 * Main program and examples
 *
 * Pepe Gallardo, 2020.
 *******************************************************************/

package scalajs.fun.gravity

import org.scalajs.dom
import org.scalajs.dom.html.{Canvas, Label, Option, Select}

import scalajs.fun.util.Periodic

object universe1 extends {
  val bodies = Array(
      Body(Vector(0, 4.5e10), Vector(3e4, 0), 2.999e30, 5e9)
    , Body(Vector(0, -4.5e10), Vector(-3e4, 0), 2.999e30, 5e9)
    ) } with Universe(bodies, 8e10)

object universe2 extends {
  val bodies = Array(
      Body(Vector(0, 4.5e10), Vector(3e4, 0), 2.999e30, 5e9)
    , Body(Vector(0, -4.5e10), Vector(-3e4, 0), 2.999e30, 5e9)
    , Body(Vector(0, 0), Vector(0.05e4, 0), 6.97e24, 2e9)
    ) } with Universe(bodies, 8e10)

object universe3 extends {
  val bodies = Array(
    Body(Vector(0, 4.5e10), Vector( 1e4,0), 1.5e30, 5e9)
    , Body(Vector(0,-4.5e10), Vector(-1e4,0), 1.5e30, 5e9)
    ) } with Universe(bodies, 1e11)

object universe4 extends {
  val bodies = Array(
      Body(Vector(-3.5e10, 0), Vector(0,  1.4e3), 3e28, 5e9)
    , Body(Vector(  -1e10, 0), Vector(0,  1.5e4), 3e28, 5e9)
    , Body(Vector(   1e10, 0), Vector(0, -1.5e4), 3e28, 5e9)
    , Body(Vector( 3.5e10, 0), Vector(0, -1.4e3), 3e28, 5e9)
    ) } with Universe(bodies, 1.6e11)

object universe5 extends {
  val bodies = Array(
      Body(Vector(-3.5e10, 0), Vector(0, 1.4e3), 3e28, 5e9)
    , Body(Vector(-1e10, 0), Vector(0, 1.645e4), 3e28, 5e9)
    , Body(Vector(1e10, 0), Vector(0, -1.645e4), 3e28, 5e9)
    , Body(Vector(3.5e10, 0), Vector(0, -1.4e3), 3e28, 5e9)
    ) } with Universe(bodies, 1.6e11)

object Gravity {
  def run(args: Array[String]): Unit = {
    val canvas = dom.document.getElementById("canvas").asInstanceOf[Canvas]
    val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    val g2D = new Graphics2D(ctx)

    val percent = 0.8
    val width = (percent*dom.window.innerWidth).toInt
    val height = (percent*dom.window.innerHeight).toInt

    canvas.width = width
    canvas.height = height

    val xOffset = width / 2
    val yOffset = height / 2

    class Runner(universe: Universe, dt: Int, val Hz: Int) extends Periodic {
      private val scale = (width min height) / (universe.radius*2)

      override def onTick(): Unit = {
        ctx.setTransform(1, 0, 0, 1, 0, 0)
        ctx.fillStyle = "white"
        ctx.fillRect(0, 0, width, height)
        universe.advance(dt)
        ctx.translate(xOffset, yOffset)
        g2D.scale = scale
        universe.drawOn(g2D)
      }
    }

    val runners = Array(
        new Runner(universe1,  3600, 100)
      , new Runner(universe2,  3600, 100)
      , new Runner(universe3,  5000, 200)
      , new Runner(universe4, 25000, 500)
      , new Runner(universe5, 25000, 500)
    )

    object Running {
      var running: scala.Option[Int] = None
      def run(i: Int): Unit = {
        running match {
          case None =>
            runners(i).start()
            running = Some(i)
          case Some(j) if (i != j) =>
            runners(j).stop()
            runners(i).start()
            running = Some(i)
        }
      }
    }

    def setupGUI(): Unit = {
      val label = dom.document.createElement("label").asInstanceOf[Label]
      label.innerText = "Choose one universe: "
      val id = "universes"
      label.htmlFor = id

      val select = dom.document.createElement("select").asInstanceOf[Select]
      select.title = "select one universe to simulate"
      select.id = id
      for (i <- 0 until runners.length) {
        val option = dom.document.createElement("option").asInstanceOf[Option]
        val str = s"Universe $i"
        option.value = str
        option.innerText = str
        select.appendChild(option)
      }

      select.onchange = ev => {
        val i = select.selectedIndex
        Running.run(i)
      }

      val h3 = dom.document.createElement("h3")
      h3.innerText = "Gravity"

      val controls = dom.document.getElementById("controls")
      controls.appendChild(h3)
      controls.appendChild(label)
      controls.appendChild(select)
    }

    setupGUI()
    Running.run(0)
  }
}