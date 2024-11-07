package scothello.view.end

import scalafx.scene.{Parent, Scene}
import scalafx.scene.control.Label
import scalafx.scene.layout.VBox
import scothello.view.{BaseScalaFXView, View}
import scothello.controller.end.EndgameController

trait EndgameView extends View

object EndgameView:
  def apply(scene: Scene, requirements: View.Requirements[EndgameController]): EndgameView =
    BaseScalaFXEndgameView(scene, requirements)

private class BaseScalaFXEndgameView(scene: Scene, requirements: View.Requirements[EndgameController])
    extends BaseScalaFXView(scene, requirements)
    with EndgameView:

  override def parent: Parent = new VBox:
    val label: Label = new Label("prova")

    children += label
