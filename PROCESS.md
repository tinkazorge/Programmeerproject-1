# day 1
Project proposal geschreven. Ik vond een app die heel erg op die van mij lijkt, dus ik moest een manier vinden waarmee mijn app echt anders werd. Uiteindelijk besloot ik dat mijn app anders wordt in de zin dat hij simpeler is in plaats van nog ingewikkelder. Zo is de app makkelijk in gebruik, en heb je ook geen last van reclames. De app zal in de basis twee functies hebben, je kookboek bekijken en een recept maken aan de hand van tekst of een afbeelding. 

# dag 2
Begonnen aan Design Document door een UML diagram te maken. Wat ik vooral tegenkwam was de manier waarop ik de database moet gaan implementeren. Ik wil in ieder geval beginnen met de sqlite database omdat ik hier al ervaring mee heb. Wat een mogelijk probleem kan worden is dat de hoeveelheid afbeeldingen niet in deze database passen. Later zal ik gaan kijken naar een online database. Waar ik ook over moet gaan nadenken is hoe ik de foto app lanceer binnen mijn app. Hier maak ik me echter minder zorgen om, omdat dit een functie is die veel apps hebben en het zal daarom minder moeilijk zijn om uit te zoeken. 

# dag 3
In de ochtend heb ik mijn design document ingeleverd zodat ik verder kon gaan met programmeren. Ik ben vooral beginnen met het aanmaken van de verschillende activities en buttons, zodat ik in ieder geval al tussen de verschillende schermen kan navigeren. 

# dag 4
Ik heb ondertussen ook twee nieuwe versies gemaakt in android studio. De tweede versie was nodig omdat ik een lagere minimale API wilde instellen, en dat kon niet meer via het project. Om die reden ben ik met een leeg project gestart waarin ik mijn oude code heb gekopieerd. De derde versie heb ik moeten maken omdat ik probeerde mijn telefoon aan mijn Android Studio te koppelen. Vanwege een onverklaarbare reden (waarschijnlijk omdat ik Android Studio op een MacBook gebruik) lukt dit niet, en na een paar pogingen wilde mijn Gradle helemaal niet meer werken. 

Vandaag moest ik beginnen aan het opslaan van de informatie die de user schrijft in de Write Recipe Activity, deze opgeslagen informatie moet vervolgens worden opgehaald in de View Recipe Activity. Waar ik op dit moment vooral moeite mee heb is hoe ik het uitklapbare menu ga implementeren. Als je op het kookboek menu klikt moeten de verschillende categorieen worden uitgeklapt. Het probleem is dat deze categorieen dynamisch worden aangemaakt door de user, ze voeren bij een nieuw recept een categorie in, en deze wordt dan automatisch aangemaakt als nieuwe categorie. Vervolgens moet elke aangemaakt categorie doorlinken naar een nieuwe activity waarin alle recepten die binnen die categorie vallen. Dit is mijn tweede probleem van vandaag. In dit scherm waar alle recepten staan moeten alleen alle titels staan, en op die titels moet geklikt kunnen worden. Pas als er op de titel wordt geklikt kan men de rest van het recept zien. Alleen de titels moeten dus uit de internal storage worden opgehaald, en als daar op wordt geklikt moet de rest van de informatie uit de internal storage worden opgehaald. 

Voor mijn prototype houd ik het nog simpel, als je op 'My Cookbook' klikt komt je meteen in een scherm waar je de recepten titels op kan halen, dit moet nu nog aan de hand van een 'View Recipes' button. Later moet je de recepten titels automatisch zien als je naar één van de categorieën gaat. Nadat ik dit geïmplenteerd had kwam ik erachter dat mijn 'View Recipe' button maar één recepttitel laat zien. Om dit op te lossen moet ik een adapter class maken met xml file voor een single list item. In de xml bepaal ik dan hoe één item eruit ziet en in de adapter class bepaal ik de functionaliteit van de lijst items. Dit geheel wordt vervolgens aangeroepen in mijn Category Activity, door het geheel in een listview van de xml van deze activity te zetten. 

# dag 6
Tip gekregen om in plaats van text files te gebruiken om de titels en categorieen op te slaan, deze in de sqlite database op te slaan. 

# dag 7
In principe staat het skelet voor mijn sqlite database. Voordat ik hem kan testen moet ik nog uitvinden hoe ik foto's van de camera in de database krijg. Ik wil namelijk dat men een foto kan nemen van zijn gerecht. In het recepten overzicht zie je dan links in de listview die foto, en rechts daarvan de (klikbare) titel van het recept. 

# dag 8
Gisteren heb ik zo veel code verandert in een poging tot het toevoegen van de foto's aan mijn database, dat het een enorm zooitje is geworden en ik niet meer wist waar ik mee bezig was. Daarom vandaag terug gegaan naar de staat waarin alles nog wel werkte en "opnieuw" begonnen. 

