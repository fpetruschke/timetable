# Warum MongoDB?

Bei mir im Betrieb wird mit Massendaten gearbeitet. Bisher habe ich mich intensiv mit MySQL & SQL auseinandergesetzt. Da wir allerdings für bestimmte Funktionalitäten - mit denen ich bisher noch nicht in Berührung gekommen bin - MongoDB verwenden, war es mir ein Anliegen, mich für die Praxis zu wappnen. Daher meine Entscheidung für MongoDB.

# Voraussetzungen

Voraussetzungen, um das Programm zu starten. In Klammern die genutzte Versionsnummer.

* Java (1.7)
* MongoDB (3.2.9)
* Mongo-Java-Driver (3.3.0)

# Probleme

Am meisten Probleme hat mir die andere Vorgehensweise von mongoDB bereitet. Es ist schwierig umzudenken, wenn man es gewohnt ist, mit relationalen Datenbanken zu arbeiten, sich überall auf die Relationen zu verlassen. Man muss bei der Arbeit mit mongoDB versuchen, im Kopf zu "de-normalisieren".  

# Arbeitsschritte

- [x] Aufbau der Projektstruktur mit Trennung von Controllern und Gui  
- [X] Erstellen des GUIs (Anzeige und Ändern von Daten)  
- [x] Installation von mongoDB  
- [x] Herstellen einer Verbindung zum lokalen mongoDB server  
- [x] Erstellen erster Standard-Abfragen (Select, insert, delete)  
- [X] Erstellen einer Tabelle, die mit Daten aus der mongoDB gespeist wird  
- [X] Erstellen einer Editierfunktionalität für Tabellenzellen (einzelne Unterrichtseinheiten)
- [ ] Hinzufügen von Räumen, Lehrern und Unterrichtsfächern
- [ ] Editieren von Räumen, Lehrern und Unterrichtsfächern

# Vorbereitung zum Verständnis von mongoDB

Die folgenden Kapitel dienten mir lediglich zur Erarbeitung der Funktionsweise von mongoDB.  

## Stammdaten

Die Stammdaten (Collection und Documents) werden beim Starten der Anwendung erzeugt.  
Bestehende Stammdaten werden **allerdings zuvor gelöscht**, damit keine Duplikate entstehen.  

## Vokabular

- Collection -> "Tabelle" z.B. db.students  
- Document -> "Zeile" z.B. db.students.findOne()  
- ObjectId -> beim Erstellen automatisch generierter, eindeutiger Schlüssel (vgl mysql PrimaryKey)  

## Basis-Befehle

| Befehl 															| Ergebnis 																	| Typ    |
| ----------------------------------------------------------------- | ------------------------------------------------------------------------- | ------ |
| use timetable														| Wenn noch nicht existent: erstellt die Datenbank "timetable"				| CREATE |
| show collections 													| zeigt alle Collections 													| SELECT |
| db.students.find()												| zeigt alle Objekte (Documents) der Collection "Students" 					| SELECT |
| db.students.find().pretty() 										| zeigt alle Objekte (Documents) der Collection "Students" mit Einrückungen	| SELECT |
| db.students.findOne()												| zeigt das erste Objekt (Document) der Collection "Students"				| SELECT |
| db.students.insert([{jsonObjectStudent1},{jsonObjectStudent2}]) 	| fügt die jeweiligen Objecte zur Collection hinzu 							| INSERT |
| db.students.remove({"_id" : ObjectId("idoftheobject123456")}) 	| entfernt das Document mit der ggb. Id aus der Collection "Students" 		| DELETE |
| db.students.update({"_id" : ObjectId("idoftheobject123456")}, <jsonObjectStudentWithIdWithNewValues>) | aktualisiert den Datensatz 			| UPDATE |
| db.students.drop() 												| löscht die collection "Students" 											| DROP	 |
| db.students.find({"age" : 20}, {"name" : "peter"}) 				| zeigt die Objecte, auf die die Suchmaske/WHERE-Bedingung passt 			| WHERE  |
| db.students.find($or:{ {"age" : 20}, {"name" : "peter"} }) 		| OR-Bedingung 																| OR     |
| db.students.find({"age" : {$gt|e/$lt|e/$ne/$e: 20} }) 		    | gt: greaterThan, lt: lessThan, e: equals, ne: doesNotEqual				| WHERE  |
| db.students.find({"age" : 20}, {"name" : 1, _id:0 }) 				| Einschränkung der Ergebnismenge (zeigt nur die Namen, niemals die _id)	| WHERE  |
| db.students.find({"age" : 20}, {"name" : 1, _id:0 }).limit(3) 	| Einschränkung: Zeigt maximal 3 Documents									| LIMIT  |
| db.students.find({"age" : 20}, {"name" : 1, _id:0 }).skip(2)		| Einschränkung: Überspringt die ersten 2 Documents							| SKIP   |
| db.students.findOne().explain("executionStats")					| zeigt Statistiken zu der Datenbankabfrage									| STATUS |

## Aggragat-Funktionen

| Befehl 																   | Ergebnis 								| Typ    		 |
| ------------------------------------------------------------------------ | ---------------------------------------| -------------- |
| db.students.aggregate({$group : {_id: "$age", total: {$sum : 1} } }) 	   | Summieren einer Ergebnismenge			| GROUP + SUM 	 |
| db.students.aggregate({$group : {_id: "$age", avgAge: {$avg : 1} } })    | Durchschnittswert einer Ergebnismenge	| GROUP + AVG 	 |
| db.students.aggregate({$group : {_id: "$age", oldest: {$max : $age} } }) | Durchschnittswert einer Ergebnismenge	| GROUP + AVG 	 |

## Indizes

Indizes sorgen dafür, dass DB-Abfragen deutlich effizienter ablaufen.  
Wann immer z.B. mit einer Bedingung das Suchergebnis gefiltert werden soll, beschleunigt das vorherige Hinzufügen eines Index die Abfrage.   
Indizes sollten immer nur bei häufig verwendeten Filter-Spalten verwendet werden.  
Je mehr Indizes bestehen, desto weniger bringt die Verwendung von Indizes.  

* db.students.ensureIndex({"age":1}) 	--> nutze das Alter als Suchindex  
* db.students.getIndexes() 				--> zeigt die angelegten Indizes der Collection  
