package me.diley;

public class WriteRunnable implements  Runnable{

    private Pracownik employee;

    WriteRunnable(Pracownik employee){
        this.employee = employee;
    }
    @Override
    public void run() {
        try {
            Controller.saveEmployee(employee);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
