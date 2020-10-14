Feature: Show ads sorted by score descending

  Scenario: Show ads
    Given the following adds
      | id | typology | description                                                                                       | pictures | size |
      | 1  | CHALET   | Este piso es una ganga, compra, compra, COMPRA!!!!!                                               |          | 300  |
      | 2  | FLAT     | Nuevo ático céntrico recién reformado. No deje pasar la oportunidad y adquiera este ático de lujo |    4     | 300  |
      | 3  | CHALET   |                                                                                                   |    2     | 300  |
    And the scores are calculated
    When ads are shown
    Then the following ads are shown
      | id | typology | description                                                                                       | pictures | size |
      | 2  | FLAT     | Nuevo ático céntrico recién reformado. No deje pasar la oportunidad y adquiera este ático de lujo |    4     | 300  |
      | 3  | CHALET   |                                                                                                   |    2     | 300  |
      | 1  | CHALET   | Este piso es una ganga, compra, compra, COMPRA!!!!!                                               |          | 300  |

  Scenario: Show ads sorted by score descending
    Given the following adds
      | id | typology | description                                                                                       | pictures | size |
      | 1  | CHALET   | Este piso es una ganga, compra, compra, COMPRA!!!!!                                               |          | 300  |
      | 2  | FLAT     | Nuevo ático céntrico recién reformado. No deje pasar la oportunidad y adquiera este ático de lujo |    4     | 300  |
      | 3  | CHALET   |                                                                                                   |    2     | 300  |
    And the scores are calculated
    When ads are shown
    Then ads are sorted by score descending