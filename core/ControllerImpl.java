package bakery.core;

import bakery.core.interfaces.Controller;
import bakery.entities.bakedFoods.Bread;
import bakery.entities.bakedFoods.Cake;
import bakery.entities.bakedFoods.interfaces.BakedFood;
import bakery.entities.drinks.Tea;
import bakery.entities.drinks.Water;
import bakery.entities.drinks.interfaces.Drink;
import bakery.entities.tables.InsideTable;
import bakery.entities.tables.OutsideTable;
import bakery.entities.tables.interfaces.Table;
import bakery.repositories.TableRepositoryImpl;
import bakery.repositories.interfaces.*;

import java.util.Collection;

import static bakery.common.ExceptionMessages.FOOD_OR_DRINK_EXIST;
import static bakery.common.ExceptionMessages.TABLE_EXIST;
import static bakery.common.OutputMessages.*;

public class ControllerImpl implements Controller {
    private FoodRepository<BakedFood> foodRepository;
    private DrinkRepository<Drink> drinkRepository;
    private TableRepository<Table> tableRepository;
    private double currentBill;

    public ControllerImpl(FoodRepository<BakedFood> foodRepository, DrinkRepository<Drink> drinkRepository, TableRepository<Table> tableRepository) {
        this.foodRepository = foodRepository;
        this.drinkRepository = drinkRepository;
        this.tableRepository = tableRepository;
    }


    @Override
    public String addFood(String type, String name, double price) {
        if (this.foodRepository.getByName(name) != null) {
            throw new IllegalArgumentException(String.format(FOOD_OR_DRINK_EXIST, type, name));
        }
        //FoodRepository foodRepository;
        if (type.equals("Bread")) {
            this.foodRepository.add(new Bread(name, price));
        } else if (type.equals("Cake")) {
            this.foodRepository.add(new Cake(name, price));
        }

        return String.format(FOOD_ADDED, name, type);
    }

    @Override
    public String addDrink(String type, String name, int portion, String brand) {
        if (this.drinkRepository.getByNameAndBrand(name, brand) != null) {
            throw new IllegalArgumentException(String.format(FOOD_OR_DRINK_EXIST, type, name));
        }
        if (type.equals("Tea")) {
            this.drinkRepository.add(new Tea(name, portion, brand));
        } else if (type.equals("Water")) {
            this.drinkRepository.add(new Water(name, portion, brand));
        }
        return String.format(DRINK_ADDED, name, brand);
    }

    @Override
    public String addTable(String type, int tableNumber, int capacity) {
        if (this.tableRepository.getByNumber(tableNumber)!= null) {
            throw new IllegalArgumentException(String.format(TABLE_EXIST, tableNumber));
        }
        if (type.equals("InsideTable")) {
            this.tableRepository.add(new InsideTable(tableNumber, capacity));
        } else if (type.equals("OutsideTable")) {
            this.tableRepository.add(new OutsideTable(tableNumber, capacity));
        }
        return String.format(String.format(TABLE_ADDED, tableNumber));
    }

    @Override
    public String reserveTable(int numberOfPeople) {
        for (Table table : this.tableRepository.getAll()) {
            if (!table.isReserved()) {
                if (table.getCapacity() >= numberOfPeople) {
                    table.reserve(numberOfPeople);
                    return String.format(TABLE_RESERVED, table.getTableNumber(), numberOfPeople);
                }
            }
        }
        return String.format(RESERVATION_NOT_POSSIBLE, numberOfPeople);
    }

    @Override
    public String orderFood(int tableNumber, String foodName) {
            if (this.tableRepository.getByNumber(tableNumber)==null) {
                return String.format(WRONG_TABLE_NUMBER, tableNumber);
            }
            Table table=this.tableRepository.getByNumber(tableNumber);
            BakedFood food=this.foodRepository.getByName(foodName);
            if (!this.foodRepository.getAll().contains(food)) {
                return String.format(NONE_EXISTENT_FOOD, foodName);
            }
           tableRepository.getByNumber(tableNumber).orderFood(food);
        return String.format(FOOD_ORDER_SUCCESSFUL, tableNumber, foodName);

    }

    @Override
    public String orderDrink(int tableNumber, String drinkName, String drinkBrand) {
        if (this.tableRepository.getByNumber(tableNumber)==null) {
            return String.format(WRONG_TABLE_NUMBER, tableNumber);
        }
        Drink drink=this.drinkRepository.getByNameAndBrand(drinkName,drinkBrand);
        if(!this.drinkRepository.getAll().contains(drink)){
            return String.format(NON_EXISTENT_DRINK,drinkName,drinkBrand);
        }
        tableRepository.getByNumber(tableNumber).orderDrink(drinkRepository.getByNameAndBrand(drinkName,drinkBrand));
        return String.format(DRINK_ORDER_SUCCESSFUL,tableNumber,drinkName,drinkBrand);

    }

    @Override
    public String leaveTable(int tableNumber) {
        double bill=this.tableRepository.getByNumber(tableNumber).getBill();
        this.tableRepository.getByNumber(tableNumber).clear();
        currentBill+=bill;
        return String.format(BILL,tableNumber,bill);
    }

    @Override
    public String getFreeTablesInfo() {
        for (Table table : tableRepository.getAll()) {
           if(!table.isReserved()){
               return table.getFreeTableInfo();
           }
        }
        return null;
    }

    @Override
    public String getTotalIncome() {
        return String.format(TOTAL_INCOME,currentBill);
    }
}
