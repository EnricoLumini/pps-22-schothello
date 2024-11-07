package scothello.controller.game

import scothello.controller.{BaseController, Controller}
import scothello.view.game.GameView
import scothello.model.ModelOps.updateState
import scothello.model.board.Tile
import scothello.model.game.state.ops.GameOps.*

/** Controller for the game page */
trait GameController extends Controller:

  /** Increases the turn */
  def nextTurn(): Unit

  /** Places a pawn on the board
    *
    * @param tile
    *   the tile where the pawn will be placed
    */
  def placePawn(tile: Tile): Unit

  /** Calculates the allowed positions for the current player */
  def calculateAllowedPos(): Unit

  /** Flips the pawns on the board
    *
    * @param tile
    *   the tile from which the pawns will be flipped
    */
  def flipPawns(tile: Tile): Unit

  /** Pauses the game */
  def pauseGame(): Unit

  /** Resume the game */
  def resumeGame(): Unit

  /** Stops the game */
  def stopGame(): Unit

object GameController:

  def apply(requirements: Controller.Requirements[GameView]): GameController =
    new GameControllerImpl(requirements)

private class GameControllerImpl(requirements: Controller.Requirements[GameView])
    extends BaseController(requirements)
    with GameController:

  override def nextTurn(): Unit =
    this.model.updateState(_.nextTurn())

  override def placePawn(tile: Tile): Unit =
    this.model.updateState(_.placePawn(tile))

  override def calculateAllowedPos(): Unit =
    this.model.updateState(_.calculateAllowedPos())

  override def flipPawns(tile: Tile): Unit =
    this.model.updateState(_.flipPawns(tile))

  override def pauseGame(): Unit =
    this.model.updateState(_.pauseGame())

  override def resumeGame(): Unit =
    this.model.updateState(_.resumeGame())

  override def stopGame(): Unit =
    this.model.updateState(_.stopGame())
