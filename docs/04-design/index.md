---
title: Design di dettaglio
nav_order: 5
---

# Design di dettaglio
{: .no_toc }

## Table of Contents
{: .no_toc .text-delta }

1. TOC
{:toc}

## Organizzazione del codice

![Packages](../img/04-design/packages.png)

Il codice è organizzato in 5 package principali:
* `model` contiene le classi che implementano le regole del gioco e che permettono di gestire il suo svolgimento.
* `view` contiene le classi che permettono di visualizzare lo stato dell'applicazione all'utente e che ne permettono l'interazione diretta tramite un interfaccia grafica.
* `controller` contiene le entità che permettono di gestire le interazioni dell'utente con l'applicazione, ovvero quelle che implementano la logica di presentazione e di accesso al Model.
* `utils` contiene classi di utilità.
* `game` contiene le classi che gestiscono il gioco, inclusa la definizione dell'applicazione JFXApp da visualizzare.

## Architettura

![Architettura MVC](../img/04-design/mvc.png)

### Model
Il Model consiste nello stato dell'applicazione, che viene modificato tramite l'applicazione di una funzione di update.
Lo stato che viene esposto è quindi sempre un oggetto immutabile, permettendone la condivisione tra le varie componenti dell'applicazione.

### Controller
L'elemento del controller è costituito dai singoli package che gesticono ciascuna delle pagine che vengono 
visualizzate durante l'esecuzione del gioco, nello specifico:
* `home`: contiene i controller per la pagina iniziale, dei crediti e di inizio gioco.
* `game`: contiene il controller che gestisce le operazioni riguardanti l'esecuzione di una partita, dall'inizio
alla fine.
* `end`: contiene il controller per la pagina finale, a partita finita.

L'aggiornamento dello stato della view avviene automaticamente grazie a `ReactiveModelWrapper`, che funge da
intermediario avvolgendo il model e notificando la view ogni volta che lo stato subisce una modifica.

### View

### Game