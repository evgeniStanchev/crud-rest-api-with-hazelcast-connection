package entities;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "players")
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String id = UUID.randomUUID().toString();

    private volatile String name;

    private volatile Integer age;

    private volatile String position;
//invariants 
    private final AtomicInteger updates;

    public Player(final String name, final int age, final String position) {
	this.name = name;
	this.age = age;
	this.position = position;
	this.updates = new AtomicInteger(0);
    }

    public int getUpdateCount() {
	return updates.get();
    }

    public void incrementAndGet() {
	updates.incrementAndGet();
    }

    public String getId() {
	return this.id;
    }

    public String getName() {
	return this.name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setAge(int age) {
	this.age = age;
    }

    public void setPosition(String position) {
	this.position = position;
    }

    public String getPosition() {
	return this.position;
    }

    public int getAge() {
	return this.age;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((age == null) ? 0 : age.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((position == null) ? 0 : position.hashCode());
	result = prime * result + ((updates == null) ? 0 : updates.get());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null)
	    return false;
	if (!(obj instanceof Player)) {
	    return false;
	}
	final Player other = (Player) obj;
	if (age == null) {
	    if (other.age != null) {
		return false;
	    }
	} else if (age != other.age) {
	    return false;
	}
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id)) {
	    return false;
	}
	if (name == null) {
	    if (other.name != null) {
		return false;
	    }
	} else if (!name.equals(other.name)) {
	    return false;
	}
	if (position == null) {
	    if (other.position != null) {
		return false;
	    }
	} else if (!position.equals(other.position)) {
	    return false;
	}
	if (updates == null) {
	    if (other.updates != null) {
		return false;
	    }
	} else if (updates.get() != other.updates.get()) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "Player [id=" + id + ", name=" + name + ", age=" + age + ", position=" + position + ", updates="
		+ updates + "]";
    }
}
