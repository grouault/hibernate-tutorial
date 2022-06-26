-- set REFERENTIAL_INTEGRITY FALSE;
set session_replication_role to replica;
truncate table Review, Movie_Genre, Movie, Genre, Actor, Movie_Details, Movie_Actor, Award RESTART IDENTITY;
set session_replication_role to default;
--set REFERENTIAL_INTEGRITY TRUE;

insert into Movie(name, description, id, certification) values('Inception', 'description init', -1, 1);
insert into Movie(name, description, id, certification) values('Memento', 'description init', -2, 2);

insert into Review (author, content, movie_id, id) values ('max', 'au top', -1, -1);
insert into Review (author, content, movie_id, id) values ('ernest', 'bof bof', -1, -2);

insert into Genre(name, id) values ('Action', -1);
insert into Genre(name, id) values ('Sci-Fi', -2);
insert into Genre(name, id) values ('Drame', -3);

insert into Movie_Genre(genre_id,movie_id) values(-1, -1);
insert into Movie_Genre(genre_id,movie_id) values(-2,   -1);
insert into Movie_Genre(genre_id,movie_id) values(-3, -1);

insert into Actor(firstname, name, id) values('Lenardo','DiCaprio',-1);
insert into Actor(firstname, name, id) values('Eisenberg','Jesse',-2);
insert into Actor(firstname, name, id) values('Bourvil','Andre',-3);
insert into Movie_Actor(character, actor_id, movie_id) values('cobb', -1, -1);