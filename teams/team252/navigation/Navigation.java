package team252.navigation;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

public class Navigation {
    protected final RobotController rc;

    public Navigation(RobotController rc) {
        this.rc = rc;
    }

    public void moveTowards(MapLocation loc) throws GameActionException {
        rc.move(rc.getLocation().directionTo(loc));
    }
}
