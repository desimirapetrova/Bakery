package bakery.entities.tables.interfaces;

import bakery.entities.bakedFoods.interfaces.BakedFood;
import bakery.entities.drinks.interfaces.Drink;
import bakery.entities.tables.interfaces.Table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static bakery.common.ExceptionMessages.INVALID_NUMBER_OF_PEOPLE;
import static bakery.common.ExceptionMessages.INVALID_TABLE_CAPACITY;

public abstract class BaseTable implements Table {
   private Collection<BakedFood> foodOrders;
    private Collection<Drink>drinkOrders;
    private int tableNumber;
    private int capacity;
    private int numberOfPeople;
    private  double pricePerPerson;
    private boolean isReserved;
    private double price;

    protected BaseTable(int tableNumber,int capacity,double pricePerPerson){
        this.foodOrders=new ArrayList<>();
        this.drinkOrders=new ArrayList<>();
        this.setTableNumber(tableNumber);
        this.setCapacity(capacity);
        this.setPricePerPerson(pricePerPerson);
    }
    @Override
    public int getTableNumber() {
        return this.tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    public void setCapacity(int capacity) {
        if(capacity<0){
            throw new IllegalArgumentException(INVALID_TABLE_CAPACITY);
        }
        this.capacity = capacity;
    }

    @Override
    public int getNumberOfPeople() {
        return this.numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        if(numberOfPeople<=0){
            throw new IllegalArgumentException(INVALID_NUMBER_OF_PEOPLE);
        }
        this.numberOfPeople = numberOfPeople;
    }

    @Override
    public double getPricePerPerson() {
        return this.pricePerPerson;
    }

    public void setPricePerPerson(double pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    @Override
    public boolean isReserved() {
        return this.isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    @Override
    public double getPrice() {
       // this.price+= this.getPricePerPerson();
        return this.price;
    }
    public void setPrice(double price) {
        this.price+= this.getPricePerPerson();
    }

    @Override
    public void reserve(int numberOfPeople) {
        this.setNumberOfPeople(numberOfPeople);
        this.isReserved=true;
    }

    @Override
    public void orderFood(BakedFood food) {
        this.foodOrders.add(food);
    }

    @Override
    public void orderDrink(Drink drink) {
        this.drinkOrders.add(drink);
    }

    @Override
    public double getBill() {
        double sum=0;
        for (BakedFood foodOrder : foodOrders) {
            sum+=foodOrder.getPrice();
        }
        for (Drink drinkOrder : drinkOrders) {
            sum+=drinkOrder.getPrice();
        }
        sum+=this.getNumberOfPeople()*this.getPricePerPerson();
        return sum;
    }

    @Override
    public void clear() {
        this.drinkOrders=new ArrayList<>();
        this.foodOrders=new ArrayList<>();
        this.numberOfPeople=0;
        this.price=0;
        isReserved=false;
    }

    @Override
    public String getFreeTableInfo() {
        StringBuilder sb=new StringBuilder();
        sb.append(String.format("Table: %d",this.getTableNumber()));
        sb.append(System.lineSeparator());
        sb.append(String.format("Type: %s",this.getClass().getSimpleName()));
        sb.append(System.lineSeparator());
        sb.append(String.format("Capacity: %d",this.getCapacity()));
        sb.append(System.lineSeparator());
        sb.append(String.format("Price per Person: %.2f",this.getPricePerPerson()));
        return sb.toString().trim();
    }
}
