package managers;

import combat.artes.Arte;
import combat.artes.support.Mana;
import core.Main;
import entities.units.enemy.Enemy;
import entities.units.Unit;
import entities.units.player.Player;
import graphics.ui.menu.CloseButton;
import graphics.ui.menu.DialogBox;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import util.DrawUtilities;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class CombatManager {

    private final int queueOffset = 80;
    private final List<Player> players;
    private final List<Enemy> enemies;
    private int round;
    private int plrTurn;
    private int enemyTurn;

    private Iterator selectedItr;

    public enum CombatState {WIN, LOSE, ADVANCE, HALT}

    public CombatManager(List<Player> plrs, List<Enemy> enemies) {
        this.players = plrs;
        this.enemies = enemies;
        round = 1;
    }

    public void roundStart() {
        plrTurn = 0;
        enemyTurn = 0;
        players.forEach(p -> {
            p.setState(Player.PlayerState.SELECTING);
            p.setMana(p.getTurnMana() + p.getManaAdd());
            p.setManaAdd(0);
            p.setQueuedManaRemoval(0);
            p.setQueuedManaExtra(0);
        });
        enemies.forEach(e -> e.setCombatState(Enemy.EnemyState.CHOOSING));
    }

    public CombatState combat(Graphics g, GameContainer gc) throws InterruptedException, SlickException {
        boolean plrsAlive = true;
        boolean enemiesAlive = true;
        Stack<Arte> stack = new Stack<>();
        Queue<Arte> reversed = new ConcurrentLinkedQueue<>();
        if (plrTurn < players.size()) {
            if (enemies.size() == 0) {
                return CombatState.WIN;
            }
            if (players.get(plrTurn).getState() == Player.PlayerState.SELECTING) {
                players.get(plrTurn).move(gc, g);
                g.drawString("SELECTING", 100, 0);
                /*
                try { g.drawString("Cards selected: " + players.get(plrTurn).getArteQueue().stream().map(a -> ((Arte) a).getName())
                        .collect(Collectors.joining(", ")), 500, 500); } catch (Exception ignored) {};
                */


                selectedItr = players.get(plrTurn).getArteQueue().iterator();
                var mouseX = gc.getInput().getMouseX();
                var mouseY = gc.getInput().getMouseY();
                int i = 0;
                //while (selectedItr.hasNext()) {
                    //DrawUtilities.drawImageCentered(g, ((Arte) selectedItr.next()).getCard(), 300 , 300 - i*queueOffset);
                    while (selectedItr.hasNext()) {
                        var arte = (Arte<?>) selectedItr.next();
                        var card = arte.getCard();
                        var cardX = 300;
                        var cardY = 300 + i * queueOffset;
                        if ((mouseX > cardX - card.getWidth() / 2 && mouseX < cardX + card.getWidth() / 2) &&
                                (mouseY > cardY - card.getHeight() / 2 && mouseY < cardY + card.getHeight() / 2 - (selectedItr.hasNext()?card.getHeight()-queueOffset:0))) {
                            card.getScaledCopy(1.3f).drawCentered(300, 300 + i * queueOffset);
                            if (gc.getInput().isMousePressed(0)) {
                                if (arte instanceof Mana &&
                                        players.get(plrTurn).getQueuedManaRemoval() > players.get(plrTurn).getMana() + players.get(plrTurn).getQueuedManaExtra() - 2) {
                                    Main.menus.add(new DialogBox(700, 400, "Cannot unqueue", "Mana can't be unqueued as the queued\ncards exceed the mana capacity.\nPlease unqueue other cards to unqueue Mana.", new CloseButton("Got it")));
                                } else {
                                    arte.unqueue();
                                    players.get(plrTurn).addQueuedManaRemoval(-arte.getCost());
                                    players.get(plrTurn).getArteQueue().remove(arte);
                                    players.get(plrTurn).getArteHand().add(arte);
                                }
                            }
                        } else card.drawCentered(300, 300 + i * queueOffset);
                        i++;
                    }
                //}
            }
                if (players.get(plrTurn).getState() == Player.PlayerState.CASTING) {
                    players.get(plrTurn).attack(enemies.get(0), gc);
                    g.drawString("CASTING", 100, 0);

                    for (Object a : players.get(plrTurn).getArteQueue()) {
                        stack.add((Arte) a);
                    }
                    for (int i = stack.size() - 1; i > -1; i--) {
                        reversed.add(stack.get(i));
                    }
                    selectedItr = reversed.iterator();
                    int i = 0;
                    while (selectedItr.hasNext()) {
                        DrawUtilities.drawImageCentered(g, ((Arte) selectedItr.next()).getCard(), 300, 300 + i * queueOffset);
                        i++;
                    }
                }
                if ((players.get(plrTurn)).getState() == Player.PlayerState.DONE) {
                    updateTeams(enemies);
                    if (enemies.size() == 0) {
                        return CombatState.WIN;
                    }
                    plrTurn++;
                }
            } else if (plrTurn >= players.size() && enemyTurn < enemies.size()) {
                if (players.size() == 0) {
                    return CombatState.LOSE;
                }
                if ((enemies.get(enemyTurn - (players.size() - 1))).getCombatState() == Enemy.EnemyState.CHOOSING) {
                    (enemies.get(enemyTurn - (players.size() - 1))).battleSelect();
                }
                if ((enemies.get(enemyTurn - (players.size() - 1))).getCombatState() == Enemy.EnemyState.MOVING) {
                    (enemies.get(enemyTurn - (players.size() - 1))).battleMove(players.get(0), gc);
                    DrawUtilities.drawStringCentered(g, "MOVING", 800, 100);
                }
                if ((enemies.get(enemyTurn - (players.size() - 1))).getCombatState() == Enemy.EnemyState.DONE) {
                    updateTeams(players);
                    if (players.size() == 0) {
                        return CombatState.LOSE;
                    }
                    enemyTurn++;
                }
            } else {
                updateTeams(enemies);
                updateTeams(players);
                round++;
                roundStart();
                return CombatState.ADVANCE;
            }

            return CombatState.HALT;
        }

        public int getRound() {
            return round;
        }

        private void updateTeams(List < ? extends Unit > units){
            for (int i = 0; i < units.size(); i++) {
                if (units.get(i).getHealth() <= 0) {
                    units.remove(i);
                }
            }
//        units.forEach(u -> {
//            u.setMana(u.getTurnMana() + u.getManaAdd());
//            if (u.getHealth() <= 0) units.remove(u);
//        });
        }

        public List<Player> getPlayers() {
            return players;
        }

        public List<Enemy> getEnemies() {
            return enemies;
        }

        public int getPlrTurn() {
            return plrTurn;
        }
        public int getEnemyTurn() {
            return enemyTurn;
        }

    }
