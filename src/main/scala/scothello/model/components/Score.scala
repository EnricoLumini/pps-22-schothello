package scothello.model.components

import scothello.model.game.config.Player

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

  /** Calculates the scores from the assigned pawns.
    *
    * @param assignedPawns
    *   The assigned pawns.
    * @return
    *   The scores.
    */
  def calculateScores(assignedPawns: AssignedPawns): Scores =
    assignedPawns
      .groupBy(_._2.player)
      .map { case (player, pawns) => player -> pawns.size }
