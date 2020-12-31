package com.gzeinnumer.gzndirectory.helper.model;

public class PermissionsResult {
    private String isGranted;
    private String permission;

    public PermissionsResult(String isGranted, String permission) {
        this.isGranted = isGranted;
        this.permission = permission;
    }

    public String isGranted() {
        return isGranted;
    }

    public String getPermission() {
        return permission;
    }

    @Override
    public String toString() {
        return "PermissionsResult{" +
                "isGranted='" + isGranted + '\'' +
                ", permission='" + permission + '\'' +
                '}';
    }
}