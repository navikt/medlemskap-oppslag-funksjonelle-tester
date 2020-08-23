# language: no
# encoding: UTF-8
Egenskap: Medlemskapsvurdering

  Scenario: En søker med medlemskap
    Gitt en søker med inputperiode fra "2020-08-16" til "2020-08-22"
    Når medlemskap skal beregnes
    Så skal svaret være "JA"