package rbacksystem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class RBACKSystem {
    static Map<String, String> users = new HashMap<>();

    static Map<String, Set<String>> rolePermissions = new HashMap<>();

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        rolePermissions.put("Admin",
                new HashSet<>(Arrays.asList(
                        "Create", "View", "Update", "Delete")));

        rolePermissions.put("Manager",
                new HashSet<>(Arrays.asList(
                        "View", "Update")));

        rolePermissions.put("Customer",
                new HashSet<>(Arrays.asList(
                        "View")));

        while (true) {

            System.out.println("\n===== RBAC SYSTEM =====");
            System.out.println("1. Assign User to Role");
            System.out.println("2. Check User Permission");
            System.out.println("3. Display Users and Roles");
            System.out.println("4. Exit");

            System.out.print("Choose an option: ");
            String choice = input.nextLine();

            switch (choice) {

                case "1":
                    assignRole(input);
                    break;

                case "2":
                    checkPermission(input);
                    break;

                case "3":
                    displayUsers();
                    break;

                case "4":
                    System.out.println("Program terminated.");
                    return;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    static void assignRole(Scanner input) {

        System.out.print("Enter username: ");
        String username = input.nextLine();

        System.out.print("Enter role (Admin/Manager/Customer): ");
        String role = input.nextLine();

        String validRole = null;

        for (String r : rolePermissions.keySet()) {
            if (r.equalsIgnoreCase(role)) {
                validRole = r;
                break;
            }
        }

        if (validRole != null) {

            users.put(username, validRole);

            System.out.println("Role assigned successfully.");
            System.out.println("User: " + username);
            System.out.println("Role: " + validRole);

        } else {

            System.out.println("Invalid role.");
        }
    }

    static void checkPermission(Scanner input) {

        System.out.print("Enter username: ");
        String username = input.nextLine();

        if (!users.containsKey(username)) {
            System.out.println("User not found.");
            return;
        }

        System.out.print(
                "Enter operation (Create/View/Update/Delete): ");

        String operation = input.nextLine();

        String role = users.get(username);

        Set<String> permissions = rolePermissions.get(role);

        boolean allowed = false;

        for (String permission : permissions) {
            if (permission.equalsIgnoreCase(operation)) {
                allowed = true;
                break;
            }
        }

        System.out.println("\nUser: " + username);
        System.out.println("Role: " + role);
        System.out.println("Operation: " + operation);

        if (allowed) {
            System.out.println("ACCESS GRANTED.");
        } else {
            System.out.println("ACCESS DENIED.");
        }
    }

    static void displayUsers() {

        System.out.println("\n===== REGISTERED USERS =====");

        if (users.isEmpty()) {
            System.out.println("No users assigned.");
            return;
        }

        for (String username : users.keySet()) {

            String role = users.get(username);

            System.out.println(
                    "User: " + username +
                    " | Role: " + role +
                    " | Permissions: " + rolePermissions.get(role)
            );
        }
    }
}
