## test: removeAndGetAll (Test à faire avec le mode FLUSH = commmit)
* Note: dans le cas ou le FlushMode est mis à COMMIT, le test plante.
* En effet, la suppression n'aura lieu qu'au commit et donc dans le Getall, on
  va récupérer le film supprimé.

## test: removeThenAddMovie
* Le test échoue.
* La raison de l'échec est qu'hibernate fait l'insert des entités puis le delete
* ==> l'ordre des opéations est fixés.
* il faut passer par un merge pour faire la modification.

## test: save_withExistingGenres
### @ManyToMany
* ce test vise à tester la persistence les genres.
* Dans ce test, une entité détaché est mergée.
* Attentions avec la méthode persist, l'entité détaché ne peut être enregistré.
  Persist est fait pour enregistrer des données transientes et non détachées.
  * Persist permet en outre de ramener l'id de l'entité créer
  * Quand on fait un merge sur une entité transiente:
    * il y a un insert into
    * par contre l'entité (l'id) n'est pas remonté / n'est pas changée