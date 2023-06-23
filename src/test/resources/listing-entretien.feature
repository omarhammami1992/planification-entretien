# language: fr
Fonctionnalité: Planifier un entretien de recrutement chez Soat

  Scénario: Lister les entretiens déjà planifiés
    Etant donné les recruteurs existants
      | id | email             | language | xp |
      | 1  | recruteur@soat.fr | Java     | 10 |
    Et les candidats existants
      | id | email             | language | xp |
      | 1  | candidat@mail.com | Java     | 5  |
    Et les entretiens existants
      | id | recruteur | candidat | horaire          |
      | 1  | 1         | 1        | 16/04/2019 15:00 |
    Quand on liste les tous les entretiens
    Alors on récupères les entretiens suivants
      | id | recruteur         | candidat          | language | horaire          |
      | 1  | recruteur@soat.fr | candidat@mail.com | Java     | 16/04/2019 15:00 |

  Scénario: Lister les recuteurs experimentés
    Etant donné les recruteurs existants
      | id | email              | language | xp |
      | 1  | recruteur1@soat.fr | Java     | 11 |
      | 2  | recruteur2@soat.fr | Java     | 5  |
      | 3  | recruteur3@soat.fr | Java     | 10 |
    Quand on liste les recuteurs experimentés
    Alors on récupères les recruteurs suivants
      | id | email              | competence     |
      | 1  | recruteur1@soat.fr | 11 ans en Java |
