set session_replication_role to replica;
truncate table Review, Movie_Genre, Movie, Genre, Actor, Movie_Details, Movie_Actor, Award RESTART IDENTITY;
set session_replication_role to default;

insert into Movie (name,certification, id) select 'Movie' || generate_series(1,1000000), 1,generate_series(-1000000,-1);

insert into Review (author, content, movie_id, id) select 'max', 'rewiew1-'|| generate_series(1,1000000), generate_series(-1000000,-1), generate_series(-1000000,-1);
insert into Review (author, content, movie_id, id) select 'max', 'rewiew2-'|| generate_series(1,1000000), generate_series(-1000000,-1), generate_series(-2000000,-1000001);
