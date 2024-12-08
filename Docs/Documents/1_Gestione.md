**Gestione del progetto**

**Modello di sviluppo: Incrementale**

Il modello di sviluppo incrementale è stato scelto per il progetto **FallSimulator** per gestire in modo efficiente l’aggiunta progressiva di funzionalità, assicurando un rilascio strutturato e una verifica continua del prodotto. Questo approccio consente di creare una versione funzionante del software già dai primi incrementi, fornendo un prodotto parzialmente utilizzabile che verrà progressivamente ampliato e migliorato.

**Descrizione del processo incrementale**

1. **Suddivisione dei requisiti in incrementi:**
   1. Il progetto viene suddiviso in funzionalità principali o "incrementi" che possono essere completati singolarmente. Gli incrementi principali includono:
      1. **Curva di base:** Iniziale sviluppo delle curve preimpostate (retta, parabola).
      1. **Estensione delle curve:** Implementazione della circonferenza e della cicloide, con calcoli avanzati per il raggio variabile.
      1. **Animazione e calcolo del tempo:** Sviluppo dell’animazione della sfera e determinazione precisa del tempo "t" di percorrenza.
      1. **Interfaccia utente:** Creazione e ottimizzazione dell’interfaccia per una migliore esperienza utente.
1. **Pianificazione degli incrementi:**
   Ogni incremento viene pianificato con obiettivi definiti. Per il FallSimulator, ogni blocco di funzionalità (curva, animazione, ecc.) rappresenta un incremento a sé stante, con una durata media stimata di alcune settimane. Ciascun incremento include:
   1. **Sviluppo:** Implementazione del codice per le nuove funzionalità.
   1. **Test iniziali e verifica:** Controllo della correttezza matematica e funzionale, con test manuali e confronti grafici tramite strumenti come GeoGebra.
   1. **Integrazione con la versione esistente:** Inserimento della nuova funzionalità nella versione stabile, garantendo la compatibilità con le funzioni già presenti.
1. **Test e feedback progressivo:**
   Al termine di ciascun incremento, viene rilasciata una versione parziale del simulatore, che viene sottoposta a test approfonditi. Il feedback ottenuto da questi test guida le modifiche e gli aggiustamenti necessari per gli incrementi successivi. Ogni versione parziale è testata per:
   1. **Precisione del calcolo delle curve.**
   1. **Qualità e fluidità dell’animazione.**
   1. **Rispondenza alle specifiche dell’interfaccia utente.**
1. **Valutazione e iterazione:**
   Prima di passare all’incremento successivo, il team valuta l’incremento corrente per assicurarsi che soddisfi i requisiti prefissati e che funzioni correttamente con il software esistente. In caso di problemi o discrepanze, l'incremento viene migliorato o rivisto prima di procedere.

**Motivazioni per la scelta dello sviluppo incrementale**

- **Rilascio progressivo e utilizzabile:** Ogni versione incrementale del software offre un prodotto parzialmente funzionante che può essere valutato e utilizzato. Questa struttura riduce il rischio di errori complessivi, poiché i problemi vengono identificati e risolti a ogni fase.
- **Flessibilità e adattabilità:** Lo sviluppo incrementale consente di integrare nuove funzionalità e adattare il software a eventuali modifiche dei requisiti, minimizzando l’impatto sul codice esistente.
- **Riduzione dei rischi:** In caso di difficoltà tecniche con una funzionalità, il team può concentrare gli sforzi sull'incremento problematico senza compromettere l'intero progetto.

**2. Gestione delle Configurazioni**

**1. Controllo di Versione**

- **Strumento utilizzato:** Il progetto **FallSimulator** utilizza **GitHub** per il controllo delle versioni, permettendo di gestire le modifiche e mantenere uno storico di tutte le modifiche effettuate. Ogni commit è documentato per consentire un tracciamento dettagliato dei cambiamenti.
- **Organizzazione del repository:** Il repository è suddiviso in branch per ciascun incremento (es. curve\_base, animazione, interfaccia) per isolare le diverse funzionalità durante lo sviluppo e facilitare l’integrazione progressiva.

**2. Gestione delle Baseline**

- **Definizione della baseline:** Ad ogni fase significativa, una **baseline** viene stabilita come punto di riferimento stabile del software, che include tutte le funzionalità sviluppate fino a quel momento.
- **Approccio alle baseline per incrementi:** Ogni incremento produce una versione funzionante e stabile del software. Dopo la verifica di ciascun incremento (come il completamento delle curve preimpostate o dell’animazione), una baseline viene creata per garantire che i progressi siano salvati e che la nuova versione possa essere recuperata in caso di problemi nelle fasi successive.

**3. Richieste di Modifica (Change Requests)**

- **Processo di modifica:** Ogni volta che viene identificata una modifica necessaria, questa viene discussa all’interno del team. Le modifiche che possono influire sul progetto in modo significativo (es. miglioramento dell’algoritmo di calcolo del tempo "t" o ottimizzazione delle prestazioni dell’animazione) vengono approvate in modo congiunto prima dell'implementazione.
- **Documentazione delle modifiche:** Le modifiche significative vengono annotate e documentate. Ciò include una descrizione della modifica, la motivazione, e l’impatto previsto sulle altre componenti del software.

**4. Backup e Recupero**

- **Backup periodici:** Per garantire la sicurezza dei dati, vengono effettuati backup regolari del repository su GitHub, e una copia locale del progetto è mantenuta sui computer degli sviluppatori.
- **Piano di recupero:** In caso di problemi con una delle versioni del software, il team è in grado di recuperare l'ultima baseline stabile dal repository di GitHub.


**3. Gestione del Team**

**1. Struttura del Team e Ruoli**

- **Team di sviluppo:** Il progetto **FallSimulator** è gestito da un team ristretto di due membri:
  - **Marco Locatelli**: Sviluppatore principale, responsabile dell’implementazione delle curve, dell’animazione, e della verifica dei calcoli matematici.
  - **Nicolò Nava**: Responsabile della documentazione e del project plan, nonché supporto nelle attività di sviluppo e nella gestione dei diagrammi UML.
- **Revisore esterno:** Il team è supportato dal **professore Gargantini**, che svolge il ruolo di revisore finale, fornendo feedback su qualità e correttezza del progetto e aiutando a risolvere eventuali ostacoli.

**2. Stile di Gestione e Comunicazione**

- **Collaborazione diretta e comunicazione continua:** Il team adotta uno **stile di gestione flessibile e collaborativo**. La maggior parte delle decisioni vengono prese insieme, in modo rapido e diretto. Entrambi i membri del team sono coinvolti nelle fasi principali di sviluppo, promuovendo un alto livello di comunicazione e trasparenza.
- **Daily Stand-up Meeting e confronto frequente:** Per monitorare l’avanzamento e affrontare eventuali problemi, il team svolge dei **Daily Stand-up Meeting**. Durante questi incontri brevi, ogni membro aggiorna l'altro sui progressi e segnala eventuali ostacoli.
- **Sprint Review e retrospettiva:** Alla fine di ogni incremento, viene svolta una revisione dello Sprint per valutare il lavoro svolto e definire gli obiettivi per l’incremento successivo. Questo permette al team di mantenere il controllo e adattarsi ai requisiti in evoluzione.

**3. Gestione dei Problemi e Risoluzione degli Ostacoli**

- **Risoluzione interna:** La maggior parte dei problemi vengono risolti all’interno del team, grazie alla comunicazione continua. In caso di difficoltà, il team si confronta per trovare soluzioni.


