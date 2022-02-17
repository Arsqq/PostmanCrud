create sequence hibernate_sequence start 1 increment 1;

create table employee (
                         id int8 not null,
                         name varchar(255),
                         categoryId int8 REFERENCES employee_category(id),
                         primary key (id)
);

create table employee_category (
                           id int8 not null,
                           category_name varchar(255)
);
