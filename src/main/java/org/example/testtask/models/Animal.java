package org.example.testtask.models;



public class Animal {

    private String name;
    private String type;
    private String sex;
    private int weight;
    private int cost;
    private String category;

    public Animal() {
    }

    public Animal(String name, String type, String sex, int weight, int cost, String category) {
        this.name = name;
        this.type = type;
        this.sex = sex;
        this.weight = weight;
        this.cost = cost;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getCategory() {
        int cost = getCost();

        if (cost <= 20) {
            return "1st";
        } else if (cost <= 40) {
            return "2nd";
        } else if (cost <= 60) {
            return "3rd";
        } else {
            return "4th";
        }
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", sex='" + sex + '\'' +
                ", weight=" + weight +
                ", cost=" + cost +
                ", category='" + category + '\'' +
                '}';
    }
}
