package se.rosscom.shopper.business.list.entity;

public class ListDetailPojo {

    public String item;
    public Long familyId;

    public ListDetailPojo() {
    }

    public ListDetailPojo(String item, Long familyId) {
        this.item = item;
        this.familyId = familyId;
    }
}
