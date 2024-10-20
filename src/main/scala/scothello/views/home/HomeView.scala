package scothello.views.home

import scalafx.scene.{Node, Parent, Scene}
import scalafx.scene.control.Button
import scalafx.scene.layout.{Pane, VBox}
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
      text = "Start Game"
      onAction = _ => print("Start Game")
