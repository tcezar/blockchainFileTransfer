package ru.tcezar.blockchain.forms;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    public static String getMembers() {
        List<String> list = new ArrayList();
        list.add("Лобинцев Олег");
        list.add("Батраков Михаил");
        list.add("Гвоздецкий Иван");
        list.add("Тухиков Андраник");
        list.add("Пак Владислав");


        String result = "";

        for (String cur : list) {
            result += cur + ";";
        }

        return result;

    }

}
