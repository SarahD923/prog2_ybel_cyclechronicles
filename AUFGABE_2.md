# Aufgabe 2: Cycle Chronicles

## Aufgabe 2.1: Äquivalenzklassen

Für jeden Einflussfaktor von `Shop#accept` werden gültige und ungültige
Äquivalenzklassen gebildet. Bei jedem Test werden die nicht untersuchten
Einflussfaktoren gültig gewählt.

| Einflussfaktor | Klasse | Werte | Erwartung |
| --- | --- | --- | --- |
| Fahrradtyp | F1: unterstützt | `RACE`, `SINGLE_SPEED`, `FIXIE` | Auftrag kann angenommen werden |
| Fahrradtyp | F2: Gravel-Bike | `GRAVEL` | Auftrag wird abgelehnt |
| Fahrradtyp | F3: E-Bike | `EBIKE` | Auftrag wird abgelehnt |
| Kunde | K1: kein offener Auftrag | Noch kein Auftrag mit demselben Kundennamen vorhanden | Auftrag kann angenommen werden |
| Kunde | K2: offener Auftrag vorhanden | Ein Auftrag mit demselben Kundennamen ist bereits offen | Auftrag wird abgelehnt |
| Warteschlange | W1: Platz vorhanden | 0 bis 4 offene Aufträge | Auftrag kann angenommen werden |
| Warteschlange | W2: voll | mindestens 5 offene Aufträge | Auftrag wird abgelehnt |

`GRAVEL` und `EBIKE` werden getrennt betrachtet, da beide Typen in der
Aufgabenstellung als eigene Ablehnungsbedingung genannt werden.

## Grenzwertanalyse

Die Warteschlangengröße ist der einzige numerische Eingabefaktor.

| Anzahl vor `accept` | Bedeutung | Erwartung |
| --- | --- | --- |
| 0 | unterer möglicher Grenzwert | Annahme bei sonst gültigem Auftrag |
| 4 | Wert direkt unter der Kapazitätsgrenze | Annahme; danach sind 5 Aufträge offen |
| 5 | Kapazitätsgrenze bereits erreicht | Ablehnung |

Eine negative Anzahl offener Aufträge ist nicht möglich. Fahrradtyp und
Kundenname besitzen keine sinnvollen numerischen Grenzwerte.

## Konkrete Testfälle

| ID | Ausgangssituation und Eingabe | Erwartetes Ergebnis |
| --- | --- | --- |
| TF1 | Leerer Shop; Typ jeweils `RACE`, `SINGLE_SPEED` oder `FIXIE`; neuer Kunde | `true` |
| TF2 | Leerer Shop; Typ `GRAVEL`; neuer Kunde | `false` |
| TF3 | Leerer Shop; Typ `EBIKE`; neuer Kunde | `false` |
| TF4 | Ein offener Auftrag für "Alex"; neuer gültiger Auftrag ebenfalls für "Alex" | `false` |
| TF5 | Ein offener Auftrag für "Alex"; neuer gültiger Auftrag für "Bea" | `true` |
| TF6 | Vier gültige offene Aufträge verschiedener Kunden; fünfter gültiger Auftrag | `true` |
| TF7 | Fünf gültige offene Aufträge verschiedener Kunden; sechster gültiger Auftrag | `false` |

## Aufgabe 2.2: Begründung für Mockito

Die Klasse `Order` ist noch nicht vollständig implementiert. Ihre Methoden
`getBicycleType` und `getCustomer` werfen eine `UnsupportedOperationException`.
Deshalb werden `Order`-Objekte mit Mockito gemockt. Für jeden Test wird
festgelegt, welchen Fahrradtyp und welchen Kundennamen die Getter zurückgeben.

Der `Shop` selbst wird nicht gemockt. Dadurch läuft in allen Tests die echte
Implementierung von `Shop#accept`. Mockito ersetzt nur die noch nicht
verwendbare Abhängigkeit `Order`.
