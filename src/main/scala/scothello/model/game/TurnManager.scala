package scothello.model.game

import scothello.model.game.config.Player
import scothello.utils.Pair

/** Manages the turns in a game. It controls the flow of turns between the players, allowing for the sequential
  * execution of player actions.
  */
object TurnManager:

  private var playerIterator: Iterator[Player] = Iterator.empty
  private var currentTurnNumber: Int = 1
  private var initialTurnPlayer: Player = Player.empty

  def apply(players: Pair[Player]): Unit =
    playerIterator = LazyList.continually(Seq(players._1, players._2)).flatten.iterator
    initialTurnPlayer = playerIterator.next()

  def initialTurn: Turn =
    Turn.ValidTurn(1, initialTurnPlayer)

  def nextTurn: Turn =
    currentTurnNumber += 1
    Turn.ValidTurn(currentTurnNumber, playerIterator.next())
