**1. Introduzione**

**Scopo del Documento**
Questo documento descrive le strategie, le attività e le linee guida per la manutenzione del software **FallSimulator**, con particolare attenzione al miglioramento continuo della qualità del codice e dell’architettura attraverso attività di refactoring e l’introduzione di design pattern.

-----
**2. Tipologie di Manutenzione**

1. **Manutenzione Correttiva**
   1. Risoluzione di bug rilevati durante il test e l’uso dell’applicazione.
   1. Esempio: Gestione dei punti coincidenti che bloccavano il sistema.
1. **Manutenzione Adattativa**
   1. Aggiornamenti per garantire la compatibilità con nuove versioni di Java o JavaFX.
1. **Manutenzione Perfettiva**
   1. Miglioramento della struttura modulare del software e ottimizzazione delle prestazioni, come l’uso di controlli per valori critici nelle derivate.
1. **Manutenzione Preventiva**
   1. Refactoring del codice per aumentare la leggibilità, ridurre la complessità e prevenire potenziali problemi futuri.
-----
**3. Attività di Refactoring**

Le attività di refactoring sono state guidate da **SonarLint** e **StanIDE**, strumenti per l’analisi statica del codice, che hanno permesso di identificare e correggere problematiche legate alla qualità del codice.

- **Introduzione di Design Pattern**
  - **Singleton**: Utilizzato per garantire una sola istanza di classi chiave, come la gestione del contesto grafico.
  - **Observer**: Implementato per notificare automaticamente i cambiamenti tra le componenti, migliorando la coesione e riducendo le dipendenze dirette.
- **Correzioni Identificate da SonarLint**
  - Rimozione di codice duplicato.
  - Semplificazione di metodi complessi.
- **Correzioni Identificate da StanIDE**
  -Miglioramento della struttura modulare.
-----
**4. Gestione dei Cambiamenti**

1. **Strumenti Utilizzati**
   1. **SonarLint**: Per l’analisi della qualità del codice.
   2. **GitHub**: Per il controllo di versione e la tracciabilità delle modifiche.
   3. **StanIDE**: per la valutazione delle metriche di complessità e l'analisi della struttura modulare 
1. **Procedure di Gestione**
   1. **Identificazione**: Ogni richiesta di modifica è stata documentata con un obiettivo chiaro e un’analisi dell’impatto.
   1. **Implementazione**: Le modifiche sono state sviluppate in branch separati per garantire l’isolamento.
   1. **Verifica**: Ogni modifica è stata sottoposta a test manuali prima dell’integrazione.
-----
**5. Attività Future di Manutenzione**

1. **Estensione delle Curve**
   1. Aggiungere nuove tipologie di curve, come e ellissi, iperboli, ecc.
1. **Ottimizzazione dell’Animazione**
   1. Ridurre ulteriormente il carico computazionale per migliorare le prestazioni su dispositivi meno potenti.
1. **Miglioramento dell’Interfaccia**
   1. Implementare una disposizione più equilibrata
1. **Automazione dei Test**
   1. Introdurre test automatici per verificare continuamente la stabilità del software.
-----
**6. Registro delle Modifiche**

|**Data**|**Descrizione della Modifica**|**Motivazione**|**Esito**|
| :-: | :-: | :-: | :-: |
|20/12/2024|Refactoring del codice con SonarLint|Migliorare la qualità del codice|Modifica implementata.|
|07/01/2025|Riorganizzazione dei package|Aumentare la modularità|Modifica implementata.|
|10/01/2025|Introduzione dei pattern Singleton e Observer|Ottimizzare la gestione delle dipendenze|Modifica implementata.|

-----
**7. Conclusioni**

Attraverso una gestione strutturata delle modifiche e attività di refactoring mirate, il software **FallSimulator** è stato migliorato sia in termini di qualità del codice che di architettura. Il piano di manutenzione garantisce che il sistema rimanga affidabile, estendibile e adattabile a futuri requisiti.

