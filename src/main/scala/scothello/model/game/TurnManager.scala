package scothello.model.game

import scothello.model.game.config.Player
import scothello.utils.Pair

/** Manages the turns in a game.
  */
object TurnManager:

  private var playerIterator: Iterator[Player] = Iterator.empty
  private var currentTurnNumber: Int = 1
  private var initialTurnPlayer: Player = Player.empty

  def apply(players: Pair[Player]): Unit =
    currentTurnNumber = 1
    playerIterator = LazyList.continually(Seq(players._1, players._2)).flatten.iterator
    initialTurnPlayer = playerIterator.next()

  /** The initial turn.
    */
  def initialTurn: Turn =
    currentTurnNumber = 1
    Turn.ValidTurn(1, initialTurnPlayer)

  /** The next turn.
    */
  def nextTurn: Turn =
    currentTurnNumber += 1
    Turn.ValidTurn(currentTurnNumber, playerIterator.next())
