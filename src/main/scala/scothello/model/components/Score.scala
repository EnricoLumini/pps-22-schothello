package scothello.model.components

import scothello.model.game.config.Player
import scothello.utils.Pair

/** A map of players to their scores.
  */
type Scores = Map[Player, Int]

object Scores:
  /** Creates a new empty score map.
    *
    * @param players
    *   The players to create the score map for.
    * @return
    *   The empty score map.
    */
  def empty(players: Pair[Player]): Scores =
    players.map(_ -> 0).toMap
