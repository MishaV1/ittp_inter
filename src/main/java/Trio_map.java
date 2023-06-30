import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trio_map {


//  Задание №5
    class Noda{
        private long account;
        private String name;
        private double value;

        public long getAccount() {
            return account;
        }

        public void setAccount(long account) {
            this.account = account;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

    }

    private Map<Long, Noda> map_account;
    private Map<String, List<Noda>> map_name;
    private Map<Double, List<Noda>> map_value;


    Trio_map(){
        map_account = new HashMap<>();
        map_value = new HashMap<>();
        map_name = new HashMap<>();
    }

    private <T> void deleting_from_map_with_list(Map<T, List<Noda>> map, T key, Noda noda){
        if(map.get(key).size() == 1){
            map.remove(key);
        }else{
            List<Noda> list = map.get(key);
            list.remove(noda);
            map.remove(key);
        }
    }

    private <T> void adding_to_map_with_list(Map<T, List<Noda>> map, T key, Noda noda){
        if(map.get(key) == null){
            List<Noda> list = new ArrayList<>();
            list.add(noda);
            map.put(key, list);
        }else{
            List<Noda> list = map.get(key);
            list.add(noda);
            map.put(key, list);
        }
    }



//  Задание №1
    public void put_element(long account, String name, double value){
        Noda noda = new Noda();
        noda.setAccount(account);
        noda.setName(name);
        noda.setValue(value);

        map_account.put(noda.getAccount(), noda);

        adding_to_map_with_list(map_name, noda.getName(), noda);

        adding_to_map_with_list(map_value, noda.getValue(), noda);

    }

//  Задание №4
    public Noda get(long account){
        return map_account.get(account);
    }

    public List<Noda> get(String name){
        return map_name.get(name);
    }

    public List<Noda> get(double value){
        return map_value.get(value);
    }

//  Задание №3
    public void edit(long account, long new_account, String name, double value){
        Noda noda = get(account);

        map_account.remove(noda.getAccount());
        deleting_from_map_with_list(map_name, noda.getName(), noda);
        deleting_from_map_with_list(map_value, noda.getValue(), noda);


        noda.setAccount(new_account);
        noda.setName(name);
        noda.setValue(value);

        map_account.put(new_account, noda);
        adding_to_map_with_list(map_name, name, noda);
        adding_to_map_with_list(map_value, value, noda);


    }

    public void edit(long account, String name){
        get(account).setName(name);
    }

    public void edit(long account, double value){
        get(account).setValue(value);
    }

    public void edit(long account, long new_account){
        Noda noda = map_account.get(account);
        map_account.remove(noda.getAccount());

        noda.setAccount(new_account);

        map_account.put(new_account, noda);

    }
//  Задание №2
    public void delete(long account){
        Noda noda = map_account.get(account);


        map_account.remove(noda.getAccount());

        deleting_from_map_with_list(map_name, noda.getName(), noda);
        deleting_from_map_with_list(map_value, noda.getValue(), noda);

    }


}
