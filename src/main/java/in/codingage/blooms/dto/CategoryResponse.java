package in.codingage.blooms.dto;

public class CategoryResponse {
    public static Object set;
    private String id;
    private String title;
    private String desc;
    private String cUrl;

    public CategoryResponse() {
    }

    public CategoryResponse(String id, String title, String desc, String cUrl) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.cUrl = cUrl;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getDesc() {

        return desc;
    }

    public void setDesc(String desc) {

        this.desc = desc;
    }

    public String getcUrl() {

        return cUrl;
    }

    public void setcUrl(String cUrl) {

        this.cUrl = cUrl;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }
}
