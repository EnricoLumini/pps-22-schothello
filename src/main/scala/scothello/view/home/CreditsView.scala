package scothello.view.home

import javafx.scene.text.TextAlignment
import scalafx.animation.TranslateTransition
import scalafx.geometry.Insets
import scalafx.geometry.Pos.Center
import scalafx.scene.control.Button
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.{Node, Parent, Scene}
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.text.{Text, TextFlow}
import scalafx.util.Duration
import scothello.controller.home.CreditsController
import scothello.game.pages.Pages
import scothello.view.{BaseScalaFXView, View}

trait CreditsView extends View

object CreditsView:
  def apply(scene: Scene, requirements: View.Requirements[CreditsController]): CreditsView =
    BaseScalaFXCreditsView(scene, requirements)

/** Represents the home view.
  * @param mainScene
  *   The parent pane.
  * @param requirements
  *   The requirements of the view.
  */
private class BaseScalaFXCreditsView(mainScene: Scene, requirements: View.Requirements[CreditsController])
    extends BaseScalaFXView(mainScene, requirements)
    with CreditsView:

  override def parent: Parent = new VBox:
    stylesheets = List(getClass.getResource("/styles/creditspage.css").toExternalForm)
    alignment = Center

    val logo: ImageView = new ImageView(
      new Image(getClass.getResource("/imgs/appname-no-background.png").toExternalForm)
    ):
      preserveRatio = true
      fitWidth = 400

    val backButton: Button = new Button("BACK"):
      onAction = _ => navigateToHome()

    val creditsText: TextFlow = new TextFlow:
      children = Seq(
        logo,
        new Text("\n\n\n Desgined, Developed and Published by: \n\n\n"):
          fill = Color.Red
        ,
        new Text("Enrico Lumini \n"):
          fill = Color.White
        ,
        new Text("Francesco Ercolani"):
          fill = Color.White
      )
    creditsText.setTextAlignment(TextAlignment.CENTER)

    val translateTransition: TranslateTransition = new TranslateTransition:
      node = creditsText
      fromY = mainScene.height.value
      toY = -creditsText.boundsInParent().getHeight / 2
      duration = Duration(7000)

    children.addAll(creditsText)

    translateTransition.onFinished = (e) => children += backButton

    translateTransition.play()

  private def navigateToHome(): Unit =
    this.navigateTo(Pages.Home)
