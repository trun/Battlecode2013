package team252;

import battlecode.common.RobotController;
import team252.player.BasePlayer;
import team252.player.HQPlayer;
import team252.player.SoldierPlayer;

public class RobotPlayer {

    public static void run(RobotController rc) {
        BasePlayer player;
        switch (rc.getType()) {
            case HQ:
                player = new HQPlayer(rc);
                break;
            case SOLDIER:
                player = new SoldierPlayer(rc);
                break;
            default:
                player = new BasePlayer(rc);
                break;
        }
        player.run();
    }

}
