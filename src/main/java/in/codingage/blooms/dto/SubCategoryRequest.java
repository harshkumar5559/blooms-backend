package in.codingage.blooms.dto;

public class SubCategoryRequest {
    private String name;
    private String id;
    private String title;
    private String desc;
    private String categoryId;

    public SubCategoryRequest(String name, String id, String title, String desc, String categoryId) {
        this.name = name;
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
