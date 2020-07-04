package com.example.logg;

public class User {

        private String id;
        private String name;
        private String prenom;
        private String username;
        private String Job;
        private String password;
        private byte[] image;


        public User(String id, String name , String prenom, String password, String username, String Job, byte[] image) {
            this.id = id;
            this.name = name;
            this.prenom = prenom;
            this.password = password;
            this.username = username;
            this.Job = Job;
            this.image = image;
        }

        public User(String id, String name , String prenom, String password, String username, String Job) {
            this.id = id;
            this.name = name;
            this.prenom = prenom;
            this.password = password;
            this.username = username;
            this.Job = Job;

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

        public String getJob() {
        return Job;
    }

        public void setJob(String Job) {
        this.Job = Job;
    }

        public byte[] getImage() {
            return image;
        }

        public void setImage(byte[] image) {
            this.image = image;
        }


    }


