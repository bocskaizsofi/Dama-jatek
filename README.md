A Dámajáték egy grafikus kezelőfelületű társasjáték, amivel a dáma angol/amerikai variánsát 
lehet játszani. A játék a menu.Menu fájlból (a főosztályból) indítható. 

Az angol/amerikai dáma szabályai 
A játékot 2 fő játszhatja egy 8x8-as sakktáblán, aminek a bal alsó sarka sötét mező. 
Minden játékosnak 12 bábuja van, ezek kezdetben a játékosokhoz közel eső első 3 sor sötét 
mezőin helyezkednek el. 
A fekete játékos lép először, lépni egyet átlósan előre lehet egy üres mezőre. Amelyik bábu 
elérte a tábla túlsó szélét, dámává válik, ennek jelzésére még egy bábut helyeznek rá, a 
dámák előnye, hogy visszafelé is tudnak mozogni az egyszerű bábukhoz hasonlóképpen.  
A bábu ugrani is tud, ha az ellenfél bábuja átlósan szomszédos vele és üres mező van 
mögötte. Az ugrások kötelezően végrehajtandóak, amennyiben egy bábu tud ugrani, muszáj is 
neki. Az átugrott bábuk lekerülnek a tábláról.  
A játék akkor ér véget, amikor az egyik játékos nem tud már lépni vagy nincs több bábuja 
vagy dámája. Ekkor ő veszít és a másik játékos a nyertes. Tehát a játékosok fő célja az 
ellenfél összes bábujának leütése, vagy az, hogy az ellenfél ne tudjon több lépést 
végrehajtani. 
Döntetlen is kialakulhat, ha a játékosok közösen megegyeznek, ha ugyanaz a lépés 
ismétlődött meg több, mint 3-szor egy játékos által vagy ha az elmúlt 40 lépésben nem történt 
sem ugrás, sem dámává válás. 

A játék menete 
A játék kezdete előtt a gép kisorsolja véletlenszerűen a színeket (sötét és világos) és a 
játékszabály alapján a sötét játékos kezd. Játék közben látható a játékosok neve, bábuinak 
száma, illetve, hogy éppen melyik játékosnak kell lépnie.  
Lépéskor először kattintással ki kell jelölni a mozgatni kívánt bábut, utána pedig a 
célmezőt. Ütés esetén is azt az mezőt kell kijelölni ahová a bábu ugrik, nem azt, ahol a 
leütésre kerülő bábu áll. A program a játékszabálytól eltérő lépést nem engedélyez, csakis a 
soron következő játékos léphet és a szabályoknak megfelelően. 
A „Játékszabály”-ra kattintva elő lehet hívni és el lehet tüntetni a játékszabályt.  
A játékosok (csak az emberiek, a gépi nem) kérhetnek szünetet, illetve döntetlent. Szünet 
kérésekor az aktuális játékállás mentésre kerül és a menüből a Játék folytatása opció 
kiválasztásával folytatható.  
Ha egy játékos döntetlent kér Játékos-Játékos módban, felugrik egy ablak, ami, 
megkérdezi az ellenfelet, hogy elfogadja-e a döntetlent. Amennyiben elfogadja a játék 
döntetlennel ér véget, ellenkező esetben a játék folytatódik. Döntetlen kérésekor Játékos-Gép 
módban a gép elfogadja a döntetlent és a játék véget ér. 
A gép teljes mértékben a szabályok szerint játszik, több helyes ütés vagy ütés nélküli lépés 
esetén azonban ezek közül véletlenszerűen választ így könnyebb ellenfél egy emberi 
játékosnál. 
A program felismeri a játék végét, felugró ablakban kiírja a győztest, vagy a döntetlent és 
annak az okát, ha nem valamelyik játékos kezdeményezte. Az „OK” gombra kattintással 
visszatérhetünk a menübe. Ha egy folytatott játék ért véget, ezek után már nincsen több 
folytatható játék. Játék végén a ranglista automatikusan frissül, a győzelem 1 pontot ér, 
döntetlenre és vereségre a játékosok nem kapnak pontot (negatívat sem). 

Fordítás
A projekt fordítható gyökérmappájában kiadva a következő parancsot: 
javac -d bin src/board/*.java src/game/*.java src/gamestart/*.java src/leaderboard/*.java src/menu/*.java

Futtatás
A főosztály a menu csomagban található, neve: Menu.
A gyökérmappából a következő paranccsal futtatható:
java -cp bin menu.Menu

A játék használata 
A menüben a felhasználó 4 lehetőség közül választhat kattintással: 
- Új játék indítása 
- Előző játék folytatása 
- Ranglista 
- Játékszabály 
A játékban minden nézetből vissza lehet térni a menübe az ablak bal felső sarkában 
található „Menü” feliratú gombbal. Fontos azonban tudni, hogy ilyen esetben az aktuális játék 
állása nem mentődik el, ezt a játékos külön gombbal tudja kérni (részletesebben a Játék 
menete pont alatt). 

Új játék indítása 
Új játék indításakor legördülő listából választható 3 játékmód: 
- Gép-Gép 
- Játékos-Gép 
- Játékos-Játékos 
Az „OK” gomb megnyomásával lehet tovább lépni, ekkor Gép-Gép mód esetén már indul 
is a játék, ha van emberi játékos, a következő lépés a nevének megadása. A játékosok neveit 
ki lehet választani a korábban játszott játékosok listájából, de új nevet is be lehet gépelni. 
Ezután az „Indít” feliratú gomb megnyomásával kezdetét veszi a játék. 
Előző játék folytatása 
Előző játék folytatása esetén a legutóbb szüneteltetett játékot lehet folytani. Ezt ugyanúgy 
lehet szüneteltetni és később újra folytatni, de végig is lehet játszani. A menübe való 
visszatérés itt sem menti a játékállást. Ha nincsen félbehagyott játék, ezt egy ablak jelzi, és 
„OK” gombbal visszatérhet a felhasználó a menübe.  

Ranglista 
A ranglistán megjelennek a részt vevő játékosok helyezésükkel és pontszámaikkal 
(győzelmeik száma). Azok a játékosok is láthatóak, akik nem értek el győzelmet. A ranglista 
helyezések szerint csökkenő, azaz győzelem száma szerint növekvő. Ha két játékos 
ugyanannyi győzelemmel rendelkezik, azonos helyezést értek el a ranglistán. Egy helyezésen 
belül a játékosok névsorban jelennek meg. 

Játékszabály: 
A menüben a Játékszabály választása után megjelenik a Dáma játékszabályának részletes 
leírása. 

A játékban használt fájlok: 
- scoreboard.txt: bináris fájl, amiben a játék a ranglistát rögzíti 
- games.txt: bináris fájl, amibe a játék a félbehagyott állapotot menti 
- dark.png: a fekete bábut ábrázoló kép 
- white.png: a világos bábut ábrázoló kép 
- dark.png: a fekete bábut ábrázoló kép 
- darkQueen.png: a fekete koronázott bábut ábrázoló kép 
- whiteQueen.png: a világos koronázott bábut ábrázoló kép 
A fenti fájlokat a felhasználó nem módosíthatja.
