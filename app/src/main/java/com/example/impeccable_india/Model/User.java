package com.example.impeccable_india.Model;

public class User
{
     String uid;
     String email ;
     String name ;

     public User(){}

     public String getUid() {
          return uid;
     }

     public void setUid(String uid) {
          this.uid = uid;
     }

     public String getEmail() {
          return email;
     }

     public void setEmail(String email) {
          this.email = email;
     }

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     @Override
     public String toString() {
          return "User{" +
                  "uid='" + uid + '\'' +
                  ", email='" + email + '\'' +
                  ", name='" + name + '\'' +
                  '}';
     }
}
