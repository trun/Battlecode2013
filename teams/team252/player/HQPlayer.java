package team252.player;

import battlecode.common.Direction;
import battlecode.common.RobotController;

public class HQPlayer extends BasePlayer {

    public HQPlayer(RobotController rc) {
        super(rc);
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            try {
                if (rc.isActive()) {
                    Direction dir = rc.getLocation().directionTo(rc.senseEnemyHQLocation());
                    if (rc.canMove(dir)) {
                        rc.spawn(dir);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }
    }

}
