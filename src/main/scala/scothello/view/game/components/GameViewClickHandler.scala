package scothello.view.game.components

import scothello.controller.game.GameController
import scothello.model.board.Tile
import scothello.model.game.state.GameState

trait GameViewClickHandler:

  /** Handles the click on a tile
    * @param tile
    *   the tile clicked
    */
  def onTileClick(tile: Tile): Unit

object GameViewClickHandler:

  def apply(gameController: GameController): GameViewClickHandler =
    new GameViewClickHandler:

      def state: GameState = gameController.state

      override def onTileClick(tile: Tile): Unit =
        state.allowedTiles.get(tile) match
          case Some(player) =>
            gameController.placePawn(tile)
            gameController.flipPawns(tile)
            gameController.nextTurn()
          case _ => ()
