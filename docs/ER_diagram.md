Udkast til ER-diagram:

```sql

 Table users {
  id int [PK, increment]
  username varchar(250)
  password varchar(250)
  email varchar(250)
  first_name varchar 
  last_name varchar 
  birthday date
}

Table wishlist {
  id int [PK, increment]
  name varchar(250)
  user_id int [ref: > users.id] // many-to-one
}

Table wish {
  id int [PK, increment]
  name varchar(250)
  link varchar(2083)
  price decimal
  qty int
  description nvarchar(max)
  wishlist_id int [ref: > wishlist.id]
}
``` 

![erdiagram](https://user-images.githubusercontent.com/113039777/228004268-d05c3b75-cf9e-4793-a62f-abde39a81b1b.png)
