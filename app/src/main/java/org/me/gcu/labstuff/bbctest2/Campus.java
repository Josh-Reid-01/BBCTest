package org.me.gcu.labstuff.bbctest2;

import java.util.Objects;

public class Campus {
    private int id;
    private String name;
    private String location;

    public Campus(int id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Campus campus = (Campus) o;
        return id == campus.id && Objects.equals(name, campus.name) && Objects.equals(location, campus.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, location);
    }

    /**
     * We simply want name displayed in the spinner
     * @return
     */
    @Override
    public String toString() {
        return name ;
    }
}