- Foto path is inmiddels niet langer gehardcode, maar elke foto heeft een timestamp als naam. 
- Het lijkt erop dat het ook is gelukt om de foto path in de sqlite database te stoppen. 
- Begonnen aan het ophalen van de informatie (foto + titel) in de CategoryActivity.

# dag 9
- Ik heb vandaag geprobeerd mijn database volledig werkend te krijgen en in mijn category activity al de informatie uit de database op te halen. 
- Het is me gelukt om titels uit de database op te halen en in de listview te laten zien. Alleen zijn de bijbehorende foto's nu nog de gehardcode foto van paella die ik er ooit hebt ingezet. 
- Je kan ook al klikken op de recepttitels om het recept te bekijken, deze activity is verder alleen nog leeg.

#dag 10
Recipe adapter en recipe data provider aangepast aan foto path content. Geprobeerd om de photopath om te zetten in Bitmap zodat ze te zien zijn in de recepten listview. Werkt nog niet. 

#dag 11
Als je op de titel klikt in de titel listview wordt er een nieuwe activity geopend waarin de titel nog een keer gedisplayed wordt. Hieronder zal dan de tekst van het recept komen. 

#dag 12
- Gisteren is het me gelukt om in de show recipe activity in ieder geval al de titel van het recept te laten zien. Ik kwam er vandaag alleen wel achter dat hij de verkeerde titel weergeeft. 
- Ondertussen heb ik wel een nieuwe method aangemaakt in mijn Databasehelper met een query voor het ophalen van de tekst die bij de titel hoort. De query lijkt te werken, maar omdat de verkeerde titel wordt opgehaald kan er niet een bijbehorende tekst worden gevonden. 
- Spinner toegevoegd met hard coded categories.

#dag 13
- Spinner haalt content op uit database, maar om de een of andere reden laat hij de photo paths zien in plaats van de categorieen. 
- Dit bleek fout te gaan in het aanmaken van de recepten. Ik had de informatie in de verkeerde volgorde gelaad, waardoor de informatie in de verkeerde kolommen terecht kwam. Wanneer ik het hele recept wil bekijken gaat het echter alsnog mis, ik krijg de verkeerde titel te zien en de query werkt niet.
- Popupmenu toegevoegd in het aanmaken van een recept zodat de user ook een image uit de gallery kan kiezen. Hierdoor slaat hij alleen niet meer de image die met de camere wordt gemaakt.
- De query in mijn getRecipeText werkt nu ook eindelijk. Alleen haalt hij dus de verkeerde tekst op. 
- In de recepttitel listview staat nu wel eindelijk een foto! Maar dat lijkt alleen te werken met afbeeldingen die uit de gallery komen. 

#dag 14
- Out of memory error verholpen door mijn Bitmap method aan te passen. 
- In de listview kunnen nu recepten met en zonder plaatje staan.
- Je kan nu een foto en een gallery image toevoegen aan een recipt. Alleen komen alle images niet meer in de listview. 
- Gallery images zijn wel weer te zien in de listview. Alleen de zelfgemaakte foto's niet. 
- De save button blijft nu altijd zichtbaar als je een extra foto toevoegt. 
- Het pop up menu van de take photo button werkt. De beelden worden nu alleen nog wel in de image view van de extra image geladen. 
- Ik kan nu twee foto's uploaden in de foto recipe activity.

#dag 15
- Als er op een titel in de listview wordt geklikt wordt nu het juiste recept opgehaald. 
- In make photo recipe krijg ik niet meer zomaar de toast "you haven't picked an image".
- App crasht niet meer als ik een photo recipe op wil slaan zonder disch picture. 
- Ik heb "take photo" veranderd naar "Select an Image".

#dag 16
- App crasht niet meer als ik een photo recipe wil bekijken. Maar hij laat ook nog steeds de foto niet zien. 
- Invulvelden leeg als je op save recipe hebt geklikt. 
- Deleten van recepten werkt, alleen wordt de listview dan niet meteen geupdate. Je moet eerst het scherm uit en dan weer in om te zien dat het gedelete is. 
- Zelf genomen foto's worden nu ook opgehaald in de recipe listview. 
- Als er een foto recept wordt opgehaald, wordt er ook echt een foto laten zien. 
- 
#dag 17
- Debugging: De titel edit texts aangepast zodat er geen " en ' in de titel kan staan. Als deze tekens in de titel staan crasht de app namelijk als een van de sqlite querys wordt aangeroepen. Je kan met de camera nu ook een extra foto toevoegen aan foto recip
- Scherm rotation uitgezet, omdat ik dat niet mooi vindt in mijn app. De listview wordt er lelijk door etcetera. 



Wat staat er verder nog op mijn to do:
- delete functie moet goed werken
