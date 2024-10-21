package scothello

import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.layout.Pane

object ScothelloGameManager:

  private var _game: Option[ScothelloGame] = Option.empty

  def startGame(): Unit =
    _game = Some(ScothelloGame())
    ScothelloFXApp.main(Array.empty)

  def navigateTo(): Unit =
    ScothelloFXApp.navigateTo()

object ScothelloFXApp extends JFXApp3:

  override def start(): Unit =
    stage = new JFXApp3.PrimaryStage:
      title = "Scothello"
      scene = new Scene:
        root = new Pane()

  def navigateTo(): Unit = ???
