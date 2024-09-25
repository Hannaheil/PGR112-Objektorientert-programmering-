Eksamen PGR112 knr: 1559

Hvordan få tilgang til databasen: 
* Åpne resource-mappen -> åpne filen: initdb.sql. 
* Kjør denne filen 
  * trykk på den blå teksten oppe til venstre
  * i databasevinduet, trykk på "+" tegnet oppe i venstre hjørnet
  * skriv inn brukernavnet og passordet til databasen som er beskrevet under
* For å logge inn på databasen:
  * Brukernavn: root
  * Passord: root
  * **NB!** Jeg har satt brukernavnet og passordet mitt i mySql til å være root bare for dette prosjektet. Etter eksamen er vurdert, skal jeg bytte tilbake. Så dette er ikke noe jeg har til vanlig. 

Testing av programmet
* For å komme inn i programmet går man gjennom en sjekk om man er student og hvilket studieprogram man tilhører. Dersom valga fra terminalen stemmer med sjekken i databasen kan man gå inn og starte registrerings prosessen. Du må altså logge inn som en "student" som allerede eksisterer i databasen
* Det er flere "Studenter" som er lagt inn i databasen. Så man kan gå inn og se på bruker og studeieprgram der. Men her er et forslag til bruker du kan logge inn med:
    * Brukernavn: Kjell
    * Studeiprogram: Art
* Note! Det er kun programmet som er aktuelt for sitt studeieårogram som blir vist når du allere er logget inn. Ved hovedmenyen, kan man velge å se programmet og der kan man se alle programmene til alle studierettningene. 

Oppsett av koden
* Jeg har lagt all sql kode som har med opprettelse av databaser, tabeller og kollonner i initdb.sql filen
* Annen sql spørring, oppdateringer og initialiseringer er skrevet inn i metoder i JDBC.java - filen. Disse metodene blir videre kalt på andre steder i scriptet
* Jeg har en abstrakt klasse: "Person" som klassene "Student" og "Participant" arver fra. I disse to klassene blir verdier hentet og satt for forekomster av disse klassene og gjort i stand til å bli satt inn i databasen
* I eventMenu klassen programmerer jeg alt som skjer i terminalen. Her skrives alle menyene og alle metodene som har med funksjunaliteten til "programmet (?)" å gjøre. Her blir også metodene fra jdbc kallt på. 

* Videre i eventMenu klassen har jeg laget instanser av alle klassene. Dette gjør det lett å holde på verdiene (f.eks. sjekke navnet på den studenten som er innlogget). Ved å ha instanser av klassene som kan holde på diverse informasjon, gjør det også lettere å få tak i denne informasjonen for så å enklere pushe det inn i en av metodene i jdbc klassen som videre kjører det inn i sql scriptet og til slutt oppdaterer databasen. 

Tolkning av oppgaven
* I oppgavetekseten sto det: "You should create a database called ... and another database called ...". Altså lage to databaser. Da jeg først leste dette leste jeg det med den forståelsen at det var en anbefaling, ikke et krav. Jeg har derfor brukt bare en database og lagd flere tabeller da jeg foretrakk det. Det var slik jeg tolket oppgaveteksten.
* Dersom jeg skulle ha brukt to databaser hadde jeg lagd en jdbc klasse til som hadde hatt en lik type connectDB() funksjon, men bare byttet ut porten hvor det står: universityDB med navnet på den andre databasem.
* Jeg hadde kodet nok så likt, men vært nøye på å initialisere hvilke database jeg spør etter i sql koden. 

Problemer
* Jeg hadde noe problemer med å finne navnet på gjesten for så å kunne slette en valgt gjest fra databasen. Dette er kommentert i koden.

SQL exceptions
* Jeg har fått en del hjelp av studieveilederne. Da har jeg blitt introduser til å bruke mer "throws SQLException" istendenfor try/catch. Så det er grunnen til at jeg har mer av det i min kode.