package ru.laba.appliances;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/** Обмен инвентарём с текстовыми файлами UTF-8 с разделителем ';'. */
public final class TextExchange {
    public void exportTo(Path file, Inventory inventory) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            writer.write("TYPE;NAME;CATEGORY;BRAND;MODEL;PURCHASE_DATE;WARRANTY_MONTHS;NOTE");
            writer.newLine();
            for (String category : inventory.categories()) {
                writeRow(writer, List.of("CATEGORY", category));
            }
            for (Appliance a : inventory.sorted(Inventory.SortBy.NAME)) {
                writeRow(writer, List.of("APPLIANCE", a.name(), a.category(), a.brand(), a.model(),
                        a.purchaseDate().toString(), Integer.toString(a.warrantyMonths()), a.note()));
            }
        }
    }

    public void importFrom(Path file, Inventory destination) throws IOException {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) continue;
                if (lineNumber == 1 && line.startsWith("TYPE;")) continue;
                try {
                    rows.add(parseRow(line));
                } catch (IllegalArgumentException e) {
                    throw new IOException("Строка " + lineNumber + ": " + e.getMessage(), e);
                }
            }
        }

        Inventory imported = new Inventory();
        for (String[] row : rows) {
            if (row.length == 2 && row[0].equalsIgnoreCase("CATEGORY")) {
                imported.addCategory(row[1]);
            } else if (row.length == 8 && row[0].equalsIgnoreCase("APPLIANCE")) {
                try {
                    if (!imported.hasCategory(row[2])) imported.addCategory(row[2]);
                    imported.add(new Appliance(row[1], row[2], row[3], row[4], LocalDate.parse(row[5]),
                            Integer.parseInt(row[6]), row[7]));
                } catch (DateTimeParseException | NumberFormatException e) {
                    throw new IOException("Некорректная дата или срок гарантии в записи о технике.", e);
                } catch (IllegalArgumentException e) {
                    throw new IOException(e.getMessage(), e);
                }
            } else {
                throw new IOException("Ожидалась запись CATEGORY или APPLIANCE.");
            }
        }

        for (String category : imported.categories()) {
            if (!destination.hasCategory(category)) destination.addCategory(category);
        }
        for (Appliance a : imported.all()) {
            String actualCategory = destination.categories().stream()
                    .filter(c -> c.equalsIgnoreCase(a.category())).findFirst().orElse(a.category());
            destination.add(new Appliance(a.name(), actualCategory, a.brand(), a.model(),
                    a.purchaseDate(), a.warrantyMonths(), a.note()));
        }
    }

    private void writeRow(BufferedWriter writer, List<String> fields) throws IOException {
        writer.write(fields.stream().map(this::escape).reduce((a, b) -> a + ";" + b).orElse(""));
        writer.newLine();
    }

    private String escape(String value) {
        if (value.contains(";") || value.contains("\"") || value.contains("\n") || value.contains("\r")) {
            return "\"" + value.replace("\"", "\"\"").replace("\r", " ").replace("\n", " ") + "\"";
        }
        return value;
    }

    private String[] parseRow(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean quoted = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (quoted) {
                if (c == '"' && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    field.append('"'); i++;
                } else if (c == '"') quoted = false;
                else field.append(c);
            } else if (c == ';') {
                fields.add(field.toString()); field.setLength(0);
            } else if (c == '"' && field.isEmpty()) {
                quoted = true;
            } else {
                field.append(c);
            }
        }
        if (quoted) throw new IllegalArgumentException("кавычки не закрыты.");
        fields.add(field.toString());
        return fields.toArray(String[]::new);
    }
}
