package core;

import combat.artes.Arte;
import combat.artes.elemental.AquaLimit;
import combat.artes.elemental.DualTheSol;
import combat.artes.elemental.Flamberge;
import combat.artes.elemental.RendingGale;
import combat.artes.mystic.*;
import combat.artes.strike.DragonFang;
import combat.artes.strike.ImpactCross;
import combat.artes.strike.SonicSlash;
import combat.artes.strike.TwinWhip;
import combat.artes.support.Elixir;
import combat.artes.support.Heal;
import combat.artes.support.Mana;
import entities.units.Unit;
import entities.units.player.Player;
import gamestates.*;
import graphics.ui.Button;
import graphics.ui.Displayable;
import graphics.ui.menu.CloseButton;
import graphics.ui.menu.DialogBox;
import graphics.ui.menu.LoadGameMenu;
import graphics.ui.menu.Menu;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import playerdata.PlayerInventory;
import playerdata.PlayerStats;
import playerdata.characters.Phaedra;
import playerdata.characters.PlayableCharacter;
import playerdata.characters.Sigur;

import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

public class Main extends StateBasedGame {
    public static int RESOLUTION_X = 1920;
    public static int RESOLUTION_Y = 1080;
    public static final int FRAMES_PER_SECOND = 60;
    public static TrueTypeFont font;
    public static Fonts fonts;
    public static final Object LOCK = new Object();
    public static boolean paused = false;

    private static AppGameContainer appgc;
    public static final int FILLER_ID = -1;
    public static final int INTRO_ID = 0;
    public static final int LOADING_ID = 1;
    public static final int GAME_ID = 2;
    public static final int BATTLE_ID = 3;
    public static final int TITLE_ID = 999;
    public static LoadingScreen loading;
    public static IntroCredit intro;
    public static Game game;
    public static BattleState battle;
    public static TitleScreen title;
    public static Filler filler;
    public static boolean debug;
    public static boolean cheat = false;
    public static List<Arte<? extends Unit>> cheatDeck = new ArrayList<>();

    public static StateBasedGame sbg;

    public static PlayerStats stats = new PlayerStats();
    public static PlayerInventory inventory = new PlayerInventory();
    public static List<PlayableCharacter> characters;

    public static AbstractQueue<Displayable> displayables = new ConcurrentLinkedQueue<>();
    public static AbstractQueue<Menu> menus = new ConcurrentLinkedQueue<>();

    public Main(String name) throws SlickException {
        super(name);

        intro = new IntroCredit(INTRO_ID);
        loading = new LoadingScreen(LOADING_ID);
        battle = new BattleState(BATTLE_ID);
        game = new Game(GAME_ID);
        title = new TitleScreen(TITLE_ID);
        filler = new Filler(FILLER_ID);
    }

    public static int getScreenWidth() {
//        return appgc.getScreenWidth();
        return RESOLUTION_X;
    }

    public static int getScreenHeight() {
//        return appgc.getScreenHeight();
        return RESOLUTION_Y;
    }


    public void initStatesList(GameContainer gc) throws SlickException {
        addState(intro);
        addState(loading);
        addState(game);
        addState(battle);
        addState(title);
        addState(filler);
    }

    public static void main(String[] args) {
        try {
            characters = new ArrayList<>(){{
                add(new Sigur());
                add(new Phaedra());
            }};
            debug = false;
            appgc = new AppGameContainer(new Main("Threnody"));
            System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");

            RESOLUTION_X = getScreenWidth();
            RESOLUTION_Y = getScreenHeight();

            appgc.setDisplayMode(getScreenWidth(), getScreenHeight(), false);
            appgc.setTargetFrameRate(FRAMES_PER_SECOND);
            appgc.setVSync(true);
            appgc.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }

    public synchronized static void save() {
        System.out.println("Saving...");
        String dir = "saves/" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date());
        new File(dir).mkdir();
        try {
            new File(dir + "/stats.dat").createNewFile();
            new File(dir + "/inventory.dat").createNewFile();
            new File(dir + "/characters.dat").createNewFile();
        } catch (IOException e) { e.printStackTrace(); }
        try (FileOutputStream fileOut = new FileOutputStream(dir + "/stats.dat");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(stats);
            System.out.println("Saved stats");
        } catch (IOException e) { e.printStackTrace();}
        try (FileOutputStream fileOut = new FileOutputStream(dir + "/inventory.dat");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(inventory);
            System.out.println("Saved inventory");
        } catch (IOException e) { e.printStackTrace(); }
        try (FileOutputStream fileOut = new FileOutputStream(dir + "/characters.dat");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(characters);
            System.out.println("Saved characters");
        } catch (IOException e) { e.printStackTrace(); }
        addMenu(new DialogBox(700, 400, "Success", "Game saved.\nYou can now quit safely.", new CloseButton("Got it")));
    }

