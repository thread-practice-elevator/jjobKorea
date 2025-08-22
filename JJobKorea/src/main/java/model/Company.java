package model;

public class Company {
    private int id;
    private String name;
    private int locationId;
    private String locationName; // JOIN 시 사용
    
    public Company() {}
    
    public Company(int id, String name, int locationId) {
        this.id = id;
        this.name = name;
        this.locationId = locationId;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getLocationId() {
        return locationId;
    }
    
    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
    
    public String getLocationName() {
        return locationName;
    }
    
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
    
    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", locationId=" + locationId +
                ", locationName='" + locationName + '\'' +
                '}';
    }
}