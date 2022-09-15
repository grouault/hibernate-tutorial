set session_replication_role to replica;
truncate table Review, Movie_Genre, Movie, Genre, Actor, Movie_Details, Movie_Actor, Award RESTART IDENTITY;
set session_replication_role to default;

insert into Movie (name,certification, id, version) values ('Inception', 1,-1, 0);
insert into Movie (name,certification, id, version) values ('Memento', 2,-2, 0);
insert into Movie (name,certification, id, version) values ('Fight Club', 2,-3, 0);

insert into Movie_Details (plot,movie_id) values ('Dominick "Dom" Cobb and Arthur are "extrac...',-1);
insert into Movie_Details (plot,movie_id) values ('The film starts with a Polaroid photo...',-2);
insert into Movie_Details (plot,movie_id) values ('The Narrator, an automobile recall spe...',-3);