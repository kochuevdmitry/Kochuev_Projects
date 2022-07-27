/*
Oracle Java 8
1 секунда	20Mb
Дан упорядоченный по неубыванию массив целых 32-разрядных чисел. Требуется удалить из него все повторения. 
Желательно получить решение, которое не считывает входной файл целиком в память, т.е., использует лишь константный объем памяти в процессе работы.
Формат ввода Первая строка входного файла содержит единственное число n, n ≤ 1000000.
На следующих n строк расположены числа — элементы массива, по одному на строку. Числа отсортированы по неубыванию.
Формат вывода Выходной файл должен содержать следующие в порядке возрастания уникальные элементы входного массива.
Пример 1 
Ввод	Вывод 
5        2
2        4  
4        8
8 
8 
8  
Пример 2 
Ввод	Вывод 
5        2
2        8
2 
2 
8 
8
*/
package ContestTest;
import java.io.*;

public class DeleteRepeats {
    static final String input = "input.txt";
    static final String output = "output.txt";
    static BufferedReader br;
    static BufferedWriter bw;

    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new FileReader(input));
        bw = new BufferedWriter(new FileWriter(output));
        int n = Integer.valueOf(br.readLine());
        String last = "";
        String curr = last;
        for (int i = 0; i < n; i++) {
            curr = br.readLine().intern();
            if (last != curr) {
                last = curr;
                bw.write(curr);
                bw.write('\n');
            }
        }
        br.close();
        bw.close();
    }


}