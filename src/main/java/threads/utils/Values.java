package threads.utils;

import java.util.ArrayList;
import java.util.List;

public class Values {
        public static volatile boolean allowToPrint = false;
        public static final Object lock = new Object();//для блокировки консоли
        public final static List<Integer> list = new ArrayList<>();
}
