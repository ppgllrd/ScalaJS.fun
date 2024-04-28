/**
 * ****************************************************************** Main
 * program and examples
 *
 * Pepe Gallardo, 2020.
 */

package scalajs.fun.gravity

import org.scalajs.dom
import org.scalajs.dom.html.{Label, Option, Select}
import scalajs.fun.util.{Animated, Animation, Graphics2D}

val universe0 =
  val radius: Double = 8e10
  val bodies = Array(
    Body(Vector2D(0, 4.5e10), Vector2D(3e4, 0), 2.999e30, 5e9),
    Body(Vector2D(0, -4.5e10), Vector2D(-3e4, 0), 2.999e30, 5e9)
  )
  Universe(bodies, radius)

val universe1 =
  val radius: Double = 8e10
  val bodies = Array(
    Body(Vector2D(0, 4.5e10), Vector2D(3e4, 0), 2.999e30, 5e9),
    Body(Vector2D(0, -4.5e10), Vector2D(-3e4, 0), 2.999e30, 5e9),
    Body(Vector2D(0, 0), Vector2D(0.05e4, 0), 6.97e24, 2e9)
  )
  Universe(bodies, radius)

val universe2 =
  val radius: Double = 1e11
  val bodies = Array(
    Body(Vector2D(0, 4.5e10), Vector2D(1e4, 0), 1.5e30, 5e9),
    Body(Vector2D(0, -4.5e10), Vector2D(-1e4, 0), 1.5e30, 5e9)
  )
  Universe(bodies, radius)

val universe3 =
  val radius: Double = 1e11
  val bodies = Array(
    Body(Vector2D(-3.5e10, 0), Vector2D(0, 1.4e3), 3e28, 5e9),
    Body(Vector2D(-1e10, 0), Vector2D(0, 1.5e4), 3e28, 5e9),
    Body(Vector2D(1e10, 0), Vector2D(0, -1.5e4), 3e28, 5e9),
    Body(Vector2D(3.5e10, 0), Vector2D(0, -1.4e3), 3e28, 5e9)
  )
  Universe(bodies, radius)

val universe4 =
  val radius: Double = 1.6e11
  val bodies = Array(
    Body(Vector2D(-3.5e10, 0), Vector2D(0, 1.4e3), 3e28, 5e9),
    Body(Vector2D(-1e10, 0), Vector2D(0, 1.645e4), 3e28, 5e9),
    Body(Vector2D(1e10, 0), Vector2D(0, -1.645e4), 3e28, 5e9),
    Body(Vector2D(3.5e10, 0), Vector2D(0, -1.4e3), 3e28, 5e9)
  )
  Universe(bodies, radius)

val planetsParty = Gravity.fromString(
  """10
    |4.0e11
    |8000
    | 0.0e00    0.000e00   0.00e00   0.00e00   2e32 8e9
    | 6.25e10   0.000e00   0.00e00   5.66e05   6e24 4e9
    |-6.25e10   0.000e00   0.00e00  -5.66e05   6e24 6e9
    | 0         6.250e10  -5.66e05   0.00e00   6e24 6.2e9
    | 0        -6.250e10   5.66e05   0.00e00   6e24 5.5e9
    | 2.828e11  2.828e11  100000 -100000   1e26 6.4e9
    | 2.828e11 -2.828e11 -100000 -100000   1e26 6.1e9
    |-2.828e11 -2.828e11 -100000  100000   1e26 6.8e9
    |-2.828e11 2.828e11   100000  100000   1e26 5e9
    |-1.828e11 1.828e11   100000  100000   1e16 4e9
    |""".stripMargin
)

val illusion = Gravity.fromString(
  """16
    |2.50e11
    |1800
    | 1.000e11  0.000e00  0.000e00  -2.52664e05 1.000e32 4e9
    |-1.000e11  0.000e00  0.000e00   2.52664e05 1.000e32 3.5e9
    | 0.000e00  1.000e11  2.52664e05 0.000e00   1.000e32 3.25e9
    | 0.000e00 -1.000e11 -2.52664e05 0.000e00   1.000e32 3e9
    | 1.000e07  0.000e00  0.000e00   0.000e00   1.000e01 5e9
    | 0.000e00  1.000e07  0.000e00   0.000e00   1.000e01 5e9
    |-1.000e07  0.000e00  0.000e00   0.000e00   1.000e01 5e9
    | 0.000e00 -1.000e07  0.000e00   0.000e00   1.000e01 5e9
    | 1.000e13  1.000e13 -1.000e06  -1.000e06   2.000e32 6e9
    | 1.000e13 -1.000e13 -1.000e06   1.000e06   2.000e32 6e9
    |-1.000e13  1.000e13  1.000e06  -1.000e06   2.000e32 6e9
    |-1.000e13 -1.000e13  1.000e06   1.000e06   2.000e32 6e9
    | 0.000e00  1.414e13  0.000e00  -1.414e06   2.000e22 6e9
    | 0.000e00 -1.414e13  0.000e00   1.414e06   2.000e22 6e9
    |-1.414e13  0.000e00  1.414e06   0.000e00   2.000e22 6e9
    | 1.414e13  0.000e00 -1.414e06   0.000e00   2.000e22 6e9
    |""".stripMargin
)

