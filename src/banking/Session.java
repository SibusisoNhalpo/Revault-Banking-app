package banking;

public final class Session {
    private static String email;
    private static String name;
    private static String accountNumber;

    private Session() {}

    public static void setSession(String e, String n, String acc) {
        email = e == null ? null : e.trim();
        name = n;
        accountNumber = acc;
    }

    public static String getEmail() {
        return email;
    }

    public static String getName() {
        return name;
    }

    public static String getAccountNumber() {
        return accountNumber;
    }

    public static boolean isLoggedIn() {
        return email != null && !email.isEmpty();
    }

    public static void clear() {
        email = null;
        name = null;
        accountNumber = null;
    }
}



