**Documento di Design del Software - FallSimulator**

**1. Introduzione**

**1.1 Scopo del Documento**  
Questo documento descrive il design e l\'architettura del progetto
FallSimulator, fornendo una panoramica delle scelte progettuali e delle
componenti principali del software. È stato redatto per garantire una
comprensione completa delle soluzioni adottate, in linea con lo standard
IEEE 1016, e per agevolare eventuali aggiornamenti o manutenzioni
future.

**1.2 Contesto del Progetto**  
Il progetto FallSimulator ha l\'obiettivo di studiare la caduta libera
lungo una traiettoria determinata, calcolando il tempo \"t\" necessario
per completarla. L\'applicazione consente di visualizzare un\'animazione
della sfera lungo curve matematiche preimpostate (come retta, parabola,
cicloide e circonferenza). Questo progetto si basa sul problema della
brachistocrona, ovvero la traiettoria che minimizza il tempo di discesa
tra due punti.

**1.3 Glossario**

- **Brachistocrona**: Traiettoria che garantisce il tempo minimo di
  caduta tra due punti.

- **Curve preimpostate**: Tratte matematiche già calcolate e disponibili
  nell\'applicazione (es. retta, parabola, ecc.).

- **Singleton**: Pattern di progettazione che garantisce l'esistenza di
  una sola istanza di una classe.

- **Listener/Observer**: Pattern che consente a un oggetto di notificare
  automaticamente i cambiamenti ai suoi osservatori.

**1.4 Riferimenti**

- Documento dei Requisiti del progetto FallSimulator.

- Diagrammi UML sviluppati per il progetto (classi, stati, casi d'uso).

- Specifiche del problema matematico della brachistocrona.

**2. Panoramica dell\'Architettura**

**2.1 Stile Architetturale**  
Il progetto **FallSimulator** adotta lo stile architetturale
**Model-View-Controller (MVC)**, che separa le responsabilità tra tre
componenti principali:

- **Model**: Gestisce la logica di calcolo delle curve matematiche e il
  tempo di percorrenza \"t\".

- **View**: Si occupa della visualizzazione grafica, inclusa
  l'interfaccia utente e l'animazione della sfera lungo la traiettoria.

- **Controller**: Interpreta i comandi dell'utente, coordina le
  operazioni tra Model e View, e assicura il flusso di informazioni.

**2.2 Design Pattern Utilizzati**  
Oltre allo stile MVC, sono stati adottati due design pattern per
migliorare la modularità e l'estendibilità del progetto:

1.  **Singleton**:

    - Utilizzato per garantire che alcune classi abbiano una sola
      istanza, come nel caso della gestione del contesto grafico
      (GraphicsContext) o dei parametri globali.

2.  **Listener/Observer**:

    - Implementato per notificare automaticamente i cambiamenti tra
      componenti. Ad esempio, il Controller osserva i cambiamenti nel
      **Model** per aggiornare dinamicamente la **View**.

**2.3 Componenti Principali**  
Il software è suddiviso in diverse componenti per garantire modularità e
facilità di manutenzione:

- **Curve**: rappresenta le curve matematiche (retta, parabola,
  cicloide, circonferenza).

- **SimulationManager**: Gestisce l'animazione delle masse lungo le curve e calcola il tempo di percorrenza \"t\" per ogni
  curva.

- **Layout**: Fornisce l'interfaccia grafica per l'utente, che
  include la selezione delle curve e l'avvio dell'animazione.

- **EventHandler**: reagisce all'interazione con l'utente

**3. Descrizione delle Componenti**

**3.1 Struttura dei Package**  
Il progetto è organizzato in diversi package per garantire modularità e
una chiara separazione delle responsabilità:

- **ingdelsw.fallsimulator.math**: Contiene le classi per i calcoli
  matematici relativi alle curve.

- **ingdelsw.fallsimulator.simulation**: Gestisce l'animazione della
  sfera lungo le traiettorie calcolate.

- **ingdelsw.fallsimulator.UI**: Contiene le classi per l'interfaccia
  grafica e l'interazione con l'utente.

- **ingdelsw.fallsimulator.controller**: Contiene le classi di coordinamento
  tra i vari moduli.

**3.2 Descrizione delle Classi Principali**

1.  **Curve** - sottoclassi Cycloid, Parabola, Circumference e CubicSpline

    - **Package:** ingdelsw.fallsimulator.math

    - **Responsabilità:** Generare le curve matematiche (retta,
      parabola, cicloide, circonferenza).

    - **Metodi principali:**
    - 
       - calculatePoints()

      - calculateSlopes()

      - curveName()

2.  **SimulationManager**

    - **Package:** ingdelsw.fallsimulator.animation

    - **Responsabilità:** Gestisce l'animazione della sfera che si muove
      lungo la curva calcolata e Calcola il tempo \"t\" necessario per
      percorrere la curva.

    - **Metodi principali:**

      - startAnimation(Curve curve)

      - calculateTimeParametrization(Curve curve, double mass)

3.  **InputController**

    - **Package:** ingdelsw.fallsimulator.ui

    - **Responsabilità:** Coordina le interazioni tra l'utente, il Model
      e la View.

    - **Metodi principali:**

      - setStartPoint(Point p)

      - setEndPoint(Point p)

**3.3 Relazioni tra le Classi**

- Il **EventHandler** osserva i cambiamenti nel **Layout** e
  aggiorna il **Layout** tramite il pattern **Observer/Listener**.

- L'**SimulationManager** utilizza i dati calcolati da
  **Curves** per sincronizzare
  l'animazione.

**4. Proprietà Desiderabili**

**4.1 Modularità**  
Il design del progetto è stato strutturato per garantire una chiara
separazione delle responsabilità tra i vari package e classi. Questo
approccio facilita l\'estensione del software, come l\'aggiunta di nuove
curve matematiche o l\'implementazione di funzioni avanzate per
l\'animazione.

**4.2 Manutenibilità**  
L\'uso di design pattern come **Singleton** e **Observer/Listener**
semplifica la gestione delle dipendenze tra le componenti, consentendo
una facile individuazione e risoluzione dei problemi. La documentazione
chiara e il codice ben organizzato rendono più semplice il lavoro su
future evoluzioni del progetto.

**4.3 Usabilità**  
L'interfaccia grafica è stata progettata per essere intuitiva e semplice
da utilizzare. L'utente può selezionare la curva, impostare i parametri
e avviare l'animazione con pochi passaggi. La visualizzazione grafica
rende il simulatore accessibile anche a chi non ha conoscenze
matematiche avanzate.
