public class BattleScene {

    public void fight(FantasyCharacter hero, FantasyCharacter monster, Realm.FightCallback fightCallback) {

        Runnable runnable = () -> {

            int turn = 1;

            boolean isFightEnded = false;
            while (!isFightEnded) {
                System.out.println("----hod: " + turn + "----");

                if (turn++ % 2 != 0) {
                    isFightEnded = makeHit(monster, hero, fightCallback);
                } else {
                    isFightEnded = makeHit(hero, monster, fightCallback);
                }
                try {


                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    private Boolean makeHit(FantasyCharacter defender, FantasyCharacter attacker, Realm.FightCallback fightCallback) {

        int hit = attacker.attack();

        int defenderHealth = defender.getHealthPoints() - hit;

        if (hit != 0) {
            System.out.println(String.format("%s Nanes udar v %d edinic!", attacker.getName(), hit));
            System.out.println(String.format("U %s ostalos %d edinic zdorovya...", defender.getName(), defenderHealth));
        } else {

            System.out.println(String.format("%s promahnulsya!", attacker.getName()));
        }
        if (defenderHealth <= 0 && defender instanceof Hero) {

            System.out.println("vi pali v boyu...");

            fightCallback.fightLost();
            return true;
        } else if(defenderHealth <= 0) {

            System.out.println(String.format("Vrag poverzhen! Vi poluchaete %d opit i %d zoloto", defender.getXp(), defender.getGold()));
            attacker.setXp(attacker.getXp() + defender.getXp());
            attacker.setGold(attacker.getGold() + defender.getGold());

            fightCallback.fightWin();
            return true;
        } else {

            defender.setHealthPoints(defenderHealth);
            return false;
        }
    }

}