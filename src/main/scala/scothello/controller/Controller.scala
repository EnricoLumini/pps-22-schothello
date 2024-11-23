package scothello.controller

import scothello.model.Model
import scothello.model.game.state.GameState
import scothello.view.View

/** Represents a controller of the game.
  */
trait Controller:

  def state: GameState

object Controller:

  type Factory[V <: View, C <: Controller] = Requirements[V] => C

  trait Requirements[V <: View] extends Model.Provider with View.Provider[V]

  trait Dependencies[V <: View](requirements: Requirements[V]) extends Controller:
    protected def view: V = requirements.view
    protected def model: Model = requirements.model

  trait Provider[C <: Controller]:
    def controller: C

/** Represents a reactive model wrapper.
  *
  * @param view
  *   The view.
  * @param model
  *   The model.
  */
class ReactiveModelWrapper(view: => View, model: Model) extends Model:
  private val internalModel = model
  override def state: GameState = internalModel.state
  override def update(u: GameState => GameState): Unit =
    internalModel.update(u)
    view.updateState(internalModel.state)

/** Represents a base controller.
  *
  * @param requirements
  *   The requirements of the controller.
  * @tparam V
  *   The type of the view.
  */
abstract class BaseController[V <: View](
    requirements: Controller.Requirements[V]
) extends Controller
    with Controller.Dependencies(requirements):

  override def state: GameState = model.state
  override val model: Model =
    new ReactiveModelWrapper(view, requirements.model)

/** Represents an empty controller.
  *
  * @param requirements
  *   The requirements of the controller.
  * @tparam V
  *   The type of the view.
  */
class EmptyController[V <: View](
    requirements: Controller.Requirements[V]
) extends BaseController[V](requirements)
