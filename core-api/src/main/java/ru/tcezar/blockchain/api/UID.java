package ru.tcezar.blockchain.api;

import java.io.Serializable;

public class UID implements Serializable {
    private static final long serialVersionUID = 7114619883328691363L;
    public final int id;
    public final String addr;

    public UID(int id, String addr) {
        this.id = id;
        this.addr = addr;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof UID) {
            String checkIP = System.getProperty("checkIP");
            if (Boolean.valueOf(checkIP)) {
                return ((UID) obj).id == this.id
                        && ((UID) obj).addr.equals(this.addr);
            }
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
