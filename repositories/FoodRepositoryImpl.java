package bakery.repositories;

import bakery.entities.bakedFoods.BaseFood;
import bakery.entities.bakedFoods.interfaces.BakedFood;
import bakery.repositories.interfaces.FoodRepository;

import java.util.*;

public class FoodRepositoryImpl  implements FoodRepository<BakedFood> {
    private Set<BakedFood> models;

    public FoodRepositoryImpl(){
        this.models=new LinkedHashSet<>();
    }
    @Override
    public BakedFood getByName(String name) {
        for (BakedFood model : models) {
            if(model.getName().equalsIgnoreCase(name)){
                return model;
            }
        }
        return null;
    }

    @Override
    public Collection<BakedFood> getAll() {
        return Collections.unmodifiableCollection(this.models);
    }

    @Override
    public void add(BakedFood bakedFood) {
        this.models.add(bakedFood);
    }
}
