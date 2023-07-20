package com.example.baseballmanagementapp.models;

public class UserTeam {
    String id, userId, teamId, status, role;

    public UserTeam(String id, String userId, String teamId, String status, String role) {
        this.userId = userId;
        this.teamId = teamId;
        this.status = status;
        this.role = role;
        this.id = id;
    }

    public UserTeam() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
