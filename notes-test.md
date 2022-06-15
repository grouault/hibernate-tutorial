## test: removeAndGetAll (Test à faire avec le mode FLUSH = commmit)
* Note: dans le cas ou le FlushMode est mis à COMMIT, le test plante.
* En effet, la suppression n'aura lieu qu'au commit et donc dans le Getall, on
  va récupérer le film supprimé.

## test: removeThenAddMovie
* Le test échoue.
* La raison de l'échec est qu'hibernate fait l'insert des entités puis le delete
* ==> l'ordre des opéations est fixés.
* il faut passer par un merge pour faire la modification.