import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;



public class Ordenadores {
    // BubbleSort
    public static <T extends Comparable<T>> void bubbleSort(T[] array) { //cormen
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j].compareTo(array[j + 1]) > 0) {
                    T temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    // InsertionSort
    public static <T extends Comparable<T>> void insertionSort(T[] array) { //cormen
        for (int i = 1; i < array.length; i++) {
            T chave = array[i];
            int j = i - 1;
            while (j >= 0 && array[j].compareTo(chave) > 0) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = chave;
        }
    }

    // SelectionSort
    public static <T extends Comparable<T>> void selectionSort(T[] array) { //ziviani
        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j].compareTo(array[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            T temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }
    }

    // ShellSort
    public static <T extends Comparable<T>> void shellSort(T[] array) {
        int n = array.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                T temp = array[i];
                int j;
                for (j = i; j >= gap && array[j - gap].compareTo(temp) > 0; j -= gap) {
                    array[j] = array[j - gap];
                }
                array[j] = temp;
            }
        }
    }

    // HeapSort
    public static <T extends Comparable<T>> void heapSort(T[] array) {
        int n = array.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }
        for (int i = n - 1; i > 0; i--) {
            T temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            heapify(array, i, 0);
        }
    }

    private static <T extends Comparable<T>> void heapify(T[] array, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        if (left < n && array[left].compareTo(array[largest]) > 0) {
            largest = left;
        }
        if (right < n && array[right].compareTo(array[largest]) > 0) {
            largest = right;
        }
        if (largest != i) {
            T swap = array[i];
            array[i] = array[largest];
            array[largest] = swap;
            heapify(array, n, largest);
        }
    }

    // MergeSort
    public static <T extends Comparable<T>> void mergeSort(T[] array) {
        if (array.length < 2) return;
        int mid = array.length / 2;
        T[] left = Arrays.copyOfRange(array, 0, mid);
        T[] right = Arrays.copyOfRange(array, mid, array.length);
        mergeSort(left);
        mergeSort(right);
        merge(array, left, right);
    }

    private static <T extends Comparable<T>> void merge(T[] array, T[] left, T[] right) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i].compareTo(right[j]) <= 0) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }
        while (i < left.length) array[k++] = left[i++];
        while (j < right.length) array[k++] = right[j++];
    }

    // QuickSort
    public static <T extends Comparable<T>> void quickSort(T[] array, int low, int high) {
        if (low < high) {
            int pi = partition(array, low, high);
            quickSort(array, low, pi - 1);
            quickSort(array, pi + 1, high);
        }
    }

    private static <T extends Comparable<T>> int partition(T[] array, int low, int high) {
        T pivot = array[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (array[j].compareTo(pivot) < 0) {
                i++;
                T temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        T temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return i + 1;
    }

//-------------------################# ################---------------------------

    // Counting Sort
    public static void countingSort(int[] array) {
        int max = Arrays.stream(array).max().orElse(Integer.MIN_VALUE);
        int min = Arrays.stream(array).min().orElse(Integer.MAX_VALUE);
        int range = max - min + 1;
        int[] count = new int[range];
        int[] output = new int[array.length];

        // Contagem das ocorrências
        for (int i = 0; i < array.length; i++) {
            count[array[i] - min]++;
        }

        // Atualização de índices
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        // Construção do array de saída
        for (int i = array.length - 1; i >= 0; i--) {
            output[count[array[i] - min] - 1] = array[i];
            count[array[i] - min]--;
        }

        // Cópia para o array original
        System.arraycopy(output, 0, array, 0, array.length);
    }

    // Radix Sort
    public static void radixSort(int[] array) {
        int max = Arrays.stream(array).max().orElse(Integer.MIN_VALUE);
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(array, exp);
        }
    }

    private static void countingSortByDigit(int[] array, int exp) {
        int[] output = new int[array.length];
        int[] count = new int[10];

        // Contagem das ocorrências com base no dígito
        for (int i = 0; i < array.length; i++) {
            int digit = (array[i] / exp) % 10;
            count[digit]++;
        }

        // Atualização de índices
        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        // Construção do array de saída
        for (int i = array.length - 1; i >= 0; i--) {
            int digit = (array[i] / exp) % 10;
            output[count[digit] - 1] = array[i];
            count[digit]--;
        }

        // Cópia para o array original
        System.arraycopy(output, 0, array, 0, array.length);
    }

    // Bucket Sort
    public static void bucketSort(Float[] array) {
        int n = array.length;
        if (n <= 0) return;

        // Criação dos buckets
        ArrayList<Float>[] buckets = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            buckets[i] = new ArrayList<>();
        }

        // Distribuição dos elementos nos buckets
        for (float value : array) {
            int bucketIndex = (int) (n * value);  // assumindo valores no intervalo [0, 1)
            buckets[bucketIndex].add(value);
        }

        // Ordenação individual dos buckets
        for (ArrayList<Float> bucket : buckets) {
            Collections.sort(bucket); //chamada para ordenação
        }

        // União dos elementos dos buckets no array original
        int index = 0;
        for (ArrayList<Float> bucket : buckets) {
            for (float value : bucket) {
                array[index++] = value;
            }
        }
    }
}
