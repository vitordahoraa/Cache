import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class CacheRun extends Exception{


    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        ArrayList<Cache> Caches = new ArrayList<>();

        int block = Cache.getCacheBlockNumFromUser(keyboard);

        for(int i = 1; i <= block;i++) {
            if(block%i==0){
                Caches.add(new Cache(block, i));
            }

        }

        keyboard.nextLine();
        String input;
        int textOrInput;


        System.out.println("Digite 1 para digitar os endereços e 2 para usar o arquivo texto");
        textOrInput=keyboard.nextInt();




        if(textOrInput==2){
            try {
                Scanner fileScanner = new Scanner(new File("src/data.txt"));
                String inputFile = fileScanner.nextLine();
                for (Cache c : Caches) {
                    System.out.println("\nNúmero de Associatividade é: " + c.getSets() + "\n");
                    c.setAddress(inputFile);
                    c.executionProtocol();
                    c.ImprimeMatriz();
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }


        }
        else {
            while (true) {
                System.out.println("Digite os endereços a serem acessados da memória, separados por vírgula. Digite -1 pra sair");

                input = keyboard.nextLine();
                if (input.equals("-1"))
                    break;
                for (Cache c : Caches) {
                    System.out.println("\nNúmero de Associatividade é: " + c.getSets() + "\n");
                    c.setAddress(input);
                    c.executionProtocol();
                    c.ImprimeMatriz();
                }
            }
        }
        MemoryFile temp;
        System.out.println("\n\nImprimindo valores salvos na memória\n\n");
        for(Cache c : Caches){
                temp = new MemoryFile(c.returnDirtyBitsBlocksAndTags(),"\nCache com "+c.getSets()+" de associação");
                temp.imprimeMemoryFile();
        }
    }
}