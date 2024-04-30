/**
 * ******************************************************************
 *
 * Self-avoiding random walks
 *
 * Pepe Gallardo, 2020.
 */

package scalajs.fun.randomWalks

import org.scalajs.dom
import org.scalajs.dom.html.*
import scalajs.fun.util.{Animated, Animation, Graphics2D}

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

  private type Color = String

  private def coordinates(x: Double, y: Double): (Double, Double) =
    (x - dimension / 2, y - dimension / 2)

  private def coordinates(p: Pos): (Double, Double) = coordinates(p.x, p.y)

  private def circle(g2D: Graphics2D, pos: Pos, radius: Double, outline: Color, fill: Color): Unit =
    val (x, y) = coordinates(pos)
    g2D.ctx.beginPath()
    g2D.ctx.arc(x + 1.0 / 2, y + 1.0 / 2, radius, 0, 2 * math.Pi)
    g2D.ctx.fillStyle = fill
    g2D.ctx.fill()
    g2D.ctx.strokeStyle = outline
    g2D.ctx.stroke()

  private def _line(g2D: Graphics2D, x0: Double, y0: Double, x1: Double, y1: Double): Unit =
    g2D.ctx.beginPath()
    g2D.ctx.moveTo(x0, y0)
    g2D.ctx.lineTo(x1, y1)
    g2D.ctx.stroke()

  private def line(g2D: Graphics2D, x0: Double, y0: Double, x1: Double, y1: Double): Unit =
    val (p0x, p0y) = coordinates(x0, y0)
    val (p1x, p1y) = coordinates(x1, y1)
    _line(g2D, p0x, p0y, p1x, p1y)

  private def line(g2D: Graphics2D, p0: Pos, p1: Pos): Unit =
    val (p0x, p0y) = coordinates(p0)
    val (p1x, p1y) = coordinates(p1)
    val offset = 1.0 / 2
    _line(g2D, p0x + offset, p0y + offset, p1x + offset, p1y + offset)

  private def drawPos(g2D: Graphics2D, i: Int): Unit =
    val p = solution(i)
    val smallRadius = 1.0 / 5
    val largeRadius = 1.0 / 3

    g2D.ctx.lineWidth = 0.075
    if i == 0 then
      circle(g2D, p, largeRadius, "black", "gray")
    else if i == solution.length - 1 then
      val (outline, fill) =
        if escapes(p) || !blocked(p) then
          ("darkgreen", "limegreen")
        else
          ("#cc0000", "red")
      circle(g2D, p, largeRadius, outline, fill)
    else
      circle(g2D, p, smallRadius, "blue", "cornflowerblue")

  private def drawBoard(g2D: Graphics2D): Unit =
    g2D.ctx.lineWidth = 0.05
    g2D.ctx.strokeStyle = "gray"
    for x <- 0 to dimension do
      line(g2D, x, 0, x, dimension)

    for y <- 0 to dimension do
      line(g2D, 0, y, dimension, y)

  private def drawFrame(g2D: Graphics2D, i: Int): Unit =
    if i > 0 then
      g2D.ctx.lineWidth = 0.05
      g2D.ctx.strokeStyle = "blue"
      line(g2D, solution(i - 1), solution(i))
      drawPos(g2D, i - 1)
      drawPos(g2D, i)

  private var frame = 0

  private val animatedRandomWalk = new Animated:
    def step(elapsed: Double): Unit =
      frame += 1

    def drawOn(g2D: Graphics2D): Unit =
      drawBoard(g2D)
      for i <- 1 to frame do
        drawFrame(g2D, i)

    val scale: Double = 0.95 / dimension

  private val animation = new Animation(animatedRandomWalk):
    override def finished: Boolean =
      frame >= solution.length

    override def onStart(): Unit =
      val seed = Random.nextInt(Int.MaxValue)
      output.innerText = s" Using $seed as random seed"
      randomSolution(seed)
      frame = 0
      animatedRandomWalk.drawOn(g2D)

  def start(): Unit =
    animation.start()

  private def setupGUI(): Element =
    val button = dom.document.createElement("button").asInstanceOf[Button]
    button.innerText = "Go!"
    button.title = "Click for a new random walk"
    button.onclick = ev => animation.start()

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
