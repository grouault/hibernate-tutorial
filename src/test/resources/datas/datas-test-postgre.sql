-- set REFERENTIAL_INTEGRITY FALSE;
set session_replication_role to replica;
truncate table Review, Movie_Genre, Movie, Genre, Movie_Details RESTART IDENTITY;
set session_replication_role to default;
--set REFERENTIAL_INTEGRITY TRUE;

insert into Movie(name, description, id, certification) values('Inception', 'description init', -1, 1);
insert into Movie(name, description, id, certification) values('Memento', 'description init', -2, 2);

-- insert into Review (author, content, movie_id, id) values ('max', 'au top', -1, -1);
-- insert into Review (author, content, movie_id, id) values ('ernest', 'bof bof', -1, -2);

insert into Genre(name, id) values ('Action', -1);