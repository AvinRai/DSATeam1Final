/**
 * CustomerInterface.java
 * @author DSA Team 1
 * Team 1 Final Project
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class UserInterface {
    public static void main(String[] args) {
        final int SIZE = 100;
        Scanner input = new Scanner(System.in);
        HashTable<Customer> customers = new HashTable<>(SIZE);
        HashTable<Employee> employees = new HashTable<>(SIZE);
//        CustomerUsernameComparator customerUsernameComparator = new CustomerUsernameComparator();
//        PriorityComparator priorityComparator = new PriorityComparator();
//        OrderIdComparator orderIdComparator = new OrderIdComparator();
        CpuNameComparator cpuNameComparator = new CpuNameComparator();
        CpuPriceComparator cpuPriceComparator = new CpuPriceComparator();
        BST<CPU> cpusByName = new BST<>();
        BST<CPU> cpusByPrice = new BST<>();
        System.out.println("Welcome to the CPU Store!");
        inputInfo(customers, employees, cpusByName, cpusByPrice, cpuNameComparator, cpuPriceComparator);
        User user = logIn(input, customers, employees);
        System.out.println(user);
        if (user instanceof Customer) {
            customerInterface(cpusByName, cpusByPrice, user, input);
        } else if (user instanceof Employee) {
            employeeInterface(cpusByName, cpusByPrice, user, input);
        }
    }

    /**
     * Interface for Employee Users
     *
     * @param cpusByName  BST of cpus sorted by name
     * @param cpusByPrice BST of cpus sorted by cpusByPrice
     * @param user        of current user
     * @param input       to read user input
     */
    private static void employeeInterface(BST<CPU> cpusByName, BST<CPU> cpusByPrice, User user, Scanner input) {
    }

    /**
     * Interface for Customer Users
     *
     * @param cpusByName  BST of cpus sorted by name
     * @param cpusByPrice BST of cpus sorted by price
     * @param user        of current user
     * @param input       to read user input
     */
    private static void customerInterface(BST<CPU> cpusByName, BST<CPU> cpusByPrice, User user, Scanner input) {
    }

    /**
     * Updates the CPU BSTs to include a new product
     *
     * @param newCPU the CPU to be added
     * @return if the product (CPU) was successfully added
     */
    public boolean addProduct(Employee emp, CPU newCPU, BST<CPU> cpusByName, BST<CPU> cpusByPrice,
                              CpuNameComparator cmpName, CpuPriceComparator cmpPrice) {
        if (!emp.getIsManager()) {
            System.out.println("Invalid request: Restricted to manager");
            return false;
        }
        cpusByName.insert(newCPU, cmpName);
        cpusByPrice.insert(newCPU, cmpPrice);
        return true;
    }

    /**
     * Updates an existing product's stock in the CPU BSTs
     *
     * @return if the product (CPU) was successfully updated
     */
    public boolean updateProductStock(Employee emp, CPU updateCPU, int updateStock, BST<CPU> cpusByName,
                                      BST<CPU> cpusByPrice, CpuNameComparator cmpName, CpuPriceComparator cmpPrice) {
        if (!emp.getIsManager()) {
            System.out.println("Invalid request: Restricted to manager");
            return false;
        }
        if (cpusByName.search(updateCPU, cmpName) == null) {
            System.out.println("Invalid request: The CPU Store does not carry this product");
            return false;
        }
        CPU tempCPU = cpusByName.search(updateCPU, cmpName);

        cpusByName.remove(updateCPU, cmpName);
        cpusByPrice.remove(updateCPU, cmpPrice);

        tempCPU.updateStock(updateStock); //need to add updateStock() method to CPU class, written below

        cpusByName.insert(tempCPU, cmpName);
        cpusByPrice.insert(tempCPU, cmpPrice);
        return true;
    }

    /**
     * Updates an existing product's stock in the CPU BSTs
     *
     * @return if the product (CPU) was successfully updated
     */
    public boolean updateProductPrice(Employee emp, CPU updateCPU, double updatePrice, BST<CPU> cpusByName,
                                      BST<CPU> cpusByPrice, CpuNameComparator cmpName, CpuPriceComparator cmpPrice) {
        if (!emp.getIsManager()) {
            System.out.println("Invalid request: Restricted to manager");
            return false;
        }
        if (cpusByName.search(updateCPU, cmpName) == null) {
            System.out.println("Invalid request: The CPU Store does not carry this product");
            return false;
        }
        CPU tempCPU = cpusByName.search(updateCPU, cmpName);

        cpusByName.remove(updateCPU, cmpName);
        cpusByPrice.remove(updateCPU, cmpPrice);

        tempCPU.updatePrice(updatePrice); //need to add setters to CPU class

        cpusByName.insert(tempCPU, cmpName);
        cpusByPrice.insert(tempCPU, cmpPrice);
        return true;
    }

    /**
     * Updates the CPU BSTs to remove a product
     *
     * @param removeCPU the CPU to be removed
     * @return if the product (CPU) was successfully removed
     */
    public boolean removeProduct(Employee emp, CPU removeCPU, BST<CPU> cpusByName, BST<CPU> cpusByPrice,
                                 CpuNameComparator cmpName, CpuPriceComparator cmpPrice) {
        if (!emp.getIsManager()) {
            System.out.println("Invalid request: Restricted to manager");
            return false;
        }
        if (cpusByName.search(removeCPU, cmpName) == null) {
            System.out.println("Invalid request: The CPU Store does not carry this product");
            return false;
        }
        cpusByName.remove(removeCPU, cmpName);
        cpusByPrice.remove(removeCPU, cmpPrice);
        return true;
    }

    /**
     * Prompt the user to login
     *
     * @param input to read user input
     */
    private static User logIn(Scanner input, HashTable<Customer> customers, HashTable<Employee> employees) {
        int choice;
        while (true) {
            System.out.println("\nLogin options: ");
            System.out.println("1. Login as a Customer");
            System.out.println("2. Create a new Customer account");
            System.out.println("3. Login as a Guest");
            System.out.println("4. Login as an Employee or Manager");
            System.out.print("\nPlease select an option: ");

            choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = input.nextLine();
                    System.out.print("Enter password: ");
                    String password = input.nextLine();

                    Customer customer = customers.get(new Customer(null, null, username, password));
                    if (customer != null) {
                        System.out.println("Login successful!");
                        return customer;
                    } else {
                        System.out.println("Invalid username or password.");
                        continue;
                    }

                case 2:
                    System.out.print("Enter first name: ");
                    String firstName = input.nextLine();
                    System.out.print("Enter last name: ");
                    String lastName = input.nextLine();
                    System.out.print("Enter username: ");
                    String newUsername = input.nextLine();
                    System.out.print("Enter password: ");
                    String newPassword = input.nextLine();

                    Customer newCustomer = new Customer(firstName, lastName, newUsername, newPassword);
                    customers.add(newCustomer);
                    System.out.println("New customer account created successfully!");
                    return newCustomer;

                case 3:
                    System.out.println("Logged in as a Guest.");
                    return new Customer("Guest", "abc123");

                case 4:
                    System.out.print("Enter username: ");
                    String employeeUsername = input.nextLine();
                    System.out.print("Enter password: ");
                    String employeePassword = input.nextLine();

                    Employee employee = employees.get(new Employee(null, null, employeeUsername, employeePassword));
                    if (employee != null) {
                        System.out.println("Login successful!");
                        return employee;
                    } else {
                        System.out.println("Invalid username or password.");
                        continue;
                    }

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    /**
     * Read from CPUs.txt and Users.txt
     *
     * @param customers hash table of customers
     * @param employees hash table of employees
     * @param cpusByName BST of cpus sorted by name
     * @param cpusByPrice BST of cpus sorted by price
     * @param cpuNameComparator compares CPUs by name
     * @param cpuPriceComparator compares CPUs by price
     */
    private static void inputInfo(HashTable<Customer> customers, HashTable<Employee> employees,
                                  BST<CPU> cpusByName, BST<CPU> cpusByPrice, CpuNameComparator cpuNameComparator, CpuPriceComparator cpuPriceComparator) {
        String firstName, lastName, username, password, cpuName, brand;
        int cores, threads, stock;
        double clockSpeed, price;
        try {
            File cpuFile = new File("CPUs.txt");
            File userFile = new File("users.txt");
            Scanner cpuReader = new Scanner(cpuFile);
            Scanner userReader = new Scanner(userFile);
            while (cpuReader.hasNextLine()) {
                cpuName = cpuReader.next();
                brand = cpuReader.next();
                clockSpeed = cpuReader.nextDouble();
                cores = cpuReader.nextInt();
                threads = cpuReader.nextInt();
                price = cpuReader.nextDouble();
                stock = cpuReader.nextInt();
                CPU cpu = new CPU(cpuName, brand, clockSpeed, cores, threads, price, stock);
                cpusByName.insert(cpu, cpuNameComparator);
                cpusByPrice.insert(cpu, cpuPriceComparator);
            }

            while (userReader.hasNextLine()) {
                String userType = userReader.next();
                firstName = userReader.next();
                lastName = userReader.next();
                username = userReader.next();
                password = userReader.next();

                if (userType.equals("C")) {
                    Customer customer = new Customer(firstName, lastName, username, password);
                    customers.add(customer);
                } else if (userType.equals("E")) {
                    Employee employee = new Employee(firstName, lastName, username, password);
                    employees.add(employee);
                } else if (userType.equals("M")) {
                    Employee employee = new Employee(firstName, lastName, username, password, true);
                    employees.add(employee);
                }
            }

            cpuReader.close();
            userReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Searches for a product depending on the key passed
     * @param cpusByName the cpus available
     * @param key the key provided. either the model name or the price
     * @param cpuNameComparator compares CPUs by name
     * @param cpuPriceComparator compares CPUs by price
     * @return the cpu searched for if it exists
     */
    private static CPU searchForProduct(BST<CPU> cpusByName, CPU key, CpuNameComparator cpuNameComparator, CpuPriceComparator cpuPriceComparator) {
        CPU findByName = cpusByName.search(key, cpuNameComparator);
        CPU findByValue = cpusByName.search(key, cpuPriceComparator);

        if (findByName != null) {
            return findByName;
        } else if (findByValue != null) {
            return findByValue;
        } else {
            return null;
        }

    }
}
