import java.util.Random;

public class Course {
    private int distance;
    private int hard;

    public Course(int distance, int hard) {
        this.distance = distance;
        this.hard = hard;
    }

    public Course() {
        Random random = new Random();
        distance = random.nextInt(51) + 40;
        hard = random.nextInt(3) + 1;
    }

    public void doIt(Team team){
        boolean win = true;
        System.out.println("Команда " + team.getNameTeam() + " приступает к испытаниям!");
        System.out.println("Полоса препятствий длинной "+ distance + " метров, со сложностью " + hard + ".");
        for (int i = 0; i < 4; i++) {
            if(team.run(i) >= distance*hard){
                System.out.println("Игрок " + team.getNamePlayer(i) + " успешно прошел дистанцию");
            }else {
                System.out.println("Игроку " + team.getNamePlayer(i) + " не хватило " + (distance*hard-team.run(i)) + " выносливости");
                win = false;
            }
        }
        System.out.println(win? "Комада успешно прошла дистанцию" : "Команда не справилась с заданием");
    }
}
