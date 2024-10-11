---
title: Processo di sviluppo
nav_order: 2
---
# Processo di sviluppo adottato

Il processo di sviluppo adottato è SCRUM-inspired, scelto basandosi sulle linee guida del corso, in modo da gestire in maniera agile il progetto.

## Ruoli

Nella fase iniziale del processo sono stati definiti i seguenti ruoli:

* L'*esperto di dominio*: Francesco Ercolani, responsabile della qualità e usabilità del prodotto e sviluppatore.
* Il *product owner*: Enrico Lumini, responsabile di supervisionare l'andamento del progetto e del coordinamento del team di sviluppo e sviluppatore.

## Meeting

Le tipologie di meeting utilizzate sono le seguenti:

* **Initial planning**: meeting di inizio progetto. È stato svolto a inizio progetto ed ha coinvolto entrambi i membri del team. Lo scopo di questo meeting è quello di definire il *Product Backlog*, ovvero l'insieme di tutti i requisiti del progetto, oltre che definire gli obbiettivi dello sprint iniziale.
* **Sprint planning**: meeting di pianificazione. Esso viene svolto all'inizio di ogni sprint e coinvolge entrambi i membri del team. Lo scopo di questo meeting è quello di selezionare gli item dal Product Backlog che verranno sviluppati durante lo sprint, oltre che definire le attività da svolgere per raggiungere gli obbiettivi dello sprint effettuando una *decomposizione in task* degli item selezionati.
* **Daily Scrum**: meeting giornaliero di circa 15 minuti, esso viene svolto circa ogni giorno e coinvolge entrambi i membri del team. Lo scopo di questo meeting è quello di tenere aggiornato il team sullo stato di avanzamento del progetto, oltre che individuare eventuali difficoltà incontrate dai membri del team.
* **Sprint review:** meeting di revisione, che viene svolto alla fine di ogni sprint e coinvolge tutti i membri del team. Lo scopo di questo meeting è quello di valutare il lavoro svolto durante lo sprint.

## Trello

Per la realizzazione del Product Backlog e per la gestione delle attività da svolgere, abbiamo deciso di utilizzare *Trello*, strumento che agevola la gestione del progetto.

A ogni Sprint Planning vengono selezionati gli item dal Product Backlog che verranno sviluppati e vengono creati i task necessari per lo sviluppo di ogni item tramite decomposizione.
All'interno dello Sprint Planning viene decisa anche una prima assegnazione dei task, mentre quelli lasciati senza assegnatario posso essere auto-assegnati dai membri del team in autonomia.

Per rendere la gestione più ordinata sono state utilizzate le label di Trello al fine di identificare le macro-aree ricoperte dai vari task, in particolare sono state definite le seguenti:
* `C.I.`: Continuous Integration
* `Docs`: Documentazione
* `Scrum`: Processo di sviluppo
* `Code`: Implementazione di codice
* `Test`: Testing
* `Style`: Stile del codice
* `Architecture`: Architettura del software

Anche per la gestione della stima della durata dei task sono state sfruttate le label di Trello, in particolare sono state definite le seguenti:
`1h`, `2h`, `4h`, `8h`, `12h`.

## DVCS

Per quanto riguarda l'utilizzo di Git e GitHub, abbiamo deciso di utilizzare i seguenti branch principali:
* `main`: branch principale, contiene il codice in fase di sviluppo
* `doc`: branch per la documentazione del progetto, schemi, diagrammi, backlog e altri artifact

Abbiamo inoltre deciso di utilizzare le seguenti convenzioni per la denominazione dei branch:
* `feature/<featureName>`: branch per lo sviluppo di una feature
* `release/<releaseName>`: branch per la produzione di una release
* `hotfix/<hitfixName>`: branch per la correzione di un bug qualora si verifichi in produzione

## Definition of Done

Una feature è considerata finita e integrabile nel main branch se soddisfa i seguenti criteri:
* I test scritti per la feature passano tutti
* Gli altri test presenti nel progetto passano tutti
* È presente della documentazione del codice

## Testing

Per garantire la correttezza delle funzionalità sviluppate, è stato adottato l'approccio del *Test Driven Development*(TDD).

Il processo di sviluppo con TDD prevede tre passaggi principali:
1. **Scrittura di un Test**: inizialmente si scrive un test che descrive il comportamento desiderato del componente o della funzionalità che si sta sviluppando. Questo test deve fallire dato che il componente o la funzionalità non è stata ancora implementata.
2. **Implementazione**: successivamente, si procede con l'implementazione del componente o della funzionalità in modo da far superare il test precedentemente scritto.
3. **Refactoring**: dopo aver fatto passare il test, sarà possibile apportare eventuali miglioramenti al codice, refattorizzandolo per renderlo più pulito e meglio comprensibile. È importante che il test continui a passare anche dopo il refactoring.

## Building

Come build tool è stato scelto *Sbt*, che gestisce le opportune dipendenze del progetto.

Inoltre, facilita l'esecuzione dei test, la generazione della documentazione e la compilazione.

## Code Quality

Per garantire la qualità del codice, è stato deciso di utilizzare *Scalafmt*, uno strumento che permette di formattare automaticamente il codice sorgente in modo da renderlo più leggibile e coerente.

## CI/CD

Per la *Continuous Integration* e *Continuous Deployment* abbiamo scelto di utilizzare le GitHub Actions.

In particolare, sono stati definiti i seguenti *workflow*:
* **Test & Format**: vengono eseguiti i test e viene controllata la formattazione del codice. Se tutti i test passano viene generata la scaladoc utilizzando il workflow **Generate Scaladoc**
* **Generate Scaladoc**: viene eseguito in seguito al successo del workflow **Test & Format**. Ha il compito di generare la scaladoc e di pusharla sul branch doc, al fine di creare la documentazione
* **Pages deploy**: generazione dell'artefatto web e caricamento su GitHub Pages, comprende sia il progetto che la documentazione

## Documentazione

Per quanto riguarda la documentazione del progetto, è stato utilizzato il linguaggio Markdown, scrivendola nell'apposito branch e, successivamente, caricata su GitHub Pages, utilizzando il workflow precedentemente descritto.

Per la generazione è stato utilizzato il tool [Jekyll](https://jekyllrb.com) che permette di generare la documentazione in formato HTML.