create table Actor (id bigint not null, birthdate date, firstName varchar(255), name varchar(255), primary key (id));

create table movie_actor (character varchar(255), actor_id bigint not null, movie_id bigint not null, primary key (actor_id, movie_id));
alter table movie_actor add constraint fk_movieactor_actor foreign key (actor_id) references Actor;
alter table movie_actor add constraint fk_movieactor_movie foreign key (movie_id) references Movie;