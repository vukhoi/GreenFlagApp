package project1.example.com.greenflagapp;

public class Account {

    int id;
    String name;
    String username;
    String password;
    String photo;
    String email;
    int age;
    String birthDate;
    String gender;
    String postalAddress;

    public Account(String name, String username, String password, String photo, String email, int age, String birthDate, String gender, String postalAddress) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.photo = photo;
        this.email = email;
        this.age = age;
        this.birthDate = birthDate;
        this.gender = gender;
        this.postalAddress = postalAddress;
    }

    public Account(int id, String name, String username, String password, String photo, String email, int age, String birthDate, String gender, String postalAddress) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.photo = photo;
        this.email = email;
        this.age = age;
        this.birthDate = birthDate;
        this.gender = gender;
        this.postalAddress = postalAddress;
    }

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

}
