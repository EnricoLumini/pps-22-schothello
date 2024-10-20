package scothello.model.game

import scothello.model.game.config.Player
import scothello.model.game.state.GameState
import scothello.utils.Pair

/** Represents a game of Othello.
  *
  * @param players
  *   The players of the game.
  * @param state
  *   The state of the game.
  */
final case class Game(
    players: Pair[Player],
    state: GameState
)

object Game:
  /** Create a new game.
    *
    * @param players
    *   The players of the game.
    * @param initialState
    *   The initial state of the game.
    * @return
    *   A new game.
    */
  def apply(
      players: Pair[Player],
      initialState: GameState
  ): Game =
    new Game(
      players = players,
      state = initialState
    )

  /** Create a game with the given players.
    *
    * @param players
    *   The players of the game.
    * @return
    *   A new game.
    */
  def apply(
      players: Pair[Player]
  ): Game =
    new Game(
      players = players,
      state = GameState(players)
    )
