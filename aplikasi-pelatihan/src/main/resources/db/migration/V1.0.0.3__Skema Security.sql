create table s_user (
  id varchar(36),
  username varchar(100) not null,
  password varchar(255) not null,
  active boolean,
  primary key (id),
  unique (username)
);

create table s_role (
  id varchar(36),
  nama varchar(100) not null,
  label varchar(255) not null,
  primary key (id),
  unique (nama)
);

create table s_permission (
  id varchar(36),
  nama varchar(100) not null,
  label varchar(255) not null,
  primary key (id),
  unique (nama)
);

create table s_user_role (
  id_user varchar(36),
  id_role varchar(36),
  primary key (id_user, id_role),
  foreign key (id_user) references s_user (id),
  foreign key (id_role) references s_role (id)
);

create table s_role_permission (
  id_permission varchar(36),
  id_role varchar(36),
  primary key (id_permission, id_role),
  foreign key (id_permission) references s_permission (id),
  foreign key (id_role) references s_role (id)
);