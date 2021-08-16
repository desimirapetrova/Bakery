package bakery.repositories;

import bakery.entities.tables.interfaces.Table;
import bakery.repositories.interfaces.TableRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class TableRepositoryImpl implements TableRepository<Table> {
    private Collection<Table>models;
    public TableRepositoryImpl(){
        this.models=new LinkedHashSet<>();
    }
    @Override
    public Table getByNumber(int number) {
        for (Table table :models) {
            if(table.getTableNumber()==number){
                return table;
            }
        }
        return null;
    }

    @Override
    public Collection<Table> getAll() {
        return Collections.unmodifiableCollection(this.models);
    }

    @Override
    public void add(Table table) {
        this.models.add(table);
    }
}
