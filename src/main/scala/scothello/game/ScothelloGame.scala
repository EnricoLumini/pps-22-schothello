package scothello.game

import scothello.game.pages.{GamePage, PageFactory, Pages}
import scothello.model.Model
import scothello.model.game.state.GameState

/** An game is a collection of pages that share a model.
  */
trait ScothelloGame:
  val model: Model
  val pages: Map[Pages, GamePage[?, ?]]

object ScothelloGame:
  /** Create a new game with the given pages.
    *
    * @param pagesFactories
    *   A map of pages and their factories.
    * @return
    *   A new game with the given pages.
    */
  def apply(
      pagesFactories: Map[Pages, PageFactory[?, ?]]
  ): ScothelloGame =
    new ScothelloGame:
      override val model: Model = Model(GameState())
      override val pages: Map[Pages, GamePage[?, ?]] =
        pagesFactories.map((page, pageFactory) => page -> GamePage(model, pageFactory))
