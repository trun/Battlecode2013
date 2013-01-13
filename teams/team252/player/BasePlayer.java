package team252.player;

import battlecode.common.RobotController;

public class BasePlayer {
    protected final RobotController rc;

    public BasePlayer(RobotController rc) {
        this.rc = rc;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            rc.yield();
        }
    }
}
