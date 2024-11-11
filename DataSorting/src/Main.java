import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Tamanhos dos arrays para teste
        int[] tamanhos = {10, 100, 1000, 10000};
        int numExecucoes = 30;

        // Nomes dos algoritmos de ordenação
        String[] nomesAlgoritmos = {"Bubble Sort", "Insertion Sort", "Selection Sort","Shell Sort", "Heap Sort", "Merge Sort"};

        // Criar o arquivo CSV para os resultados
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resultados.csv"))) {
            // Cabeçalho do arquivo CSV
            writer.write("Algoritmo,Tipo de Vetor,Tamanho,Tempo (ns)\n");

            // Execução dos testes para cada tamanho de array
            for (int tamanho : tamanhos) {
                System.out.println("Testando com array de tamanho " + tamanho);

                // Geração dos arrays de teste
                Integer[] crescente = gerarArrayCrescente(tamanho);
                Integer[] decrescente = gerarArrayDecrescente(tamanho);
                Integer[] aleatorio = gerarArrayAleatorio(tamanho);
                Integer[] repetidos = gerarArrayRepetidos(tamanho);



                // ########### TERMINA TESTE DE CORRETUDE ################ //
                if (tamanho == 10) { // Executa este bloco somente quando o tamanho é 10
                    try (BufferedWriter writerTeste = new BufferedWriter(new FileWriter("resultados_ordenacao.txt"))) {
                        // Nome dos algoritmos e os métodos correspondentes
                        String[] nomesAlgoritmosTeste = {
                                "Bubble Sort", "Insertion Sort", "Selection Sort",
                                "Shell Sort", "Heap Sort", "Merge Sort"
                        };


                        // Mapa de vetores de teste
                        Map<String, Integer[]> vetoresTeste = Map.of(
                                "Crescente", crescente,
                                "Decrescente", decrescente,
                                "Aleatorio", aleatorio,
                                "Repetidos", repetidos
                        );

                        // Executar cada algoritmo para cada vetor e gravar o resultado no arquivo
                        for (String nomeAlgoritmoTeste : nomesAlgoritmos) {
                            writerTeste.write("\n--- Teste de " + nomeAlgoritmoTeste + " ---\n");

                            for (String tipoVetor : vetoresTeste.keySet()) {
                                Integer[] vetorOriginal = vetoresTeste.get(tipoVetor);
                                writerTeste.write("Tipo de vetor: " + tipoVetor + "\n");
                                writerTeste.write("Vetor original: " + Arrays.toString(vetorOriginal) + "\n");

                                // Copia do vetor para não alterar o original
                                Integer[] vetorCopia = Arrays.copyOf(vetorOriginal, vetorOriginal.length);

                                // Executa o algoritmo de ordenação
                                if (nomeAlgoritmoTeste.equals("Bubble Sort")) {
                                    Ordenadores.bubbleSort(vetorCopia);
                                } else if (nomeAlgoritmoTeste.equals("Insertion Sort")) {
                                    Ordenadores.insertionSort(vetorCopia);
                                } else if (nomeAlgoritmoTeste.equals("Selection Sort")) {
                                    Ordenadores.selectionSort(vetorCopia);
                                } else if (nomeAlgoritmoTeste.equals("Shell Sort")) {
                                    Ordenadores.shellSort(vetorCopia);
                                } else if (nomeAlgoritmoTeste.equals("Heap Sort")) {
                                    Ordenadores.heapSort(vetorCopia);
                                } else if (nomeAlgoritmoTeste.equals("Merge Sort")) {
                                    Ordenadores.mergeSort(vetorCopia);
                                }

                                // Grava o vetor ordenado no arquivo
                                writerTeste.write("Vetor ordenado: " + Arrays.toString(vetorCopia) + "\n\n");
                            }
                        }
                        System.out.println("Resultados gravados em 'resultados_ordenacao.txt'");
                    } catch (IOException e) {
                        System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
                    }
                }
                // ########### TERMINA TESTE DE CORRETUDE ################ //

                // Testar cada algoritmo de ordenação
                for (String algoritmo : nomesAlgoritmos) {
                    for (int exec = 0; exec < numExecucoes; exec++) {
                        // Testar com arrays de diferentes tipos
                        testarERegistrar(writer, algoritmo, "Crescente", Arrays.copyOf(crescente, crescente.length));
                        testarERegistrar(writer, algoritmo, "Decrescente", Arrays.copyOf(decrescente, decrescente.length));
                        testarERegistrar(writer, algoritmo, "Aleatorio", Arrays.copyOf(aleatorio, aleatorio.length));
                        testarERegistrar(writer, algoritmo, "Repetido", Arrays.copyOf(repetidos, repetidos.length));

                    }
                }
            }

            System.out.println("Resultados salvos em 'resultados.csv'");
        } catch (IOException e) {
            System.err.println("Erro ao escrever o arquivo: " + e.getMessage());
        }
    }

    // Função para medir o tempo de execução e registrar no CSV
    private static void testarERegistrar(BufferedWriter writer, String algoritmo, String tipoVetor, Integer[] array) {
        long tempoInicio = System.nanoTime();

        // Executar o algoritmo de ordenação
        if (algoritmo.equals("Bubble Sort")) {
            Ordenadores.bubbleSort(array);
        } else if (algoritmo.equals("Insertion Sort")) {
            Ordenadores.insertionSort(array);
        } else if (algoritmo.equals("Selection Sort")) {
            Ordenadores.selectionSort(array);
        } else if (algoritmo.equals("Shell Sort")) {
            Ordenadores.shellSort(array);
        } else if (algoritmo.equals("Heap Sort")) {
            Ordenadores.heapSort(array);
        } else if (algoritmo.equals("Merge Sort")) {
            Ordenadores.mergeSort(array);
        }

        long tempoFim = System.nanoTime();
        long tempoExecucao = tempoFim - tempoInicio;

        // Escrever o resultado no arquivo CSV
        try {
            writer.write(algoritmo + "," + tipoVetor + "," + array.length + "," + (tempoExecucao) + "\n");
        } catch (IOException e) {
            System.err.println("Erro ao registrar o resultado: " + e.getMessage());
        }
    }


    // Função para gerar array crescente
    private static Integer[] gerarArrayCrescente(int tamanho) {
        Integer[] array = new Integer[tamanho];
        for (int i = 0; i < tamanho; i++) {
            array[i] = i;
        }
        return array;
    }

    // Função para gerar array decrescente
    private static Integer[] gerarArrayDecrescente(int tamanho) {
        Integer[] array = new Integer[tamanho];
        for (int i = 0; i < tamanho; i++) {
            array[i] = tamanho - i;
        }
        return array;
    }

    // Função para gerar array aleatório
    private static Integer[] gerarArrayAleatorio(int tamanho) {
        Integer[] array = new Integer[tamanho];
        Random rand = new Random();
        for (int i = 0; i < tamanho; i++) {
            array[i] = rand.nextInt(tamanho);
        }
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
