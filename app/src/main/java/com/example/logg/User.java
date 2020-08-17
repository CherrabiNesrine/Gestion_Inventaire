package com.example.logg;

public class User {

        private String id;
        private String name;
        private String prenom;
        private String username;
        private String password;
        private byte[] image;


        public User(String id, String name , String prenom, String password, String username,  byte[] image) {
            this.id = id;
            this.name = name;
            this.prenom = prenom;
            this.password = password;
            this.username = username;
            this.image = image;
        }

        public User(String id, String name , String prenom, String password, String username) {
            this.id = id;
            this.name = name;
            this.prenom = prenom;
            this.password = password;
            this.username = username;
        }



        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrenom() {
        return prenom;
    }

        public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

        public String getPassword() {
        return password;
    }

        public void setPassword(String password) {
        this.password = password;
    }

        public String getUserName() {
        return username;
    }

        public void setUserName(String username) {
        this.username = username;
    }

    

        public byte[] getImage() {
            return image;
        }

        public void setImage(byte[] image) {
            this.image = image;
        }


    }


