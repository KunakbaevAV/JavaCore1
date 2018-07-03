import java.util.Random;

class Player{
    private String name;
    private int stamina;

    Player(String name) {
        Random random = new Random();
        this.name = name;
        stamina = random.nextInt(151) + 100;
    }

    public int getStamina() {
        return stamina;
    }

    public String getName() {
        return name;
    }

    public Player(String name, int stamina) {
        this.name = name;
        this.stamina = stamina;
    }


}
