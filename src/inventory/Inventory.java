package inventory;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Inventory implements Serializable {

    private final ArrayList<ItemStack> content;
    private int size;

    public Inventory(int size) {
        size = this.size;
        content = new ArrayList<>();
        content.ensureCapacity(size);
        for (int i = 0; i < size; i++) content.set(i, new ItemStack(Material.NONE));
    }

    public void add(@NotNull ItemStack item) {
        if (content.size() >= this.size) {
            throw new IllegalStateException();
        }
        content.add(item);
    }

    public void set(int index, @NotNull ItemStack item) {
        content.set(index, item);
    }

    public void remove(int index) {
        content.set(index, new ItemStack(Material.NONE));
    }

    public void fill(@NotNull ItemStack item) {
        for (int i = 0; i < this.size; i++) content.set(i, item);
    }

    public void empty() {
        fill(new ItemStack(Material.NONE));
    }

    public ArrayList<ItemStack> content() {
        return content;
    }

}
