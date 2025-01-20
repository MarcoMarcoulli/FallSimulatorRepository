**Documento di Test - FallSimulator**

-----
**1. Piano di Test**

**1.1 Scopo del Piano di Test**
Il piano di test per il progetto **FallSimulator** descrive l'ambito, l'approccio, le risorse e il programma delle attività di test previste per garantire che il software soddisfi i requisiti funzionali e non funzionali. Il focus principale è verificare la correttezza dei calcoli matematici, la fluidità dell’animazione e l’usabilità dell’interfaccia.

-----
**1.2 Ambito del Test**
Il test copre le seguenti aree principali:

- **Curve matematiche**: Verifica della corretta generazione di retta, parabola, circonferenza (con raggio variabile) e cicloide.
- **Calcolo del tempo "t"**: Verifica che i tempi di percorrenza siano calcolati correttamente per ciascuna curva.
- **Animazione**: Assicurarsi che la sfera segua correttamente la traiettoria con un movimento fluido.
- **Interfaccia Utente**: Test dell'usabilità e della reattività delle funzionalità principali, come la selezione delle curve e l’avvio dell’animazione.
-----
**1.3 Approccio**

- **Curve matematiche**:
  - Verifica che ogni curva (retta, parabola, cicloide, circonferenza con raggio variabile) sia generata correttamente in base ai parametri forniti dall'utente.
  - I risultati saranno confrontati con strumenti esterni, come GeoGebra, per garantire la precisione.
- **Calcolo del tempo "t"**:
  - Confronto dei tempi calcolati per diverse curve e scenari con risultati teorici.
  - Controllo della coerenza del tempo "t" rispetto ai parametri fisici forniti.
- **Animazione**:
  - Test della fluidità dell'animazione lungo le curve, con particolare attenzione alla parabola e alla cicloide per garantire che la cicloide sia sempre più veloce.
  - Verifica che la sfera si muova seguendo la traiettoria corretta senza rallentamenti o disallineamenti grafici.
- **Interfaccia Utente**:
  - Test sull’usabilità dell’interfaccia per assicurare che l’utente possa selezionare una curva, impostare i parametri e avviare l’animazione senza errori.
-----

**1.4 Rischi e Mitigazioni**

- **Prestazioni dell’animazione**: Possibili rallentamenti su macchine meno performanti. Mitigazione: Ottimizzare il codice e testare su diverse configurazioni.
- **Errori nei calcoli complessi**: Problemi con derivate tendenti a zero o gestione di due variabili. Mitigazione: Implementare controlli per approssimare valori critici.
- **Blocchi del sistema**: Possibili crash causati da input non validi. Mitigazione: Validare tutti gli input prima dell’elaborazione.
-----
**1.5 Identificazione dei Test Associati**

- **CurveGenerator**:
  - Test per ogni tipo di curva, includendo scenari con parametri limite (es. raggio minimo per la circonferenza).
- **Curves**:
  - Test per verificare il comportamento dell’animazione su curve semplici (es. retta) e complesse (es. cicloide).
- **SimulationManager**:
  - Test con valori estremi per verificare la robustezza del calcolo del tempo.
- **EventHandler**:
  - Test con input non validi, come punti coincidenti o parametri errati, per verificare i controlli di validazione.
-----
**2. Specifica della Procedura di Test**

**2.1 Descrizione Generale**
La procedura di test definisce i passaggi dettagliati per eseguire ciascun test, garantendo che i risultati siano riproducibili. Ogni procedura include i prerequisiti, le azioni da eseguire e i criteri di successo.

-----
**2.2 Procedura di Test per le Curve**

- **Prerequisiti**: 
  - Ambiente di sviluppo configurato con JavaFX.
  - Accesso ai parametri necessari per generare le curve.
- **Passaggi**: 
  - Avviare l’applicazione e accedere alla sezione di generazione delle curve.
  - Selezionare il tipo di curva (es. retta, parabola, cicloide, circonferenza).
  - Inserire i parametri richiesti (es. raggio per la circonferenza).
  - Generare la curva e visualizzarla sul canvas.
- **Criteri di Successo**: 
  - La curva generata corrisponde alla forma attesa.
  - Nessun errore si verifica durante il processo.
-----
**2.3 Procedura di Test per il Calcolo del Tempo**

- **Prerequisiti**: 
  - Curva generata correttamente.
  - Accesso ai parametri fisici (es. massa, attrito).
- **Passaggi**: 
  - Inserire i parametri richiesti per il calcolo del tempo.
  - Eseguire il calcolo.
  - Confrontare il tempo calcolato con il risultato teorico (es. tramite GeoGebra).
- **Criteri di Successo**: 
  - Il tempo calcolato è corretto e verificabile.
  - Non si verificano discrepanze significative rispetto ai valori teorici.
-----
**2.4 Procedura di Test per l’Animazione**

- **Prerequisiti**: 
  - Curva generata correttamente.
  - Tempo di percorrenza calcolato.
- **Passaggi**: 
  - Avviare l’animazione della sfera lungo la curva.
  - Osservare il movimento della sfera.
  - Verificare che l’animazione sia fluida e coerente con il tempo calcolato.
- **Criteri di Successo**: 
  - La sfera si muove lungo la traiettoria senza rallentamenti o scatti.
  - Il tempo di animazione corrisponde al tempo calcolato.
-----
**2.5 Procedura di Test per l’Interfaccia Utente**

- **Prerequisiti**: 
  - Ambiente di test configurato.
- **Passaggi**: 
  - Eseguire l’applicazione.
  - Interagire con i controlli dell’interfaccia (es. selezione della curva, inserimento dei parametri).
  - Avviare l’animazione e osservare il comportamento del sistema.
  - Tentare input non validi (es. punti coincidenti).
- **Criteri di Successo**: 
  - L’interfaccia risponde correttamente agli input validi.
  - Gli errori di input vengono gestiti con messaggi appropriati.
-----
**3. Registro di Test**

**3.1 Scopo del Registro di Test**
Il registro di test fornisce una registrazione cronologica di tutti gli eventi rilevanti durante l'esecuzione dei test, inclusi i risultati, le anomalie riscontrate e le azioni intraprese.

-----
**3.2 Registro degli Eventi di Test**

|**Data**|**Componente**|**Test Eseguito**|**Risultato**|**Azioni**|
| :-: | :-: | :-: | :-: | :-: |
|18-10-2024|Curve|Generazione della circonferenza con raggio variabile|Fallito (tilt del sistema)|Modificati i calcoli per gestire 2 variabili. Test ripetuto con successo.|
|12-11-2024|SimulationManager|Animazione della parabola e della cicloide|Parabola più veloce della cicloide|Aggiunto controllo per derivate vicine a zero. Test ripetuto con successo.|
|07-12-2024|InputController|Inserimento di due punti coincidenti|Blocco del sistema|Aggiunto controllo per ignorare punti duplicati. Test ripetuto con successo.|
|05-01-2025|SimulationManager|Calcolo del tempo "t" su una cicloide|Successo|Verifica con GeoGebra confermata.|
|08-01-2025|Layout|Test di input non validi|Successo|I messaggi di errore vengono mostrati correttamente.|

-----