    public synchronized static void load(String save) {
        System.out.println("Loading...");
        try (FileInputStream fileIn = new FileInputStream("./saves/" + save + "/stats.dat");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            stats = (PlayerStats) in.readObject();
            System.out.println("Loaded stats");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (FileInputStream fileIn = new FileInputStream("./saves/" + save + "/inventory.dat");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            inventory = (PlayerInventory) in.readObject();
            System.out.println("Loaded inventory");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (FileInputStream fileIn = new FileInputStream("./saves/" + save + "/characters.dat");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            characters = (ArrayList<PlayableCharacter>) in.readObject();
            System.out.println("Loaded inventory");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        String date = "date";
        try {
            date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").parse(save));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        addMenu(new DialogBox(700, 400, "Loaded game", "Save from\n" + date + "\nsuccessfully loaded.", new CloseButton("Start Game", () -> sbg.enterState(GAME_ID, new FadeOutTransition(), new FadeInTransition()))));
    }

    public static synchronized void continueGame() {
        File file = new File("saves");
        List<String> directories = Arrays.asList(file.list((current, name) -> new File(current, name).isDirectory()));
        if (directories.isEmpty()) {
            System.out.println("No saves found");
            return;
        }
        directories.sort(Collections.reverseOrder());
        load(directories.get(0));
    }

    public static synchronized void openLoadSaveMenu() {
        File file = new File("saves");
        List<String> directories = Arrays.asList(Objects.requireNonNull(file.list((current, name) -> new File(current, name).isDirectory())));
        if (directories.isEmpty()) {
            System.out.println("No saves found");
            return;
        }
        directories.sort(Collections.reverseOrder());
        addMenu(new LoadGameMenu(directories));
    }

    public static synchronized void newGame() {
        stats = new PlayerStats();
        inventory = new PlayerInventory();
        characters = new ArrayList<>();
    }

    public static synchronized void quitGame() {
        System.exit(0);
    }

    public static synchronized void cheatOn() {
        cheat = true;
        if (Game.plrTeam != null && !Game.plrTeam.isEmpty()) {
            var p =  Game.plrTeam.get(0);
            Game.plrTeam.get(0).getArteDeck().clear();
            try {
                Game.plrTeam.get(0).addToDeck(new ImpactCross(p));
                Game.plrTeam.get(0).addToDeck(new AmongUs(p));
                Game.plrTeam.get(0).addToDeck(new Expiation(p));
                Game.plrTeam.get(0).addToDeck(new Elixir(p));
                Game.plrTeam.get(0).addToDeck(new DragonFang(p));
                Game.plrTeam.get(0).addToDeck(new RendingGale(p));
                Game.plrTeam.get(0).addToDeck(new AquaLimit(p));
                Game.plrTeam.get(0).addToDeck(new DivineConqueror(p));
                Game.plrTeam.get(0).addToDeck(new DualTheSol(p));
                Game.plrTeam.get(0).addToDeck(new Heal(p));
                Game.plrTeam.get(0).addToDeck(new Mana(p));
                Game.plrTeam.get(0).addToDeck(new SonicSlash(p));
                Game.plrTeam.get(0).addToDeck(new TwinWhip(p));
                Game.plrTeam.get(0).addToDeck(new Flamberge(p));
                Game.plrTeam.get(0).addToDeck(new TrillionDrive(p));
                Game.plrTeam.get(0).addToDeck(new InnumerableWounds(p));
                Game.plrTeam.get(0).addToDeck(new GardenOfInnocence(p));
            } catch (SlickException e) { e.printStackTrace(); }
        } else { try { Game.plrTeam = new ArrayList<>(){{ add(new Player()) }} } catch (SlickException e) { e.printStackTrace(); }
        //addMenu(new DialogBox(700, 400, "Cheat Mode On", "Turned on cheat mode.", new CloseButton("Got it")));
    }

    public static synchronized void constructCheatDeck(Player p) {
        var arteDeck = cheatDeck;
        try {
            for (int i = 0; i < 2; i++) {
                p.addToDeck(new ImpactCross(p));
                p.addToDeck(new AmongUs(p));
                p.addToDeck(new Expiation(p));
                p.addToDeck(new Elixir(p));
                p.addToDeck(new DragonFang(p));
                p.addToDeck(new RendingGale(p));
                p.addToDeck(new AquaLimit(p));
                p.addToDeck(new DivineConqueror(p));
                p.addToDeck(new DualTheSol(p));
                p.addToDeck(new Heal(p));
                p.addToDeck(new Mana(p));
                p.addToDeck(new SonicSlash(p));
                p.addToDeck(new TwinWhip(p));
                p.addToDeck(new Flamberge(p));
                p.addToDeck(new TrillionDrive(p));
                p.addToDeck(new InnumerableWounds(p));
                p.addToDeck(new GardenOfInnocence(p));
            }
        } catch (SlickException e) { e.printStackTrace(); }
    }


    public static synchronized void addDisplayable(Displayable displayable) {
        displayables.add(displayable);
    }

    public static synchronized void addMenu(Menu menu) {
        menus.add(menu);
    }
}