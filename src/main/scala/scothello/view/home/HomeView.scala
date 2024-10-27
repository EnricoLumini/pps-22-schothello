package scothello.view.home

import scalafx.geometry.Pos.{Center, TopCenter}
import scalafx.scene.{Node, Parent, Scene}
import scalafx.scene.control.Button
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.VBox
import scothello.controller.home.HomeController
import scothello.game.pages.Pages
import scothello.view.{BaseScalaFXView, View}

trait HomeView extends View

object HomeView:
  def apply(scene: Scene, requirements: View.Requirements[HomeController]): HomeView =
    BaseScalaFXHomeView(scene, requirements)

/** Represents the home view.
  * @param scene
  *   The parent pane.
  * @param requirements
  *   The requirements of the view.
  */
private class BaseScalaFXHomeView(scene: Scene, requirements: View.Requirements[HomeController])
    extends BaseScalaFXView(scene, requirements)
    with HomeView:

  override def parent: Parent = new VBox:
    spacing = 170
    stylesheets = List("file:style/homepage.css")

    val logo: ImageView = new ImageView(new Image("file:res/img/appname-no-background.png")):
      preserveRatio = true
      alignment = TopCenter

    val button: Button = new Button("Start"):
      id = "startButton"
      text = "Start"
      alignment = Center
      onAction = _ => navigateToStart()

    children += logo += button

  private def navigateToStart(): Unit =
    this.navigateTo(Pages.Start)
