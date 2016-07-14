package br.edu.ifpi.projetoeventos.models.enums;

public enum EProfileType {

    PARTICIPANT(1, "Participante"),
    ORGANIZER(2, "Organizador"),
    PANELIST(3, "Palestrante");

    private final int id;
    private final String name;

    private EProfileType(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public static EProfileType getById(int id) {
        for (EProfileType e : EProfileType.values())
        {
            if (id == e.getId()) return e;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return this.getName();
    }

}