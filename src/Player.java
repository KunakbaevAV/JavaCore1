import java.util.Random;

class Player{
    private String name;
    private int stamina;
    private String result;

    Player(String name) {
        Random random = new Random();
        this.name = name;
        stamina = random.nextInt(151) + 100;
    }

    int getStamina() {
        return stamina;
    }

    String getName() {
        return name;
    }

    void setResult(String result) {
        this.result = name + result;
    }

    String getResult() {
        return result;
    }

    public Player(String name, int stamina) {
        this.name = name;
        this.stamina = stamina;
    }


}
