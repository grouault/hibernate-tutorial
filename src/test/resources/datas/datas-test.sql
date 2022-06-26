set REFERENTIAL_INTEGRITY FALSE;
truncate table Review;
truncate table Movie_Genre;
truncate table Movie;
truncate table Genre;
truncate table Movie_Details;
set REFERENTIAL_INTEGRITY TRUE;

insert into Movie(name, description, id, certification) values('Inception', 'description init', -1L,1);
insert into Movie(name, description, id, certification) values('Memento', 'description init', -2L,2);

insert into Review (author, content, movie_id, id) values ('max', 'au top', -1L, -1L);
insert into Review (author, content, movie_id, id) values ('ernest', 'bof bof', -1L, -2L);

insert into Genre(name, id) values ('Action', -1L);

insert into Actor(firstname, name, id) values('Lenardo','DiCaprio',-1L);
insert into Actor(firstname, name, id) values('Eisenberg','Jesse',-2L);
insert into Actor(firstname, name, id) values('Bourvil','Andre',-3L);
insert into Movie_Actor(character, actor_id, movie_id) values('cobb', -1L, -1L);