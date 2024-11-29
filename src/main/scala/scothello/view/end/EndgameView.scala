package scothello.view.end

import javafx.scene.text.TextAlignment
import scalafx.beans.property.{ObjectProperty, StringProperty}
import scalafx.collections.ObservableBuffer
import scalafx.geometry.Pos.Center
import scalafx.scene.{Parent, Scene}
import scalafx.scene.control.{Button, Label, TableColumn, TableView}
import scalafx.scene.layout.VBox
import scalafx.scene.paint.Color
import scalafx.scene.text.{Text, TextFlow}
import scothello.view.{BaseScalaFXView, View}
import scothello.controller.end.EndgameController
import scothello.game.pages.Pages
import scothello.model.game.config.Player
import scothello.utils.Pair.oppositeOf

/** The view for the endgame page.
  */
trait EndgameView extends View

object EndgameView:
  def apply(scene: Scene, requirements: View.Requirements[EndgameController]): EndgameView =
    BaseScalaFXEndgameView(scene, requirements)

private class BaseScalaFXEndgameView(mainScene: Scene, requirements: View.Requirements[EndgameController])
    extends BaseScalaFXView(mainScene, requirements)
    with EndgameView:

  override def parent: Parent = new VBox:
    alignment = Center
    stylesheets = List(getClass.getResource("/styles/endgamepage.css").toExternalForm)

    val mainLayout: VBox = if reactiveState.value.isOver then gameOverLayout(this) else gameStoppedLayout()

    val startNewGameButton: Button = new Button:
      text = "New Game"
      onAction = _ => navigateToStartPage()

    val homeButton: Button = new Button:
      text = "Home"
      onAction = _ => navigateToHomePage()

    val exitButton: Button = new Button:
      text = "Exit"
      onAction = _ => System.exit(0)

    mainLayout.children.addAll(startNewGameButton, homeButton, exitButton)

    children.addAll(mainLayout)

  private def gameOverLayout(parent: VBox): VBox =
    var firstPlayer: Player = null
    var label: TextFlow = null

    reactiveState.value.winner match
      case Some(winner) =>
        parent.setStyle(
          """
            | -fx-background-image: url('/imgs/win-background.png');
            | -fx-background-size: cover;
            | -fx-background-position: center center;
          """.stripMargin
        )
        firstPlayer = winner

        label = new TextFlow:
          children = Seq(
            new Text(firstPlayer.name + " "):
              fill = Color.Red
            ,
            new Text("is the winner!"):
              fill = Color.White
          )

      case None =>
        firstPlayer = reactiveState.value.players._1
        label = new TextFlow:
          children = Seq(
            new Text("The game ends in a draw!"):
              fill = Color.White
          )

    new VBox:
      alignment = Center
      spacing = 40

      val secondPlayer: Player = reactiveState.value.players.oppositeOf(firstPlayer).get
      label.setTextAlignment(TextAlignment.CENTER)

      val scores: Map[Player, Int] = reactiveState.value.playerScores

      var secondPlayerScore: Int = 0

      scores.get(secondPlayer) match
        case Some(value) =>
          secondPlayerScore = value
        case None => ()

      val scoresData: ObservableBuffer[(Player, Int)] = ObservableBuffer(
        (firstPlayer, reactiveState.value.board.size - secondPlayerScore),
        (secondPlayer, secondPlayerScore)
      )
      val scoresTable: TableView[(Player, Int)] = new TableView[(Player, Int)](scoresData):
        val cellHeight: Double = 60

        this.setColumnResizePolicy(TableView.ConstrainedResizePolicy);
        this.setFixedCellSize(cellHeight)
        this.setMaxSize(mainScene.height.value * 0.5, cellHeight * 3)
        prefWidth = 200
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

      children.addAll(label, scoresTable)

  private def gameStoppedLayout(): VBox =
    new VBox:
      spacing = 40
      alignment = Center
      children = List(
        new Label("Game was interrupted"):
          id = "gameInterruptedLabel"
      )

  private def navigateToStartPage(): Unit =
    this.navigateTo(Pages.Start)

  private def navigateToHomePage(): Unit =
    this.navigateTo(Pages.Home)
