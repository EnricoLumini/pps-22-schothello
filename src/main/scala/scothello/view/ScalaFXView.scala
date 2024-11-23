package scothello.view

import scalafx.scene.{Parent, Scene}
import scothello.controller.Controller
import scothello.model.game.state.GameState
import scothello.view.utils.ResettableObjectProperty

/** Represents a ScalaFX view.
  *
  * @param scene
  *   The parent pane.
  */
trait ScalaFXView(
    val scene: Scene
) extends View:

  def reactiveState: ResettableObjectProperty[GameState]

  def parent: Parent

  override def show(): Unit =
    scene.root = parent

object ScalaFXView:
  type Factory[C <: Controller, V <: View] = (Scene, View.Requirements[C]) => V

/** Represents a base ScalaFX view.
  *
  * @param scene
  *   The parent pane.
  * @param requirements
  *   The requirements of the view.
  * @tparam C
  *   The type of the controller.
  */
abstract class BaseScalaFXView[C <: Controller](
    scene: Scene,
    requirements: View.Requirements[C]
) extends BaseView[C](requirements)
    with ScalaFXView(scene):

  private val _reactiveState: ResettableObjectProperty[GameState] = ResettableObjectProperty(controller.state)

  override def reactiveState: ResettableObjectProperty[GameState] =
    _reactiveState.set(controller.state)
    _reactiveState

  override def updateState(state: GameState): Unit =
    super.updateState(state)
    _reactiveState() = state
