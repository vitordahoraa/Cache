import java.util.ArrayList;
import java.util.Scanner;

public class CacheRun {


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
        while (true) {
            System.out.println("Digite os endereços a serem acessados da memória, separados por vírgula. Digite -1 pra sair");

            input = keyboard.nextLine();
            if(input.equals("-1"))
                break;
            for(Cache c : Caches) {
                System.out.println("\nNúmero de Associatividade é: "+c.getSets()+"\n");
                c.setAddress(input);
                c.executionProtocol();
                c.ImprimeMatriz();
            }
        }
        MemoryFile temp;
        for(Cache c : Caches){
                temp = new MemoryFile(c.returnDirtyBitsBlocksAndTags(),"\nCache com "+c.getSets()+" de associação");
                temp.imprimeMemoryFile();
        }
    }
}