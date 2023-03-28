Udkast til ER-diagram:

```sql

Table users {
    id integer [PK, unique, increment]
    username varchar(250) [unique, not null]
    password varchar(250) [not null]
    email varchar(250) [unique, not null]
    first_name varchar [not null]
    last_name varchar [not null]
    birthday date
}

Table wishlist{
    id integer [PK, unique, increment]
    name varchar(250)
    user_id integer [ref: > users.id] // many-to-one
}

Table wish{
    id integer [PK, unique, increment]
    name varchar(250)
    link varchar(2083)
    price decimal
    qty integer
    description nvarchar(max)
    wishlist_id integer [ref: > wishlist.id]
}
``` 

![erdiagram](https://user-images.githubusercontent.com/113039777/228004268-d05c3b75-cf9e-4793-a62f-abde39a81b1b.png)
