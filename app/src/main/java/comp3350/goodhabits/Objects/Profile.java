package comp3350.goodhabits.Objects;

// Это класс для объекта профиля пользователя
public class Profile {
    private final String name; // Имя пользователя
    private final String email; // Электронная почта пользователя

    public Profile(String name, String email){
        this.name = name;
        this.email = email;
    }


    public String getName(){
        return this.name;
    }


    public String getEmail(){
        return this.email;
    }
}
