package com.ODIN.HUMMVEE.DTO;

public class AuthRequest {

    private String username;
    private String password;

    public AuthRequest(){
        super();
    }

    public AuthRequest(String username , String password){
        super();
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return this.username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

    @Override
    public String toString(){
        return "[username :" + username + " , password :" + password+"]";
    }

}
