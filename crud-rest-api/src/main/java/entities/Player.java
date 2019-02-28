package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "players")
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "position")
    private String position;

    public Player(final String name, final int age, final String position) {
	this.name = name;
	this.age = age;
	this.position = position;
    }

    public Player() {

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
	result = prime * result + age;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((position == null) ? 0 : position.hashCode());
	return result;
    }

    @Override
    public boolean equals(final Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (!(obj instanceof Player))
	    return false;
	Player other = (Player) obj;
	if (age != other.age)
	    return false;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (position == null) {
	    if (other.position != null)
		return false;
	} else if (!position.equals(other.position))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Player [id=" + id + ", name=" + name + ", age=" + age + ", position=" + position + "]";
    }

}
