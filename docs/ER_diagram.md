### Udkast til ER-diagram:

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

Table shared_wishlist{
  id integer [PK, increment]
  uid varchar(50)
  wishlist_id integer [ref: > wishlist.id]
  link varchar(2083)
}
``` 

### Model udarbejdet i ```dbdiagram.io```

![Wishlist](https://user-images.githubusercontent.com/113039777/231762691-56bce14e-ca6c-4355-8e6d-2fa45cac4227.png)

