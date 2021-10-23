package com.piter.importedcars.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum District {

  DOLNOSLASKIE("dolnośląskie", 2),
  KUJAWSKO_POMORSKIE("kujawsko-pomorskie", 4),
  LUBELSKIE("lubelskie", 6),
  LUBUSKIE("lubuskie", 8),
  LODZKIE("łódzkie", 10),
  MALOPOLSKIE("małopolskie", 12),
  MAZOWIECKIE("mazowieckie", 14),
  OPOLSKIE("opolskie", 16),
  PODKARPACKIE("podkarpackie", 18),
  PODLASKIE("podlaskie", 20),
  POMORSKIE("pomorskie", 22),
  SLASKIE("śląskie", 24),
  SWIETOKRZYSKIE("świętokrzyskie", 26),
  WARMONSKO_MAZURSKIE("warmińsko-mazurskie", 28),
  WIELKOPOLSKIE("wielkopolskie", 30),
  ZACHODNIOPOMORSKIE("zachodniopomorskie", 32);

  private final String polishDistrictName;
  private final int polishDistrictCode;
}
