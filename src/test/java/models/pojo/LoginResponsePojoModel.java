package models.pojo;

public class LoginResponsePojoModel {
   String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    @Override
    public String toString() {
        return "{'token='" + token + '\'' +'}';
    }
}
