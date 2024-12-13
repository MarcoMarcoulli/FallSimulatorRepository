**Documento Requisiti - FallSimulator**

**1. Introduzione**

- **Scopo del Documento:**
  Questo documento definisce i requisiti funzionali e non funzionali del progetto **FallSimulator**. La sua funzione è di stabilire una base chiara e condivisa delle specifiche del progetto tra il team di sviluppo e i revisori, guidando la progettazione e lo sviluppo dell'applicazione.
- **Contesto del Progetto:**
  Il progetto FallSimulator ha l'obiettivo di studiare la caduta libera lungo una traiettoria determinata, con un focus sul calcolo del tempo di percorrenza "t" su una curva definita. Questo obiettivo nasce dal problema della brachistocrona, che richiede la ricerca della traiettoria ideale per minimizzare il tempo di discesa tra due punti.
- **Glossario e Terminologia:**
  - **Curva preimpostata:** Tipi di traiettorie già definite nell'applicazione (es. parabola, retta, circonferenza, cicloide).
  - **Animazione della sfera:** Movimento simulato di una sfera lungo la curva, visibile nell’interfaccia utente.
  - **Tempo "t":** Tempo impiegato dalla sfera per percorrere la traiettoria.

**2. Requisiti Funzionali**

**2.1 Generazione delle Curve**

- **Descrizione:** L'applicazione deve generare le curve matematiche preimpostate, selezionabili dall'utente.
- **Dettagli operativi:** L'utente può scegliere tra retta, parabola, circonferenza (con raggio variabile) e cicloide.
- **Diagramma dei casi d’uso:** Descrive le interazioni dell'utente con l'app per selezionare e visualizzare le curve.
2. **Calcolo del Tempo di Percorrenza**
- **Descrizione:** Per ogni curva, l'applicazione deve calcolare il tempo "t" impiegato dalla sfera per percorrere l’intera traiettoria.
- **Criteri di verifica:** Test matematici e confronto con risultati di GeoGebra per verificarne la correttezza.

**2.3 Animazione della Sfera**

- **Descrizione:** L'applicazione deve animare una sfera che percorre la curva, rispettando i parametri di tempo calcolati.
- **Criteri di qualità:** L'animazione deve essere fluida, rappresentando fedelmente la traiettoria.
2. ` `**Interfaccia Utente**
- **Descrizione:** L’interfaccia deve permettere la selezione della curva, l’impostazione dei parametri (es. raggio della circonferenza) e l’avvio dell’animazione.
- **Diagrammi di stati e classi:** Visualizza le interazioni e la struttura logica dell'interfaccia utente.

**3. Requisiti Non Funzionali**

**3.1 Usabilità**

- **Descrizione:** L’interfaccia deve essere intuitiva e di facile utilizzo, consentendo una navigazione agevole tra le funzionalità.
- **Criteri di misurazione:** Test di usabilità con utenti per valutare accessibilità e intuitività.
2. **Prestazioni**
- **Descrizione:** L’animazione della sfera deve mantenere una fluidità adeguata e il calcolo delle curve deve avvenire in modo rapido.
- **Criteri di misurazione:** Tempo massimo di risposta per il rendering dell'animazione e calcolo delle curve.

**3.3 Precisione**

- **Descrizione:** I calcoli devono essere matematicamente accurati e verificabili.
- **Criteri di verifica:** Confronto dei risultati del calcolo con software come GeoGebra.

**3.4 Manutenibilità**

- **Descrizione:** Il codice deve essere organizzato in modo modulare per consentire aggiornamenti e manutenzioni future.
- **Criteri di misurazione:** Misure di copertura dei test e semplicità nell’isolare le funzionalità.

**3.5 Compatibilità**

- **Descrizione:** L’applicazione deve essere compatibile con il sistema operativo usato dal team.
- **Criteri di verifica:** Test di compatibilità eseguiti su ambienti di sviluppo e dispositivi specificati.

**4. Modellazione dei Requisiti**

- **Diagrammi dei Casi d’Uso:**
  Fornisce una panoramica delle interazioni tra l’utente e le principali funzionalità del simulatore.
- **Diagrammi delle Classi:**
  Mostra l'architettura e la struttura logica del codice, inclusa la gestione delle curve e dell’animazione.
- **Diagrammi di Stato:**
  Visualizza il comportamento dinamico dell’animazione della sfera lungo le curve, rappresentando i vari stati in cui si trova durante la simulazione.

