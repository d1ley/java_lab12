package me.diley;

import java.io.File;
import java.util.Scanner;

public class Show {
    public static void showMenu(Database dataBase){
        int choose = 0;
        do{
            try{
                System.out.println("MENU");

                System.out.println("\t 1. Lista pracowników");
                System.out.println("\t 2. Dodaj pracownika");
                System.out.println("\t 3. Usuń pracownika");
                System.out.println("\t 4. Kopia zapasowa");
                System.out.println("\t 0. End program");
                System.out.print("Wybór -> ");
                Scanner scan  = new Scanner(System.in);
                choose = scan.nextInt();
                if(choose == 0) return;

                switch (choose){
                    case 1: {
                        System.out.println("Lista pracowników");
                        dataBase.showAllEmployeers();}break;
                    case 2: {
                        System.out.println("Dodanie Pracownika: ");

                        Pracownik temp = Controller.newEmployee();
                        if(dataBase.has(temp)){
                            temp.showEmployee();
                            System.out.println("Dodanie Pracownika: ");
                            if(Controller.getKey()){
                                dataBase.addEmployee(temp);
                                System.out.println("New Employee added");
                            }else{
                                System.out.println("New Employee not added");
                            }
                        }else{
                            System.out.println("Pracownik o podanym numerze PESEL już istnieje");
                        }
                    }break;
                    case 3:{
                        System.out.println("Usuniencie pracownika");
                        System.out.print("Wprowadz numer PESEL pracownika do usunięcia :");
                        Scanner scanner = new Scanner(System.in);
                        String tempPesel = scanner.nextLine();
                        Pracownik employee = dataBase.searchEmployee(tempPesel);
                        if(employee != null){
                            employee.showEmployee();
                            System.out.println("Usuniencie pracownika");
                            if(Controller.getKey()){
                                dataBase.removeEmployee(employee);
                                System.out.println("Employee removed Successfully");
                            }else{
                                System.out.println("Employee removed Aborted");
                            }
                        }
                    }break;
                    case 4: {
                        String choose3;

                        Scanner scanner  = new Scanner(System.in);
                        do{
                            System.out.print("[Z]achowaj/[O]dtwórz/[P]regląd       :        ");
                            choose3 = scanner.nextLine();
                        }while (!Controller.isValidSerializationChoose(choose3));
                        if(choose3.equalsIgnoreCase("Z")){
                            System.out.println("Dostepne backupy");
                            System.out.println("__________________________________________");
                            File backupNames = new File("./backups/");
                            for (File tempFile: backupNames.listFiles()
                            ) {
                                System.out.println("\t\t"+ tempFile.getName());
                            }
                            System.out.println("__________________________________________");
                            System.out.print("Nazwa backupu( bez .zip):\t");
                            scan.close();
                            Scanner scanner2  = new Scanner(System.in);
                            String backupName = scanner.nextLine();
                            Controller.saveAsyncEmployees(dataBase,backupName);

                        } else if (choose3.equalsIgnoreCase("O")) {
                            System.out.println("Wpisz nazwe backupu z ktorego chcesz odczytac dane");
                            System.out.println("__________________________________________");
                            File backupNames = new File("./backups/");
                            for (File tempFile: backupNames.listFiles()
                            ) {
                                System.out.println("\t\t"+ tempFile.getName());
                            }
                            System.out.println("__________________________________________");
                            System.out.print("Nazwa backupu( bez .zip):\t");
                            Scanner scanner2 = new Scanner(System.in);
                            String backupName = scanner2.nextLine();
                            Controller.getAsyncEmployees(dataBase,backupName);
                        }else if (choose3.equalsIgnoreCase("P")) {
                            System.out.println("Dostepne backupy");
                            System.out.println("__________________________________________");
                            File backupNames = new File("./backups/");
                            for (File tempFile: backupNames.listFiles()
                            ) {
                                System.out.println("\t\t"+ tempFile.getName());
                            }
                            System.out.println("__________________________________________");
                        }
                    }break;
                }
            }catch (Exception e){
                System.out.println("Nieprawidlowy wybór");
                e.printStackTrace();
            }
        }while (choose !=0);
    }
}
