package com.example.baseballmanagementapp.models;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Team {
    String teamId, name, city, province, country, stadium, foundedYear;
    String logo;
    List<Manager> managerList = new ArrayList<>();
    public Team() {

    }
    public Team(String name, String teamId) {
        this.name = name;
        this.teamId = teamId;
    }

    public Team(String teamId, String name, String city, String province, String country, String stadium, String foundedYear, String logo) {
        this.teamId = teamId;
        this.name = name;
        this.city = city;
        this.province = province;
        this.country = country;
        this.stadium = stadium;
        this.foundedYear = foundedYear;
        this.logo = logo;
    }

    public Team(String teamName) {
        this.name = teamName;
    }

    public void addManagers(@Nullable Manager manager){
        managerList.add(manager);
        manager.addTeam(this);
    }

    public List<Manager> getManagerList() {
        return managerList;
    }

    public void setManagerList(List<Manager> managerList) {
        this.managerList = managerList;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getFoundedYear() {
        return foundedYear;
    }

    public void setFoundedYear(String foundedYear) {
        this.foundedYear = foundedYear;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public boolean equals(@Nullable String str) {
        return this.getName().equals(str);
    }
}
