# Programozás 1 - Tehetséggondozó program projekt munka:
## Képnézegető applikáció

## HUNcamper csapat:
Tagok:
- Fajka Viktor
- Nahimi Selim Krisztián
- Zabos Péter

## Szegedi Tudományegyetem TTIK Természettudományi és Informatika Kar

Projektmunka célja egy képnézegető applikáció elkészítése. Szoftverfejlesztés ciklusainak megismerése, és csapaton belüli munka megosztása.

# Követelmények:
- A program elkészítése egy GUI (graphical user interace) rendszer használatával valósul meg Java nyelvben. A projektünkben Java Swinggel valósítjuk ezt meg, ami egy segédlet (toolkit) a GUI-hoz.
- File gombra kattintva egy file kiválasztó ablak ugrik fel, ahol ki tudjuk választani a számunkra szimpatikus képet, melyet meg tudunk tekinteni, és elemi képszerkesztő műveleteket végezni rajta.
- A file böngészőben csak a kép kiterjesztésű file-ok jelenjenek meg.
- Alapvető kiterjesztések elérhetőek (3, 4 fajta), .jpeg, .png, .bpm,...
- Ezek bővíthetőek, tehát egy új class file elkészítésével, és a projekt file-jai közé másolva, azonnal elérhető lesz az új kiterjesztés típus is.
- A kép betöltése után különböző funkciók elérhetőek a programom belül
  - a képet tudjuk ***tükrözni***, azaz tengelyes szimetriával megkapjuk a kép tükörmását
  - a képet el lehet ***forgatni -270 és 270 fok között***, ezzel tetejére lehet állítani, vagy jobbra és balra dönteni
  - a képnek a ***színskáláját*** lehet állítani, tehát a színek összetételét konfigurálni
- A kép szerkesztése után ezeket a változásokat el lehet menteni egy új képként, tetszőleges támogatott formátumban.
- Képszerkesztő funkciók is szintén bővíthetőek. Ennek az a lényege, ha egy új funkciót szeretnénk hozzáadni a programhoz, ami előre leprogramozott, akkor ne kelljen változtatni a programkódon, hanem csak a class file-okat a projekt struktúrájába másolni futásidőben, és azok rögtön használhatóak lesznek.
- *Extra* követelmény: reflection mechanizmus használata, futás közben lehessen bővíteni új class file-okkal a programot. (Opcionális)

# Program használata
- A program indítása után a File menüponton belül az Helpre kattintva elolvashatjuk az utmutatót a használatrol. 
- Open-re kattintva egy File chooser jelenik meg ahol a megfelelő file kiterjesztést kiválasztva megtudjuk nyitni az adott képet.
- Operations menüponton belül kitudjuk választani milyen módosításokat szeretnénk elvégezni
  - **Flip horizontal** függőleges tengelyen tükrözi a képet 
  - **Flip vertical** vizszintes tengelyen tükrözi a képet
  - **Invert** invertálja a képet
  - **RGB Edit** színskála szerint tudjuk változtatni a kép színét
  - **Rotate** kép elforgatása 90 fokkal
- File menüponton belül a Save-ra kattintva ismét egy File chooser jelenik meg ahol le tudjuk mententeni a képünket

  

# További segédletek

- Program kód
- [UML diagram](UML/UML.png), a program szellemi tervét és alapjait reprezentáló tervrajz. (tervdokumentáció)
- [Tesztjegyzőkönyvek](Test.md)
- [JavaDoc Dokumentáció](https://huncamper.github.io/prog1-projekt/)

