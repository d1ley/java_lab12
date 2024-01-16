package me.diley;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Database {
    private HashMap<String,Pracownik> mainDatabase = new HashMap<String,Pracownik>();

    public  void setDataBase(HashMap<String,Pracownik> pracownikHashMap){
        this.mainDatabase = pracownikHashMap;
    }
    public HashMap<String,Pracownik> getMainDatabase(){
        return mainDatabase;
    }
    public boolean addEmployee(Pracownik employee){
        try{
            if(has(employee)){
                mainDatabase.put(employee.getPesel(), employee);
                return true;
            }
        }catch (Exception e){
            System.out.println("Nieprawidlowy pracownik");
        }
        return false;
    }
    public Pracownik removeEmployee(Pracownik employee){
        return mainDatabase.remove(employee.getPesel());
    }
    public Pracownik searchEmployee(String employeePesel){
        return mainDatabase.get(employeePesel);
    }
    public void showAllEmployeers(){
        AtomicInteger Qty = new AtomicInteger(1);
        mainDatabase.forEach((pesel, employee)->{
            employee.showEmployee();
            System.out.println("                                       [Pozycja: "+Qty+"/"+ mainDatabase.size()+"]");
            Qty.getAndIncrement();
            if(!Controller.getKeyNext()){
                return;
            }

        });
    }
    public int size(){
        return mainDatabase.size();
    }
    public boolean has(Pracownik employee){
        return !(mainDatabase.containsKey(employee.getPesel()));
    }

    public void createCopytemp(ObjectOutputStream out){
        mainDatabase.forEach((pesel, employee)->{
            try {
                out.writeObject(employee);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }
    public void clear(){
        mainDatabase.clear();
    }


}
