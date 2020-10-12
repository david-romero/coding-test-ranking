Feature: Calculate scores

  Scenario: Calculate scores for the existing ads
    Given the following adds
     | id | typology | description                                                                                       | pictures | size |
     | 1  | CHALET   | Este piso es una ganga, compra, compra, COMPRA!!!!!                                               |          | 300  |
     | 2  | FLAT     | Nuevo ático céntrico recién reformado. No deje pasar la oportunidad y adquiera este ático de lujo |    4     | 300  |
     | 3  | CHALET   |                                                                                                   |    2     | 300  |
    When the scores are calculated
    Then the following scores are calculated
    | ad | score |
    | 1  |   -5  |
    | 2  |   85  |
    | 3  |   20  |