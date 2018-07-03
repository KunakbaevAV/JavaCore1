import java.util.ArrayList;

class Team {
    private String name;
    private ArrayList<Player> players= new ArrayList<>();
    private String resultTeam;

    void setResultTeam(String resultTeam) {
        this.resultTeam = "Команда " + name + " " + resultTeam;
    }

    int run(int numberPlayer){
        return players.get(numberPlayer).getStamina();
    }

    String getNamePlayer(int numberPlayer){
        return players.get(numberPlayer).getName();
    }

    String getNameTeam() {
        return name;
    }

    void setPlayerResults(int numberPlayer, String result){
        players.get(numberPlayer).setResult(result);
    }

    Team(String name, String namePlayer1, String namePlayer2, String namePlayer3, String namePlayer4) {
        this.name = name;
        Player player1 = new Player(namePlayer1);
        Player player2 = new Player(namePlayer2);
        Player player3 = new Player(namePlayer3);
        Player player4 = new Player(namePlayer4);
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
    }

    void showResults(){
        System.out.println("Результаты испытания:");
        for (Player player : players) {
            System.out.println(player.getResult());
        }
        System.out.println(resultTeam);
    }

}
