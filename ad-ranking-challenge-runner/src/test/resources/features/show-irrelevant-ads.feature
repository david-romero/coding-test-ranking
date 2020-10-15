Feature: Show irrelevant ads

  Scenario: Show irrelevant ads
    Given the following adds
      | id | typology | description                                                                                       | pictures | size |
      | 1  | CHALET   | Este piso es una ganga, compra, compra, COMPRA!!!!!                                               |          | 300  |
      | 2  | FLAT     | Nuevo ático céntrico recién reformado. No deje pasar la oportunidad y adquiera este ático de lujo |    4     | 300  |
      | 3  | CHALET   |                                                                                                   |    2     | 300  |
    And the scores are calculated
    When irrelevant ads are shown
    Then the following ads are returned
      | id | typology | description                                                                                       | pictures | size |
      | 3  | CHALET   |                                                                                                   |    2     | 300  |
      | 1  | CHALET   | Este piso es una ganga, compra, compra, COMPRA!!!!!                                               |          | 300  |
    And the attribute irrelevantDate is not null for each ad

  Scenario: Show ads sorted by score descending
    Given the following adds
      | id | typology | description                                                                                       | pictures | size |
      | 1  | CHALET   | Este piso es una ganga, compra, compra, COMPRA!!!!!                                               |          | 300  |
      | 2  | FLAT     | Nuevo ático céntrico recién reformado. No deje pasar la oportunidad y adquiera este ático de lujo |    4     | 300  |
      | 3  | CHALET   |                                                                                                   |    2     | 300  |
    And the scores are calculated
    When irrelevant ads are shown
    Then irrelevant ads are sorted by the date in which the ad became irrelevant