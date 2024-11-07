import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Tamanhos dos arrays de teste
        int[] tamanhos = {10, 100, 1000, 10000};

        // Vetores para guardar os valores de cada execução

        int numExecucoes = 30; // Número de vezes que cada algoritmo será executado

        // Estrutura para armazenar os tempos de execução
        Map<String, List<Long>> resultados = new LinkedHashMap<>();

        // Execução dos testes para cada tamanho e tipo de vetor
        for (int tamanho : tamanhos) {
            System.out.println("Testando com array de tamanho " + tamanho);

            // Geração dos arrays de teste
            Integer[] crescente = gerarArrayCrescente(tamanho);
            Integer[] decrescente = gerarArrayDecrescente(tamanho);
            Integer[] aleatorio = gerarArrayAleatorio(tamanho);
            Integer[] repetidos = gerarArrayRepetidos(tamanho);

            // Lista de algoritmos e seus métodos de execução
            List<Consumer<Integer[]>> algoritmos = Arrays.asList(
                    Ordenadores::bubbleSort,
                    Ordenadores::insertionSort,
                    Ordenadores::selectionSort,
                    Ordenadores::shellSort,
                    Ordenadores::heapSort,
                    Ordenadores::mergeSort
            );
            String[] nomesAlgoritmos = {
                    "Bubble Sort", "Insertion Sort", "Selection Sort",
                    "Shell Sort", "Heap Sort", "Merge Sort"
            };

            // Tipos de vetores para teste
            Map<String, Integer[]> vetoresTeste = Map.of(
                    "Crescente", crescente,
                    "Decrescente", decrescente,
                    "Aleatorio", aleatorio,
                    "Repetidos", repetidos
            );

            // Executar cada algoritmo para cada vetor e imprimir o resultado
            if(tamanho == 10) { //executa este bloco somente quanto o tamanho é 10
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("resultados_ordenacao.txt"))) {
                    // Executar cada algoritmo para cada vetor e gravar o resultado no arquivo
                    for (int i = 0; i < algoritmos.size(); i++) {
                        String nomeAlgoritmo = nomesAlgoritmos[i];
                        Consumer<Integer[]> algoritmo = algoritmos.get(i);

                        writer.write("\n--- Teste de " + nomeAlgoritmo + " ---\n");
                        for (String tipoVetor : vetoresTeste.keySet()) {
                            Integer[] vetorOriginal = vetoresTeste.get(tipoVetor);
                            writer.write("Tipo de vetor: " + tipoVetor + "\n");
                            writer.write("Vetor original: " + Arrays.toString(vetorOriginal) + "\n");

                            // Copia do vetor para não alterar o original
                            Integer[] vetorCopia = Arrays.copyOf(vetorOriginal, vetorOriginal.length);

                            // Executa o algoritmo de ordenação
                            algoritmo.accept(vetorCopia);
                            writer.write("Vetor ordenado: " + Arrays.toString(vetorCopia) + "\n\n");
                        }
                    }
                    System.out.println("Resultados gravados em 'resultados_ordenacao.txt'");
                } catch (IOException e) {
                    System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
                }
            }
            // Executar cada algoritmo para cada vetor 30 vezes
            for (int i = 0; i < algoritmos.size(); i++) {
                String nomeAlgoritmo = nomesAlgoritmos[i];
                Consumer<Integer[]> algoritmo = algoritmos.get(i);

                for (String tipoVetor : vetoresTeste.keySet()) {
                    Integer[] vetorOriginal = vetoresTeste.get(tipoVetor);
                    String chave = nomeAlgoritmo + " - " + tipoVetor;

                    // Lista para armazenar os tempos de execução
                    List<Long> temposExecucao = new ArrayList<>();

                    for (int exec = 0; exec < numExecucoes; exec++) {
                        Integer[] vetorCopia = Arrays.copyOf(vetorOriginal, vetorOriginal.length);
                        long tempoInicio = System.nanoTime();
                        algoritmo.accept(vetorCopia);
                        long tempoFim = System.nanoTime();
                        long tempoExecucao = tempoFim - tempoInicio;
                        temposExecucao.add(tempoExecucao);
                    }

                    // Armazenar os tempos no mapa de resultados
                    resultados.putIfAbsent(chave, new ArrayList<>());
                    resultados.get(chave).addAll(temposExecucao);
                }
            }
        }

        // Escrever os resultados em um arquivo CSV
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resultados.csv"))) {
            writer.write("Algoritmo,Tipo de Vetor,Execucao,Tempo (ns)\n");
            for (String chave : resultados.keySet()) {
                List<Long> tempos = resultados.get(chave);
                String[] partesChave = chave.split(" - ");
                String algoritmo = partesChave[0];
                String tipoVetor = partesChave[1];

                for (int i = 0; i < tempos.size(); i++) {
                    writer.write(algoritmo + "," + tipoVetor + "," + (i + 1) + "," + tempos.get(i) + "\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever o arquivo: " + e.getMessage());
        }

        System.out.println("Resultados salvos em 'resultados.csv'");
    }





        // Execução dos testes para cada tamanho
        /*for (int tamanho : tamanhos) {
            System.out.println("Testando com array de tamanho " + tamanho);

            // Geração dos arrays de teste
            Integer[] crescente = gerarArrayCrescente(tamanho);
            Integer[] decrescente = gerarArrayDecrescente(tamanho);
            Integer[] aleatorio = gerarArrayAleatorio(tamanho);
            Integer[] repetidos = gerarArrayRepetidos(tamanho);

            // Teste de cada algoritmo com cada array
            // Fazer aqui um for para executar 30 vezes cada teste
            //for(int i = 0; i < 30; i++){}
            testarAlgoritmo("Bubble Sort", aleatorio, Ordenadores::bubbleSort);
            testarAlgoritmo("Insertion Sort", aleatorio, Ordenadores::insertionSort);
            testarAlgoritmo("Selection Sort", aleatorio, Ordenadores::selectionSort);
            testarAlgoritmo("Shell Sort", aleatorio, Ordenadores::shellSort);
            testarAlgoritmo("Heap Sort", aleatorio, Ordenadores::heapSort);
            testarAlgoritmo("Merge Sort", aleatorio, Ordenadores::mergeSort);

            /*
            Integer[] arrayQuickSort = Arrays.copyOf(aleatorio, aleatorio.length);
            long tempoInicio = System.nanoTime();
            Ordenadores.quickSort(arrayQuickSort, 0, arrayQuickSort.length - 1);
            long tempoFim = System.nanoTime();
            System.out.println("Quick Sort: " + (tempoFim - tempoInicio) + " ns");


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

            Float[] arrayBucketSort = new Random().ints(tamanho, 0, 100).mapToObj(i -> i / 100.0f).toArray(Float[]::new);
            tempoInicio = System.nanoTime();
            Ordenadores.bucketSort(arrayBucketSort);
            tempoFim = System.nanoTime();
            System.out.println("Bucket Sort: " + (tempoFim - tempoInicio) + " ns");

            System.out.println();
        }*/

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

        // Definir uma proporção de elementos repetidos (ex: 50% repetidos)
        int numRepetidos = tamanho / 2;
        int valorRepetido = rand.nextInt(10); // Valor que será repetido em parte do array

        // Preencher metade do array com o valor repetido
        for (int i = 0; i < numRepetidos; i++) {
            array[i] = valorRepetido;
        }

        // Preencher o restante do array com valores aleatórios
        for (int i = numRepetidos; i < tamanho; i++) {
            array[i] = rand.nextInt(100); // Valores aleatórios, limite de 0 a 99 para diversidade
        }

        // Embaralhar o array para distribuir os elementos repetidos de forma aleatória
        List<Integer> arrayList = Arrays.asList(array);
        Collections.shuffle(arrayList);
        arrayList.toArray(array);

        return array;
    }
}
