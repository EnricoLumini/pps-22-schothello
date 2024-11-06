package scothello.view.game.components

import scalafx.beans.property.{DoubleProperty, ObjectProperty, ReadOnlyDoubleProperty}
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Pos.Center
import scalafx.scene.Scene
import scalafx.scene.layout.{HBox, Pane, Priority}
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Circle, Rectangle}
import scothello.model.game.state.GameState
import scothello.model.board.{AllowedTiles, Board}
import scothello.model.components.AssignedPawns
import scothello.view.utils.ColorMapper
import scalafx.Includes.jfxObservableValue2sfx
import scalafx.beans.value.ObservableValue
import scothello.controller.game.GameController

object BoardComponent:

  private val boardToWindowMargin: Int = 20
  private val squares: ObservableBuffer[Rectangle] = ObservableBuffer()
  private val allowedTilesCircles: ObservableBuffer[Circle] = ObservableBuffer[Circle]()

  def boardComponent(topComponentHeights: Double*)(using
      displayScene: Scene,
      reactiveGameState: ObjectProperty[GameState],
      clickHandler: GameViewClickHandler,
      gameController: GameController
  ): HBox =
    new HBox:
      alignment = Center

      val board: Pane = new Pane:
        alignment = Center
        prefHeight <== displayScene.height - topComponentHeights.sum - 2 * boardToWindowMargin
        prefWidth <== prefHeight

        given board: Pane = this
        given gameBoard: Board = reactiveGameState.map(_.board).getValue

        drawBoard(prefWidth, prefHeight)

        drawPawns(reactiveGameState.map(_.assignedPawns).getValue)
        reactiveGameState.map(_.assignedPawns).onChange { (_, _, assignedPawn) =>
          drawPawns(assignedPawn)
        }

        drawAllowedMoves(reactiveGameState.map(_.allowedTiles).getValue)
        reactiveGameState.map(_.allowedTiles).onChange { (_, _, allowedTiles) =>
          if AllowedTiles.checkIfAvailableMoves(allowedTiles) then gameController.nextTurn()
          else drawAllowedMoves(allowedTiles)
        }

        reactiveGameState.map(_.turn).onChange { (_, _, _) =>
          clearAllowedTilesCircles
          gameController.calculateAllowedPos()
        }

      children += board

  private def drawBoard(w: DoubleProperty, h: DoubleProperty)(using
      board: Pane,
      gameBoard: Board,
      clickHandler: GameViewClickHandler
  ): Unit =
    gameBoard.tiles.foreach { tile =>
      val square: Rectangle = new Rectangle:
        width <== w / gameBoard.cols
        height <== h / gameBoard.rows
        fill = Color.web("#009067")
        stroke = Color.Black
        strokeWidth = 2
        x = tile.col * width.value
        y = tile.row * height.value

        onMouseClicked = _ => clickHandler.onTileClick(tile)

      board.children.add(square)
      squares.add(square)
    }

  private def clearAllowedTilesCircles(using board: Pane): Unit =
    allowedTilesCircles.foreach(circle => board.children.remove(circle))

  private def drawPawns(assignedPawn: AssignedPawns)(using board: Pane, gameBoard: Board): Unit =
    gameBoard.tiles.zipWithIndex.foreach { (tile, i) =>
      assignedPawn.get(tile) match
        case Some(pawn) =>
          val pawnCircle: Circle = new Circle():
            val square: Rectangle = squares.get(i)
            radius <== square.width / 3
            fill = ColorMapper.toFxColor(pawn.player.color)
            centerX = square.x.value + square.width.value / 2
            centerY = square.y.value + square.height.value / 2
          board.children += pawnCircle
        case None => ()
    }

  private def drawAllowedMoves(allowedTiles: AllowedTiles)(using board: Pane, gameBoard: Board): Unit =
    gameBoard.tiles.zipWithIndex.foreach { (tile, i) =>
      allowedTiles.get(tile) match
        case Some(player) =>
          val pawnCircle: Circle = new Circle():
            val square: Rectangle = squares.get(i)
            radius <== square.width / 3
            fill = Color.Transparent
            stroke = Color.Black
            strokeWidth = 2
            centerX = square.x.value + square.width.value / 2
            centerY = square.y.value + square.height.value / 2
            mouseTransparent = true

          allowedTilesCircles += pawnCircle
          board.children += pawnCircle
        case None => ()
    }