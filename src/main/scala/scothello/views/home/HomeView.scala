package scothello.views.home

import scalafx.geometry.Pos.{BottomCenter, Center, CenterRight, TopCenter, TopLeft}
import scalafx.scene.{Node, Parent, Scene}
import scalafx.scene.control.Button
import scalafx.scene.control.ContentDisplay.Bottom
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.VBox
import scothello.Pages
import scothello.controllers.home.HomeController
import scothello.views.{BaseScalaFXView, View}

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

    val logo: ImageView = new ImageView(new Image("file:res/img/appname-no-background.png")):
      preserveRatio = true
      alignment = TopCenter

    val button: Button = new Button("Start"):
      id = "startButton"
      text = "Start"
      alignment = Center
      onAction = _ => navigateToHome()

    logo.fitWidthProperty().bind(width)
    logo.fitHeightProperty().bind(height)

    children += logo += button

  // TODO: Find how to get this inside onAction
  private def navigateToHome(): Unit =
    this.navigateTo(Pages.Home)
