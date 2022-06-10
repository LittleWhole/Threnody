package graphics.ui.menu;

import core.Main;
import graphics.ui.Button;
import graphics.ui.UserInterfaceable;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import util.Commandable;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class SavegameList implements UserInterfaceable
{
    private List<String> saves;
    private List<Button> buttons;

    public SavegameList(List<String> saves) {
        this.saves = saves;
        buttons = new java.util.ArrayList<>();
        for (int i = 0; i < saves.size(); i++) {
            int finalI = i;
            var date = "date";
            try {
                date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").parse(saves.get(i)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            buttons.add(new Button(Main.RESOLUTION_X / 2, (Main.RESOLUTION_Y - 800) + (70 * i), date, () -> {
                Main.menus.clear();
                Main.load(saves.get(finalI));
            }));

        }
    }

    @Override
    public void render(Graphics g, int mouseX, int mouseY) {
        buttons.forEach(button -> button.render(g, mouseX, mouseY));
    }

    @Override
    public void update(GameContainer gc) {
        buttons.forEach(button -> button.update(gc));
    }
}
