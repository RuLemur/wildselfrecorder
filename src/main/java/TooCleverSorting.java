import java.util.*;

/**
 * Created by RuLemur on 25.01.2018 in 22:25.
 * TooCleverSorting
 */
public class TooCleverSorting {

    public static void main(String[] args) {
        String strForSort = "Папа у Васи силен в математике" +
                "Учится папа за Васю весь год" +
                "Где это видано где это слыхано" +
                "Папа решает а Вася сдает" +
                "" +
                "Мелом расчерчен асфальт на квадратики" +
                "Манечка с Танечкой прыгают тут" +
                "Где это видано где это слыхано" +
                "В классы играют а в класс не идут";
        //TODO: можно проверить, что в строке нет лишних символов...

        strForSort = strForSort.toLowerCase();

        List allWords = new ArrayList(Arrays.asList(strForSort.split(" ")));//разбиваем строку на массив слов.

        // Дальше у меня было 2 варианта. Отсортировать сразу все слова, а потом разбить на группы,
        // или сначала разбить на группы, а потом сортировать.
        // нужно потестить эффективность

        long startTimeOne = System.nanoTime();
        HashMap<Character, ArrayList<String>> map = firstSolution(allWords);
        long endTimeOne = (System.nanoTime() - startTimeOne);
        System.out.println("Первый способ отработал за: " + endTimeOne);

        long startTimeTwo = System.nanoTime();
        HashMap<Character, ArrayList<String>> map2 = secondSolution(allWords);
        long endTimeTwo = (System.nanoTime() - startTimeTwo);
        System.out.println("Второй способ отработал за: " + endTimeTwo);

        /*
            Первый способ отрабатывает раз в 100 дольше, но при этом он выглядит проще и логичнее... Такие дела
         */

        /*
            Теперь у нас есть мапа с правильно разложеными значениями и с неё можно делать что угодно :)
            Например вывести те наборы, где больше 1 слова
         */
        for (char key : map.keySet()) {
            ArrayList<String> arrayList = map.get(key);
            if (arrayList.size() > 1) {
                System.out.println(key + ":" + arrayList);
            }
        }
    }

    /*
        Варинт, в котором мы сделаем сначала разбитие на группы, а затем сортировка
     */
    private static HashMap<Character, ArrayList<String>> firstSolution(List<String> allWords) {
        HashMap<Character, ArrayList<String>> map = arrayToMap(allWords);

        Comparator stringComparator = (Comparator<String>) (s1, s2) -> {
            if (s1.length() < s2.length()) {
                return 1;
            } else if (s1.length() == s2.length()) {
                return s1.compareTo(s2);
            }
            return -1;

        };
        map.forEach((k, v) -> v.sort(stringComparator));

        return map;
    }

    /*
        Вариант второй, с другим порядком действий: сначала сортируем массив, потом уже раскладываем по мапе.

     */
    private static HashMap<Character, ArrayList<String>> secondSolution(List<String> allWords) {

        List<String> wordsArray = allWords;
        Collections.sort(wordsArray);
        Comparator stringComparator = (Comparator<String>) (s1, s2) -> {
            char c1 = s1.charAt(0);
            char c2 = s2.charAt(0);

            if (c1 == c2) {
                if (s1.length() < s2.length()) {
                    return 1;
                } else if (s1.length() == s2.length()) {
                    return s1.compareTo(s2);
                }
                return -1;
            }
            return 0;
        };
        wordsArray.sort(stringComparator);
        return arrayToMap(wordsArray);

    }

    /*
        Почему бы и не вынести одинаковый код в отдельный метод...
        Тут мы раскладываем слова, которые начинаются на одну букву в разные списки, которые лежат в одной мапеы
     */
    private static HashMap<Character, ArrayList<String>> arrayToMap(List<String> arrayWithWords) {
        HashMap<Character, ArrayList<String>> map = new HashMap<>();
        for (String str : arrayWithWords) {
            char firstLetter = str.charAt(0);
            map.computeIfAbsent(firstLetter, k -> new ArrayList<>());
            map.get(firstLetter).add(str);
        }
        return map;
    }

}
