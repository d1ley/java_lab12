package me.diley;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.*;


public class Controller {
    public static Pracownik newEmployee() {
        String pesel;
        String name;
        String lastName;
        BigDecimal salary;
        String phoneNumber;
        Pracownik temp = null;

        System.out.println("__________________________________________");

        String position;

        Scanner scan = new Scanner(System.in);
        do {
            System.out.print("[D]yrektor/[H]andlowiec      :        ");
            position = scan.nextLine();
        } while (!isValidPosition(position));
        System.out.println("__________________________________________");
        System.out.print("Indetyfikator PESEL          :      ");
        pesel = scan.nextLine();
        while (!checkPesel(pesel)) {
            System.out.println("Invalid PESEL");
            System.out.print("Indetyfikator PESEL          :      ");
            pesel = scan.nextLine();
        }
        ;
        System.out.print("Imię                         :      ");
        name = scan.nextLine();
        System.out.print("Nazwisko                     :      ");
        lastName = scan.nextLine();
        System.out.print("Wynagrodzenie                :      ");
        salary = getBigDecimalFromUser();
        System.out.print("Telefon służbowy numer       :      ");
        phoneNumber = scan.nextLine();
        if (phoneNumber.equals("")) {
            phoneNumber = "-brak-";
        }

        if (position.equals("D")) {
            BigDecimal serviceAllowance;
            String cardNumber;
            BigDecimal costLimit;
            System.out.print("Dodatek slużbowy             :      ");
            serviceAllowance = getBigDecimalFromUser();
            System.out.print("Karta sluzbowa numer         :      ");
            cardNumber = scan.nextLine();
            if (cardNumber.equals("")) {
                cardNumber = "-brak-";
            }
            System.out.print("Limit kosztów/miesiąc(zł)    :      ");
            costLimit = getBigDecimalFromUser();
            temp = new Dyrektor(pesel, name, lastName, salary, phoneNumber, serviceAllowance, cardNumber, costLimit);

        } else if (position.equals("H")) {
            BigDecimal commission;
            BigDecimal commissionLimit;
            System.out.print("Prowizja %                   :      ");
            commission = getBigDecimalFromUser();
            System.out.print("Limit prowizji/miesiąc(zł)   :      ");
            commissionLimit = getBigDecimalFromUser();
            temp = new Handlowiec(pesel, name, lastName, salary, phoneNumber, commission, commissionLimit);
        }

        return temp;

    }

    public static boolean isValidPosition(String choose) {
        return choose.equals("D") || choose.equals("H");
    }

    public static boolean isValidSerializationChoose(String choose) {
        return choose.equalsIgnoreCase("Z") || choose.equalsIgnoreCase("O") || choose.equalsIgnoreCase("P");
    }


    public static boolean getKey() {
        String choose = "";
        do {
            System.out.println("[Enter] - Podtwierdz \n [Q] Porzuc");
            Scanner scan = new Scanner(System.in);
            choose = scan.nextLine();
            if (choose.equals("")) {
                return true;
            } else if (choose.equals("Q") || choose.equals("q")) {
                return false;
            } else {
                System.out.println("Nierozpoznawalny wybor");
            }
        } while (true);
    }

    public static boolean getKeyNext() {
        String choose = "";
        do {
            System.out.println("[Enter] - Next employee \n [Q] Quit");
            Scanner scan = new Scanner(System.in);
            choose = scan.nextLine();
            if (choose.equals("")) {
                return true;
            } else if (choose.equals("Q") || choose.equals("q")) {
                return false;
            } else {
                System.out.println("Nierozpoznawalny wybor");
            }
        } while (true);
    }

    public static boolean checkPesel(String pesel) {
        if (pesel != null && pesel.length() == 11) {
            for (char c : pesel.toCharArray()) {
                if (!Character.isDigit(c)) {
                    return false;
                }

            }
            return checkPeselF(pesel);
        }
        return false;
    }

    private static boolean checkPeselF(String pesel) {
        return ((Integer.parseInt(String.valueOf(pesel.charAt(0)))) + 3 * (Integer.parseInt(String.valueOf(pesel.charAt(1)))) + 7 * (Integer.parseInt(String.valueOf(pesel.charAt(2)))) +
                9 * (Integer.parseInt(String.valueOf(pesel.charAt(3)))) + (Integer.parseInt(String.valueOf(pesel.charAt(4)))) + 3 * (Integer.parseInt(String.valueOf(pesel.charAt(5)))) +
                7 * (Integer.parseInt(String.valueOf(pesel.charAt(6)))) + 9 * (Integer.parseInt(String.valueOf(pesel.charAt(7)))) + (Integer.parseInt(String.valueOf(pesel.charAt(8)))) +
                3 * (Integer.parseInt(String.valueOf(pesel.charAt(9)))) + (Integer.parseInt(String.valueOf(pesel.charAt(10))))) % 10 == 0;
    }

