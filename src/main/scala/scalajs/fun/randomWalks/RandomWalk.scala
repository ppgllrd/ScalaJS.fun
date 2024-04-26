/**
 * ****************************************************************** Two
 * Self-avoiding random walks
 *
 * Pepe Gallardo, 2020.
 */

package scalajs.fun.randomWalks

import org.scalajs.dom
import org.scalajs.dom.html.{Button, Canvas, Div, Element, Label}

import scalajs.fun.util.Periodic

import scala.collection.mutable.ListBuffer
import scala.util.Random

class RandomWalk(n: Int):
  private val dimension = 2 * n + 1

  private def inBorder(n: Int): Boolean =
    n == 0 || n == dimension - 1

  private case class Pos(x: Int, y: Int)

  private def escapes(p: Pos): Boolean =
    inBorder(p.x) || inBorder(p.y)

  private def neighbours(p: Pos): Array[Pos] =
    Array(Pos(p.x - 1, p.y), Pos(p.x + 1, p.y), Pos(p.x, p.y - 1), Pos(p.x, p.y + 1))

  private def blocked(p: Pos): Boolean =
    neighbours(p).forall(p => visited(p.x)(p.y))

  private var solution = ListBuffer[Pos]()

  private val visited =
    Array.ofDim[Boolean](dimension, dimension)

  private def unvisited(p: Pos): Boolean =
    !visited(p.x)(p.y)

  private def oneStep(rnd: Random, p: Pos): Pos =
    val positions = neighbours(p)
    var i = rnd.nextInt(positions.length)

    while !unvisited(positions(i)) do
      i = (i + 1) % positions.length

    positions(i)

  private def randomSolution(seed: Int): Unit =
    val rnd = new Random(seed)

    for x <- 0 until dimension do
      for y <- 0 until dimension do
        visited(x)(y) = false

    var pos = Pos(n, n)
    visited(n)(n) = true
    solution = ListBuffer(pos)

    while !escapes(pos) && !blocked(pos) do
      pos = oneStep(rnd, pos)
      visited(pos.x)(pos.y) = true
      solution.append(pos)

  private val canvas = dom.document.getElementById("canvas").asInstanceOf[Canvas]
  private val g2D = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

  private val (width, height) =
    val percent = 0.8
    ((percent * dom.window.innerWidth).toInt, (percent * dom.window.innerHeight).toInt)

  canvas.width = width
  canvas.height = height

  private val cellSize =
    val pixels = width min height
    val cellSz = pixels.toDouble / dimension
    val scale = pixels / (dimension * cellSz)
    g2D.setTransform(scale, 0, 0, scale, 0, 0)
    cellSz

  private type Color = String

  private def coordinates(x: Double, y: Double): (Double, Double) = (x * cellSize, y * cellSize)

  private def coordinates(p: Pos): (Double, Double) = coordinates(p.x, p.y)

  private def circle(pos: Pos, radius: Double, outline: Color, fill: Color): Unit =
    val (x, y) = coordinates(pos)
    g2D.beginPath()
    g2D.arc(x + cellSize / 2, y + cellSize / 2, radius, 0, 2 * math.Pi)
    g2D.fillStyle = fill
    g2D.fill()
    g2D.strokeStyle = outline
    g2D.stroke()

  private def _line(x0: Double, y0: Double, x1: Double, y1: Double): Unit =
    g2D.beginPath()
    g2D.moveTo(x0, y0)
    g2D.lineTo(x1, y1)
    g2D.stroke()

  private def line(x0: Double, y0: Double, x1: Double, y1: Double): Unit =
    val (p0x, p0y) = coordinates(x0, y0)
    val (p1x, p1y) = coordinates(x1, y1)
    _line(p0x, p0y, p1x, p1y)

  private def line(p0: Pos, p1: Pos): Unit =
    val (p0x, p0y) = coordinates(p0)
    val (p1x, p1y) = coordinates(p1)
    val offset = cellSize / 2
    _line(p0x + offset, p0y + offset, p1x + offset, p1y + offset)

  private def drawPos(i: Int): Unit =
    val p = solution(i)
    val smallRadius = cellSize / 6
    val largeRadius = cellSize / 3

    g2D.lineWidth = 2
    if i == 0 then
      circle(p, largeRadius, "black", "gray")
    else if i == solution.length - 1 then
      val (outline, fill) =
        if escapes(p) || !blocked(p) then
          ("darkgreen", "limegreen")
        else
          ("#aa0000", "red")
      circle(p, largeRadius, outline, fill)
    else
      circle(p, smallRadius, "blue", "cornflowerblue")

  private def drawBoard(): Unit =
    g2D.clearRect(0, 0, dimension * cellSize, dimension * cellSize)

    g2D.lineWidth = 0.5
    g2D.strokeStyle = "gray"
    for x <- 0 to dimension do
      line(x, 0, x, dimension)

    for y <- 0 to dimension do
      line(0, y, dimension, y)

  private def drawFrame(i: Int): Unit =
    if i > 0 then
      g2D.lineWidth = 2
      g2D.strokeStyle = "blue"
      line(solution(i - 1), solution(i))
      drawPos(i - 1)
      drawPos(i)

  val periodic: Periodic = new Periodic:
    override val Hz: Int = 60

    private var frame = 0

    override def finished: Boolean =
      frame >= solution.length

    override def onStart(): Unit =
      val seed = Random.nextInt(Int.MaxValue)
      output.innerText = s" Using $seed as random seed"
      randomSolution(seed)
      drawBoard()
      frame = 0

    override def onTick(): Unit =
      drawFrame(frame)
      frame += 1

  private def setupGUI(): Element =
    val button = dom.document.createElement("button").asInstanceOf[Button]
    button.innerText = "Go!"
    button.title = "Click for a new random walk"
    button.onclick = ev => periodic.start()

    val output = dom.document.createElement("output").asInstanceOf[Label]

    val div = dom.document.createElement("div").asInstanceOf[Div]
    div.style.padding = "10px"
    div.appendChild(button)
    div.appendChild(output)

    val h3 = dom.document.createElement("h3")
    h3.innerText = "Self avoiding random walks"

    val controls = dom.document.getElementById("controls")
    controls.appendChild(h3)
    controls.appendChild(div)

    output

  private val output = setupGUI()
