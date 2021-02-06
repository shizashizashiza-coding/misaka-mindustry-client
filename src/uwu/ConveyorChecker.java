package uwu;

import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.Block;
import mindustry.world.Tile;

public class ConveyorChecker {
    public ConveyorChecker() {
        BaseDialog d = new BaseDialog("Conveyor shiza");
        d.add(findFakeConveyor());
        d.button("Close", () -> d.hide());
        d.show();
    }

    public String findFakeConveyor() {
        StringBuilder b = new StringBuilder();
        for (int x = 0; x < Vars.world.width(); x++) {
            for (int y = 0; y < Vars.world.height(); y++) {
                Tile t = Vars.world.tile(x, y);
                if (t.block() != null && isConveyor(t.block())) {
                    int tgt = t.build.rotation;
                    int lineChecker = 0;
                    if (x - 1 > 0 && Vars.world.tile(x - 1, y).block() != null && isConveyor(Vars.world.tile(x - 1, y).block()) && rotationChecker(Vars.world.tile(x - 1, y).build.rotation, tgt, -1, 0)) {
                        lineChecker++;
                    }
                    if (x + 1 < Vars.world.width() && Vars.world.tile(x + 1, y).block() != null && isConveyor(Vars.world.tile(x + 1, y).block()) && rotationChecker(Vars.world.tile(x + 1, y).build.rotation, tgt, 1, 0)) {
                        lineChecker++;
                    }
                    if (y - 1 > 0 && Vars.world.tile(x, y - 1).block() != null && isConveyor(Vars.world.tile(x, y - 1).block()) && rotationChecker(Vars.world.tile(x, y - 1).build.rotation, tgt, 0, -1)) {
                        lineChecker++;
                    }
                    if (y + 1 < Vars.world.height() && Vars.world.tile(x, y + 1).block() != null && isConveyor(Vars.world.tile(x, y + 1).block()) && rotationChecker(Vars.world.tile(x, y + 1).build.rotation, tgt, 0, 1)) {
                        lineChecker++;
                    }
                    if (lineChecker < 2) {
                        b.append("at " + x + " " + y + "\n");
                    }
                }
            }

        }
        return b.toString();
    }

    public boolean rotationChecker(int a, int b, int dx, int dy) {
        if (a == b) {
            return true;
        }
        if (a == 0 && dy == 1 && ((a + 1 == b || b == 3))) {
            return true;
        }
        if (a == 1 && dx == 1 && ((a + 1 == b || a == b + 1))) {
            return true;
        }
        if (a == 2 && dy == -1 && ((a + 1 == b || a == b + 1))) {
            return true;
        }
        return a == 3 && dx == -1 && (b == 0 || a == b + 1);
    }

    public boolean isConveyor(Block b) {
        return b == Blocks.conveyor || b == Blocks.titaniumConveyor || b == Blocks.armoredConveyor || b == Blocks.plastaniumConveyor;
    }
}