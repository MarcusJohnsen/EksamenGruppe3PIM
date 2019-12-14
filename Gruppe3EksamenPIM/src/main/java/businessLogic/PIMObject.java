package businessLogic;

public abstract class PIMObject implements Comparable<PIMObject> {

    protected int objectID;
    protected String objectTitle;
    protected String objectDescription;

    public PIMObject(int objectID, String objectTitle, String objectDescription) {
        this.objectID = objectID;
        this.objectTitle = objectTitle;
        this.objectDescription = objectDescription;
    }

    public int getObjectID() {
        return objectID;
    }

    public String getObjectTitle() {
        return objectTitle;
    }

    public String getObjectDescription() {
        return objectDescription;
    }

    @Override
    public int compareTo(PIMObject otherBundle) {
        int thisID = this.objectID;
        int otherID = otherBundle.objectID;
        return thisID - otherID;
    }

}
