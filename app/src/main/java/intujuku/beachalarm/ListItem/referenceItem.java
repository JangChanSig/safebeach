package intujuku.beachalarm.ListItem;

/**
 * Created by p on 8/15/2017.
 */

public class referenceItem {
    int image;
    String title;
    String contents;
    String ref;

    int getImage() {
        return this.image;
    }
    String getTitle() {
        return this.title;
    }
    String getContents()
    {
        return this.contents;
    }
    String getRef() {
        return ref;
    }

    public referenceItem(String title, String contents, String ref, int image) {
        this.image = image;
        this.title = title;
        this.contents = contents;
        this.ref = ref;
    }
}
