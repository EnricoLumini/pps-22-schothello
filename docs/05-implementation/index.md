---
title: Implementazione
nav_order: 6
---

# Implementazione
{: .no_toc }

## Table of Contents
{: .no_toc .text-delta }

1. TOC
{:toc}


## Francesco Ercolani
Il mio contributo nello sviluppo del progetto _Scothello_ riguarda le seguenti parti:

* Realizzazione aspetti di graphic user interface:
  * organizzazione in fogli di stile della view
  * scelta e uniformazione aspetti di stile/design generale delle pagine
  * creazione pagina di inizio partita
  * creazione pagina crediti

* Implementazione della logica di gioco:
  * gestione turno
  * algoritmo posizioni permesse e flip pedine
  * sistema di notifica
  * fine partita

Per quanto riguarda invece le parti per le quali il mio contributo è stato parziale e/o complementare,
figurano le seguenti parti:
 
* Setup iniziale della repository
* Integrazione MVC con ScalaFX
* Situazioni di refactor

### Realizzazione aspetti di graphic user interface

Per sfruttare appieno l'elasticità di ScalaFX e per rendere lo stile generale del gioco uniforme, mantenendo
quindi colori, forme e dimensioni degli elementi comuni alle varie pagine, ho optato per l'utilizzo dei fogli 
di stile.

All'interno del package `resources` troviamo:
* `imgs`: contenente le immagini di background delle singole pagine e i loghi utilizzati
* `styles`: contente i file css 

Il meccanismo utilizzato per avere uno stile uniforme per elementi come i bottoni o i text field
mantenendo la possibilità di modificare determinate caratteristiche specifiche in contesti separati dalle 
pagine, consiste nella creazione di un file css `rootstyle.css` che detta le caratteristiche generali, e una serie 
di css (uno per pagina) che descrivono gli elementi di stile più specifici.

L'object `ScothelloFXApp` è l'entry point dell'applicazione grafica in quanto estende `JFXApp3`. 
Al suo interno avviene la creazione dello `Stage` primario che contiene la `rootScene`.
È proprio a quest'ultima  che viene assegnato il css di definizione dello stile generale. Di seguito 
vediamo come:

```scala
lazy val rootScene: Scene = new Scene:
  root = new Pane()
  stylesheets = List(getClass.getResource("/styles/rootstyle.css").toExternalForm)

override def start(): Unit =
  stage = new JFXApp3.PrimaryStage:
    title = "Scothello"
    scene = rootScene
    width = 1000
    height = 1000
    resizable = false
  postInitAction()
```

Vediamo ora `rootstyle.css` (tagliato per questione di spazio):

```html
@font-face {
    src: url('RenegadePursuit.ttf');
}

.root {
    -fx-font-family: 'Renegade Pursuit';
    -fx-font-size: 25px;
    -fx-font-color: white;
    -fx-text-fill: white;
    -fx-padding: 5px;
}

.label {
    -fx-font-color: white;
    -fx-text-fill: white;
}

.text-field {
    -fx-background-color: #f0f0f0;
    -fx-border-color: #cccccc;
    -fx-border-radius: 5px;
    -fx-background-radius: 5px;
    -fx-padding: 10px;
    -fx-font-size: 14px;
    -fx-text-fill: #333333;
    -fx-max-width: 350px;
    -fx-max-height: 50px;
}
...
```

Seguendo quindi questo meccanismo di assegnazione dei fogli di stile alle varie schermate è stato mio compito 
quello di far sì che gli elementi fossero predisposti in modo coerente e adottassero uno stile adatto
al contesto dell'applicazione.

Mi sono occupato della creazione della pagina di crediti `CreditsView`, nella quale attraverso una transizione di scorrimento testo 
dal basso verso l'alto si mostrano i componenti del team, e della pagina di inizio partita `StartView` nella quale vengono
inseriti i nickname dei due partecipanti e alla pressione del tasto _start_ viene iniziata la partita.
Vediamo quest'ultimo passaggio per chiarire il meccanismo:
```scala
trait StartView extends View

object StartView:
  def apply(scene: Scene, requirements: View.Requirements[StartController]): StartView =
    BaseScalaFXStartView(scene, requirements)

private class BaseScalaFXStartView(mainScene: Scene, requirements: View.Requirements[StartController])
    extends BaseScalaFXView(mainScene, requirements)
    with StartView:

  override def parent: Parent = new VBox:
    spacing = 30
    alignment = Center
    stylesheets = List(getClass.getResource("/styles/startpage.css").toExternalForm)
    
            ...

    if validInput then
            controller.startGame((player1Name, player2Name))
    navigateToGamePage()
            ...

```

Così come tutte le altre pagine, quest'ultima è associata al rispettivo controller, e grazie al cake pattern 
è possibile chiamare l'operazione startgame su controller.
Quest'ultima aggiorna il model assegnando i nickname ai player come valori dello stato.

## Enrico Lumini