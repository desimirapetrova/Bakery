package bakery.repositories;

import bakery.entities.drinks.interfaces.Drink;
import bakery.repositories.interfaces.DrinkRepository;

import java.util.*;

public class DrinkRepositoryImpl implements DrinkRepository<Drink> {
    private Set<Drink> models;
    public DrinkRepositoryImpl(){
        this.models=new LinkedHashSet<>();
    }
    @Override
    public Drink getByNameAndBrand(String drinkName, String drinkBrand) {
        for (Drink model : models) {
            if(model.getName().equalsIgnoreCase(drinkName)&&model.getBrand().equalsIgnoreCase(drinkBrand)){
                return model;
            }
        }
        return null;
    }

    @Override
    public Collection<Drink> getAll() {
        return Collections.unmodifiableCollection(this.models);
    }

    @Override
    public void add(Drink drink) {
        this.models.add(drink);
    }
}
