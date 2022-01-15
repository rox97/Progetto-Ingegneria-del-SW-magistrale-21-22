# Progetto-Ingegneria-del-SW-magistrale-21-22
Progetto Ingegneria del SW magistrale 21/22

Simone Baldi(VR457324), Andrea Caliari, Andrea Rossetti

# Scenari
## Compilare un Form/Indagine/Poll (agente: professore)
### Initial Assumption
Un docente ha la possibilità di creare sondaggi per valutare la bontà di un corso, dare un opinione sui servizi forniti dall’istituto, oppure, sulla qualità delle infrastrutture.
### Normal
Il docente può decidere il titolo del sondaggio e la categoria o il corso nella quale inserirlo, dopo di che viene chiesto di inserire la domanda e il tipo di riposta che può essere una risposta multipla, singola o un semplice si/no. Inoltre, viene chiesto se il sondaggio dev’essere obbligatorio o meno per accedere a un corso o ad un esame.
### What can go wrong
Il docente potrebbe dimenticare di compilare una risposta e lasciare vuoto il campo, viene visualizzato un pop up dicendo di eliminare la checkbox oppure di inserire una riga di testo.
Il docente potrebbe sbagliare la categoria o il corso, quindi, il docente ha la possibilità di modificare in qualunque momento il sondaggio.
### Other Activities
Il docente ha la possibilità di vedere le statistiche dei vari sondaggi.
### System state on completion
Il docente è loggato all’interno della piattaforma. Il sondaggio viene caricato e inserito all’interno della categoria/corso specificato all’inizio della creazione.
Una volta completato sarà visibile al docente stesso e agli studenti.

## Condivisione materiale didattico (agente: professore)
### Initial Assumption
Il docente una volta fatto il login sulla piattaforma ha la possibilità di caricare materiale didattico e condividerlo con gli studenti.
### Normal
Il docente accede al corso nel quale vuole condividere il materiale didattico, accede alla sezione di condivisione del materiale e seleziona il file da condividere.
### What can go wrong
Il docente potrebbe caricare un file con un formato non accettato dal sistema, viene notificato tramite un pop up che il file non rispetta l’estensione corretta.
Il docente potrebbe inviare per sbaglio un file errato, ha la possibilità di rimuovere il file errato.
### Other Activities
### System state on completion
Il docente dopo aver loggato nel sistema può caricare i file, una volta completato l’upload gli studenti sono in grado di vedere il file (slides, homework, ecc..) nella pagina del corso e possono scaricarli.

## Archiviazione e consultazione materiale personale (agente: studente)
### Initial Assumption
Allo studenti viene data la possibilità di archiviare materiale personale o compiti per casa su una piattaforma cloud.
### Normal
Lo studente, una volta fatto il login sulla piattaforma, può accedere alla sezione dedicata al suo spazio personale di archiviazione cloud, oppure, accedere alla sezione di archiviazione dedicata al corso, nella quale può archiviare homework.
Nella sezione del Corso può essere inserita dal docente una data di scadenza entro la quale consegnare gli esercizi, dopo di che lo studente potrà visualizzare il commento del professore o l’eventuale voto inerente al compito per casa.
### What can go wrong
Lo studente può inviare un file errato, oppure, accorgersi successivamente di aver inviato un file errato.
Viene data la possibilità selezionare il file errato e di eliminarlo per poi successivamente ricaricarlo.
### Other Activities
### System state on completion
Una volta fatto login nella piattaforma lo studente carica il proprio materiale oppure carica degli esercizi sulla pagina del corso che poi verranno valutati dal professore una volta scaduto il termine di consegna.

