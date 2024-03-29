# 20222023_FSST_mblatter_MsgBoard
A projekt ausm FSST Unterricht

# Angabe: 
https://www.htl-steyr.ac.at/intern/wiki/doku.php?id=el-it:programmiersprachen:java:exercises:534-project-digital-message-board#project_-_digital_message_board

Project - Digital Message Board

Arbeitsverzeichnis: H:\FSST\Project

Es ist ein System zu entwerfen, welches ein digitales Informationsportal umsetzt. Dafür ist

    eine Datenbank
    ein Client zum
        Lesen von Nachrichten
        Verfassen von Nachrichten
        Verwalten der Abo's
    ein Server zum
        Empfang neuer Nachrichten
        Speichern der Nachrichten
        Verteilen der Nachrichten
        Auswerten von Statistiken

zu erstellen.

Im System müssen die Benutzer verwaltet werden. Benutzer können in eine oder mehrere Gruppen von folgenden Gruppen gehören:

    Reader (dürfen nur Nachrichten zu einem Thema abonieren)
    Editor (dürfen auch Nachrichten verfassen und veröffentlichen)
    Administratoren (dürfen Topics anlegen, stilllegen, Nachrichten löschen, …)

Nachrichten müssen immer einem Thema zugeordnet werden, sie haben einen bestimmten Status (eingereicht, veröffentlicht, abgelaufen, gesperrt). Weiters muss ein Veröffentlichungszeitpunkt und ein „offline“-Zeitpunkt für die Nachrichten verfasst werden. Standardmäßig sind Nachrichten immer sofort veröffentlicht und laufen nach spätestens 4 Wochen aus.

Neben den Nachrichten können auch gesamte Themengebiete veröffentlicht bzw. gesperrt werden (dies dürfen nur Administratoren).

Einfache Benutzer müssen die Möglichkeit haben, Themengebiete zu abonnieren und diese Abo's wieder zu kündigen. Sie sollen auch Kommentare zu einzelnen Nachrichten abgeben können, sowie eine Nachricht zu beurteilen (0 … 5 Sterne). Benutzer können auch zu Benutzergruppen zusammengefasst werden. Den Benutzergruppen können auch Themen zugeordnet werden - alle Benutzer die zu dieser Benutzergruppe gehören, müssen Nachrichten zu den zugeordneten Themen erhalten.

Zusätzlich zu den oben genannten Personen sollen Displays im System vorgesehen sein. Diese stellen die Nachrichten zu bestimmten Themen dar. Eine Zeit, wann diese Systeme eingeschalten werden muss abgespeichert werden.

Für Administratoren ist die Auswertung einer Statistik sinnvoll. Folgende Auswertungen sind gewünscht:

    Anzahl der Kommentare pro Benutzer
    Wieviele Posts gibt es zu jedem Thema
    Wieviele Posts gibt es durchschnittlich pro Thema
    Was sind die letzten 10 Nachrichten
    Welches sind die am besten bewerteten Nachrichten
    Welches Thema wurde am oftesten abonniert
    Wieviele Nachrichten wurden an den letzten 7 Tagen (pro Tag) veröffentlicht

Um die Software testen zu können muss die Datenbank mit Testwerten befüllt werden.

# External Links
Datenbank Modell: https://app.genmymodel.com/api/repository/OpenSauce/20222023-FSST-mblatter-MsgBoard

# Protokol
## Login
| Partei      | Kommando | Erläuterung     | Beispiel |
| :---        |    :----:   |          :--- | :--- |
| Client      |    LOGIN\t\<Benutzername>\t\<Passwort>   |   Einloggen des Benutzers mit dem angegebenen Benutzernamen und Passwort.    | LOGIN Max123\tmypassword
| Server      |    LOGIN_SUCCESS   |  Der Benutzer wurde erfolgreich eingeloggt.  | LOGIN_SUCCESS
| Server      |    LOGIN_FAILED\t\<reason>    |  Die Anmeldung ist fehlgeschlagen.  | LOGIN_FAILED\tinvalid command!
## Messages
| Partei      | Kommando | Erläuterung     | Beispiel |
| :---        |    :----:   |          :--- | :--- |
| Client      |    TOPICS   |  Abrufen einer Liste aller verfügbaren Themen.  | TOPICS
| Server      |    TOPICS\t\<Thema1>\t\<Thema2> |  Liste aller verfügbaren Themen wurde erfolgreich abgerufen.  | TOPICS\tPolitik\tWirtschaft\tSport
| Client      |    MY_NEWS  |  Abrufen einer Liste von Nachrichten die der Benutzer abonniert hat.  | MY_NEWS
| Server      |    MY_NEWS\t\<Nachricht1>\t\<Nachricht2> | Eine Liste von Nachrichten die der Benutzer abonniert hat wurde erfolgreich abgerufen.  | MY_NEWS\tArtikel1\tArtikel2\tArtikel3
| Client      |    NEWS\t\<Thema>   |  Abrufen einer Liste von Nachrichten für das angegebene Thema.  | NEWS\tSport
| Server      |    NEWS\t\<Nachricht1>\t\<Nachricht2> | Eine Liste von Nachrichten für das angegebene Thema wurde erfolgreich abgerufen.  | NEWS\tArtikel1\tArtikel2\tArtikel3
| Client      |    SUBSCRIBE\t\<Thema>   |  Abonnieren des angegebenen Themas.  | SUBSCRIBE\tPolitik
| Server      |    SUBSCRIBE_SUCCESS\t\<Thema>    |  Das Abonnement für das angegebene Thema wurde erfolgreich hinzugefügt.  | SUBSCRIBE_SUCCESS\tPolitik
| Client      |    UNSUBSCRIBE\t\<Thema>    |  Kündigen des Abonnements für das angegebene Thema.  | UNSUBSCRIBE\t Wirtschaft
| Server      |    UNSUBSCRIBE_SUCCESS\t\<Thema>    |  Das Abonnement für das angegebene Thema wurde erfolgreich gekündigt.  | UNSUBSCRIBE_SUCCESS\tWirtschaft
| Client      |    COMMENT\t\<Nachricht-ID>\t\<Kommentar>    |  Hinzufügen eines Kommentars zu der angegebenen Nachricht.  | COMMENT\t1234\t"Hallo"
| Server      |    COMMENT_SUCCESS    |  Der Kommentar wurde erfolgreich hinzugefügt.  | COMMENT_SUCCESS
| Client      |    RATE\t\<Nachricht-ID>\t\<Bewertung>      |    Bewertung einer Nachricht     | RATE\t1234\t4
| Server      |    RATE_SUCCSESS      |    Die Bewertung wurde erfolgreich hinzugefügt.    | RATE_SUCCESS

## Logout
| Partei      | Kommando | Erläuterung     | Beispiel |
| :---        |    :----:   |          :--- | :--- |
| Client      |    LOGOUT    |  Ausloggen des Benutzers.  | LOGOUT
| Server      |    LOGOUT_SUCCESS    |  Der Benutzer wurde erfolgreich ausgeloggt.|  LOGOUT_SUCCESS


        
