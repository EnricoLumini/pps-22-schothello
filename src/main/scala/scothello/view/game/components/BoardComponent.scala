package scothello.view.game.components

import scalafx.beans.property.{DoubleProperty, ObjectProperty}
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Pos.Center
import scalafx.scene.Scene
import scalafx.scene.layout.{HBox, Pane}
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Circle, Rectangle}
import scothello.model.game.state.GameState
import scothello.model.board.{AllowedTiles, Board}
import scothello.model.components.AssignedPawns
import scothello.view.utils.ColorMapper
import scalafx.Includes.jfxObservableValue2sfx
import scothello.controller.game.GameController

object BoardComponent:

  private val heightMargin: Int = 40
  private val squares: ObservableBuffer[Rectangle] = ObservableBuffer()
  private val allowedTilesCircles: ObservableBuffer[Circle] = ObservableBuffer[Circle]()

  def boardComponent(using
      mainScene: Scene,
      header: HBox,
      reactiveState: ObjectProperty[GameState],
      clickHandler: GameViewClickHandler,
      gameController: GameController
  ): HBox =
    new HBox:
      alignment = Center

      val board: Pane = new Pane:
        prefHeight <== mainScene.height - (header.prefHeight + mainScene.height / 32) - 2 * heightMargin
        prefWidth <== prefHeight

        given board: Pane = this
        given gameBoard: Board = reactiveState.map(_.board).getValue

        drawBoard(prefWidth, prefHeight)

        drawPawns(reactiveState.map(_.assignedPawns).getValue)
        reactiveState.map(_.assignedPawns).onChange { (_, _, assignedPawn) =>
          drawPawns(assignedPawn)
        }

        drawAllowedMoves(reactiveState.map(_.allowedTiles).getValue)
        reactiveState.map(_.allowedTiles).onChange { (_, _, allowedTiles) =>
          drawAllowedMoves(allowedTiles)
          allowedTiles match
            case tiles if tiles.isEmpty => gameController.nextTurn()
            case _                      => None
        }

        reactiveState.map(_.turn).onChange { (_, _, _) =>
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
