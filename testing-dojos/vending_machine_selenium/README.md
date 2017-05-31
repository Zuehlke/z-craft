Testing Dojo: Vending Machine
=============================

Die Aufgabe ist, die Anforderungen des "Vending Machine" Code-Katas mit der Selenium IDE abzutesten.

Vorbereitung
------------

- Die Anforderungen liegen unter https://github.com/guyroyse/vending-machine-kata
- Eine Implementierung mit Weboberfläche läuft unter http://vend.coderforchrist.com/
- Installiert ggf. Firefox und das Selenium IDE Addon: https://addons.mozilla.org/en-US/firefox/addon/selenium-ide/
- Funktionierende Tests für den ersten Abschnitt liegen als Selenium-Testsuite in der Datei example-suite.html dabei, die in der Selenium-IDE geladen werden kann.

Durchführung
------------

Bearbeitet die Anforderungen abschnittsweise in Teams:

- Lest die Anforderungen durch
- Identifiziert die zu testenden Aspekte
- Formuliert Tests
- Implementiert die Tests in der Selenium IDE
- Überlegt und diskutiert folgende Punkte:
  - welche Testgranularität ist sinnvoll?
  - wie sollen die Tests geschnitten werden?
  - Wie sollen sie in Suiten organisiert werden?
  
Technische Tipps
----------------
- Die IDE kann Useraktionen aufzeichnen, dies lässt sich mit dem roten Knopf rechts oben steuern.
- Zum Prüfen von Bedingungen klickt man rechts auf ein Element und bekommt im Kontextmenü Vorschläge.
- Die vorgeschlagenen Elementselektoren sind nicht immer korrekt, wenn man sie manuell anpasst muss man darauf achten dass die Syntax von CSS und XPath unterschiedlich sind.
- Die IDE kann leider nicht die ganze Testsuite in einer Datei abspeichern, sondern nur jeden Testfall separat und die Suite dann als übergeordnetes Element nochmal in einer Datei, die auf die Testfälle verweist.