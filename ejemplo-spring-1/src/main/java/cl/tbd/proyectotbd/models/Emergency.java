package cl.tbd.proyectotbd.models;

//import java.sql.Date;

public class Emergency {
    private Integer id;
    private String name;
    private String description;
    private String start_date;
    private String end_date;
    private Integer id_institution;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public Integer getId_institution() {
        return id_institution;
    }

    public void setId_institution(Integer id_institution) {
        this.id_institution = id_institution;
    } 
}