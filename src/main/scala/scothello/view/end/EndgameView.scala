package scothello.view.end

import scalafx.Includes.when
import scalafx.beans.property.{ObjectProperty, StringProperty}
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Pos.Center
import scalafx.scene.{Parent, Scene}
import scalafx.scene.control.{Button, Label, TableColumn, TableView}
import scalafx.scene.layout.{HBox, VBox}
import scothello.view.{BaseScalaFXView, View}
import scothello.controller.end.EndgameController
import scothello.game.pages.Pages
import scothello.model.game.config.Player
import scothello.utils.Pair.oppositeOf

trait EndgameView extends View

object EndgameView:
  def apply(scene: Scene, requirements: View.Requirements[EndgameController]): EndgameView =
    BaseScalaFXEndgameView(scene, requirements)

private class BaseScalaFXEndgameView(mainScene: Scene, requirements: View.Requirements[EndgameController])
    extends BaseScalaFXView(mainScene, requirements)
    with EndgameView:

  override def parent: Parent = new VBox:

    val mainLayout: VBox = if reactiveState.value.isOver then gameFinishedLayout() else gameStoppedLayout()

    val startNewGameButton: Button = new Button:
      text = "New Game"
      onAction = _ => println("New game") // navigateToStartPage()

    val homeButton: Button = new Button:
      text = "Home"
      onAction = _ => println("Home")

    val exitButton: Button = new Button:
      text = "Exit"
      onAction = _ => println("Exit")

    mainLayout.children.addAll(startNewGameButton, homeButton, exitButton)

    children.addAll(mainLayout)

    // val label: Label = new Label():
    //  text <== when(reactiveState.map(_.isOver)) then gameFinished() otherwise gameStopped()

    // children += label

  private def gameFinishedLayout(): VBox =
    new VBox:
      alignment = Center

      val winner: Player = reactiveState.value.winner.get
      val winnerLabel: Label = new Label:
        text = winner.name + " is the winner!"

      val scoresData: ObservableBuffer[(Player, Int)] = ObservableBuffer(
        (winner, 0),
        (reactiveState.value.players.oppositeOf(winner).get, 0)
      )
      val scoresTable: TableView[(Player, Int)] = new TableView[(Player, Int)](scoresData):
        val rowHeight = 30
        val headerHeight = 25

        prefWidth = mainScene.width.value * 0.5
        prefHeight = headerHeight + rowHeight * scoresData.size
        columns ++= List(
          new TableColumn[(Player, Int), String]:
            text = "Player name"
            cellValueFactory = playerScore => StringProperty(playerScore.value._1.name)
            sortable = false
          ,
          new TableColumn[(Player, Int), Int]:
            text = "Score"
            cellValueFactory = playerScore => ObjectProperty(playerScore.value._2)
            sortable = false
        )

      children.addAll(winnerLabel, scoresTable)

  private def gameStoppedLayout(): VBox =
    new VBox:
      alignment = Center
      children = List(
        new Label("Game Stopped")
      )

  private def navigateToStartPage(): Unit =
    this.navigateTo(Pages.Start)
