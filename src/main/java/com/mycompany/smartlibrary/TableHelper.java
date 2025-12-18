package com.mycompany.smartlibrary;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class TableHelper {

    //===========================================================================================================================//

    public static void printTableHeader(String format, String... headers) {
        printBorder(format, BorderType.TOP);
        System.out.printf(format, (Object[]) headers);
        printBorder(format, BorderType.MIDDLE);
    }

    public static void printTableRow(String format, Object... columns) {
        System.out.printf(format, columns);
    }

    //===========================================================================================================================//

    public static void printBorder(String format, BorderType type) {
        List<Integer> widths = extractWidths(format);
        System.out.print(type.left);

        for (int i = 0; i < widths.size(); i++) {
            int totalLength = widths.get(i) + 2;
            System.out.print("─".repeat(totalLength));

            if (i < widths.size() - 1) {
                System.out.print(type.mid);
            }
        }

        System.out.println(type.right);
    }

    private static List<Integer> extractWidths(String format) {
        List<Integer> widths = new ArrayList<>();
        Matcher matcher = Constants.FORMAT_PATTERN.matcher(format);

        while (matcher.find()) {
            widths.add(Integer.parseInt(matcher.group(1)));
        }
        return widths;
    }

    //===========================================================================================================================//

}
