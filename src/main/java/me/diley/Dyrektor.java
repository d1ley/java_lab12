package me.diley;

import java.math.BigDecimal;

public class Dyrektor extends Pracownik  {

    private BigDecimal ServiceAllowance;

    private String CardNumber;
    private BigDecimal CostLimit;
    public void setServiceAllowance(BigDecimal allowance){
        this.ServiceAllowance = allowance;
    }
    public BigDecimal getServiceAllowance(){
        return this.ServiceAllowance;
    }
    public void setCardNumber(String cardNumber){
        this.CardNumber = cardNumber;
    }
    public String getCardNumber(){
        return this.CardNumber;
    }
    public void setCostLimit(BigDecimal costLimit){
        this.CostLimit = costLimit;
    }

    public BigDecimal getCostLimit() {
        return this.CostLimit;
    }

    @Override
    public void setPosition() {
        this.Position = "Dyrektor";
    }

    @Override
    public String getPosition() {
        return this.Position;
    }
    Dyrektor(String pesel, String name, String lastName, BigDecimal salary,String phoneNumber,BigDecimal serviceAllowance, String cardNumber,BigDecimal costLimit){
        this.setPesel(pesel);
        this.setName(name);
        this.setLastName(lastName);
        this.setPosition();
        this.setSalary(salary);
        this.setPhoneNumber(phoneNumber);
        this.setServiceAllowance(serviceAllowance);
        this.setCardNumber(cardNumber);
        this.setCostLimit(costLimit);
    }
    @Override
    public void showEmployee(){
        System.out.println("__________________________________________");
        System.out.println("Indetyfikator PESEL          :      " + getPesel());
        System.out.println("Imię                         :      " + getName());
        System.out.println("Nazwisko                     :      " + getLastName());
        System.out.println("Stanowisko                   :      " + getPosition());
        System.out.println("Wynagrodzenie                :      " + getSalary());
        System.out.println("Telefon służbowy numer       :      " + getPhoneNumber());

        System.out.println("Dodatek slużbowy             :      " + getServiceAllowance());
        System.out.println("Karta sluzbowa numer         :      " + getCardNumber());
        System.out.println("Limit kosztów/miesiąc(zł)    :      " + getCostLimit());
        System.out.println("__________________________________________");

    }
}
