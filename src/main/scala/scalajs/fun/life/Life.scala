/**
 * ******************************************************************
 *
 * Game of life
 *
 * Pepe Gallardo, 2020.
 */

package scalajs.fun.life

import scalajs.fun.util.{Animated, Graphics2D}

class Life(val n: Int) extends Animated:
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

  def stepUp(): Unit =
    for i <- 0 until n do
      for j <- 0 until n do
        val count = neighbors(i, j)
        nextCells(i)(j) = if cells(i)(j) then count == 2 || count == 3 else count == 3
    val tmp = cells
    cells = nextCells
    nextCells = tmp

  def apply(i: Int): Array[Boolean] =
    cells(i)

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

  title("Game of Life")

  override lazy val scale: Double = 0.9 / n

  override def step(elapsed: Double): Unit =
    stepUp()

  override def drawOn(g2D: Graphics2D): Unit =
    val offset = n / 2
    val ctx = g2D.ctx
    ctx.strokeStyle = "black"
    ctx.strokeRect(-offset, -offset, n, n)

    val border = 2
    for i <- border until n - border do
      for j <- border until n - border do
        if cells(i)(j) then
          ctx.fillStyle = "blue"
          ctx.fillRect(i - offset, j - offset, 1, 1)

object Life:
  def withInitialization(n: Int, coordinates: Array[Int]): Life =
    val life = Life(n)
    life.initWith(coordinates)
    life

  def run(args: Array[String]): Unit =
    val life1 = Life.withInitialization(250, Array(0, 1, 1, 3, 2, 0, 2, 1, 2, 4, 2, 5, 2, 6))
    life1.start()
