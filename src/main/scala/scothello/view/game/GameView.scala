package scothello.view.game

import scalafx.scene.control.Label
import scalafx.scene.{Parent, Scene}
import scalafx.scene.layout.{HBox, Pane, Priority, Region, StackPane, VBox}
import scothello.view.{BaseScalaFXView, View}
import scothello.controller.game.GameController
import scalafx.geometry.Pos.Center
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Circle, Rectangle}
import scothello.model.board.Board
import scothello.model.game.config.{Player, PlayerColor}
import scalafx.Includes.jfxObservableValue2sfx
import scalafx.collections.ObservableBuffer
import scothello.model.components.AssignedPawns
import scothello.model.board.AllowedTiles
import scothello.model.game.TurnManager

trait GameView extends View

object GameView:
  def apply(scene: Scene, requirements: View.Requirements[GameController]): GameView =
    new BaseScalaFXGameView(scene, requirements)

private class BaseScalaFXGameView(mainScene: Scene, requirements: View.Requirements[GameController])
    extends BaseScalaFXView(mainScene, requirements)
    with GameView:

  override def parent: Parent = new VBox:
    stylesheets = List(getClass.getResource("/styles/gamepage.css").toExternalForm)

    val headerHBox: HBox = new HBox:
      prefHeight = mainScene.height.value / 8
      alignment = Center

    val leftPlayerVBox: VBox = new VBox:
      prefWidth = mainScene.width.value / 3
      alignment = Center

      val leftPlayerHBox: HBox = new HBox:
        alignment = Center
        spacing = 20

        val player1PawnColor: Circle = new Circle:
          radius = 10
          fill = Color.Black
          stroke = Color.Black
          strokeWidth = 2
        val player1NameLabel: Label = new Label:
          id = "playerLabel"
          text <== reactiveState.map(_.players._1.name)

        children.addAll(player1PawnColor, player1NameLabel)

      val player1ScoreLabel: Label = new Label:
        id = "scoreLabel"
        val player1: Player = reactiveState.map(_.players._1).getValue
        text <== reactiveState.map(_.playerScores.getOrElse(player1, 0).toString)

      children.addAll(leftPlayerHBox, player1ScoreLabel)

    val clockVBox: VBox = new VBox:
      prefWidth = mainScene.width.value / 3
      alignment = Center
      val clockLabel: Label = new Label:
        id = "clockLabel"
        text = "Time:"
      val timeLabel: Label = new Label:
        id = "timeLabel"
        text = "00:00"
      children.addAll(clockLabel, timeLabel)

    val rightPlayerVBox: VBox = new VBox:
      prefWidth = mainScene.width.value / 3
      alignment = Center

      val rightPlayerHBox: HBox = new HBox:
        alignment = Center
        spacing = 20

        val player2NameLabel: Label = new Label:
          id = "playerLabel"
          text <== reactiveState.map(_.players._2.name)
        //        textFill <== reactiveState.map(_.turn.player.name).map {
        //          case "ciuccia" => Color.Red
        //          case _ => Color.Black
        //        }

        val player2PawnColor: Circle = new Circle:
          radius = 10
          fill = Color.White
          stroke = Color.Black
          strokeWidth = 2

        children.addAll(player2NameLabel, player2PawnColor)

      val player2ScoreLabel: Label = new Label:
        id = "scoreLabel"
        val player2: Player = reactiveState.map(_.players._2).getValue
        text <== reactiveState.map(_.playerScores.getOrElse(player2, 0).toString)
      children.addAll(rightPlayerHBox, player2ScoreLabel)

    headerHBox.children.addAll(leftPlayerVBox, clockVBox, rightPlayerVBox)

    val gameBox: HBox = new HBox:
      alignment = Center

      val board: Pane = new Pane:
        val heightMargin: Int = 10
        prefHeight <== mainScene.height - headerHBox.prefHeight.value - 2 * heightMargin
        prefWidth <== prefHeight

        val squares: ObservableBuffer[Rectangle] = ObservableBuffer()
        val gameBoard: Board = reactiveState.map(_.board).getValue
        gameBoard.tiles.foreach { tile =>
          val square: Rectangle = new Rectangle:
            width <== prefWidth / gameBoard.cols
            height <== prefHeight / gameBoard.rows
            fill = Color.web("#009067")
            stroke = Color.Black
            strokeWidth = 2
            x = tile.col * width.value
            y = tile.row * height.value

            onMouseClicked = _ => println("Clicked on square: " + tile)

          children += square
          squares += square
        }

        drawPawns(reactiveState.map(_.assignedPawns).getValue)
        reactiveState.map(_.assignedPawns).onChange { (_, _, assignedPawn) =>
          drawPawns(assignedPawn)
        }

        drawAllowedMoves(reactiveState.map(_.allowedTiles).getValue)
        reactiveState.map(_.allowedTiles).onChange { (_, _, allowedTiles) =>
          drawAllowedMoves(allowedTiles)
        }

        // TODO: Find a better position for this method
        private def drawPawns(assignedPawn: AssignedPawns): Unit =
          children.filter(_.isInstanceOf[Circle]).clear()
          gameBoard.tiles.zipWithIndex.foreach { (tile, i) =>
            assignedPawn.get(tile) match
              case Some(pawn) =>
                val pawnCircle: Circle = new Circle():
                  val square: Rectangle = squares.get(i)
                  radius <== square.width / 3
                  fill = pawn.player.color match
                    case PlayerColor.Black => Color.Black
                    case PlayerColor.White => Color.White
                  centerX = square.x.value + square.width.value / 2
                  centerY = square.y.value + square.height.value / 2
                children += pawnCircle
              case None => ()
          }

        private def drawAllowedMoves(allowedTiles: AllowedTiles): Unit =
          children.filter(_.isInstanceOf[Circle]).clear()
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

                children += pawnCircle
              case None => ()
          }

      children += board

    children += headerHBox += gameBox

    println(reactiveState.map(_.assignedPawns).getValue)
