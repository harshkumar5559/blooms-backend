package in.codingage.blooms.dto;

public class CategoryRequest {
    private String title;//category ka naam ui se aata h
    private String id; //update ke time use hota h
    private String desc;//category ka description
    private String cUrl;//image url

    public String getId() {

        return id;
    }

    public CategoryRequest(String title, String desc, String cUrl) {
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
}
