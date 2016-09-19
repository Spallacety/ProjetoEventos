package br.edu.ifpi.projetoeventos.models.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import br.edu.ifpi.projetoeventos.models.others.Mappable;

public class User extends Mappable {

    private String email;
    private String name;

    User(){
//        setID(UUID.randomUUID().toString());
    }

    User(String id) {
        setID(id);
    }

    public String getEmail() {
        return this.email;
    }

    public String getName() {
        return this.name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void fromMap(Map<String, Object> map) {
        if(map != null) {
            setID((String) map.get("ID"));
            setName((String) map.get("name"));
            setEmail((String) map.get("email"));
        }
    }

    @Override
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ID", getID());
        result.put("email", getEmail());
        result.put("name", getName());
        return result;
    }

    public static Map<String, Object> getListMap(List<User> list){
        if(list != null && !list.isEmpty()){
            Map<String, Object> hashMap = new HashMap<>();
            hashMap.put("size", list.size());
            for (int i = 0; i < list.size(); i++) {
                hashMap.put(String.valueOf(i), list.get(i).getID());
            }
            return hashMap;
        }
        return null;
    }
}