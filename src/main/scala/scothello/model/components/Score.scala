package scothello.model.components

import scothello.model.game.config.Player
import scothello.utils.Pair

/** A map of players to their scores.
  */
type Scores = Map[Player, Int]

object Scores:
  /** Creates a new empty score map.
    *
    * @return
    *   The empty score map.
    */
  def empty: Scores = Map.empty

  /** Creates a new score map with the given players.
    *
    * @param players
    *   The players.
    * @return
    *   The score map.
    */
  def initialize(players: Pair[Player]): Scores =
    players.map(_ -> 0).toMap
