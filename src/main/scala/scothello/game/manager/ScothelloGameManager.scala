package scothello.game.manager

import javafx.application.Platform
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.layout.Pane
import scothello.game.ScothelloGame
import scothello.game.pages.Pages

/** The main entry point for the application.
  */
object ScothelloGameManager:

  /** Starts the game.
    */
  def startGame(): Unit =
    ScothelloFXApp.main(Array.empty)

  /** Navigates to the specified page.
    *
    * @param page
    *   The page to navigate to.
    */
  def navigateTo(page: Pages): Unit =
    ScothelloFXApp.navigateTo(page)

object ScothelloFXApp extends JFXApp3:

  private lazy val _game: ScothelloGame =
    ScothelloGame(
      Pages.values.map(page => page -> page.pageFactory).toMap
    )

  lazy val rootScene: Scene = new Scene:
    root = new Pane()
    stylesheets = List(getClass.getResource("/styles/rootstyle.css").toExternalForm)

  override def start(): Unit =
    stage = new JFXApp3.PrimaryStage:
      title = "Scothello"
      scene = rootScene
      width = 1000
      height = 1000
      resizable = false
    postInitAction()

  private def postInitAction(): Unit =
    navigateTo(Pages.Home)

  def navigateTo(page: Pages): Unit =
    Platform.runLater { () =>
      _game.pages(page).view.show()
    }
