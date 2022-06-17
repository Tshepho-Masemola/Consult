package entitties;

public class Lists {
    private int listId;
    private String listName;

    public Lists() {
    }

    public Lists(int listId, String listName) {
        this.listId = listId;
        this.listName = listName;
    }

    public Lists(String listName) {
        this.listName = listName;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}
