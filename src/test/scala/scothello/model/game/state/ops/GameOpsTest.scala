package scothello.model.game.state.ops

import scothello.model.board.{AllowedTiles, Tile}
import scothello.model.components.Pawn
import scothello.model.game.config.Player
import scothello.model.game.state.ops.StartOps.startGame
import scothello.utils.Pair
import scothello.{BaseTest, PlayerProvider}
import scothello.model.game.state.ops.GameOps.{
  calculateAllowedPos,
  endGame,
  flipPawns,
  nextTurn,
  pauseGame,
  placePawn,
  resumeGame
}

class GameOpsTest extends BaseTest with PlayerProvider:

  val playerNames: Pair[String] = Pair.fromSeq(twoPlayers.map(_.name))
  val tiles: Seq[Tile] = Seq(Tile(1, 1), Tile(6, 4), Tile(3, 5), Tile(5, 6))

  "A state with Game Ops" should "advance to the next turn" in {
    val initialState = startGame(playerNames)
    val newState = initialState.nextTurn()
    newState.turn should not be initialState.turn
    newState.turn.number shouldBe initialState.turn.number + 1
    newState.turn.player shouldBe initialState.players
      .oppositeOf(initialState.turn.player)
      .get
  }

  it should "place a pawn on the board" in {
    val initialState = startGame(playerNames)
    tiles foreach { tile =>
      val newState = initialState.placePawn(tile)
      newState.assignedPawns(tile) shouldBe Pawn(initialState.turn.player)
      newState.assignedPawns.size shouldBe initialState.assignedPawns.size + 1
    }
  }

  it should "calculate allowed positions for the current player" in {
    val initialState = startGame(playerNames)
    val newState = initialState.calculateAllowedPos()
    newState.allowedTiles should not be empty
    newState.allowedTiles shouldBe AllowedTiles
      .calculate(initialState.turn.player, initialState.assignedPawns)
  }

  it should "flip pawns correctly and update scores" in {
    val initialState = startGame(playerNames)
    val blackTile = Tile(3, 2)
    val state = initialState
      .placePawn(blackTile)
      .flipPawns(blackTile)
    val blackPlayer = initialState.turn.player
    val whitePlayer = initialState.players.oppositeOf(blackPlayer).get
    val pawns = Map(
      Tile(3, 2) -> Pawn(blackPlayer),
      Tile(3, 3) -> Pawn(blackPlayer),
      Tile(3, 4) -> Pawn(blackPlayer),
      Tile(4, 3) -> Pawn(blackPlayer),
      Tile(4, 4) -> Pawn(whitePlayer)
    )
    val scores = Map(
      blackPlayer -> 4,
      whitePlayer -> 1
    )

    state.assignedPawns shouldBe pawns
    state.playerScores shouldBe scores
  }

  it should "pause the game" in {
    val initialState = startGame(playerNames)
    val newState = initialState.pauseGame()
    newState.isPaused shouldBe true
  }

  it should "resume the game" in {
    val initialState = startGame(playerNames)
    val pausedState = initialState.pauseGame()
    val newState = pausedState.resumeGame()
    newState.isPaused shouldBe false
  }

  it should "end the game and determine a drawn" in {
    val initialState = startGame(playerNames)
    val newState = initialState.endGame()
    newState.winner shouldBe None
  }

  it should "end the game and determine a winner" in {
    val initialState = startGame(playerNames)
    val blackPlayer = initialState.turn.player
    val whitePlayer = initialState.players.oppositeOf(blackPlayer).get
    val pawns = Map(
      Tile(2, 2) -> Pawn(whitePlayer),
      Tile(2, 3) -> Pawn(blackPlayer),
      Tile(2, 4) -> Pawn(whitePlayer),
      Tile(2, 5) -> Pawn(whitePlayer),
      Tile(3, 2) -> Pawn(blackPlayer),
      Tile(3, 3) -> Pawn(whitePlayer),
      Tile(3, 4) -> Pawn(whitePlayer),
      Tile(3, 5) -> Pawn(blackPlayer),
      Tile(4, 2) -> Pawn(whitePlayer),
      Tile(4, 3) -> Pawn(whitePlayer),
      Tile(4, 4) -> Pawn(whitePlayer),
      Tile(4, 5) -> Pawn(blackPlayer),
      Tile(5, 3) -> Pawn(whitePlayer),
      Tile(5, 5) -> Pawn(whitePlayer)
    )
    val state = initialState.copy(assignedPawns = pawns)
    val newState = state.endGame()
    newState.winner shouldBe Some(whitePlayer)
  }
