package team252.player;

import battlecode.common.RobotController;

public class HQPlayer extends BasePlayer {

    public HQPlayer(RobotController rc) {
        super(rc);
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        while (true) {
            rc.yield(); // TODO
        }
    }

}