val dance10 = Gravity.fromString(
  """10
   |13.000e11
   |10000
   | 1.000e11  0              0  129132.5 1e32 5e10
   |-1.000e11  0              0 -129132.5 1e32 5e10
   | 2.828e11  2.828e11  100000 -100000   1e26 4e10
   | 2.828e11 -2.828e11 -100000 -100000   1e26 4e10
   |-2.828e11 -2.828e11 -100000  100000   1e26 4e10
   |-2.828e11 2.828e11   100000  100000   1e26 4e10
   | 9.000e11 0               0   97500   1e24 3e10
   |-9.000e11 0               0  -97500   1e24 3e10
   | 0.000    9e11       -97500       0   1e24 3e10
   | 0.000   -9e11        97500       0   1e24 3e10""".stripMargin
)

val eightStarRot = Gravity.fromString(
  """8
    |22e10
    |10000
    |6.85e10 0 0 39e3 5e29 4e9
    |8.125e10 0 0 -31e3 5e29 4e9
    |11.875e10 0 0 -11e3 5e29 4e9
    |13.125e10 0 0 -81e3 5e29 4e9
    |-6.85e10 0 0 -39e3 5e29 4e9
    |-8.125e10 0 0 31e3 5e29 4e9
    |-11.875e10 0 0 11e3 5e29 4e9
    |-13.125e10 0 0 81e3 5e29 4e9""".stripMargin
)

val spiral = Gravity.fromString(
  """17
    |3.0e11
    |20000
    |  0.0        0.0        0.0       0.0     1.989e30        8e9
    |  0.0       16.0e10     2.0e4    -2.0e4   5.974e24        6e9
    |-16.0e10     0.0        2.0e4     2.0e4   5.974e24        6e9
    |  0.0      -16.0e10    -2.0e4     2.0e4   5.974e24        6e9
    | 16.0e10     0.0       -2.0e4    -2.0e4   5.974e24        6e9
    |  6.0e10     6.0e10    -5.0e3     5.0e4   5.974e27        4e9
    | -6.0e10     6.0e10    -5.0e4    -5.0e3   5.974e27        4e9
    | -6.0e10    -6.0e10     5.0e3    -5.0e4   5.974e27        4e9
    |  6.0e10    -6.0e10     5.0e4     5.0e3   5.974e27        4e9
    | 18.0e10    18.0e10    -1.0e4     1.0e2   6.419e23        6e9
    |-18.0e10    18.0e10     1.0e2    -1.0e4   6.419e23        6e9
    |-18.0e10   -18.0e10     1.0e4     1.0e2   6.419e23        6e9
    | 18.0e10   -18.0e10    -1.0e2     1.0e4   6.419e23        6e9
    |  0.0       23.0e10     1.0e4    -1.0e4   5.974e23        6e9
    |-23.0e10     0.0        1.0e4     1.0e4   5.974e23        6e9
    |  0.0      -23.0e10    -1.0e4     1.0e4   5.974e23        6e9
    | 23.0e10     0.0       -1.0e4    -1.0e4   5.974e23        6e9""".stripMargin
)

object Gravity:
  def fromString(string: String): AnimatedGravity =
    def int(s: String): Int =
      s.dropWhile(_.isSpaceChar).toInt

    def double(s: String): Double =
      s.dropWhile(_.isSpaceChar).toDouble

    val lines = string.linesIterator.filter(_.nonEmpty).toArray
    val numBodies = int(lines(0))
    val radius = double(lines(1))
    val dt = int(lines(2))

    val bodies = lines.drop(3).map { line =>
      val Array(x, y, vx, vy, m, r) = line.dropWhile(_.isSpaceChar).split(" +").map(double)
      Body(Vector2D(x, y), Vector2D(vx, vy), m, r)
    }
    AnimatedGravity(Universe(bodies, radius), dt)

  class AnimatedGravity(universe: Universe, dt: Int) extends Animated:
    override def step(elapsed: Double): Unit =
      universe.advance(dt, elapsed)

    override def drawOn(g2D: Graphics2D): Unit =
      universe.drawOn(g2D)

    override val scale: Double = 1.0 / (universe.radius * 2)

  def run(args: Array[String]): Unit =

    val animations = Array(
      AnimatedGravity(universe0, 3600),
      AnimatedGravity(universe1, 1090),
      AnimatedGravity(universe2, 10000),
      AnimatedGravity(universe3, 35000),
      AnimatedGravity(universe4, 25000),
      planetsParty,
      illusion,
      dance10,
      eightStarRot,
      spiral
    ).map(Animation(_))

    object Executor:
      private var running: scala.Option[Int] = None

      def run(i: Int): Unit =
        running match
          case None =>
            animations(i).start()
            running = Some(i)
          case Some(j) if i != j =>
            animations(j).stop()
            animations(i).start()
            running = Some(i)
          case _ => ;

    def setupGUI(): Unit =
      val label = dom.document.createElement("label").asInstanceOf[Label]
      label.innerText = "Choose one universe: "
      val id = "universes"
      label.htmlFor = id

      val select = dom.document.createElement("select").asInstanceOf[Select]
      select.title = "select one universe to simulate"
      select.id = id
      for i <- animations.indices do
        val option = dom.document.createElement("option").asInstanceOf[Option]
        val str = s"Universe $i"
        option.value = str
        option.innerText = str
        select.appendChild(option)

      select.onchange = ev =>
        val i = select.selectedIndex
        Executor.run(i)

      val h3 = dom.document.createElement("h3")
      h3.innerText = "Gravity"

      val controls = dom.document.getElementById("controls")
      controls.appendChild(h3)
      controls.appendChild(label)
      controls.appendChild(select)

    setupGUI()
    Executor.run(0)
