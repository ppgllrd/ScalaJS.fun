package scalajs.fun.life

import org.scalajs.dom
import scalajs.fun.util.{Animated, Graphics2D, Animation}

class Life(val n: Int):
  private var cells = Array.ofDim[Boolean](n, n)

  private var nextCells = Array.ofDim[Boolean](n, n)

  private def neighbors(x: Int, y: Int): Int =
    var count = 0
    for i <- -1 to 1 do
      for j <- -1 to 1 do
        if i != 0 || j != 0 then
          val x1 = x + i
          val y1 = y + j
          if x1 >= 0 && x1 < n && y1 >= 0 && y1 < n && cells(x1)(y1) then
            count += 1
    count

  def step(): Unit =
    for i <- 0 until n do
      for j <- 0 until n do
        val count = neighbors(i, j)
        nextCells(i)(j) = if cells(i)(j) then count == 2 || count == 3 else count == 3
    val tmp = cells
    cells = nextCells
    nextCells = tmp

  def apply(i: Int): Array[Boolean] =
    cells(i)

  def drawOn(g2D: Graphics2D): Unit =
    val offset = n / 2
    val ctx = g2D.ctx
    ctx.beginPath()
    ctx.strokeStyle = "black"
    ctx.rect(-offset, -offset, n, n)
    ctx.stroke()

    val border = 2
    for i <- border until n - border do
      for j <- border until n - border do
        if cells(i)(j) then
          ctx.fillStyle = "blue"
          ctx.fillRect(i - offset, j - offset, 1, 1)

  def initWith(coordinates: Array[Int]): Unit =
    require(coordinates.length % 2 == 0)
    for i <- 0 until n do
      for j <- 0 until n do
        cells(i)(j) = false

    val center = n / 2
    for i <- coordinates.indices by 2 do
      val x = center + coordinates(i)
      val y = center + coordinates(i + 1)
      cells(x)(y) = true

  private case class Config(life: Life)

object Life:
  def withInitialization(n: Int, coordinates: Array[Int]): Life =
    val life = Life(n)
    life.initWith(coordinates)
    life

  def run(args: Array[String]): Unit =
    val h3 = dom.document.createElement("h3")
    h3.innerText = "Game of Life"
    val controls = dom.document.getElementById("controls")
    controls.appendChild(h3)

    class AnimatedLife(val life: Life, override val Hz: Int) extends Animated:
      val scale: Double = 0.9 / life.n

      override def step(): Unit =
        life.step()

      override def drawOn(g2D: Graphics2D): Unit =
        life.drawOn(g2D)

    Animation(AnimatedLife(life1, 60)).start()

val life1 = Life.withInitialization(250, Array(0, 1, 1, 3, 2, 0, 2, 1, 2, 4, 2, 5, 2, 6))
