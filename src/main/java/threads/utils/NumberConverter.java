package threads.utils;

import threads.exception.NoNumberException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NumberConverter implements Runnable {
    public Thread converter;

    private Map<String, Integer> ONE_TO_NINE = new HashMap<>();
    private Map<String, Integer> THIRTEEN_TO_NINETEEN = new HashMap<>();
    private Map<String, Integer> DECADES = new HashMap<>();
    private Map<String, Integer> EXCLUDE = new HashMap<>();
    private Map<String, Integer> EXCLUDE_100_1000 = new HashMap<>();

    private String ERROR = "Введено некорректное число";

    public NumberConverter() {
        init();
        converter = new Thread(this, "numberConverter");
        converter.start();
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String number;

        while (true) {
            synchronized (Values.lock) {
                while (Values.allowToPrint) {
                    try {
                        Values.lock.wait();
                    } catch (InterruptedException e) {
                        e.getMessage();
                    }
                }
                number = scanner.nextLine();
                Values.allowToPrint = true;
                Values.lock.notify();
            }

            try {
                convert(number);
            } catch (NoNumberException e) {
                synchronized (Values.lock) {
                    System.out.println(e.getMessage());
                    Values.allowToPrint = false;
                    continue;
                }
            }
        }
    }

    private void init() {
        ONE_TO_NINE.put("one", 1);
        ONE_TO_NINE.put("two", 2);
        ONE_TO_NINE.put("three", 3);
        ONE_TO_NINE.put("four", 4);
        ONE_TO_NINE.put("five", 5);
        ONE_TO_NINE.put("six", 6);
        ONE_TO_NINE.put("seven", 7);
        ONE_TO_NINE.put("eight", 8);
        ONE_TO_NINE.put("nine", 9);

        THIRTEEN_TO_NINETEEN.put("thirteen", 13);
        THIRTEEN_TO_NINETEEN.put("fourteen", 14);
        THIRTEEN_TO_NINETEEN.put("fifteen", 15);
        THIRTEEN_TO_NINETEEN.put("sixteen", 16);
        THIRTEEN_TO_NINETEEN.put("seventeen", 17);
        THIRTEEN_TO_NINETEEN.put("eighteen", 18);
        THIRTEEN_TO_NINETEEN.put("nineteen", 19);

        DECADES.put("twenty", 20);
        DECADES.put("thirty", 30);
        DECADES.put("forty", 40);
        DECADES.put("fifty", 50);
        DECADES.put("sixty", 60);
        DECADES.put("seventy", 70);
        DECADES.put("eighty", 80);
        DECADES.put("ninety", 90);

        EXCLUDE.put("ten", 10);
        EXCLUDE.put("eleven", 11);
        EXCLUDE.put("twelve", 12);
        EXCLUDE_100_1000.put("hundred", 100);
        EXCLUDE_100_1000.put("thousand", 1000);
    }

    public int convert(String input) {
        String[] numbers = input.split(" ");
        int number;

        switch (numbers.length) {
            case 1:
                number = convertOne(numbers[0]);
                synchronized (Values.list) {
                    sort(number);
                }
                return number;
            case 2:
                number = convertTwo(numbers);
                synchronized (Values.list) {
                    sort(number);
                }
                return number;
            case 3:
                number = convertThree(numbers);
                synchronized (Values.list) {
                    sort(number);
                }
                return number;
            case 4:
                number = convertFour(numbers);
                synchronized (Values.list) {
                    sort(number);
                }
                return number;
            case 5:
                number = convertFive(numbers);
                synchronized (Values.list) {
                    sort(number);
                }
                return number;
            case 6:
                number = convertSix(numbers);
                synchronized (Values.list) {
                    sort(number);
                }
                return number;
            default:
                throw new NoNumberException(ERROR);
        }
    }

    private void sort(int number) {
        Values.list.add(number);
        Values.list.sort(Integer::compareTo);
    }

    private int convertOne(String number) throws NoNumberException {
        if (EXCLUDE.containsKey(number)) return EXCLUDE.get(number);
        if (EXCLUDE_100_1000.containsKey(number)) return EXCLUDE_100_1000.get(number);
        if (ONE_TO_NINE.containsKey(number)) return ONE_TO_NINE.get(number);
        if (THIRTEEN_TO_NINETEEN.containsKey(number)) return THIRTEEN_TO_NINETEEN.get(number);
        if (DECADES.containsKey(number)) return DECADES.get(number);

        throw new NoNumberException(ERROR);
    }

    private int convertTwo(String[] numbers) {
        if (DECADES.containsKey(numbers[0]) && ONE_TO_NINE.containsKey(numbers[1]))
            return DECADES.get(numbers[0]) + ONE_TO_NINE.get(numbers[1]);
        throw new NoNumberException(ERROR);
    }

    private int convertThree(String[] numbers) {
        try {
            int digit3 = DECADES.containsKey(numbers[2]) ? DECADES.get(numbers[2]) : ONE_TO_NINE.get(numbers[2]);
            return ONE_TO_NINE.get(numbers[0]) * EXCLUDE_100_1000.get(numbers[1]) + digit3;
        } catch (Exception e) {
            throw new NoNumberException(ERROR);
        }
    }

    private int convertFour(String[] numbers) {
        try {
            return ONE_TO_NINE.get(numbers[0]) * EXCLUDE_100_1000.get(numbers[1]) + DECADES.get(numbers[2]) +
                    ONE_TO_NINE.get(numbers[3]);
        } catch (Exception e) {
            throw new NoNumberException(ERROR);
        }
    }

    private int convertFive(String[] numbers) {
        try {
            int digit5 = DECADES.containsKey(numbers[4]) ? DECADES.get(numbers[4]) : ONE_TO_NINE.get(numbers[4]);
            return ONE_TO_NINE.get(numbers[0]) * EXCLUDE_100_1000.get("thousand") +
                    ONE_TO_NINE.get(numbers[2]) * EXCLUDE_100_1000.get("hundred") + digit5;
        } catch (Exception e) {
            throw new NoNumberException(ERROR);
        }
    }

    private int convertSix(String[] numbers) {
        try {
            return convertFive(numbers) + ONE_TO_NINE.get(numbers[5]);
        } catch (Exception e) {
            throw new NoNumberException(ERROR);
        }
    }
}
