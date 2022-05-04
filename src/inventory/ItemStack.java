package inventory;

import java.io.Serializable;
import java.util.Objects;

public class ItemStack implements Cloneable, Serializable {
    private Material type;
    private int amount;
    private byte data;

    public ItemStack(Material type) {
        this.type = type;
        this.amount = 1;
        this.data = 0x0b;
    }

    public ItemStack(Material type, int amount) {
        this.type = type;
        this.amount = amount;
        this.data = 0x0b;
    }

    public ItemStack(Material type, int amount, byte data) {
        this.type = type;
        this.amount = amount;
        this.data = data;
    }

    public int getAmount() {
        return amount;
    }

    public byte getData() {
        return data;
    }

    public void setData(byte data) {
        this.data = data;
    }

    public Material getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemStack)) return false;
        ItemStack itemStack = (ItemStack) o;
        return amount == itemStack.amount && data == itemStack.data && type == itemStack.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, amount, data);
    }
}
