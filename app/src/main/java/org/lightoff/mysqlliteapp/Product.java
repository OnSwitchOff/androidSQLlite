package org.lightoff.mysqlliteapp;

public class Product {
    private int id;
    private String name;
    private int count;
    private String unit;
    private int pic;

    Product(int _id,String name,int _count, String unit, int _pic){
        this.id=_id;
        this.name = name;
        this.count=_count;
        this.unit = unit;
        this.pic =_pic;
    }

    public int getId() {
        return id;
    }

    public int getPic() {
        return pic;
    }
    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getUnit() {
        return this.unit;
    }
    public void setUnit(String _unit){
        this.unit=_unit;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public int getCount() {
        return count;
    }
    public void addCount(){ this.count++;}
    public void decCount() {
        if(this.count>0)
            this.count--;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    @Override
    public String toString() {
        return this.name + " : " + this.id;
    }
}
