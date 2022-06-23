# TP Mapping

Dur dur de faire un TP sur le mapping car en pratique on a déjà codé pas mal de chose, donc ce TP sera un peu spécial
car on va mettre des choses en place que l'on n'a pas vu (original comme concept!) mais bon,
au moins ça fait un TP pas trop rébarbatif et ça nous servira pour le prochain TP qui sera plus une mise en pratique.

voici donc l'énoncé du TP :

1) brancher les tests unitaires sur une instance de base postgresql (hibernate4all-test) plutot qu' H2

2) mettre en place flywaydb afin d'industrialiser la création des tables pour la webapp et les TUs

3) ajouter un attribut rating à la classe review et ajouter @Min et @Max à cet attribut pour que sa valeur soit comprise entre 0 et 10

et créer le script flyway pour ajouter la colonne à la table Review

4) mettre en place hibernate-validator

5) créer une nouvelle entité Award : un film pourra avoir plusieurs awards mais un award ne sera associé qu'à un film