/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.thecodetasticfour.superherosightingsgroup.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author plainjane
 */
public class Location {
    private int locationId ;
    private String locationName;
    private String locationDescription;
    private String locationStreet;
    private String locationZipcode;
    private String locationCity;
    private String locationState;
    private String locationCountry;
    private BigDecimal latitude;
    private BigDecimal longitude;
    
    
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

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public String getLocationStreet() {
        return locationStreet;
    }

    public void setLocationStreet(String locationStreet) {
        this.locationStreet = locationStreet;
    }
    
    public String getLocationZipcode() {
        return locationZipcode;
    }

    public void setLocationZipcode(String locationZipcode) {
        this.locationZipcode = locationZipcode;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }

    public String getLocationState() {
        return locationState;
    }

    public void setLocationState(String locationState) {
        this.locationState = locationState;
    }

    public String getLocationCountry() {
        return locationCountry;
    }

    public void setLocationCountry(String locationCountry) {
        this.locationCountry = locationCountry;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.locationId;
        hash = 53 * hash + Objects.hashCode(this.locationName);
        hash = 53 * hash + Objects.hashCode(this.locationDescription);
        hash = 53 * hash + Objects.hashCode(this.locationStreet);
        hash = 53 * hash + Objects.hashCode(this.locationZipcode);
        hash = 53 * hash + Objects.hashCode(this.locationCity);
        hash = 53 * hash + Objects.hashCode(this.locationState);
        hash = 53 * hash + Objects.hashCode(this.locationCountry);
        hash = 53 * hash + Objects.hashCode(this.latitude);
        hash = 53 * hash + Objects.hashCode(this.longitude);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
        if (this.locationId != other.locationId) {
            return false;
        }
        if (!Objects.equals(this.locationName, other.locationName)) {
            return false;
        }
        if (!Objects.equals(this.locationDescription, other.locationDescription)) {
            return false;
        }
        if (!Objects.equals(this.locationStreet, other.locationStreet)) {
            return false;
        }
        if (!Objects.equals(this.locationZipcode, other.locationZipcode)) {
            return false;
        }
        if (!Objects.equals(this.locationCity, other.locationCity)) {
            return false;
        }
        if (!Objects.equals(this.locationState, other.locationState)) {
            return false;
        }
        if (!Objects.equals(this.locationCountry, other.locationCountry)) {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        return true;
    }
    


}
