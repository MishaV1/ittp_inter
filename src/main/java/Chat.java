import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Chat {

    static class Companion extends Thread{

        Chat chat = new Chat();
        private List<String> text;

        private Scenario scenario;


        Companion(String name, List<String> text, Scenario scenario){
            super(name);
            this.text = text;
            this.scenario = scenario;
        }


        @Override
        public void run() {

            for(int i = 0; i < text.size(); i++){

                try {
                    scenario.write_in_console(super.getName(), text.get(i));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }


    static class Scenario{
        private List<String> queue;

        Scenario(List<String> queue){
            this.queue =  queue;
        }

        public synchronized void write_in_console(String name, String text) throws InterruptedException {
            while(!queue.get(0).equals(name)){
                wait();
            }

            System.out.println(name + ":" + text);
            queue.remove(0);
            notifyAll();
        }

    }




    public static void main(String[] args) throws InterruptedException {
        Map<String, List<String>> map = new HashMap<>();
        List<String> script = new ArrayList<>();


        try(FileReader reader = new FileReader("src/scene.txt")){
            Scanner in  = new Scanner(reader);
            while (in.hasNext()){
                String string = in.nextLine();
                String[] words = string.split(":");
                script.add(words[0]);

                if(map.containsKey(words[0])){
                    List<String> list = map.get(words[0]);
                    list.add(words[1]);
                    map.put(words[0], list);
                }else{
                    List<String> list = new ArrayList<>();
                    list.add(words[1]);
                    map.put(words[0], list);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Scenario scenario = new Scenario(script);

        for(String name : map.keySet()){
            Companion companion = new Companion(name, map.get(name), scenario);
            companion.start();
        }



    }
}
