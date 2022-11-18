public enum ActivityType {
    Exit,
    ReadHeaders,
    Read,
    Create,
    Update;


    public static ActivityType ConvertIntToActivityType(int activity) {
        return switch (activity) {
            case 0 -> ActivityType.Exit;
            case 1 -> ActivityType.ReadHeaders;
            case 2 -> ActivityType.Read;
            case 3 -> ActivityType.Create;
            case 4 -> ActivityType.Update;
            default -> null;
        };
    }
}
