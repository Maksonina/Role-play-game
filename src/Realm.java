import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Realm {
    private static BufferedReader br;

    private static FantasyCharacter player = null;

    private static BattleScene battleScene = null;

    public static void main(String[] args) {

        br = new BufferedReader(new InputStreamReader(System.in));

        battleScene = new BattleScene();

        System.out.println("vvedite imya personazha:");

        try {
            command(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void command(String string) throws IOException {

        if (player == null) {
            player = new Hero(
                    string,
                    100,
                    20,
                    20,
                    0,
                    0
            );
            System.out.println(String.format("spasti nash mir ot drakonov vizvalsya %s! da budet ego bronya krepka i biceps krugl!", player.getName()));

            printNavigation();
        }

        switch (string) {
            case "1": {
                System.out.println("torgovec ne priehal");
                command(br.readLine());
            }
            break;
            case "2": {
                commitFight();
            }
            break;
            case "3":
                System.exit(1);
                break;
            case "da":
                command("2");
                break;
            case "net": {
                printNavigation();
                command(br.readLine());
            }
        }

        command(br.readLine());

    }
    private static void printNavigation() {
        System.out.println("kuda poiti?");
        System.out.println("1. k torgovcu");
        System.out.println("2. v temniy les");
        System.out.println("3. vihod");
    }
    private static void commitFight() {
        battleScene.fight(player, createMonster(), new FightCallback() {
            @Override
            public void fightWin() {
                System.out.println(String.format("%s pobedil! teper u vas %d opita i %d zolota, a takzhe ostalos %d edinic zdoroviya.", player.getName(), player.getXp(), player.getGold(), player.getHealthPoints()));
                System.out.println("zhelaete prodolzhit pohod ili vernutsya v gorod? (da/net)");
                try {
                    command(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fightLost() {

            }
        });
    }
    private static FantasyCharacter createMonster() {

        int random = (int) (Math.random() * 10);

        if (random % 2 == 0) return new Goblin(
                "Goblin",
                50,
                10,
                10,
                100,
                20
        );
        else return new Skeleton(
                "Skelet",
                25,
                20,
                20,
                100,
                10
        );
    }
    interface FightCallback {
        void fightWin();
        void fightLost();
    }
}