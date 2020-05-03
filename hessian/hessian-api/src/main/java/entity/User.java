package entity;

import java.io.Serializable;

/**
 * @author Jigubigu
 * @version 1.0
 * @date 2020/5/3 13:45
 */
public class User implements Serializable {
    private Long id;
    private String name;
    private Integer agr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAgr() {
        return agr;
    }

    public void setAgr(Integer agr) {
        this.agr = agr;
    }
}