## Aggiunta dei voti degli studenti (agente: professore)
### Initial Assumption
Il professore può inserire i voti degli studenti iscritti al suo corso in modo che possano vederlo registrato nel proprio libretto personale. 
Il professore deve essere loggato nel sistema tramite le proprie credenziali.
### Normal
Il professore dopo aver valutato le prove degli studenti deve inserire i voti all’interno del sistema. L’utente accede alla propria pagina personale dove può vedere i corsi dove insegna. Sceglie il corso in questione e visualizza l’elenco degli studenti iscritti regolarmente al corso. 
Accede alla pagina di inserimento dei voti dove è presente un elenco degli studenti con la possibilità di inserire i voti per ogni singolo studente e un commento. I voti sono numerici con la possibilità di indicare che uno studente era assente (A).
All’inizio della pagina sono presenti le caselle per inserire le informazioni sulla prova che è stata valutata (prova orale, scritta, laboratorio, ecc. e data). Dopo aver inserito tutti i voti, salva la modifica. Gli studenti ricevono una mail dove viene comunicato l’inserimento di un voto sul proprio libretto.
### What can go wrong
Viene inserito un voto errato. Se il professore se ne dovesse accorgere o gli fosse fatto notare, può accedere alla pagina dell’elenco degli studenti e modificare il voto inserito. 
Uno o più studenti non erano iscritti al corso quando sono stati inseriti i voti anche se hanno partecipato alla prova. Dopo che gli studenti sono stati iscritti al corso, il professore accede nuovamente alla pagina di inserimento dei voti e inserisce le stesse informazioni già inserite la volta precedente (tipo di prova e data) e inserisce il voto solo degli studenti mancanti (ora visualizzati). 
### Other Activities
Gli studenti possono visualizzare il voto sul proprio libretto.
### System state on completion
Una volta salvata la modifica dei voti, il sistema visualizza un messaggio di successo e l’utente ritorna sulla pagina del corso.

## Aggiunta compiti per casa con scadenza per la consegna (agente: professore)
### Initial Assumption
Il professore aggiunge la consegna per un compito da fare a casa con una scadenza entro la quale inviare il o i files con le risposte o l’elaborato da valutare/correggere. 
Il professore deve essere loggato nel sistema tramite le proprie credenziali
### Normal
Il professore accede al sistema con le proprie credenziali. accede alla propria pagina personale dove può vedere i corsi dove insegna. Sceglie il corso in questione e visualizza la pagina principale del corso. Sceglie di aggiungere un elemento alla pagina. 
Scegliere tra le opzioni di inserire una consegna per un compito a casa con scadenza. Nella pagina di inserimento viene richiesto il titolo del compito, la descrizione/consegna completa, eventuali allegati utili allo svolgimento del compito e la data entro la quale gli studenti devono consegnare lo svolgimento.
### What can go wrong
Viene inserito un compito errato o viene fatto un errore nell’inserimento (errore di scrittura, file errato, data errata, ecc.). 
Il professore può, sulla pagina del corso, modificare la consegna o cancellarla completamente.
### Other Activities
### System state on completion
Una volta salvata la consegna, il sistema visualizza un messaggio di successo e l’utente ritorna sulla pagina del corso.

## Schedulare una video call con gli studenti iscritti al corso (agente: professore)
### Initial Assumption
Il professore ha eseguito il login tramite le sue credenziali. Nel sistema è implementato un servizio di video conferenze. Il professore vuole inserire nel calendario di un corso un appuntamento per una video chiamata.
### Normal	
Il professore accede al sistema con le proprie credenziali. Accede alla propria pagina personale dove può vedere i corsi dove insegna. Sceglie il corso in questione e visualizza la pagina principale del corso. Sceglie di aggiungere un elemento al corso. A questo punto sceglie il titolo della video chiamata, la data e l’ora. Il sistema registra l’evento nel calendario del corso e invia una mail informativa agli studenti con la data e il link per partecipare alla chiamata.
### What can go wrong
il professore sbaglia ad inserire alcuni dati (titolo o data) della chiamata. Accedendo al calendario può modificare o cancellare l’evento e il link di partecipazione viene disattivato.
### Other Activities
gli studenti possono accedere alla loro pagina e vedere l’evento nel calendario e il link di partecipazione (disattivato fino al momento scelto dal professore).
### System state on completion
Una volta salvato l’evento, il sistema visualizza un messaggio di successo e l’utente ritorna sulla pagina del corso. Viene inviata una mail anche al professore con le stesse informazioni appena inserite e il link per avviare la chiamata.


