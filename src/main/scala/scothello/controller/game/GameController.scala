package scothello.controller.game

import scothello.controller.{BaseController, Controller}
import scothello.view.game.GameView
import scothello.model.board.Tile
import scothello.model.game.state.ops.GameOps.*

/** Controller for the game page
  */
trait GameController extends Controller:

  /** Increases the turn
    */
  def nextTurn(): Unit

  /** Places a pawn on the board
    *
    * @param tile
    *   the tile where the pawn will be placed
    */
  def placePawn(tile: Tile): Unit

  /** Calculates the allowed positions for the current player
    */
  def calculateAllowedPos(): Unit

  /** Flips the pawns on the board
    *
    * @param tile
    *   the tile from which the pawns will be flipped
    */
  def flipPawns(tile: Tile): Unit

  /** Pauses the game
    */
  def pauseGame(): Unit

  /** Resume the game
    */
  def resumeGame(): Unit

  /** Ends the game
    */
  def endGame(): Unit

object GameController:

  def apply(requirements: Controller.Requirements[GameView]): GameController =
    new GameControllerImpl(requirements)

private class GameControllerImpl(requirements: Controller.Requirements[GameView])
    extends BaseController(requirements)
    with GameController:

  override def nextTurn(): Unit =
    this.model.update(_.nextTurn())

  override def placePawn(tile: Tile): Unit =
    this.model.update(_.placePawn(tile))

  override def calculateAllowedPos(): Unit =
    this.model.update(_.calculateAllowedPos())

  override def flipPawns(tile: Tile): Unit =
    this.model.update(_.flipPawns(tile))

  override def pauseGame(): Unit =
    this.model.update(_.pauseGame())

  override def resumeGame(): Unit =
    this.model.update(_.resumeGame())

  override def endGame(): Unit =
    this.model.update(_.endGame())
