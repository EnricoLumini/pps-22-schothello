package scothello.game.pages

import scalafx.scene.Scene
import scothello.controller.Controller
import scothello.view.{ScalaFXView, View}

/** A factory for creating a page with a controller and a view.
  * @tparam C
  *   The type of the controller.
  * @tparam V
  *   The type of the view.
  */
trait PageFactory[C <: Controller, V <: View]:
  def viewFactory: View.Factory[C, V]
  def controllerFactory: Controller.Factory[V, C]

object PageFactory:
  def apply[C <: Controller, V <: View](
      _viewFactory: View.Factory[C, V],
      _controllerFactory: Controller.Factory[V, C]
  ): PageFactory[C, V] =
    new PageFactory[C, V]:
      override val viewFactory: View.Factory[C, V] = _viewFactory
      override val controllerFactory: Controller.Factory[V, C] = _controllerFactory

object ScalaFXPageFactory:
  def apply[C <: Controller, V <: View](
      scalaFXViewFactory: ScalaFXView.Factory[C, V],
      _controllerFactory: Controller.Factory[V, C]
  )(using rootScene: Scene): PageFactory[C, V] =
    new PageFactory[C, V]:
      override def viewFactory: View.Factory[C, V] = scalaFXViewFactory(rootScene, _)
      override def controllerFactory: Controller.Factory[V, C] = _controllerFactory