    private static BigDecimal getBigDecimalFromUser() {
        Scanner scan = new Scanner(System.in);
        BigDecimal result;
        while (true) {
            try {
                result = new BigDecimal(scan.nextLine());
                return result;
            } catch (Exception e) {
                System.out.println("The value must be a Number");
                System.out.print("                             :      ");
            }
        }
    }


    public static boolean zipDirectory(String sourceDir, String zipFile) throws IOException {
        Path sourcePath = Paths.get(sourceDir);
        try (FileOutputStream fos = new FileOutputStream(zipFile);
             CheckedOutputStream checksum = new CheckedOutputStream(fos, new Adler32());
             ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(checksum))) {

            Files.walk(sourcePath)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry entry = new ZipEntry(sourcePath.relativize(path).toString());
                        try {
                            zos.putNextEntry(entry);
                            Files.copy(path, zos);
                            zos.closeEntry();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            return true;
        }

    }


    public static boolean unzipDirectory(String zipFile, String destDir) throws IOException {
        Path destPath = Paths.get(destDir);

        try (FileInputStream fis = new FileInputStream(zipFile);
             CheckedInputStream checksum = new CheckedInputStream(fis, new Adler32());
             ZipInputStream zis = new ZipInputStream(new BufferedInputStream(checksum))) {

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                Path entryPath = destPath.resolve(entry.getName());

                if (entry.isDirectory()) {
                    Files.createDirectories(entryPath);
                } else {
                    Files.createDirectories(entryPath.getParent());
                    Files.copy(zis, entryPath, StandardCopyOption.REPLACE_EXISTING);
                }

                zis.closeEntry();
            }
            return true;
        }
    }

    public static boolean saveEmployee(Pracownik employee) throws Exception {
        try {

            FileOutputStream fileOut = new FileOutputStream("./data/employees/" + employee.getPesel() + ".txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(employee);

            out.close();
            fileOut.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static Pracownik getEmployee(String path) {
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileInputStream);
            Object obj = null;
            obj = in.readObject();


            in.close();
            fileInputStream.close();
            return (Pracownik) obj;

        } catch (Exception e) {
            System.out.println("Nie udalo sie odczytac pracownika");
        }

        return null;
    }

    public static void saveAsyncEmployees(Database dataBase, String backupName) throws Exception {
        try {

            ExecutorService executorService = Executors.newFixedThreadPool(10);
            ArrayList<CompletableFuture<Void>> completableFutures = new ArrayList<>();
            dataBase.getMainDatabase().forEach((pesel, employee) -> {
                completableFutures.add(CompletableFuture.runAsync(new WriteRunnable(employee), executorService));
            });
            for (CompletableFuture<Void> cFut : completableFutures
            ) {
                try {
                    cFut.get();
                } catch (ExecutionException e) {
                    System.out.println("Interrupted exception: " + e);
                } catch (InterruptedException e) {
                    System.out.println("Execution exception: " + e);
                }
            }

            File outdir = new File("./data/employees/");
            zipDirectory(outdir.getPath(), "./backups/" + backupName + ".zip");
            System.out.println("Backup o nazwie: " + backupName + " zostal stworzony");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            File outdir = new File("./data/employees/");
            for (File tempfile : outdir.listFiles()
            ) {
                tempfile.delete();
            }
        }

    }


    public static void getAsyncEmployees(Database dataBase, String backupName) throws IOException {
        try {
            List<Pracownik> employees = new ArrayList<Pracownik>();

            try {
                unzipDirectory("./backups/" + backupName + ".zip", "./data/employees/");
            } catch (FileNotFoundException e) {
                System.out.println("Nieprawidlowa nazwa backupu!");
                throw new FileNotFoundException();

            } catch (Exception e) {
                System.out.println("Nie mozna odczytac ten backup");
                throw new Exception();
            }
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            ArrayList<CompletableFuture<Pracownik>> completableFutures = new ArrayList<>();

            File employeesData = new File("./data/employees/");

            for (File employeeFile : employeesData.listFiles()
            ) {
                completableFutures.add(CompletableFuture.supplyAsync(new SupplierPracownik(employeeFile.getPath()), executorService));
            }

            for (CompletableFuture<Pracownik> cFut : completableFutures
            ) {
                try {
                    Pracownik pracownik = cFut.get();
                    if (pracownik != null) {
                        employees.add(pracownik);
                    }

                } catch (ExecutionException e) {
                    System.out.println("Interrupted exception: " + e);
                    throw new Exception();
                } catch (InterruptedException e) {
                    System.out.println("Execution exception: " + e);
                    throw new Exception();
                }
            }
            if (!employees.isEmpty()) {
                dataBase.clear();
            }
            for (Pracownik pracownik : employees
            ) {
                dataBase.addEmployee(pracownik);
            }
            System.out.println("Odczytano Asynchronicznie");

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            File outdir = new File("./data/employees/");
            for (File tempfile : outdir.listFiles()
            ) {
                tempfile.delete();
            }
        }

    }
}




