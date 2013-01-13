package team252.navigation;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Navigation {
    protected final RobotController rc;

    private MapLocation goal = null;
    private boolean tracing = false;
    private boolean traceRight = false;
    private MapLocation traceNode = null;
    private int roundsTracing = 0;

    private Set<MapLocation> enemyMines;

    public Navigation(RobotController rc) {
        this.rc = rc;
        initMines();
    }

    private void initMines() {
        enemyMines = new HashSet<MapLocation>(Arrays.asList(rc.senseNonAlliedMineLocations(rc.getLocation(), Integer.MAX_VALUE)));
    }

    public void moveTowards(MapLocation loc) throws GameActionException {
        rc.move(rc.getLocation().directionTo(loc));
    }

    public void bugInDirection(Direction dir) throws GameActionException {
        if (dir.equals(Direction.NONE)) return;
        if (tracing) {
            if (canMove(dir)
                    && rc.getLocation().distanceSquaredTo(goal) < traceNode.distanceSquaredTo(goal)
                    || roundsTracing > 20) {
                tracing = false;
                roundsTracing = 0;
                goal = null;
                rc.move(dir);
            } else {
                int turns = 0;
                while(canMove(dir) && turns < 8) {
                    dir = (traceRight) ? dir.rotateLeft() : dir.rotateRight();
                    turns++;
                }
                turns = 0;
                while(!canMove(dir) && turns < 8) {
                    dir = (traceRight) ? dir.rotateRight() : dir.rotateLeft();
                    turns++;
                }
                rc.move(dir);
            }
            roundsTracing++;
        } else {
            Direction nextDir = deadReckon(dir);
            if (canMove(nextDir)) {
                rc.move(nextDir);
            } else {
                traceRight = shouldTraceRight(nextDir);
                tracing = true;
                goal = rc.getLocation().add(dir, 8);
                traceNode = rc.getLocation();
            }
        }
    }

    public void bugTowards(MapLocation loc) throws GameActionException {
        bugInDirection(rc.getLocation().directionTo(loc));
    }

    protected Direction deadReckon(Direction dir) {
        if (canMove(dir)) {
            return dir;
        } else if (canMove(dir.rotateRight())) {
            return dir.rotateRight();
        } else if (canMove(dir.rotateLeft())) {
            return dir.rotateLeft();
        }
        return dir;
    }

    protected boolean canMove(Direction dir) {
        return rc.canMove(dir)
                && !enemyMines.contains(rc.getLocation().add(dir));
    }

    private boolean shouldTraceRight(Direction dir) {
        Direction rightDir = dir.rotateRight();
        Direction leftDir = dir.rotateLeft();
        int traceRightNum = 0;
        int traceLeftNum = 0;
        while (!canMove(rightDir)) {
            traceRightNum++;
            rightDir = rightDir.rotateRight();
        }
        while (!canMove(leftDir)) {
            traceLeftNum++;
            leftDir = leftDir.rotateLeft();
        }
        return traceRightNum <= traceLeftNum;
    }

}
