public class Main {
    public static void main(String[] args) throws Exception {
        String selection;
        if (args.length == 0) {
            System.out.print("Номер 1..50 или all: ");
            StringBuilder line = new StringBuilder();
            int value;
            while ((value = System.in.read()) != -1 && value != '\n') if (value != '\r') line.append((char)value);
            selection = line.toString().trim();
        } else selection = args[0];
        try {
            if (selection.equalsIgnoreCase("all")) for (int i = 1; i <= 50; i++) run(i);
            else {
                int number = Integer.parseInt(selection);
                if (number < 1 || number > 50) throw new NumberFormatException();
                run(number);
            }
        } catch (NumberFormatException e) {
            System.err.println("Введите номер от 1 до 50 или all."); System.exit(1);
        }
    }
    private static void run(int number) throws Exception {
        switch (number) {
            case 1: task01.Demo.main(new String[0]); break;
            case 2: task02.Demo.main(new String[0]); break;
            case 3: task03.Demo.main(new String[0]); break;
            case 4: task04.Demo.main(new String[0]); break;
            case 5: task05.Demo.main(new String[0]); break;
            case 6: task06.Demo.main(new String[0]); break;
            case 7: task07.Demo.main(new String[0]); break;
            case 8: task08.Demo.main(new String[0]); break;
            case 9: task09.Demo.main(new String[0]); break;
            case 10: task10.Demo.main(new String[0]); break;
            case 11: task11.Demo.main(new String[0]); break;
            case 12: task12.Demo.main(new String[0]); break;
            case 13: task13.Demo.main(new String[0]); break;
            case 14: task14.Demo.main(new String[0]); break;
            case 15: task15.Demo.main(new String[0]); break;
            case 16: task16.Demo.main(new String[0]); break;
            case 17: task17.Demo.main(new String[0]); break;
            case 18: task18.Demo.main(new String[0]); break;
            case 19: task19.Demo.main(new String[0]); break;
            case 20: task20.Demo.main(new String[0]); break;
            case 21: task21.Demo.main(new String[0]); break;
            case 22: task22.Demo.main(new String[0]); break;
            case 23: task23.Demo.main(new String[0]); break;
            case 24: task24.Demo.main(new String[0]); break;
            case 25: task25.Demo.main(new String[0]); break;
            case 26: task26.Demo.main(new String[0]); break;
            case 27: task27.Demo.main(new String[0]); break;
            case 28: task28.Demo.main(new String[0]); break;
            case 29: task29.Demo.main(new String[0]); break;
            case 30: task30.Demo.main(new String[0]); break;
            case 31: task31.Demo.main(new String[0]); break;
            case 32: task32.Demo.main(new String[0]); break;
            case 33: task33.Demo.main(new String[0]); break;
            case 34: task34.Demo.main(new String[0]); break;
            case 35: task35.Demo.main(new String[0]); break;
            case 36: task36.Demo.main(new String[0]); break;
            case 37: task37.Demo.main(new String[0]); break;
            case 38: task38.Demo.main(new String[0]); break;
            case 39: task39.Demo.main(new String[0]); break;
            case 40: task40.Demo.main(new String[0]); break;
            case 41: task41.Demo.main(new String[0]); break;
            case 42: task42.Demo.main(new String[0]); break;
            case 43: task43.Demo.main(new String[0]); break;
            case 44: task44.Demo.main(new String[0]); break;
            case 45: task45.Demo.main(new String[0]); break;
            case 46: task46.Demo.main(new String[0]); break;
            case 47: task47.Demo.main(new String[0]); break;
            case 48: task48.Demo.main(new String[0]); break;
            case 49: task49.Demo.main(new String[0]); break;
            case 50: task50.Demo.main(new String[0]); break;
            default: throw new IllegalArgumentException("Номер должен быть 1..50");
        }
    }
}