## Inviare avvisi agli iscritti al corso (agente: professore)
### Initial Assumption
Il professore ha la possibilità di inviare degli avvisi ad ogni partecipante (solo a tutti) del corso tramite la sezione apposita all’interno della sua aerea personale.
### Normal
Il professore accede al corso nel quale vuole inviare l’annuncio, entra nella sezione annunci e può scegliere di inviare un avviso andando a compilare i campi che lo compongono come il titolo, il contenuto, programmare l’orario e la data di pubblicazione, se lo vuole. Compilato l’avviso il professore provvederà a caricare l’avviso sulla bacheca degli studenti. Se l’avviso contiene una programmazione oraria diventerà pubblico all’orario indicato.
### What can go wrong
Il professore ha la possibilità di rimuovere l’avviso oppure di modificarlo. Nel caso della modifica la data di pubblicazione verrà aggiornata al momento della modifica e verrà reinviata una notifica agli studenti.
### Other Activities
Gli studenti ricevono una notifica quando il docente pubblica l’avviso.
### System state on completion
Il professore può vedere il suo annuncio appena pubblicato o in caso di annuncio programmato il riepilogo di esso.

## Bacheca annunci (agente: studente)
### Initial Assumption
Lo studente può accedere alla bacheca annunci tramite la sezione apposita all’interno della sua area personale per vedere gli annunci disponibili o crearne uno.
### Normal
Lo studente può accedere all’interfaccia della bacheca annunci andando a vedere quali sono presenti e scegliere se rispondere ad uno di essi. La bacheca è organizzata a categorie.
In caso volesse rispondere ad un annuncio, dopo aver selezionato una categoria, può farlo cliccando su di esso dove gli verrà mostrata l’interfaccia per scrivere un messaggio al proprietario dell’annuncio.
In caso lo studente volesse creare un annuncio può farlo accedendo alla categoria desiderata e cliccando sul pulsante nuovo annuncio. Dopo di che andrà ad inserire il titolo e il contenuto del relativo annuncio. 
Infine, per conferma la creazione gli basterà confermare la scelta.
### What can go wrong
Lo studente che vuole creare un annuncio potrebbe omettere il titolo o il testo dell’annuncio però quando andrà a confermare verrà avvisato non è stata inserita quella determinata parte. Lo studente che sta rispondendo ad un annuncio e si dimenticata di inserire il testo di risposta verrà avvisato se tenta di inviare la risposta senza di esso.
Lo studente può accedere alla sua sezione per andare a modificare i suoi post, nel caso in cui si fosse che c’è un errore nel annuncio.
### Other Activities
### System state on completion
Lo studente che risponde ad un annuncio riceverà un popup che la sua risposta è stata inviata correttamente invece lo studente che crea un annuncio vedrà il resoconto del suo annuncio creato.

## Voto elezioni studentesche (agente: studente)
### Initial Assumption	
Lo studente ha la possibilità di votare tramite un’interfaccia apposita all’interno della sua aera personale.
### Normal
Lo studente può votare per le elezioni tramite l’interfaccia apposita andando a cliccare sulla sua preferenza tra i candidati presenti. Il voto è preso in considerazione solo se lo studente dopo aver selezionato la sua scelta conferma la votazione tramite il tasto di conferma.
### What can go wrong
Lo studente seleziona la lista ma senza indicare una preferenza tra i candidati di quella lista; in questo caso se lo studente andrà a confermare la votazione il sistema lo avvertirà con un popup che non avrà effettuato quella determinata scelta e che se vuole può inviare comunque la votazione.
### Other Activities
L’organizzatore delle elezioni potrà vedere le percentuali delle votazioni tramite la sua area personale.
### System state on completion
Lo studente vedrà il resoconto della sua votazione con tutte le preferenze da lui indicate.

# Casi di test

