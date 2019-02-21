package com.thunder.as31try.rxandroid;

import java.util.List;

public class User {
        private String name;
        private List<Addr> mAddrs;

    public User() {
    }

    static class Addr{
            String city;
            String street;

            Addr(String city, String street) {
                this.city = city;
                this.street = street;
            }

        @Override
        public String toString() {
            return "Addr{" +
                    "city='" + city + '\'' +
                    ", street='" + street + '\'' +
                    '}';
        }
    }

        public User(String name, List<Addr> addr) {
            this.name = name;
            mAddrs = addr;
        }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name == null ? "" : name;
    }

    public List<Addr> getAddrs() {
        return mAddrs;
    }

    public void setAddrs(List<Addr> addr) {
        mAddrs = addr;
    }
}