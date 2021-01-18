# language: no
# encoding: UTF-8
Egenskap: Medlemskapsvurdering

  Scenario: En søker med medlemskap
    Gitt en søker med inputperiode fra "2020-08-16" til "2020-08-22"
    Når medlemskap skal beregnes
    Så skal svaret være "JA"

  Scenario: En søker med uavklart medlemskap
    Gitt en søker som har jobbet mindre enn 25% siste året
    Når medlemskap skal beregnes
    Så skal svaret være "UAVKLART"

  Scenario: En søker med statsborgerskap utenfor EØS med medlemskap
    Gitt en søker med gyldig oppholdstillatelse
    Når medlemskap skal beregnes
    Så skal svaret være "JA"