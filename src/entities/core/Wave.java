package entities.core;

import entities.units.Unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Wave {

    private final ArrayList<HashMap<Unit, Integer>> ledger;
    private final int delay;
    private final int spread;
    private final int duration;

    public Wave(ArrayList<HashMap<Unit, Integer>> ledger, int delay, int spread, int duration) {
        this.ledger = ledger;
        this.delay = delay;
        this.spread = spread;
        this.duration = duration;
    }

    public ArrayList<HashMap<Unit, Integer>> getLedger() {
        return ledger;
    }

    public int getDelay() {
        return delay;
    }

    public int getSpread() {
        return spread;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Wave{" +
                "ledger=" + ledger +
                ", delay=" + delay +
                ", spread=" + spread +
                ", duration=" + duration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Wave)) return false;
        Wave wave = (Wave) o;
        return delay == wave.delay && spread == wave.spread && duration == wave.duration && Objects.equals(ledger, wave.ledger);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ledger, delay, spread, duration);
    }

}
