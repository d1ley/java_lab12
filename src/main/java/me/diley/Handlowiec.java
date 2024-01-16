package me.diley;

import java.math.BigDecimal;

public class Handlowiec extends Pracownik {
    private BigDecimal Commission;
    private BigDecimal CommissionLimit;

    public void setCommission(BigDecimal commission){
        this.Commission = commission;
    }
    public BigDecimal getCommission(){
        return this.Commission;
    }
    public void setCommissionLimit(BigDecimal commissionLimit){
        this.CommissionLimit = commissionLimit;
    }
    public BigDecimal getCommissionLimit(){
        return this.CommissionLimit;
    }

    @Override
    public void setPosition() {
        this.Position = "Handlowiec";
    }

    @Override
    public String getPosition() {
        return this.Position;
    }

    @Override
    public void showEmployee() {
        System.out.println("__________________________________________");
        System.out.println("Indetyfikator PESEL          :      " + getPesel());
        System.out.println("Imię                         :      " + getName());
        System.out.println("Nazwisko                     :      " + getLastName());
        System.out.println("Stanowisko                   :      " + getPosition());
        System.out.println("Wynagrodzenie                :      " + getSalary());
        System.out.println("Telefon służbowy numer       :      " + getPhoneNumber());
        System.out.println("Prowizja %                   :      " + getCommission());
        System.out.println("Limit prowizji/miesiąc(zł)   :      " + getCommissionLimit());
        System.out.println("__________________________________________");


    }

    Handlowiec(String pesel, String name, String lastName, BigDecimal salary,String phoneNumber,BigDecimal commission,BigDecimal commissionLimit){
        this.setPesel(pesel);
        this.setName(name);
        this.setLastName(lastName);
        this.setPosition();
        this.setSalary(salary);
        this.setPhoneNumber(phoneNumber);
        this.setCommission(commission);
        this.setCommissionLimit(commissionLimit);
    }
}
