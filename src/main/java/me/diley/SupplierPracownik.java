package me.diley;

import java.io.EOFException;
import java.util.function.Supplier;

public class SupplierPracownik implements Supplier<Pracownik> {

    public String  path;

    SupplierPracownik(String path){
        this.path = path;
    }
    @Override
    public Pracownik get() {

        return Controller.getEmployee(path);
    }
}
