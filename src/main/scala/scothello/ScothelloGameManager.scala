package scothello

import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.layout.Pane

object ScothelloGameManager:

  private var _game: Option[ScothelloGame] = Option.empty

  def startGame(): Unit =
    ScothelloFXApp.main(Array.empty)
    _game = Some(
      ScothelloGame(
        Pages.values.map(page => page -> page.pageFactory).toMap
      )
    )
    navigateTo(Pages.Home)

  def navigateTo(page: Pages): Unit =
    _game match
      case Some(game) => game.pages(page).view.show()
      case None       => throw new IllegalStateException("Game not started")

object ScothelloFXApp extends JFXApp3:

  lazy val rootScene: Scene = new Scene:
    root = new Pane()

  override def start(): Unit =
    stage = new JFXApp3.PrimaryStage:
      title = "Scothello"
      scene = rootScene
