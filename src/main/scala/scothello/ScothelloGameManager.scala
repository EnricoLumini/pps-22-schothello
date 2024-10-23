package scothello

import javafx.application.Platform
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.layout.Pane

object ScothelloGameManager:

  def startGame(): Unit =
    ScothelloFXApp.main(Array.empty)

  def navigateTo(page: Pages): Unit =
    ScothelloFXApp.navigateTo(page)

object ScothelloFXApp extends JFXApp3:

  private lazy val _game: ScothelloGame =
    ScothelloGame(
      Pages.values.map(page => page -> page.pageFactory).toMap
    )

  lazy val rootScene: Scene = new Scene:
    root = new Pane()

  override def start(): Unit =
    stage = new JFXApp3.PrimaryStage:
      title = "Scothello"
      scene = rootScene
      width = 800
      height = 600

    postInitAction()

  private def postInitAction(): Unit =
    navigateTo(Pages.Home)

  def navigateTo(page: Pages): Unit =
    Platform.runLater { () =>
      _game.pages(page).view.show()
    }
