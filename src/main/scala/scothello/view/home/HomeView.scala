package scothello.view.home

import scalafx.geometry.Insets
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
  * @param mainScene
  *   The parent pane.
  * @param requirements
  *   The requirements of the view.
  */
private class BaseScalaFXHomeView(mainScene: Scene, requirements: View.Requirements[HomeController])
    extends BaseScalaFXView(mainScene, requirements)
    with HomeView:

  override def parent: Parent = new VBox:
    stylesheets = List(getClass.getResource("/styles/homepage.css").toExternalForm)

    val logo: ImageView = new ImageView(
      new Image(getClass.getResource("/imgs/appname-no-background.png").toExternalForm)
    ):
      preserveRatio = true
      alignment = TopCenter

    val startButton: Button = new Button("START"):
      margin = Insets(mainScene.height.value * 0.15, 0, 10, 0)
      alignment = Center
      onAction = _ => navigateToStart()

    val creditsButton: Button = new Button("CREDITS"):
      alignment = Center
      onAction = _ => navigateToCredits()

    children.addAll(logo, startButton, creditsButton)

  private def navigateToStart(): Unit =
    this.navigateTo(Pages.Start)

  private def navigateToCredits(): Unit =
    this.navigateTo(Pages.Credits)
