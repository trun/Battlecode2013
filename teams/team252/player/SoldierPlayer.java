package team252.player;

import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import team252.navigation.Navigation;

public class SoldierPlayer extends BasePlayer {
    private final Navigation nav;

    private State state;
    private MapLocation enemyHQ;

    private static enum State {
        INIT,
        MINING,
        ATTACK,
    }

    public SoldierPlayer(RobotController rc) {
        super(rc);
        nav = new Navigation(rc);
        state = State.INIT;
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            try {
                rc.setIndicatorString(0, String.valueOf(rc.roundsUntilActive()));
                if (rc.isActive()) {
                    if (state == State.INIT) {
                        enemyHQ = rc.senseEnemyHQLocation();
                        state = State.ATTACK;
                    } else if (state == State.ATTACK) {
                        nav.bugTowards(enemyHQ);
                    }
                }
                rc.yield();
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }
    }
}
