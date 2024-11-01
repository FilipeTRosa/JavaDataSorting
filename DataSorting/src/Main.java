import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Tamanhos dos arrays de teste
        int[] tamanhos = {10, 100, 1000, 10000};

        // Execução dos testes para cada tamanho
        for (int tamanho : tamanhos) {
            System.out.println("Testando com array de tamanho " + tamanho);

            // Geração dos arrays de teste
            Integer[] crescente = gerarArrayCrescente(tamanho);
            Integer[] decrescente = gerarArrayDecrescente(tamanho);
            Integer[] aleatorio = gerarArrayAleatorio(tamanho);
            Integer[] repetidos = gerarArrayRepetidos(tamanho);

            // Teste de cada algoritmo com cada array
            testarAlgoritmo("Bubble Sort", crescente, Ordenadores::bubbleSort);
            testarAlgoritmo("Insertion Sort", decrescente, Ordenadores::insertionSort);
            testarAlgoritmo("Selection Sort", aleatorio, Ordenadores::selectionSort);
            testarAlgoritmo("Shell Sort", repetidos, Ordenadores::shellSort);
            testarAlgoritmo("Heap Sort", crescente, Ordenadores::heapSort);
            testarAlgoritmo("Merge Sort", aleatorio, Ordenadores::mergeSort);

            Integer[] arrayQuickSort = Arrays.copyOf(aleatorio, aleatorio.length);
            long tempoInicio = System.nanoTime();
            Ordenadores.quickSort(arrayQuickSort, 0, arrayQuickSort.length - 1);
            long tempoFim = System.nanoTime();
            System.out.println("Quick Sort: " + (tempoFim - tempoInicio) + " ns");

            /*
            // Testes específicos para Counting, Radix e Bucket Sort
            int[] arrayCountingSort = Arrays.stream(aleatorio).mapToInt(Integer::intValue).toArray();
            tempoInicio = System.nanoTime();
            Ordenadores.countingSort(arrayCountingSort);
            tempoFim = System.nanoTime();
            System.out.println("Counting Sort: " + (tempoFim - tempoInicio) + " ns");

            int[] arrayRadixSort = Arrays.stream(aleatorio).mapToInt(Integer::intValue).toArray();
            tempoInicio = System.nanoTime();
            Ordenadores.radixSort(arrayRadixSort);
            tempoFim = System.nanoTime();
            System.out.println("Radix Sort: " + (tempoFim - tempoInicio) + " ns");

            float[] arrayBucketSort = new Random().ints(tamanho, 0, 100).mapToObj(i -> i / 100.0f).toArray(Float[]::new);
            tempoInicio = System.nanoTime();
            Ordenadores.bucketSort(arrayBucketSort);
            tempoFim = System.nanoTime();
            System.out.println("Bucket Sort: " + (tempoFim - tempoInicio) + " ns");
            */
            System.out.println();
        }
    }

    // Função para medir o tempo de execução de um algoritmo
    private static <T extends Comparable<T>> void testarAlgoritmo(String nome, T[] array, Consumer<T[]> algoritmo) {
        T[] arrayCopia = Arrays.copyOf(array, array.length);
        long tempoInicio = System.nanoTime();
        algoritmo.accept(arrayCopia);
        long tempoFim = System.nanoTime();
        System.out.println(nome + ": " + (tempoFim - tempoInicio) + " ns");
    }

    // Funções auxiliares para criar diferentes tipos de arrays
    private static Integer[] gerarArrayCrescente(int tamanho) {
        Integer[] array = new Integer[tamanho];
        for (int i = 0; i < tamanho; i++) array[i] = i;
        return array;
    }

    private static Integer[] gerarArrayDecrescente(int tamanho) {
        Integer[] array = new Integer[tamanho];
        for (int i = 0; i < tamanho; i++) array[i] = tamanho - i;
        return array;
    }

    private static Integer[] gerarArrayAleatorio(int tamanho) {
        Integer[] array = new Integer[tamanho];
        Random rand = new Random();
        for (int i = 0; i < tamanho; i++) array[i] = rand.nextInt(tamanho);
        return array;
    }

    private static Integer[] gerarArrayRepetidos(int tamanho) {
        Integer[] array = new Integer[tamanho];
        Random rand = new Random();
        int valorRepetido = rand.nextInt(tamanho);
        for (int i = 0; i < tamanho; i++) array[i] = valorRepetido;
        return array;
    }
}
