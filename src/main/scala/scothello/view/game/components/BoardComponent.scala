package scothello.view.game.components

import scalafx.beans.property.DoubleProperty
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
import scothello.controller.game.GameController
import scothello.game.pages.Pages
import scothello.model.game.config.Player
import scothello.view.BaseScalaFXView
import scothello.view.utils.ResettableObjectProperty
import scalafx.Includes.jfxNode2sfx

object BoardComponent:

  private val boardToWindowMargin: Int = 20
  private val squares: ObservableBuffer[Rectangle] = ObservableBuffer()
  private val allowedTilesCircles: ObservableBuffer[Circle] = ObservableBuffer[Circle]()

  def boardComponent(reactiveGameState: ResettableObjectProperty[GameState], topComponentHeights: Double*)(using
      displayScene: Scene,
      clickHandler: GameViewClickHandler,
      gameController: GameController,
      gameView: BaseScalaFXView[GameController]
  ): HBox =
    new HBox:
      alignment = Center

      val board: Pane = new Pane:
        alignment = Center
        prefHeight <== displayScene.height - topComponentHeights.sum - 2 * boardToWindowMargin
        prefWidth <== prefHeight

        given board: Pane = this
        given gameBoard: Board = reactiveGameState.value.board

        drawBoard(prefWidth, prefHeight)

        drawPawns(reactiveGameState.value.assignedPawns)

        reactiveGameState.map(_.assignedPawns).onChange { (_, _, assignedPawn) =>
          drawPawns(assignedPawn)
        }

        drawAllowedMoves(reactiveGameState.value.allowedTiles, reactiveGameState.value.turn.player)

        reactiveGameState.map(_.turn).onChange { (_, _, _) =>
          clearAllowedTilesCircles
          gameController.calculateAllowedPos()

          if AllowedTiles.checkIfNoAllowedMoves(reactiveGameState.value.allowedTiles) then
            reactiveGameState.removeListeners()
            gameController.endGame()
            gameView.navigateTo(Pages.End)
          else if AllowedTiles
              .checkIfPlayerNoAllowedMoves(reactiveGameState.value.allowedTiles, reactiveGameState.value.turn.player)
          then gameController.nextTurn()
          else drawAllowedMoves(reactiveGameState.value.allowedTiles, reactiveGameState.value.turn.player)
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
          val square: Rectangle = squares.get(i)
          val circleId = square.x.value + "," + square.y.value

          board.children.find(_.id.value == circleId).foreach(board.children -= _)

          val pawnCircle: Circle = new Circle():
            id = circleId
            radius <== square.width / 3
            fill = ColorMapper.toFxColor(pawn.player.color)
            centerX = square.x.value + square.width.value / 2
            centerY = square.y.value + square.height.value / 2
          board.children += pawnCircle
        case None => ()
    }

  private def drawAllowedMoves(allowedTiles: AllowedTiles, player: Player)(using board: Pane, gameBoard: Board): Unit =
    gameBoard.tiles.zipWithIndex.foreach { (tile, i) =>
      if allowedTiles.get(player).exists(_.contains(tile)) then
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
      else ()
    }
