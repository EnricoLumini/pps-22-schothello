package scothello.views

import scothello.controllers.Controller
import scothello.model.Model

/** Represents a game page.
  *
  * @param model
  *   The model.
  * @param pageFactory
  *   The page factory.
  * @tparam C
  *   The type of the controller.
  * @tparam V
  *   The type of the view.
  */
trait GamePage[C <: Controller, V <: View](
    override val model: Model,
    val pageFactory: PageFactory[C, V]
) extends Model.Requirements
    with View.Requirements[C]
    with Controller.Requirements[V]:
  override lazy val view: V = pageFactory.viewFactory(this)
  override lazy val controller: C = pageFactory.controllerFactory(this)

object GamePage:
  /** Creates a game page.
    *
    * @param model
    *   The model.
    * @param pageFactory
    *   The page factory.
    * @tparam C
    *   The type of the controller.
    * @tparam V
    *   The type of the view.
    * @return
    *   A game page.
    */
  def apply[C <: Controller, V <: View](
      model: Model,
      pageFactory: PageFactory[C, V]
  ): GamePage[C, V] =
    new GamePage[C, V](model, pageFactory) {}
