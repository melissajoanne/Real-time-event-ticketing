package lk.ac.iit.RealTimeTicket.model;

import jakarta.persistence.*;


    @MappedSuperclass
    public class User {

        private String name;
        private String email;

        // Constructors
        public User() {
        }

        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

