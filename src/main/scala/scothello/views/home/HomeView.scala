package scothello.views.home

import scalafx.scene.{Node, Parent, Scene}
import scalafx.scene.control.Button
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
    spacing = 10

    children += new Button:
      text = "Start"
      onAction = _ => navigateToHome()

  // TODO: Find how to get this inside onAction
  private def navigateToHome(): Unit =
    this.navigateTo(Pages.Home)
