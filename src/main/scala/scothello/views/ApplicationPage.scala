package scothello.views

import scothello.controllers.Controller
import scothello.model.Model

/** A trait that represents a page in the application.
  *
  * @tparam C
  *   The type of the controller.
  * @tparam V
  *   The type of the view.
  */
trait ApplicationPage[C <: Controller, V <: View](
    override val model: Model,
    val pageFactory: PageFactory[C, V]
) extends Model.Requirements
    with View.Requirements[C]
    with Controller.Requirements[V]:
  override lazy val view: V = pageFactory.viewFactory(this)
  override lazy val controller: C = pageFactory.controllerFactory(this)

object ApplicationPage:
  /** Creates a new application page.
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
    *   A new application page.
    */
  def apply[C <: Controller, V <: View](
      model: Model,
      pageFactory: PageFactory[C, V]
  ): ApplicationPage[C, V] =
    new ApplicationPage[C, V](model, pageFactory) {}
