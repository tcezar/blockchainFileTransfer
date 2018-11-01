package ru.tcezar.blockchain.api;

public class UID{
    public final int id;
    public final String addr;

    public UID(int id, String addr) {
        this.id = id;
        this.addr = addr;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof UID) {
            return ((UID) obj).id == this.id;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UID{" +
                "id=" + id +
                ", addr='" + addr + '\'' +
                '}';
    }

}
