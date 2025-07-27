package assignments.assignment05;

import javalib.funworld.*;
import javalib.worldimages.*;

import java.util.ArrayList;
import java.util.Random;

import tester.Tester;

import java.awt.*;

interface IFish {
    WorldImage draw();
}

abstract class AFish implements IFish {
    int posnX;
    int posnY;
    int size;
    static final int SCENE_MAX = 500;

    AFish(int posnX, int posnY, int size) {
        this.posnX = posnX;
        this.posnY = posnY;
        this.size = size;
    }

    abstract CircleImage getFish();

    public WorldImage draw() {
        return this.getFish();
    }

    boolean overlaps(AFish other) {
        int dx = this.posnX - other.posnX;
        int dy = this.posnY - other.posnY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        int thisRadius = this.size / 2;
        int otherRadius = other.size / 2;

        return distance < (thisRadius + otherRadius);
    }

    static int wrap(int n) {
        if (n > SCENE_MAX) {
            return 0;
        } else if (n < 0) {
            return SCENE_MAX;
        } else {
            return n;
        }
    }
}

class PlayerFish extends AFish {
    PlayerFish(int posnX, int posnY) {
        super(posnX, posnY, 30);
    }

    PlayerFish(int posnX, int posnY, int size) {
        super(posnX, posnY, size);
    }

    public CircleImage getFish() {
        return new CircleImage(this.size, OutlineMode.SOLID, Color.RED);
    }

    public PlayerFish move(String key) {
        int dx = 0, dy = 0;

        switch (key) {
            case "up":
                dy = -5;
                break;
            case "down":
                dy = 5;
                break;
            case "left":
                dx = -5;
                break;
            case "right":
                dx = 5;
                break;
            default:
                break;
        }

        int newX = PlayerFish.wrap(this.posnX + dx);
        int newY = PlayerFish.wrap(this.posnY + dy);

        return new PlayerFish(newX, newY);
    }

    PlayerFish grow(int amount) {
        return new PlayerFish(this.posnX, this.posnY, this.size + amount);
    }
}

class BackgroundFish extends AFish {
    boolean movesRight;
    int speed;
    Color color;

    BackgroundFish(int posnX, int posnY, int size, boolean movesRight, int speed, Color color) {
        super(posnX, posnY, size);
        this.movesRight = movesRight;
        this.speed = speed;
        this.color = color;
    }

    public CircleImage getFish() {
        return new CircleImage(this.size, OutlineMode.SOLID, this.color);
    }

    BackgroundFish move() {
        if (this.movesRight) {
            return new BackgroundFish(BackgroundFish.wrap(this.posnX + this.speed), this.posnY, this.size, this.movesRight, this.speed, this.color);
        } else {
            return new BackgroundFish(BackgroundFish.wrap(this.posnX - this.speed), this.posnY, this.size, this.movesRight, this.speed, this.color);
        }
    }
}

class FeedingFrenzy extends World {
    int tickCount;
    PlayerFish player;
    ArrayList<BackgroundFish> backgroundFishes;
    static final Random rand = new Random();

    FeedingFrenzy(PlayerFish player, ArrayList<BackgroundFish> backgroundFishes, int tickCount) {
        this.player = player;
        this.backgroundFishes = backgroundFishes;
        this.tickCount = tickCount;
    }

    FeedingFrenzy() {
        this(new PlayerFish(250, 250), generateRandomFishes(5), 0);
    }

    static ArrayList<BackgroundFish> generateRandomFishes(int count) {
        ArrayList<BackgroundFish> fishes = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int posnX = rand.nextInt(500);
            int posnY = rand.nextInt(500);
            int size = 10 + rand.nextInt(30);
            boolean movesRight = rand.nextBoolean();
            int speed = 1 + rand.nextInt(3);
            Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            fishes.add(new BackgroundFish(posnX, posnY, size, movesRight, speed, color));
        }

        return fishes;
    }

    public WorldScene makeScene() {
        WorldScene scene = new WorldScene(500, 500);

        for (BackgroundFish bgFish : this.backgroundFishes) {
            scene = scene.placeImageXY(bgFish.draw(), bgFish.posnX, bgFish.posnY);
        }

        scene = scene.placeImageXY(this.player.draw(), this.player.posnX, this.player.posnY);

        return scene;
    }

    public FeedingFrenzy onKeyEvent(String key) {
        PlayerFish moved = this.player.move(key);
        return new FeedingFrenzy(moved, this.backgroundFishes, this.tickCount);
    }

    public FeedingFrenzy onTick() {
        ArrayList<BackgroundFish> newFishList = new ArrayList<>();
        PlayerFish newPlayer = this.player;
        int newTick = this.tickCount + 1;

        addNewFish(newFishList, newTick);

        for (BackgroundFish bg : this.backgroundFishes) {
            BackgroundFish movedFish = bg.move();
            newPlayer = handleCollision(newPlayer, movedFish, newFishList);
        }
        return new FeedingFrenzy(this.player, newFishList, newTick);
    }

    public void addNewFish(ArrayList<BackgroundFish> newFishList, int tickCount) {
        if (tickCount % 15 == 0) {
            newFishList.addAll(generateRandomFishes(1));
        }
    }

    private PlayerFish handleCollision(PlayerFish player, BackgroundFish movedFish, ArrayList<BackgroundFish> newFishList) {
        if (player.overlaps(movedFish)) {
            if (player.size > movedFish.size) {
                return player.grow(movedFish.size / 4);
            } else {
                this.endOfWorld("GAME OVER. You were eaten.");
                return player;
            }
        } else {
            newFishList.add(movedFish);
        }
        return player;
    }
}

class ExamplesMyWorldProgram {
    void testBigBang(Tester t) {
        FeedingFrenzy f = new FeedingFrenzy();
        f.bigBang(500, 500, 0.1);
    }
}